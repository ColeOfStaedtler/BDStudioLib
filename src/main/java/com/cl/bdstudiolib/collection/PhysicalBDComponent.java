package com.cl.bdstudiolib.collection;

import com.cl.bdstudiolib.BDDisplay;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.joml.Matrix4f;

import java.util.function.Consumer;

public abstract class PhysicalBDComponent extends BDComponent {
    
    public PhysicalBDComponent(String name, Matrix4f localTransformation) {
        super(name, localTransformation);
    }
    
    public abstract void build(Location location, Consumer<Display> displayConsumer);

    @Override
    public void build(Location location, String directory, Matrix4f baseTransformation, Consumer<BDDisplay> displayConsumer) {
        build(location, display -> {
            Matrix4f transformation = baseTransformation.mul(getLocalTransformation());
            display.setTransformationMatrix(transformation);
            displayConsumer.accept(new BDDisplay(display, transformation, directory, getName()));
        });
    }
}
