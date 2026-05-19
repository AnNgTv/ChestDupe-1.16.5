package me.gemini.chestdupe;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class HudButton implements HudRenderCallback {
    private static final int BUTTON_X = 10;
    private static final int BUTTON_Y = 10;
    private static final int BUTTON_WIDTH = 50;
    private static final int BUTTON_HEIGHT = 20;

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        
        // Only show button if a chest is open (as requested, but "outside" the UI)
        // or always show it. Let's show it always but color it differently if a chest is open.
        boolean isOpen = client.currentScreen instanceof GenericContainerScreen;
        
        int color = isOpen ? 0xFF00FF00 : 0xFFFF0000; // Green if ready, Red if not
        
        client.textRenderer.drawWithShadow(matrixStack, new LiteralText("[ DUPE ]"), BUTTON_X, BUTTON_Y, color);
        
        if (isOpen) {
            client.textRenderer.drawWithShadow(matrixStack, new LiteralText("Ready to dupe!"), BUTTON_X, BUTTON_Y + 12, 0xFFFFFF);
        }
    }

    // This handles the click logic for the HUD button
    public static void handleMouseClick(double mouseX, double mouseY, int button) {
        if (button == 0) { // Left click
            if (mouseX >= BUTTON_X && mouseX <= BUTTON_X + BUTTON_WIDTH &&
                mouseY >= BUTTON_Y && mouseY <= BUTTON_Y + BUTTON_HEIGHT) {
                DupeLogic.executeDupe();
            }
        }
    }
}
