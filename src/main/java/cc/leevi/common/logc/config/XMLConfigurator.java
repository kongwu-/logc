package cc.leevi.common.logc.config;

import cc.leevi.common.logc.Level;
import cc.leevi.common.logc.LifeCycle;
import cc.leevi.common.logc.LogcLogger;
import cc.leevi.common.logc.LoggerContext;
import cc.leevi.common.logc.appender.Appender;
import cc.leevi.common.logc.appender.AppenderAttachableImpl;
import cc.leevi.common.logc.appender.AppenderBase;
import cc.leevi.common.logc.filter.Filter;
import cc.leevi.common.logc.layout.Layout;
import cc.leevi.common.logc.util.ReflectionUtils;
import cc.leevi.common.logc.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLConfigurator implements Configurator{

    private final URL url;

    private final LoggerContext loggerContext;

    static final String APPENDER_TAG = "appender";

    static final String LOGGER_TAG = "logger";

    static final String CLASS_ATTR = "class";

    static final String NAME_ATTR = "name";

    static final String VALUE_ATTR = "value";

    static final String LEVEL_ATTR = "level";

    static final String FILTER_ATTR = "filter";

    static final String LAYOUT_TAG = "layout";

    static final String ENCODING_TAG = "encoding";

    static final String PARAM_TAG = "param";

    static final String ROOT_TAG = "root";

    static final String APPENDER_REF_TAG = "appender-ref";

    static final String APPENDER_REF_ATTR = "ref";

    private Map<String,Appender> appenderCache = new HashMap<>();

    public XMLConfigurator(URL url, LoggerContext loggerContext) {
        this.url = url;
        this.loggerContext = loggerContext;
    }

    @Override
    public void doConfigure() {
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(url.openStream());
            parse(document.getDocumentElement());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void parse(Element document) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        parseLoggers(document);
        parseRoot(document);
    }

    private void parseLoggers(Element document) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        NodeList loggerNodeList = document.getElementsByTagName(LOGGER_TAG);

        for (int i = 0; i < loggerNodeList.getLength(); i++) {
            LogcLogger logger = parseChildrenOfLoggerElement((Element) loggerNodeList.item(i));
            loggerContext.addLogger(logger);
        }
    }

    private void parseRoot(Element document) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Element rootEle = getFirstElementByTagName(document,ROOT_TAG);

        LogcLogger root = parseChildrenOfLoggerElement(rootEle);
        loggerContext.setRoot(root);
    }

    private LogcLogger parseChildrenOfLoggerElement(Element element) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        LogcLogger logger = new LogcLogger();

        String name = element.getAttribute(NAME_ATTR);
        logger.setName(name);

        String level = element.getAttribute(LEVEL_ATTR);
        if(!StringUtils.isEmpty(level)){
            logger.setLevel(Level.parse(level));
        }

        AppenderAttachableImpl aai = new AppenderAttachableImpl();
        logger.setAai(aai);

        NodeList appenderRefNodeList = element.getElementsByTagName(APPENDER_REF_TAG);
        int refLength = appenderRefNodeList.getLength();
        for (int i = 0; i < refLength; i++) {
            Element appenderRefNode = (Element) appenderRefNodeList.item(i);
            String appenderName = appenderRefNode.getAttribute(APPENDER_REF_ATTR);
            Appender appender = findAppenderByName(element.getOwnerDocument(), appenderName);
            aai.addAppender(appender);
        }
        return logger;
    }

    protected Appender findAppenderByName(Document document, String appenderName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Appender appender = appenderCache.get(appenderName);
        if(appender != null){
            return appender;
        }
        NodeList appenderNodeList = document.getElementsByTagName(APPENDER_TAG);
        for (int i = 0; i < appenderNodeList.getLength(); i++) {
            Element appenderEle = (Element) appenderNodeList.item(i);
            String itemAppenderName = appenderEle.getAttribute(NAME_ATTR);

            if(appenderName.equals(itemAppenderName)){
                String itemAppenderClassName = appenderEle.getAttribute(CLASS_ATTR);

                appender = (Appender) Class.forName(itemAppenderClassName).newInstance();
                appender.setName(itemAppenderName);

                Element layoutEle = getFirstElementByTagName(appenderEle, LAYOUT_TAG);
                Layout layout = parseLayout(layoutEle);
                ((AppenderBase)appender).setLayout(layout);
                startComponent(layout);
                Element encodingELe = getFirstElementByTagName(appenderEle, ENCODING_TAG);
                if(encodingELe!=null){
                    String encoding = parseEncoding(encodingELe);
                    ((AppenderBase)appender).setEncoding(encoding);
                }
                NodeList filterNodeList = appenderEle.getElementsByTagName(FILTER_ATTR);
                if(filterNodeList.getLength()>0){
                    List<Filter> filterList = parseFilter(filterNodeList);
                    ((AppenderBase)appender).setFilterList(filterList);
                    for (Filter filter : filterList) {
                        startComponent(filter);
                    }
                }
                startComponent(appender);
                return appender;
            }
        }
        return null;
    }

    private String parseEncoding(Element encodingEle){
        return encodingEle.getNodeValue();
    }

    private Element getFirstElementByTagName(Element ele,String tagName){
        NodeList elements = ele.getElementsByTagName(tagName);
        if(elements.getLength()>0){
            return (Element) elements.item(0);
        }
        return null;
    }

    protected List<Filter> parseFilter(NodeList filterNodeList) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        List<Filter> filterList = new ArrayList<>(filterNodeList.getLength());

        for (int j = 0; j < filterNodeList.getLength(); j++) {
            Element filterEle = (Element) filterNodeList.item(0);
            String filterClassName = filterEle.getAttribute(CLASS_ATTR);
            Filter filter = (Filter) Class.forName(filterClassName).newInstance();
            NodeList paramNodeList = filterEle.getElementsByTagName(PARAM_TAG);
            for (int k = 0; k < paramNodeList.getLength(); k++) {
                Element paramEle = (Element) paramNodeList.item(k);
                ReflectionUtils.setFiled(filter,paramEle.getAttribute(NAME_ATTR),paramEle.getAttribute(VALUE_ATTR));
            }
            filterList.add(filter);
        }
        return filterList;
    }

    protected Layout parseLayout(Element layoutEle) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String className = layoutEle.getAttribute(CLASS_ATTR);
        Object instance 	= Class.forName(className).newInstance();
        Layout layout   	= (Layout)instance;

        NodeList params 	= layoutEle.getChildNodes();
        final int length 	= params.getLength();

        for (int loop = 0; loop < length; loop++) {
            Node currentNode = params.item(loop);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element currentElement = (Element) currentNode;
                String tagName = currentElement.getTagName();
                if(tagName.equals(PARAM_TAG)) {
                    String name = currentElement.getAttribute(NAME_ATTR);
                    String value = currentElement.getAttribute(VALUE_ATTR);
                    ReflectionUtils.setFiled(layout,name,value);
                }
            }
        }
        return layout;
    }

    private void startComponent(LifeCycle component){
        component.start();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
