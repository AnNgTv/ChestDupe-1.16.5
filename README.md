# ForceOp-1.16.5

A lightweight Minecraft mod for Fabric 1.16.5 designed to exploit creative mode permissions to run commands. It works by creating a specially crafted "Written Book" that triggers a command when clicked.

## 🚀 Features
- **In-Game Command Interface**: Use `.w` or `.write` in chat to create malicious books.
- **NBT Injection**: Automatically generates books with hidden `clickEvent` triggers.
- **Customizable**: Set custom titles, authors, and text for your books.

## 🛠 Usage

This mod is designed to be used in **Creative Mode**. It leverages the ability of creative players to update item NBT data via the `CreativeInventoryActionC2SPacket`.

### Command Syntax
```
.w <title> <text> <command> [author]
```
- **`<title>`**: The title of the book.
- **`<text>`**: The text displayed on the first page.
- **`<command>`**: The command to execute (must start with `/`).
- **`[author]`**: (Optional) The author name. Defaults to your username.

> **Note**: Use a dash `-` to represent a space in any of the arguments.

### Example
To create a book that grants you Operator status when clicked:
```
.w Secret-Book Click-Me /op-AnNgTv
```

## 📦 Installation

1.  Ensure you have **Fabric Loader** installed for Minecraft 1.16.5.
2.  Download the latest release (or build from source).
3.  Place the `.jar` file into your `.minecraft/mods` folder.
4.  Launch the game.

## ⚠️ Disclaimer

This mod is for educational and testing purposes only. Use it responsibly and only on servers where you have permission to test vulnerabilities. The author is not responsible for any bans or damage caused by the use of this mod.
