package com.sc.adminht.common;

import com.sc.adminht.entity.system.UserInfo;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class Sds {

    /**
     * @param args
     */
    public static void main(String[] args) {
        UserInfo p = new UserInfo();

        reflect(p);
    }

    public static void reflect(Object obj) {
        if (obj == null) {
            return;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        String[] types1 = {"int", "java.lang.String", "boolean", "char", "float", "double", "long", "short", "byte"};
        String[] types2 = {"Integer", "java.lang.String", "java.lang.Boolean", "java.lang.Character",
                "java.lang.Float", "java.lang.Double", "java.lang.Long", "java.lang.Short", "java.lang.Byte"};
        Map<String,Object> map = new LinkedHashMap<>();
        for (int j = 0; j < fields.length; j++) {
            fields[j].setAccessible(true);
            // 字段名
            map.put(fields[j].getName(),11);

            // 字段值
            /*for (int i = 0; i < types1.length; i++) {
                if (fields[j].getType().getName()
                        .equalsIgnoreCase(types1[i]) || fields[j].getType().getName().equalsIgnoreCase(types2[i])) {
                    try {
                        System.out.print(fields[j].get(obj) + "     ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }*/
        }System.out.println(map);
    }
}

