package me.saturn.chestdupe.mixin;

import me.saturn.chestdupe.Config;
import net.minecraft.client.gui.screen.Screen;
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

@Mixin(HandledScreen.class)
public abstract class ContainerScreenMixin extends Screen {

    protected ContainerScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("TAIL"))
    protected void onInit(CallbackInfo ci) {
        if (!Config.showButtons) return;
        
        // Filter for supported screens
        Object obj = (Object) this;
        if (!(obj instanceof GenericContainerScreen || obj instanceof ShulkerBoxScreen || 
              obj instanceof HopperScreen || obj instanceof Generic3x3ContainerScreen)) {
            return;
        }

        this.addButton(new ButtonWidget(this.width / 2 - 50, this.height / 2 - 120, 100, 20, new LiteralText("Dupe"), (button) -> {
            dupe(false);
        }));
        this.addButton(new ButtonWidget(this.width / 2 - 50, this.height / 2 - 95, 100, 20, new LiteralText("Dupe & Disconnect"), (button) -> {
            dupe(true);
        }));
    }

    @Inject(method = "keyPressed(III)Z", at = @At("HEAD"), cancellable = true)
    public void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        // Filter for supported screens
        Object obj = (Object) this;
        if (!(obj instanceof GenericContainerScreen || obj instanceof ShulkerBoxScreen || 
              obj instanceof HopperScreen || obj instanceof Generic3x3ContainerScreen)) {
            return;
        }

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
        
        HandledScreen<?> screen = (HandledScreen<?>) (Object) this;
        ScreenHandler handler = screen.getScreenHandler();

        int containerSlots = 0;
        if (handler instanceof GenericContainerScreenHandler) {
            containerSlots = ((GenericContainerScreenHandler) handler).getRows() * 9;
        } else if (handler instanceof ShulkerBoxScreenHandler) {
            containerSlots = 27;
        } else if (handler instanceof HopperScreenHandler) {
            containerSlots = 5;
        } else if (handler instanceof Generic3x3ContainerScreenHandler) {
            containerSlots = 9;
        }
        
        for (int i = 0; i < containerSlots; i++) {
            if (handler.getSlot(i).hasStack()) {
                this.client.interactionManager.clickSlot(handler.syncId, i, 0, SlotActionType.QUICK_MOVE, this.client.player);
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
