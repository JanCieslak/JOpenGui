package jopengui.nodes;

import jopengui.gfx.GuiShader;
import jopengui.utils.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Container extends Node {
    public Container(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    @Override
    public void render(GuiShader shader, Matrix4f parentModel) {
        Matrix4f thisModel = Maths.createModelMatrix(parentModel, this.position, this.rotation, this.scale);
        shader.loadModel(thisModel);
        renderBackground(shader);

        for (var child : children) {
            child.render(shader, thisModel);
        }
    }
}
