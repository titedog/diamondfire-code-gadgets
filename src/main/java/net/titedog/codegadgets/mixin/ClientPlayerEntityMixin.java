package net.titedog.codegadgets.mixin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.titedog.codegadgets.util.VariableTypeUtils;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    private final MinecraftClient client = MinecraftClient.getInstance();

    @Inject(at = @At("HEAD"), method = "tick()V")
    private void checkLocationItem(CallbackInfo ci) {
        if(client == null) {
            return;
        }

        ItemStack heldItem = client.player.getMainHandStack();
        if(heldItem == null) {
            return;
        }

        if(VariableTypeUtils.isVariableType(heldItem, "loc")) {
            CompoundTag dataTag = heldItem.getTag();
            if(dataTag == null) {
                return;
            }

            CompoundTag bukkitNBT = dataTag.getCompound("PublicBukkitValues");
            if(bukkitNBT == null) {
                return;
            }

            JsonObject varItemNbt = new JsonParser().parse(bukkitNBT.getString("hypercube:varitem")).getAsJsonObject().getAsJsonObject("data").getAsJsonObject().getAsJsonObject("loc");
            if(varItemNbt == null) {
                return;
            }

            String x = varItemNbt.get("x").getAsString();
            String y = varItemNbt.get("y").getAsString();
            String z = varItemNbt.get("z").getAsString();

            if(x == null || y == null || z == null) {
                return;
            }

            client.player.sendMessage(new LiteralText(x + ", " + y + ", " + z),true);
        }
    }
}
