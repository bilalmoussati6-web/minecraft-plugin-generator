# Minecraft Plugin Generator

Een uitgebreide Java-gebaseerde tool voor het genereren van Minecraft Bukkit/Spigot/Paper plugins. De generator kan automatisch alle soorten plugins creëren met complete boilerplate code.

## Functies

- 🎮 **Meerdere plugin types**: Commands, Events, Custom Items, Databases, Config Files, Listeners, Utilities
- ⚙️ **Automatische configuratie**: plugin.yml, pom.xml, en project structure
- 🛠️ **CLI Interface**: Eenvoudige command-line tool voor plugin generatie
- 📦 **Maven Integration**: Volledig geconfigureerde Maven build
- 🔧 **Aanpasbare templates**: Eenvoudig aan te passen code templates

## Installation

### Vereisten
- Java 21 of hoger
- Maven 3.8+
- Git

### Build
```bash
git clone https://github.com/bilalmoussati6-web/minecraft-plugin-generator.git
cd minecraft-plugin-generator
mvn clean package
```

## Gebruik

### Als JAR bestand
```bash
java -jar target/plugin-generator.jar -name MyPlugin -type command
```

### Als IDE project
```bash
mvn clean install
```

## Ondersteunde Plugin Types

1. **command** - Command plugins met argument handling
2. **event** - Event listener plugins
3. **item** - Custom item plugins
4. **database** - Database integration plugins
5. **config** - Configuration file based plugins
6. **listener** - Full event listener plugins
7. **full** - Complete plugin met alles

## CLI Opties

```
-name, --name <name>           Plugin naam (verplicht)
-type, --type <type>           Plugin type (verplicht)
-author, --author <author>     Plugin auteur (standaard: You)
-desc, --desc <description>    Plugin beschrijving
-version, --version <version>  Plugin versie (standaard: 1.0.0)
-output, --output <path>       Output directory (standaard: ./generated-plugins)
-help                          Toon deze help
```

## Voorbeelden

### Simpel command plugin
```bash
java -jar target/plugin-generator.jar -name MyCommandPlugin -type command -author "Jouw Naam"
```

### Event listener plugin
```bash
java -jar target/plugin-generator.jar -name MyEventPlugin -type event -desc "Een custom event listener"
```

### Volledige plugin met alles
```bash
java -jar target/plugin-generator.jar -name CompletePlugin -type full -author "Developer" -version 2.0.0
```

## Project Structuur

```
minecraft-plugin-generator/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/minecraft/generator/
│   │           ├── PluginGenerator.java
│   │           ├── model/
│   │           ├── generator/
│   │           ├── template/
│   │           └── util/
│   └── test/
└── generated-plugins/
    └── (Generated plugins worden hier opgeslagen)
```

## Gegenereerde Plugin Structuur

Elke gegenereerde plugin heeft deze structuur:

```
GeneratedPlugin/
├── pom.xml
├── plugin.yml
├── README.md
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/minecraft/plugin/
│   │           ├── Main.java
│   │           ├── commands/
│   │           ├── events/
│   │           ├── items/
│   │           └── config/
│   └── resources/
│       └── config.yml
└── target/
```

## Ontwikkeling

### Requirements
- IntelliJ IDEA, Eclipse, of VS Code
- Maven 3.8+
- JDK 21

### Build & Run
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.minecraft.generator.PluginGenerator"
```

## Contributing

Contributions zijn welkom! Maak een fork, maak je wijzigingen en stuur een pull request.

## Licentie

MIT License - zie LICENSE bestand voor details

## Ondersteuning

Voor vragen of problemen, open een GitHub Issue of neem contact op.

## Versiegeschiedenis

### v1.0.0
- Initial release
- Support voor 7 verschillende plugin types
- Volledige CLI interface
- Maven integration

---

Veel plezier met het genereren van Minecraft plugins! 🎮