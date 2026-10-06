<h1 align="center">GMRandomcommand</h1>

<p align="center">
  <strong>A lightweight, secure, and modern Random Command Executor plugin for Minecraft.</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/API-1.12.2%20--%2026.1.2-green.svg" alt="API Support">
  <img src="https://img.shields.io/badge/Java-8%2B-blue.svg" alt="Java Support">
  <img src="https://img.shields.io/badge/Build-Maven-orange.svg" alt="Build Tool">
</p>

---

## 📖 Overview

**GMRandomcommand** is a Spigot/Paper plugin designed to execute randomized commands from pre-configured lists. It is perfect for server administrators looking to create randomized rewards (Crates, Playtime Rewards, Vote Rewards) without relying on heavy or unsafe plugins.

It supports both **evenly distributed random selection** and **weighted probability (percentage-based) selection**.

## ✨ Features

- **Weighted & Even Randomization**: Define command lists with exact percentages or equal chances.
- **Multiple Files Support**: Organize your command lists cleanly inside the `lists/` folder. Create as many `.yml` files as you need.
- **PlaceholderAPI Integration**: Fully supports [PlaceholderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI) (e.g., `%player_name%`, `%vault_eco_balance%`).
- **Hex Color Support**: Fully supports standard `&` colors and modern hex `&#RRGGBB` colors (for Minecraft 1.16+).
- **Secure by Design**: Built from scratch to be safe, fast, and reliable. Only admins and console can execute the core command.
- **Highly Customizable**: All messages are editable in `messages.yml`.

## 🚀 Installation

1. Download the latest `GMRandomcommand-x.x.x.jar` from the [Releases](#) tab (or build it yourself).
2. Drop the `.jar` file into your server's `plugins/` directory.
3. Restart or reload your server.
4. (Optional) Install **PlaceholderAPI** for placeholder support.

## 💻 Commands & Permissions

| Command | Description | Permission | Default |
| :--- | :--- | :--- | :--- |
| `/gmrc <list>` | Run a random command from the specified list (as Console). | `gmrandomcommand.admin` | OP |
| `/gmrc <list> <player>` | Run a random command targeting a specific player. | `gmrandomcommand.admin` | OP |
| `/gmrc reload` | Reloads all config files and lists. | `gmrandomcommand.admin` | OP |

> **Note:** The command is intended to be executed by **Console** (e.g., triggered via GUI Menu plugins, Crates, or NPCs) or Server Owners. Regular players do not need access to this command.

## 🛠️ Configuration

### `lists/example.yml`

You can create unlimited `.yml` files inside the `plugins/GMRandomcommand/lists/` directory.

```yaml
# Example 1: Evenly distributed (Equal chances)
basic_reward:
  commands:
    - "give %player% diamond 1"
    - "give %player% iron_ingot 5"

# Example 2: Weighted probability
vip_reward:
  use-weight: true
  commands:
    - "50:give %player% diamond 5" # 50% chance
    - "30:give %player% emerald 10" # 30% chance
    - "19:eco give %player% 5000" # 19% chance
    - "1:broadcast &e%player% won the jackpot!" # 1% chance
```

## 🏗️ Building from Source

This project uses Maven. To build the project locally, clone the repository and run:

```bash
mvn clean package
```

The compiled `.jar` will be generated in the `target/` folder.

---
*Created with ❤️ for the Minecraft Community.*
