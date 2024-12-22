package com.cl.bdstudiolib.collection.types;

import com.cl.bdstudiolib.collection.BDComponent;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.joml.Matrix4f;

import java.util.function.Consumer;

public final class ItemDisplayBDComponent extends BDComponent {
    private final ItemStack itemStack;

    public ItemDisplayBDComponent(String name, Matrix4f localTransformation, ItemStack itemStack) {
        super(name, localTransformation);

        this.itemStack = itemStack;
    }

    @Override
    public void buildDisplays(Location location, Matrix4f baseTransformation, Consumer<Display> displayConsumer) {
        ItemDisplay itemDisplay = location.getWorld().spawn(location, ItemDisplay.class);
        itemDisplay.setItemStack(itemStack);
        applyTransformation(itemDisplay, baseTransformation);

        displayConsumer.accept(itemDisplay);
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }
}