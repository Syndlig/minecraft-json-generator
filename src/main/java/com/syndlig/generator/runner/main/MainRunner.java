package com.syndlig.generator.runner.main;

import com.syndlig.generator.runner.Runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@ComponentScan("com.syndlig.generator")
@SpringBootApplication
public class MainRunner implements ApplicationRunner {
    public static final String OUTPUT_LOCATION = "E:\\MinecraftModding\\mc_out_cornucopia\\";

    private static final Logger logger = LoggerFactory.getLogger(MainRunner.class);

    public static void main(String[] args) {
        SpringApplication.run(MainRunner.class, args);
    }

    @Autowired
    private Runner runner = new Runner();

    @Override
    public void run(ApplicationArguments args) {
        logger.debug("Application started with command-line arguments: {}", Arrays.asList(args.getSourceArgs()));
        if (args.getSourceArgs().length > 0) {
            logger.debug("NonOptionArgs: {}", args.getNonOptionArgs());
            logger.debug("OptionNames: {}", args.getOptionNames());
            for (String name : args.getOptionNames()) logger.debug("arg --{} = {}", name, args.getOptionValues(name));
        }
        runner.run(args);
    }
}