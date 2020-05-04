package jopengui.gfx;

import jopengui.nodes.Node;
import org.joml.Matrix4f;
import utilClasses.Window;

import java.util.List;

public class GuiRenderer {
    List<Node> nodeList;
    GuiShader shader;

    public GuiRenderer(List<Node> nodeList) {
        this.nodeList = nodeList;
        shader = new GuiShader();
        shader.bind();
        shader.loadMatrix("projection", new Matrix4f().ortho(0, Window.WIDTH, Window.HEIGHT,0, -1.0f, 1.0f));
    }

    public void handleEvents() {
        for (var node : nodeList) {
            node.handleEvents();
        }
    }

    public void render() {
        shader.bind();
        for (var node : nodeList) {
            Matrix4f initialModel = new Matrix4f();
            node.render(shader, initialModel);
        }
    }

    public void dispose() {
        shader.dispose();
    }
}
