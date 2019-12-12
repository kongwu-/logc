package cc.leevi.common.logc.layout.pattern;

public class Node {

    public static final int LITERAL = 0;

    public static final int KEYWORD = 1;


    public Node(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public Node() {
    }

    protected int type;

    protected String value;

    protected Node next;

    protected Converter converter;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }
}
