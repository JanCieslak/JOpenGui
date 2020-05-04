package jopengui.nodes.containers;

import jopengui.gfx.GuiShader;
import jopengui.nodes.Node;
import jopengui.utils.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class NodeList extends Node {
    public NodeList(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    @Override
    public void render(GuiShader shader, Matrix4f parentModel) {
        Matrix4f thisModel = Maths.createModelMatrix(parentModel, this.position, this.rotation, this.scale);
        shader.loadModel(thisModel);
        renderBackground(shader);

        float yOffset = 0.0f;

        for (int i = 0; i < children.size(); i++) {
            Matrix4f offsetModel = new Matrix4f(thisModel).translate(0.0f, yOffset, 0.0f);
            shader.loadModel(offsetModel);
            children.get(i).render(shader, offsetModel);
            yOffset += children.get(i).getPosition().y + children.get(i).getSize().y;
        }
    }
}
