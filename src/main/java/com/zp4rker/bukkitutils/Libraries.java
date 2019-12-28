package com.zp4rker.bukkitutils;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
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

    public static File downloadFromCentral(String groupId, String artifactId, String version) throws IOException {
        String link = String.format("https://repo1.maven.org/maven2/%s/%s/%s/%s-%s.jar", groupId.replace(".", "/"), artifactId, version, artifactId, version);
        return downloadLibrary(link, artifactId + "-" + version + ".jar");
    }

    private static File downloadLibrary(String link, String name) throws IOException {
        URL url = new URL(link);
        File file = new File("./libs/" + name);

        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        if (file.exists()) return file;
        else file.createNewFile();

        byte[] bytes = ByteStreams.toByteArray(url.openStream());
        Files.write(file.toPath(), bytes);

        return file;
    }

}
