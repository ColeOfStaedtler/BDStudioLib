package com.cl.bdstudiolib;

import com.cl.bdstudiolib.adapter.types.BlockComponentAdapter;
import com.cl.bdstudiolib.adapter.types.CollectionAdapter;
import com.cl.bdstudiolib.adapter.types.ItemComponentAdapter;
import com.cl.bdstudiolib.adapter.types.Matrix4fAdapter;
import com.cl.bdstudiolib.adapter.types.TextComponentAdapter;
import com.cl.bdstudiolib.collection.types.BlockDisplayBDComponent;
import com.cl.bdstudiolib.collection.types.CollectionBDComponent;
import com.cl.bdstudiolib.collection.types.ItemDisplayBDComponent;
import com.cl.bdstudiolib.collection.types.TextDisplayBDComponent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.util.Base64;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

public record DisplayModelSchematic(CollectionBDComponent collection) {
    private static final Matrix4f IDENTITY = new Matrix4f();
    private static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .serializeNulls()
            .setPrettyPrinting()
            .enableComplexMapKeySerialization()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
            .registerTypeAdapter(CollectionBDComponent.class, new CollectionAdapter())
            .registerTypeAdapter(BlockDisplayBDComponent.class, new BlockComponentAdapter())
            .registerTypeAdapter(ItemDisplayBDComponent.class, new ItemComponentAdapter())
            .registerTypeAdapter(TextDisplayBDComponent.class, new TextComponentAdapter())
            .registerTypeAdapter(Matrix4f.class, new Matrix4fAdapter())
            .create();

    /**
     * Creates a {@link DisplayModelSchematic} from a BDStudio file.
     * @param file The BDStudio file to read from. This file is expected to exist.
     * @return The {@link DisplayModelSchematic} created from the BDStudio file.
     * @throws IOException If an I/O error occurs.
     */
    public static DisplayModelSchematic fromBDStudioFile(File file) throws IOException {
        return fromBDStudioFormat(file.toURI().toURL().openStream());
    }

    /**
     * Creates a {@link DisplayModelSchematic} from a stream.
     * @param stream The stream to read from. This file is expected to exist.
     * @return The {@link DisplayModelSchematic} created from the stream.
     * @throws IOException If an I/O error occurs.
     */
    public static DisplayModelSchematic fromBDStudioFormat(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String base64EncodedLine = reader.readLine();
        reader.close();

        byte[] compressedData = Base64.getDecoder().decode(base64EncodedLine);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedData);
        String uglyJson = getUglyString(inputStream);

        return new DisplayModelSchematic(GSON.fromJson(uglyJson, CollectionBDComponent.class));
    }

    @NotNull
    private static String getUglyString(ByteArrayInputStream inputStream) throws IOException {
        GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int read;

        while ((read = gzipInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, read);
        }

        String uglyJson = outputStream.toString();

        gzipInputStream.close();
        outputStream.close();

        uglyJson = uglyJson.substring(1, uglyJson.length() - 1);
        return uglyJson;
    }

    /**
     * Spawns the {@link DisplayModelSchematic} at the given location. (Uses identity as base transformation)
     * @param location The location to spawn the {@link DisplayModelSchematic} at.
     * @param displayConsumer consumes all BDDisplays.
     *
     */
    public void spawn(Location location, Consumer<BDDisplay> displayConsumer) {
        collection.build(location, "", IDENTITY, displayConsumer);
    }

    /**
     * Spawns the {@link DisplayModelSchematic} at the given location.
     * @param location The location to spawn the {@link DisplayModelSchematic} at.
     * @param baseTransformation transformation to apply to the entire model
     * @param displayConsumer consumes all BDDisplays
     */
    public void spawn(Location location, Matrix4f baseTransformation, Consumer<BDDisplay> displayConsumer) {
        collection.build(location, "", baseTransformation, displayConsumer);
    }
}
