package com.zp4rker.bukkitutils;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Files;

public class KotlinRuntime {

    public static boolean loaded() {
        try {
            Class.forName("kotlin.Unit");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static File[] loadLibraries(String version) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String coreUrl = "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/$version/kotlin-stdlib-$version.jar".replace("$version", version);
        String jdk8Url = "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk8/$version/kotlin-stdlib-jdk8-$version.jar".replace("$version", version);

        return new File[] {downloadLibrary(coreUrl, "kotlin-stdlib-" + version + ".jar"), downloadLibrary(jdk8Url, "kotlin-stdlib-jdk8-" + version + ".jar")};
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
