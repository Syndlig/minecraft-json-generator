package com.syndlig.generator.generator;

import com.syndlig.generator.runner.main.MainRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public abstract class Generator {
    Properties toBeGenerated = new Properties();
    String dir;
    String propertiesFile;

    private static Logger logger = LoggerFactory.getLogger(Generator.class);

    public void generate() {
        if (load()) {
            File resourceFolder = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(dir)).getPath());
            toBeGenerated.stringPropertyNames().forEach(prop -> {
                traverse(resourceFolder, buildPath(), prop);
            });
        }
    }

    private boolean load() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(Paths.get(dir, propertiesFile).toString())) {
            toBeGenerated.load(input);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            return false;
        }
        return true;
    }

    private void traverse(File directory, String path, String name) {
        for (File file : directory.listFiles()) {
            if (!file.getName().contains(".properties")) {
                if (file.isDirectory()) {
                    traverse(file, buildPath(path, file.getName()), name);
                } else {
                    makeFile(file, name, path);
                }
            }
        }
    }

    private String buildPath() {
        return Paths.get(MainRunner.OUTPUT_LOCATION, dir).toString();
    }

    String buildPath(String parent, String child) {
        return Paths.get(parent, child).toString();
    }

    protected abstract void makeFile(File file, String name, String directory);

    @SuppressWarnings("ResultOfMethodCallIgnored")
    void write(String output, String path) {
        try {
            new File(path).getParentFile().mkdirs();
            new File(path).createNewFile();
            PrintWriter writer = new PrintWriter(path);
            writer.print(output);
            writer.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }

    String createJson(File json, String... values) {
        try {
            String input = new String(Files.readAllBytes(Paths.get(json.getPath())));
            return String.format(input, values);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return null;
    }
}