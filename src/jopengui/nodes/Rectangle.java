package jopengui.nodes;

import jopengui.gfx.GuiShader;
import jopengui.utils.Maths;
import org.joml.Matrix4f;

public class Rectangle extends Node {
    public Rectangle(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    @Override
    public void render(GuiShader shader, Matrix4f parentModel) {
        Matrix4f thisModel = Maths.createModelMatrix(parentModel, this.position, this.rotation, this.scale);
        shader.loadModel(thisModel);
        renderBackground(shader);
    }
}
