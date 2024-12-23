package com.cl.bdstudiolib.adapter.types;

import com.cl.bdstudiolib.adapter.ComponentAdapter;
import com.cl.bdstudiolib.collection.types.ItemDisplayBDComponent;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.joml.Matrix4f;

import java.lang.reflect.Type;

public final class ItemComponentAdapter implements ComponentAdapter<ItemDisplayBDComponent> {

    @Override
    public ItemDisplayBDComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        String name = deserializeName(object);
        Matrix4f transformation = deserializeTransforms(object, context);

        int endIndex = name.indexOf("[");
        Material material = Material.getMaterial(name.substring(0, endIndex == -1 ? 0 : endIndex).toUpperCase());

        if (material == null) {
            throw new IllegalArgumentException("Material " + name + " does not exist!");
        }

        ItemStack itemStack = new ItemStack(material);

        try {
            ItemMeta meta = itemStack.getItemMeta();
            meta.setCustomModelData(object.get("nbt").getAsInt());
            itemStack.setItemMeta(meta);
        } catch (NumberFormatException ignored) {
        }

        return new ItemDisplayBDComponent(name, transformation, new ItemStack(material));
    }
}
