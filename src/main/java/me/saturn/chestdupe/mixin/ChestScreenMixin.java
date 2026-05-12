package me.saturn.chestdupe.mixin;

import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
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
        this.addButton(new ButtonWidget(this.width / 2 - 50, this.height / 2 - 120, 100, 20, new LiteralText("Dupe"), (button) -> {
            dupe(false);
        }));
        this.addButton(new ButtonWidget(this.width / 2 - 50, this.height / 2 - 95, 100, 20, new LiteralText("Dupe & Disconnect"), (button) -> {
            dupe(true);
        }));
    }

    private void dupe(boolean disconnect) {
        if (this.client == null || this.client.player == null || this.client.interactionManager == null) return;

        int rows = this.handler.getRows();
        int chestSlots = rows * 9;
        
        for (int i = 0; i < chestSlots; i++) {
            if (this.handler.getSlot(i).hasStack()) {
                this.client.interactionManager.clickSlot(this.handler.syncId, i, 0, SlotActionType.QUICK_MOVE, this.client.player);
            }
        }
        
        if (disconnect) {
            if (this.client.getNetworkHandler() != null && this.client.getNetworkHandler().getConnection() != null) {
                this.client.getNetworkHandler().getConnection().disconnect(new LiteralText("Duping..."));
            }
        } else {
            this.client.player.closeHandledScreen();
        }
    }
}
