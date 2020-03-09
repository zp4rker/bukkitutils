package com.zp4rker.bukkitutils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SpigotSettings extends YamlConfiguration {

    private SpigotSettings(File file) throws IOException, InvalidConfigurationException {
        super();
        load(file);
    }

    public static SpigotSettings getInstance() {
        try {
            return new SpigotSettings(new File("spigot.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            System.out.println("Unable to access or read spigot.yml!");
            return null;
        }
    }

}
