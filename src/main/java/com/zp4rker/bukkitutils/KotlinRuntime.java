package com.zp4rker.bukkitutils;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class KotlinRuntime {

    public static void setup(JavaPlugin plugin, String kotlinVersion) throws IOException {
        if (loaded()) return;

        plugin.getLogger().info("Kotlin runtime not found! Fetching libraries...");
        File[] libs = loadLibraries(kotlinVersion);
        plugin.getLogger().info("Successfully fetched libraries! Loading Kotlin runtime...");
        Libraries.addLibraries(libs);

        if (loaded()) plugin.getLogger().info("Successfully loaded Kotlin runtime!");
    }

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
