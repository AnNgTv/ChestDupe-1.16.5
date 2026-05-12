package me.saturn.chestdupe.gui;

import me.saturn.chestdupe.Config;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class ConfigScreen extends Screen {
    public ConfigScreen() {
        super(new LiteralText("ChestDupe Configuration"));
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 100;
        int y = this.height / 2 - 50;

        this.addButton(new ButtonWidget(x, y, 200, 20, new LiteralText("Show Buttons: " + (Config.showButtons ? "ON" : "OFF")), (button) -> {
            Config.showButtons = !Config.showButtons;
            button.setMessage(new LiteralText("Show Buttons: " + (Config.showButtons ? "ON" : "OFF")));
            Config.save();
        }));

        this.addButton(new ButtonWidget(x, y + 25, 200, 20, new LiteralText("Auto Close: " + (Config.autoClose ? "ON" : "OFF")), (button) -> {
            Config.autoClose = !Config.autoClose;
            button.setMessage(new LiteralText("Auto Close: " + (Config.autoClose ? "ON" : "OFF")));
            Config.save();
        }));

        this.addButton(new ButtonWidget(x, y + 60, 200, 20, new LiteralText("Done"), (button) -> {
            this.client.openScreen(null);
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
