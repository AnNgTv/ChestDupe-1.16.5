package me.saturn.chestdupe.mixin;

import me.saturn.chestdupe.Config;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({GenericContainerScreen.class, ShulkerBoxScreen.class, HopperScreen.class, Generic3x3ContainerScreen.class})
public abstract class ContainerScreenMixin extends HandledScreen<ScreenHandler> {

    public ContainerScreenMixin(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    protected void onInit(CallbackInfo ci) {
        if (!Config.showButtons) return;

        this.addButton(new ButtonWidget(this.width / 2 - 50, this.height / 2 - 120, 100, 20, new LiteralText("Dupe"), (button) -> {
            dupe(false);
        }));
        this.addButton(new ButtonWidget(this.width / 2 - 50, this.height / 2 - 95, 100, 20, new LiteralText("Dupe & Disconnect"), (button) -> {
            dupe(true);
        }));
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    public void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (keyCode == Config.dupeKey) {
            dupe(false);
            cir.setReturnValue(true);
        } else if (keyCode == Config.dupeDisconnectKey) {
            dupe(true);
            cir.setReturnValue(true);
        }
    }

    private void dupe(boolean disconnect) {
        if (this.client == null || this.client.player == null || this.client.interactionManager == null) return;

        int containerSlots = 0;
        if (this.handler instanceof GenericContainerScreenHandler) {
            containerSlots = ((GenericContainerScreenHandler) this.handler).getRows() * 9;
        } else if (this.handler instanceof ShulkerBoxScreenHandler) {
            containerSlots = 27;
        } else if (this.handler instanceof HopperScreenHandler) {
            containerSlots = 5;
        } else if (this.handler instanceof Generic3x3ContainerScreenHandler) {
            containerSlots = 9;
        }
        
        for (int i = 0; i < containerSlots; i++) {
            if (this.handler.getSlot(i).hasStack()) {
                this.client.interactionManager.clickSlot(this.handler.syncId, i, 0, SlotActionType.QUICK_MOVE, this.client.player);
            }
        }
        
        if (disconnect) {
            if (this.client.getNetworkHandler() != null && this.client.getNetworkHandler().getConnection() != null) {
                this.client.getNetworkHandler().getConnection().disconnect(new LiteralText("Duping..."));
            }
        } else if (Config.autoClose) {
            this.client.player.closeHandledScreen();
        }
    }
}
