package com.cl.bdstudiolib;

import org.bukkit.entity.Display;
import org.joml.Matrix4f;

public record BDDisplay(Display display, Matrix4f transformationMatrix, String directoryName, String componentName) {
}
