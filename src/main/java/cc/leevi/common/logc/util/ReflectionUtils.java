package cc.leevi.common.logc.util;


import cc.leevi.common.logc.ConfigException;

import java.lang.reflect.Field;

/**
 * 简易反射工具类，演示代码，不做缓存
 */
public class ReflectionUtils {

    public static void setFiled(Object instance,String fieldName,String fieldValue){
        Class<?> clazz = instance.getClass();
        try{
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance,fieldValue);
        }catch (ReflectiveOperationException e){
            throw new ConfigException(e);
        }
    }

    public static Object newInstance(Class<?> clazz){
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new ConfigException(e);
        } catch (IllegalAccessException e) {
            throw new ConfigException(e);
        }
    }

}
