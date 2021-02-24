package net.titedog.codegadgets.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class VariableTypeUtils {
    public static boolean isVariableType(ItemStack item, String checkType) {
        try {
            CompoundTag dataTag = item.getTag();
            if(dataTag == null) {
                return false;
            }

            CompoundTag bukkitNBT = dataTag.getCompound("PublicBukkitValues");
            if(bukkitNBT == null) {
                return false;
            }

            JsonObject varItemNbt = new JsonParser().parse(bukkitNBT.getString("hypercube:varitem")).getAsJsonObject();
            if(varItemNbt == null) {
                return false;
            }

            if(varItemNbt.get("id").getAsString().equals(checkType)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            return false;
        }
    }
}
