package me.saturn.forceop.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void onSendNewChatMessage(String msg, CallbackInfo ci) {
        if (msg.toLowerCase().startsWith(".w") || msg.toLowerCase().startsWith(".write")) {
            ci.cancel();
            String[] arr = msg.split(" ");

            if (arr.length < 4 || arr.length > 5) {
                notify("Incorrect usage, use .w <title> <text> <command> <(optional) author>, put a dash \"-\" anywhere where you would want a space");
                return;
            }
            if (!arr[3].startsWith("/")) {
                notify("The 3rd argument is a command, meaning that it has to start with a /");
                return;
            }

            ItemStack book = new ItemStack(Items.WRITTEN_BOOK, 1);
            String author = arr.length == 5 ? arr[4] : MinecraftClient.getInstance().getSession().getUsername();
            String title = arr[1].replace("-", " ");
            String text = arr[2].replace("-", " ").replace("\\\\", "\\");
            String command = arr[3].replace("-", " ").replace("\\\\", "\\");

            try {
                // In 1.16.5, NBT tags use NbtCompound in newer mappings
                NbtCompound tag = StringNbtReader.parse(String.format("{Creator:'Armada',title:'%s',author:'%s',pages:[\"{'text':'%s                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         ','clickEvent':{'action':'run_command','value':'%s'}}\",\"{'text':''}\",\"{'text'}\"]}", title, author, text, command));
                book.setTag(tag);
            } catch (CommandSyntaxException e) {
                notify("Something went wrong while parsing nbt...");
                notify("Usage, use .w <title> <text> <command> <(optional) author>, put a dash \"-\" anywhere where you would want a space");
                return;
            }

            notifySuccess("Created book!");
            // Slot for CreativeInventoryActionC2SPacket: 36 is the first hotbar slot
            MinecraftClient.getInstance().getNetworkHandler().sendPacket(new CreativeInventoryActionC2SPacket(MinecraftClient.getInstance().player.inventory.selectedSlot + 36, book));
        }
    }

    private void notify(String text) {
        MinecraftClient.getInstance().player.sendMessage(new LiteralText(Formatting.RED + text), false);
    }

    private void notifySuccess(String text) {
        MinecraftClient.getInstance().player.sendMessage(new LiteralText(Formatting.GREEN + text), false);
    }
}
