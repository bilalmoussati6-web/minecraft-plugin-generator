package com.minecraft.generator;

import com.minecraft.generator.model.PluginConfig;
import com.minecraft.generator.service.PluginGeneratorService;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Minecraft Plugin Generator - Main entry point
 * Generates complete Minecraft Bukkit/Spigot/Paper plugins
 */
public class PluginGenerator {

    private static final Logger logger = LoggerFactory.getLogger(PluginGenerator.class);

    public static void main(String[] args) {
        try {
            Options options = createOptions();
            CommandLineParser parser = new DefaultParser();
            HelpFormatter formatter = new HelpFormatter();

            try {
                CommandLine cmd = parser.parse(options, args);

                if (cmd.hasOption("help")) {
                    formatter.printHelp(
                        "java -jar plugin-generator.jar [OPTIONS]",
                        "\nMinecraft Plugin Generator v1.0.0\n\nOPTIONS:",
                        options,
                        "\nExamples:\n" +
                        "  java -jar plugin-generator.jar -name MyPlugin -type command\n" +
                        "  java -jar plugin-generator.jar -name EventPlugin -type event -author 'Your Name'\n" +
                        "  java -jar plugin-generator.jar -name FullPlugin -type full -version 2.0.0"
                    );
                    return;
                }

                // Validate required parameters
                if (!cmd.hasOption("name") || !cmd.hasOption("type")) {
                    logger.error("Error: -name and -type parameters are required!");
                    formatter.printHelp(
                        "java -jar plugin-generator.jar [OPTIONS]",
                        options
                    );
                    System.exit(1);
                }

                // Build plugin configuration
                PluginConfig config = PluginConfig.builder()
                    .name(cmd.getOptionValue("name"))
                    .type(cmd.getOptionValue("type"))
                    .author(cmd.getOptionValue("author", "You"))
                    .description(cmd.getOptionValue("desc", "A custom Minecraft plugin"))
                    .version(cmd.getOptionValue("version", "1.0.0"))
                    .outputPath(cmd.getOptionValue("output", "./generated-plugins"))
                    .build();

                logger.info("Starting plugin generation...");
                logger.info("Plugin Name: {}", config.getName());
                logger.info("Plugin Type: {}", config.getType());
                logger.info("Author: {}", config.getAuthor());
                logger.info("Version: {}", config.getVersion());

                // Generate plugin
                PluginGeneratorService generatorService = new PluginGeneratorService();
                String pluginPath = generatorService.generatePlugin(config);

                logger.info("✓ Plugin successfully generated!");
                logger.info("Location: {}", pluginPath);
                System.out.println("\n✓ Your plugin is ready at: " + pluginPath);
                System.out.println("\nNext steps:");
                System.out.println("1. cd " + pluginPath);
                System.out.println("2. mvn clean package");
                System.out.println("3. Copy the JAR to your server plugins folder");

            } catch (ParseException e) {
                logger.error("Error parsing command line arguments: {}", e.getMessage());
                formatter.printHelp("plugin-generator", options);
                System.exit(1);
            }

        } catch (Exception e) {
            logger.error("Fatal error: {}", e.getMessage(), e);
            System.exit(1);
        }
    }

    /**
     * Create CLI options
     */
    private static Options createOptions() {
        Options options = new Options();

        options.addOption(Option.builder("n")
            .longOpt("name")
            .hasArg()
            .argName("name")
            .desc("Plugin name (required)")
            .required(false)
            .build());

        options.addOption(Option.builder("t")
            .longOpt("type")
            .hasArg()
            .argName("type")
            .desc("Plugin type: command, event, item, database, config, listener, full (required)")
            .required(false)
            .build());

        options.addOption(Option.builder("a")
            .longOpt("author")
            .hasArg()
            .argName("author")
            .desc("Plugin author (default: You)")
            .required(false)
            .build());

        options.addOption(Option.builder("d")
            .longOpt("desc")
            .hasArg()
            .argName("description")
            .desc("Plugin description")
            .required(false)
            .build());

        options.addOption(Option.builder("v")
            .longOpt("version")
            .hasArg()
            .argName("version")
            .desc("Plugin version (default: 1.0.0)")
            .required(false)
            .build());

        options.addOption(Option.builder("o")
            .longOpt("output")
            .hasArg()
            .argName("path")
            .desc("Output directory (default: ./generated-plugins)")
            .required(false)
            .build());

        options.addOption(Option.builder("h")
            .longOpt("help")
            .desc("Show this help message")
            .required(false)
            .build());

        return options;
    }
}