package com.cl.bdstudiolib.collection;

import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.joml.Matrix4f;

import java.util.function.Consumer;

public abstract class BDComponent {
    private final String name;
    private final Matrix4f localTransformation;

    public BDComponent(String name, Matrix4f localTransformation) {
        this.name = name;
        this.localTransformation = localTransformation;
    }

    protected void applyTransformation(Display display, Matrix4f base) {
        display.setTransformationMatrix(base.mul(localTransformation));
    }

    public abstract void buildDisplays(Location location, Matrix4f baseTransformation, Consumer<Display> displayConsumer);

    public Matrix4f getLocalTransformation() {
        return localTransformation;
    }

    public String getName() {
        return name;
    }
}
