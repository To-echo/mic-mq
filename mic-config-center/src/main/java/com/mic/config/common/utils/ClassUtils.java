package com.mic.config.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tianp
 **/
public class ClassUtils {
    private static final String CLASS_PATH = ClassUtils.class.getResource("/").toString().replace("file:/", "");
    private static Set<Class<?>> set = new HashSet<>();

    static {
        scanClass(CLASS_PATH, set);
    }

    public static <T> Collection<T> getClassCollection(Class<T> type) {
        ArrayList list = new ArrayList<>();
        set.forEach(e -> {
            for (Class clazz : e.getInterfaces()) {
                if (clazz.equals(type)) {
                    try {
                        list.add(e.newInstance());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        return list;
    }

    private static void scanClass(String dirPath, Set<Class<?>> set) {
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            return;
        }
        File[] files = file.listFiles(pathname -> pathname.isDirectory() || pathname.getName().endsWith("class"));
        if (files == null || files.length == 0) {
            return;
        }
        for (File f : files) {

            if (f.isDirectory()) {
                scanClass(dirPath + "/" + f.getName(), set);
                continue;
            }
            String path = f.getPath();
            //去掉 .class，只留下类名
            String className = path.substring(CLASS_PATH.length(), path.length() - 6).replace("\\", ".");
            try {
                Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
                set.add(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
