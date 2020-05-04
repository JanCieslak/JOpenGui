package jopengui.utils;

import jopengui.nodes.Node;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Maths {
    public static Matrix4f createModelMatrix(Matrix4f parentModel, Vector2f position, float rotation, Vector2f scale) {
        return new Matrix4f(parentModel)
                .translate(position.x, position.y, 0.0f)
                .rotateXYZ(0.0f, 0.0f, rotation)
                .scale(scale.x, scale.y, 0.0f);
    }
}
