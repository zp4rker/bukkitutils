package com.zp4rker.bukkitutils;

import java.io.File;
import java.io.IOException;

public class KotlinRuntime {

    public static boolean loaded() {
        try {
            Class.forName("kotlin.Unit");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static File[] loadLibraries(String version) throws IOException {
        if (librariesPresent(version)) return new File[] {new File("./libs/kotlin-stdlib-" + version + ".jar"), new File("./libs/kotlin-stdlib-jdk8-" + version + ".jar")};
        String groupId = "org.jetbrains.kotlin";
        return new File[] {Libraries.downloadFromCentral(groupId, "kotlin-stdlib", version), Libraries.downloadFromCentral(groupId, "kotlin-stdlib-jdk8", version)};
    }

    private static boolean librariesPresent(String version) {
        File core = new File("./libs/kotlin-stdlib-" + version + ".jar");
        File jdk8 = new File("./libs/kotlin-stdlib-jdk8-" + version + ".jar");

        return core.exists() && jdk8.exists();
    }

}
