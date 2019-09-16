package com.syndlig.generator.generator;

import java.io.File;

public class OrchardGenerator extends Generator {
    public OrchardGenerator() {
        dir = "orchard";
        propertiesFile = "orchard.properties";
    }

    protected void makeFile(File file, String orchardName, String directory) {
        String tree = toBeGenerated.getProperty(orchardName);
        orchardName = orchardName.toLowerCase();
        String filePath = buildPath(directory, file.getName().replace("NAME", orchardName));
        if (filePath.contains("#")) {
            for (int i = 0; i <= 3; i++) {
                write(createJson(file, orchardName, tree, String.valueOf(i)), filePath.replace("#", String.valueOf(i)));
            }
        } else {
            write(createJson(file, orchardName, tree), filePath);
        }
    }
}