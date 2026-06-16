package com.minecraft.generator.service;

import com.minecraft.generator.model.PluginConfig;
import com.minecraft.generator.template.TemplateProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Service for generating Minecraft plugins
 */
public class PluginGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(PluginGeneratorService.class);
    private final TemplateProvider templateProvider = new TemplateProvider();

    /**
     * Generate a complete plugin structure
     */
    public String generatePlugin(PluginConfig config) throws IOException {
        String pluginDirName = config.getName() + "-plugin";
        Path pluginPath = Paths.get(config.getOutputPath(), pluginDirName);

        logger.info("Creating plugin directory: {}", pluginPath);
        Files.createDirectories(pluginPath);

        // Create directory structure
        createDirectoryStructure(pluginPath, config);

        // Generate files based on type
        generatePluginFiles(pluginPath, config);

        return pluginPath.toString();
    }

    /**
     * Create the directory structure for the plugin
     */
    private void createDirectoryStructure(Path pluginPath, PluginConfig config) throws IOException {
        String packagePath = config.getPackageName().replace(".", "/");

        // Main source directories
        Files.createDirectories(pluginPath.resolve("src/main/java/com/minecraft/plugin/" + 
            config.getName().toLowerCase()));
        Files.createDirectories(pluginPath.resolve("src/main/java/com/minecraft/plugin/" + 
            config.getName().toLowerCase() + "/commands"));
        Files.createDirectories(pluginPath.resolve("src/main/java/com/minecraft/plugin/" + 
            config.getName().toLowerCase() + "/events"));
        Files.createDirectories(pluginPath.resolve("src/main/java/com/minecraft/plugin/" + 
            config.getName().toLowerCase() + "/items"));
        Files.createDirectories(pluginPath.resolve("src/main/java/com/minecraft/plugin/" + 
            config.getName().toLowerCase() + "/config"));
        Files.createDirectories(pluginPath.resolve("src/main/java/com/minecraft/plugin/" + 
            config.getName().toLowerCase() + "/util"));
        
        // Resources
        Files.createDirectories(pluginPath.resolve("src/main/resources"));
        
        // Test directories
        Files.createDirectories(pluginPath.resolve("src/test/java"));

        logger.debug("Directory structure created");
    }

    /**
     * Generate plugin files based on type
     */
    private void generatePluginFiles(Path pluginPath, PluginConfig config) throws IOException {
        // Always generate these files
        generatePomXml(pluginPath, config);
        generatePluginYml(pluginPath, config);
        generateMainClass(pluginPath, config);
        generateGitignore(pluginPath);
        generateLicense(pluginPath);

        // Generate type-specific files
        switch (config.getType().toLowerCase()) {
            case "command" -> generateCommandPlugin(pluginPath, config);
            case "event" -> generateEventPlugin(pluginPath, config);
            case "item" -> generateItemPlugin(pluginPath, config);
            case "database" -> generateDatabasePlugin(pluginPath, config);
            case "config" -> generateConfigPlugin(pluginPath, config);
            case "listener" -> generateListenerPlugin(pluginPath, config);
            case "full" -> generateFullPlugin(pluginPath, config);
            default -> logger.warn("Unknown plugin type: {}", config.getType());
        }

        logger.debug("Plugin files generated successfully");
    }

    private void generatePomXml(Path pluginPath, PluginConfig config) throws IOException {
        String content = templateProvider.getPomXmlTemplate(config);
        writeFile(pluginPath.resolve("pom.xml"), content);
    }

    private void generatePluginYml(Path pluginPath, PluginConfig config) throws IOException {
        String content = templateProvider.getPluginYmlTemplate(config);
        writeFile(pluginPath.resolve("plugin.yml"), content);
    }

    private void generateMainClass(Path pluginPath, PluginConfig config) throws IOException {
        String content = templateProvider.getMainClassTemplate(config);
        Path mainClassPath = pluginPath.resolve("src/main/java/com/minecraft/plugin/" + 
            config.getName().toLowerCase() + "/Main.java");
        writeFile(mainClassPath, content);
    }

    private void generateCommandPlugin(Path pluginPath, PluginConfig config) throws IOException {
        String content = templateProvider.getCommandTemplate(config);
        Path commandPath = pluginPath.resolve("src/main/java/com/minecraft/plugin/" + 
            config.getName().toLowerCase() + "/commands/TestCommand.java");
        writeFile(commandPath, content);
    }

    private void generateEventPlugin(Path pluginPath, PluginConfig config) throws IOException {
        String content = templateProvider.getEventListenerTemplate(config);
        Path listenerPath = pluginPath.resolve("src/main/java/com/minecraft/plugin/" + 
            config.getName().toLowerCase() + "/events/PlayerEventListener.java");
        writeFile(listenerPath, content);
    }

    private void generateItemPlugin(Path pluginPath, PluginConfig config) throws IOException {
        String content = templateProvider.getCustomItemTemplate(config);
        Path itemPath = pluginPath.resolve("src/main/java/com/minecraft/plugin/" + 
            config.getName().toLowerCase() + "/items/CustomItem.java");
        writeFile(itemPath, content);
    }

    private void generateDatabasePlugin(Path pluginPath, PluginConfig config) throws IOException {
        String content = templateProvider.getDatabaseManagerTemplate(config);
        Path dbPath = pluginPath.resolve("src/main/java/com/minecraft/plugin/" + 
            config.getName().toLowerCase() + "/config/DatabaseManager.java");
        writeFile(dbPath, content);
    }

    private void generateConfigPlugin(Path pluginPath, PluginConfig config) throws IOException {
        String content = templateProvider.getConfigManagerTemplate(config);
        Path configPath = pluginPath.resolve("src/main/java/com/minecraft/plugin/" + 
            config.getName().toLowerCase() + "/config/ConfigManager.java");
        writeFile(configPath, content);
        
        // Also generate config.yml
        String configYml = templateProvider.getConfigYmlTemplate(config);
        writeFile(pluginPath.resolve("src/main/resources/config.yml"), configYml);
    }

    private void generateListenerPlugin(Path pluginPath, PluginConfig config) throws IOException {
        String content = templateProvider.getFullEventListenerTemplate(config);
        Path listenerPath = pluginPath.resolve("src/main/java/com/minecraft/plugin/" + 
            config.getName().toLowerCase() + "/events/FullEventListener.java");
        writeFile(listenerPath, content);
    }

    private void generateFullPlugin(Path pluginPath, PluginConfig config) throws IOException {
        // Generate all types of files
        generateCommandPlugin(pluginPath, config);
        generateEventPlugin(pluginPath, config);
        generateItemPlugin(pluginPath, config);
        generateDatabasePlugin(pluginPath, config);
        generateConfigPlugin(pluginPath, config);
        generateListenerPlugin(pluginPath, config);
    }

    private void generateGitignore(Path pluginPath) throws IOException {
        String content = templateProvider.getGitignoreTemplate();
        writeFile(pluginPath.resolve(".gitignore"), content);
    }

    private void generateLicense(Path pluginPath) throws IOException {
        String content = templateProvider.getLicenseTemplate();
        writeFile(pluginPath.resolve("LICENSE"), content);
    }

    /**
     * Write content to file
     */
    private void writeFile(Path filePath, String content) throws IOException {
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, content.getBytes(StandardCharsets.UTF_8));
        logger.debug("File created: {}", filePath.getFileName());
    }
}