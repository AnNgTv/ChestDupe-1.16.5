package me.gemini.chestdupe.mixin;

import me.gemini.chestdupe.DupeLogic;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GenericContainerScreen.class)
public abstract class ChestScreenMixin extends HandledScreen<GenericContainerScreenHandler> {

    public ChestScreenMixin(GenericContainerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    protected void onInit(CallbackInfo ci) {
        // Calculate positions relative to the chest GUI background
        int xRight = (this.width - this.backgroundWidth) / 2 + this.backgroundWidth + 5;
        int xLeft = (this.width - this.backgroundWidth) / 2 - 45; // 40 width + 5 offset
        int y = (this.height - this.backgroundHeight) / 2;

        // Add "DUPE" button to the right
        this.addButton(new ButtonWidget(xRight, y, 40, 20, new LiteralText("§aDUPE"), button -> {
            DupeLogic.executeDupe();
        }));

        // Add "DUPE" button to the left
        this.addButton(new ButtonWidget(xLeft, y, 40, 20, new LiteralText("§aDUPE"), button -> {
            DupeLogic.executeDupe();
        }));
    }
}
