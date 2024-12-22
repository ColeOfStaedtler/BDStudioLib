package com.cl.bdstudiolib.collection.types;

import com.cl.bdstudiolib.collection.BDComponent;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.joml.Matrix4f;

import java.util.function.Consumer;

public final class BlockDisplayBDComponent extends BDComponent {
    private final BlockData blockData;

    public BlockDisplayBDComponent(String name, Matrix4f localTransformation, BlockData blockData) {
        super(name, localTransformation);

        this.blockData = blockData;
    }

    @Override
    public void buildDisplays(Location location, Matrix4f baseTransformation, Consumer<Display> displayConsumer) {
        BlockDisplay blockDisplay = location.getWorld().spawn(location, BlockDisplay.class);
        blockDisplay.setBlock(blockData);
        applyTransformation(blockDisplay, baseTransformation);

        displayConsumer.accept(blockDisplay);
    }

    public BlockData getBlockData() {
        return blockData;
    }
}
