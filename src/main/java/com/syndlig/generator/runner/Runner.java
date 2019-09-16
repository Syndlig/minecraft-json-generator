package com.syndlig.generator.runner;

import com.syndlig.generator.generator.CropGenerator;
import com.syndlig.generator.generator.OrchardGenerator;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class Runner {
    public void run(ApplicationArguments args) {
        new CropGenerator().generate();
        new OrchardGenerator().generate();
    }
}