package com.zp4rker.bukkitutils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class Libraries {

    public static void addLibrary(File file) throws NoSuchMethodException, MalformedURLException, InvocationTargetException, IllegalAccessException {
        URLClassLoader cl = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        method.setAccessible(true);
        method.invoke(cl, file.toURI().toURL());
    }

    public static void addLibraries(File... files) {
        Arrays.stream(files).forEach(lib -> {
            try {
                addLibrary(lib);
            } catch (MalformedURLException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                System.out.println("FAILED TO ADD LIBRARIES TO CLASSPATH");
            }
        });
    }

}
