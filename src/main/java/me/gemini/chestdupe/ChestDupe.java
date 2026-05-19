package me.gemini.chestdupe;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.screen.slot.SlotActionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChestDupe implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("chestdupe");

    @Override
    public void onInitialize() {
        LOGGER.info("ChestDupe initialized! Use with caution.");
        
        net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback.EVENT.register(new HudButton());
        
        net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(net.minecraft.server.command.CommandManager.literal("dupe")
                .executes(context -> {
                    // Since commands run on server-side or are sent to server, 
                    // we usually use client-side keybinds or GUI buttons for dupe.
                    // But we can trigger a client-side execution if this is a client-side mod.
                    DupeLogic.executeDupe();
                    return 1;
                })
            );
        });
    }
}
