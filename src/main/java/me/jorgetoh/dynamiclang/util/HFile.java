package me.jorgetoh.dynamiclang.util;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class HFile {
    private final File file;
    private FileConfiguration config;

    public HFile(File file, FileConfiguration config) {
        this.file = file;
        this.config = config;
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void setConfig(FileConfiguration config) {
        this.config = config;
    }
}
