package cn.edu.cqu.monitorplatform.utils;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @ClassName ClassUtils
 * @Description TODO
 * @Author AlbertoWang
 * @Date 23:50
 **/
public class ClassUtils {
    // 遍历类名与变量
    public static final HashMap<String, Object> classTraversal(Object object) throws IllegalAccessException {
        HashMap<String, Object> variableMap = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String variableName = fields[i].getName();
            boolean accessFlag = fields[i].isAccessible();
            fields[i].setAccessible(true);
            Object variableObject = fields[i].get(object);
            variableMap.put(variableName, variableObject);
            fields[i].setAccessible(accessFlag);
        }
        return variableMap;
    }
}
