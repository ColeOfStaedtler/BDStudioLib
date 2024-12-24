package com.cl.bdstudiolib.collection.types;

import com.cl.bdstudiolib.collection.PhysicalBDComponent;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.joml.Matrix4f;

import java.util.function.Consumer;

public final class BlockDisplayBDComponent extends PhysicalBDComponent {
    private final BlockData blockData;

    public BlockDisplayBDComponent(String name, Matrix4f localTransformation, BlockData blockData) {
        super(name, localTransformation);

        this.blockData = blockData;
    }

    @Override
    public void build(Location location, Consumer<Display> displayConsumer) {
        BlockDisplay blockDisplay = location.getWorld().spawn(location, BlockDisplay.class);
        blockDisplay.setBlock(blockData);

        displayConsumer.accept(blockDisplay);
    }

    public BlockData getBlockData() {
        return blockData;
    }
}
