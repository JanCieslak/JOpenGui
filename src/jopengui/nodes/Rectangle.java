package jopengui.nodes;

import jopengui.gfx.GuiShader;
import org.joml.Matrix4f;

public class Rectangle extends Node {
    public Rectangle(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    @Override
    public void render(GuiShader shader, Matrix4f origin, Node parent) {
        bindLocation(shader, origin);
        renderBackground(shader);
    }
}
