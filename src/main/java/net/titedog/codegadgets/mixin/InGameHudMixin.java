package net.titedog.codegadgets.mixin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin  {
    private final MinecraftClient client = MinecraftClient.getInstance();

    @Inject(at = @At("HEAD"), method = "renderHeldItemTooltip", cancellable = true)
    public void renderVariableItem(MatrixStack matrices, CallbackInfo callbackInfo) {
        if(client == null) {
            return;
        }
        try {
            assert client.player != null;
            ItemStack itemStack = client.player.inventory.getMainHandStack();
            String variableType = "";

            if(itemStack == null) {
                return;
            }

            CompoundTag dataTag = itemStack.getTag();
            if(dataTag == null) {
                return;
            }

            CompoundTag bukkitNBT = dataTag.getCompound("PublicBukkitValues");
            if(bukkitNBT == null) {
                return;
            }

            JsonObject varItemNbt = new JsonParser().parse(bukkitNBT.getString("hypercube:varitem")).getAsJsonObject().getAsJsonObject("data");
            if(varItemNbt == null) {
                return;
            }
            if(varItemNbt.isJsonNull()) {
                return;
            }
            if(!(varItemNbt.isJsonObject())) {
                return;
            }
            if(varItemNbt.get("scope") == null) {
                return;
            }

            if(varItemNbt.get("scope").getAsString().contains("unsaved")) {
                variableType = "§fScope: §7GAME";
            } else if(varItemNbt.get("scope").getAsString().contains("saved")) {
                variableType = "§fScope: §eSAVE";
            } else if(varItemNbt.get("scope").getAsString().contains("local")) {
                variableType = "§fScope: §aLOCAL";
            } else {
                return;
            }

            int x1 = (client.getWindow().getScaledWidth() - client.textRenderer.getWidth(new LiteralText(itemStack.getName().asString()))) / 2;
            int y1 = client.getWindow().getScaledHeight() - 45;

            int x2 = (client.getWindow().getScaledWidth() - client.textRenderer.getWidth(variableType)) / 2;
            int y2 = client.getWindow().getScaledHeight() - 35;

            client.textRenderer.drawWithShadow(matrices, new LiteralText(itemStack.getName().asString()), (float)x1, (float)y1, 16777215);
            client.textRenderer.drawWithShadow(matrices, variableType, (float)x2, (float)y2, 16777215);
        } catch(Exception exc) {
            exc.printStackTrace();
        }

    }
}
