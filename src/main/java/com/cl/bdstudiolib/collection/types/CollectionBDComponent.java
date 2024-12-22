package com.cl.bdstudiolib.collection.types;

import com.cl.bdstudiolib.collection.BDComponent;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.joml.Matrix4f;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public final class CollectionBDComponent extends BDComponent {
    private final Collection<BDComponent> components;

    public CollectionBDComponent(String name, Matrix4f localTransformation, Collection<BDComponent> components) {
        super(name, localTransformation);

        this.components = components;
    }

    public Set<BDComponent> getComponents() {
        return new HashSet<>(components);
    }

    @Override
    public void buildDisplays(Location location, Matrix4f baseTransformation, Consumer<Display> displayConsumer) {
        Matrix4f newBase = baseTransformation.mul(getLocalTransformation());

        for (BDComponent component : components) {
            component.buildDisplays(location, new Matrix4f(newBase), displayConsumer);
        }
    }
}
