package com.syndlig.generator.generator;

import java.io.File;
import java.util.Optional;

public class CropGenerator extends Generator {
    public CropGenerator() {
        dir = "crops";
        propertiesFile = "crops.properties";
    }

    protected void makeFile(File file, String cropName, String directory) {
        int height = Optional.of(Integer.parseInt(toBeGenerated.getProperty(cropName))).orElse(1);
        if (height == 1 && file.getName().contains("top")) return;

        cropName = cropName.toLowerCase().replace(' ', '_');
        String filePath = buildPath(directory, file.getName().replace("NAME", cropName));
        if (filePath.contains("#")) {
            for (int i = 1; i <= 7; i++) {
                write(createJson(file, cropName, String.valueOf(i)), filePath.replace("#", String.valueOf(i)));
            }
        } else {
            write(createJson(file, cropName), filePath);
        }
    }
}