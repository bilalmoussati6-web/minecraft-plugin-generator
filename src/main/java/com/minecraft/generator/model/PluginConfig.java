package com.minecraft.generator.model;

/**
 * Configuration model for plugin generation
 */
public class PluginConfig {

    private String name;
    private String type;
    private String author;
    private String description;
    private String version;
    private String outputPath;

    // Private constructor for builder pattern
    private PluginConfig(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.author = builder.author;
        this.description = builder.description;
        this.version = builder.version;
        this.outputPath = builder.outputPath;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getVersion() {
        return version;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getPackageName() {
        return "com.minecraft.plugin." + name.toLowerCase();
    }

    /**
     * Builder pattern for PluginConfig
     */
    public static class Builder {
        private String name;
        private String type;
        private String author = "You";
        private String description = "A custom Minecraft plugin";
        private String version = "1.0.0";
        private String outputPath = "./generated-plugins";

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder outputPath(String outputPath) {
            this.outputPath = outputPath;
            return this;
        }

        public PluginConfig build() {
            if (name == null || type == null) {
                throw new IllegalArgumentException("name and type are required");
            }
            return new PluginConfig(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "PluginConfig{" +
            "name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", author='" + author + '\'' +
            ", version='" + version + '\'' +
            '}';
    }
}