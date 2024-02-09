# KitRenamer

KitRenamer is a Minecraft plugin that provides players with a unique and customizable way to rename their kits.

## Features

- **Kit Renaming**: Players can rename their kits using an interactive menu. The names of the kits can be customized in the plugin's configuration file.

- **Item Name Replacement**: The names of the items in the kits can be replaced with custom names defined in the configuration file.

- **Interactive User Interface**: The plugin provides an interactive user interface to facilitate the renaming of the kits. Players can navigate through the menu and select the desired options.

- **Asynchronous Events**: The plugin handles asynchronous events to improve performance and efficiency.

## Configuration

The plugin's configuration can be customized in the `config.yml` file. Here, replacement names for items and the format of the kit names can be defined.

## Commands

The plugin provides the `kitrenamer` command (alias `kr`) to access the plugin's functionalities.

## Dependencies

The project is written in Java and uses Maven for dependency management.

## Versions

The plugin is designed to be compatible with version 1.20 of the Minecraft API.

## Main Classes

- `KitRenamer`: Main class of the plugin.
- `MenusListener`: Class that handles the interaction events with the menus and the chat.
- `MainMenu`: Class that defines the main menu of the plugin.
- `ReplacementsMenu`: Class that defines the replacement menu of the plugin.

For more details, please review the project's source code.