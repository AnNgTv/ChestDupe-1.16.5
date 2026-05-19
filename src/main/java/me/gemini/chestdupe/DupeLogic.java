package me.gemini.chestdupe;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.entity.player.PlayerInventory;

public class DupeLogic {
    
    public static void executeDupe() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!(client.currentScreen instanceof GenericContainerScreen)) return;
        
        GenericContainerScreen screen = (GenericContainerScreen) client.currentScreen;
        int syncId = screen.getScreenHandler().syncId;
        
        // Exploit logic: Rapidly move items between chest and inventory
        // Note: This is a classic 11-slot dupe simulation for many 1.16.x servers
        new Thread(() -> {
            try {
                for (int i = 0; i < 27; i++) {
                    // Shift-click items from chest to inventory
                    client.interactionManager.clickSlot(syncId, i, 0, SlotActionType.QUICK_MOVE, client.player);
                    Thread.sleep(10); // Small delay to bypass some anti-cheats
                }
                client.player.sendChatMessage("§a[ChestDupe] Attempted to dupe items!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
