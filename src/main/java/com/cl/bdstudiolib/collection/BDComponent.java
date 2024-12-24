package com.cl.bdstudiolib.collection;

import com.cl.bdstudiolib.BDDisplay;
import org.bukkit.Location;
import org.joml.Matrix4f;

import java.util.function.Consumer;

public abstract class BDComponent {
    private final String name;
    private final Matrix4f localTransformation;

    public BDComponent(String name, Matrix4f localTransformation) {
        this.name = name;
        this.localTransformation = localTransformation;
    }

    public abstract void build(Location location, String directory, Matrix4f baseTransformation, Consumer<BDDisplay> displayConsumer);

    public Matrix4f getLocalTransformation() {
        return localTransformation;
    }

    public String getName() {
        return name;
    }
}
