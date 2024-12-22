package com.cl.bdstudiolib.collection.types;

import com.cl.bdstudiolib.collection.BDComponent;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.TextDisplay;
import org.joml.Matrix4f;

import java.util.function.Consumer;

public final class TextDisplayBDComponent extends BDComponent {
    public record Data(
            Component text,
            TextDisplay.TextAlignment alignment,
            int width,
            Color background,
            byte textOpacity,
            boolean seeThrough
    ) {}

    private final Data data;

    public TextDisplayBDComponent(String name, Matrix4f localTransformation, Data data) {
        super(name, localTransformation);
        this.data = data;
    }

    @Override
    public void buildDisplays(Location location, Matrix4f baseTransformation, Consumer<Display> displayConsumer) {
        TextDisplay textDisplay = location.getWorld().spawn(location, TextDisplay.class);
        textDisplay.text(data.text());
        textDisplay.setAlignment(data.alignment());
        textDisplay.setLineWidth(data.width());
        textDisplay.setBackgroundColor(data.background());
        textDisplay.setTextOpacity(data.textOpacity());
        textDisplay.setSeeThrough(data.seeThrough());
        applyTransformation(textDisplay, baseTransformation);

        displayConsumer.accept(textDisplay);
    }

    public Data getData() {
        return data;
    }
}
