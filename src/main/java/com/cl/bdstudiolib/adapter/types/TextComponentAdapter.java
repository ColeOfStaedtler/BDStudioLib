package com.cl.bdstudiolib.adapter.types;

import com.cl.bdstudiolib.adapter.ComponentAdapter;
import com.cl.bdstudiolib.collection.types.TextDisplayBDComponent;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.entity.TextDisplay;

import java.lang.reflect.Type;

public final class TextComponentAdapter implements ComponentAdapter<TextDisplayBDComponent> {

    @Override
    public TextDisplayBDComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        String text = deserializeName(object);

        ComponentBuilder<TextComponent, TextComponent.Builder> builder = Component.text();
        builder.append(Component.text(text));
        builder.color(TextColor.fromHexString(object.get("color").getAsString()));

        Style.Builder styleBuilder = Style.style();

        boolean bold = object.get("bold").getAsBoolean();
        boolean italic = object.get("italic").getAsBoolean();
        boolean underlined = object.get("underlined").getAsBoolean();
        boolean strikethrough = object.get("strikethrough").getAsBoolean();

        if (bold) {
            styleBuilder.decorate(TextDecoration.BOLD);
        }

        if (italic) {
            styleBuilder.decorate(TextDecoration.ITALIC);
        }

        if (underlined) {
            styleBuilder.decorate(TextDecoration.UNDERLINED);
        }

        if (strikethrough) {
            styleBuilder.decorate(TextDecoration.STRIKETHROUGH);
        }

        builder.style(styleBuilder.build());

        TextColor color = TextColor.fromHexString(object.get("backgroundColor").getAsString());

        TextDisplayBDComponent.Data data = new TextDisplayBDComponent.Data(
                builder.build(),
                TextDisplay.TextAlignment.valueOf(object.get("alignment").getAsString().toUpperCase()),
                object.get("length").getAsInt(),
                color == null ? Color.WHITE : Color.fromRGB(color.red(), color.green(), color.blue()),
                (byte) (127 * object.get("alpha").getAsFloat()), // untested
                object.get("backgroundAlpha").getAsFloat() < 0.5F // untested
        );

        return new TextDisplayBDComponent(text, deserializeTransforms(object, context), data);
    }
}
