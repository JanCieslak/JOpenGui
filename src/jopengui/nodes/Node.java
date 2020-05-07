package jopengui.nodes;

import jopengui.gfx.GuiShader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;

public abstract class Node {
    protected Vector2f position;
    protected float rotation = 0.0f;
    protected Vector2f scale = new Vector2f(1.0f);
    protected Vector2f size;
    protected Vector3f backgroundColor = new Vector3f(1.0f, 0.0f, 1.0f); // magenta
    protected List<Node> children;
    protected Node parent = null;
    private int vao;

    public Node(float x, float y) {
        this.position = new Vector2f(x, y);
        this.size = new Vector2f();
        children = new ArrayList<>();

        vao = -1;
    }

    public Node(float x, float y, float w, float h) {
        this.position = new Vector2f(x, y);
        this.size = new Vector2f(w, h);
        children = new ArrayList<>();

        // todo indices rendering
        float[] vertices = new float[] {
                0.0f, 0.0f,
                0 + size.x, 0 + size.y,
                0, 0 + size.y,
                0, 0,
                0 + size.x, 0 + size.y,
                0 + size.x, 0 };

        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        storeDataInAttribList(0, 2, vertices);
        glBindVertexArray(0);
    }

    // todo cleanup
    // return vboId in order to delete the VBOs after assigning them to VAO
    protected void storeDataInAttribList(int attribNumber, int attribSize, float[] data) {
        int vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attribNumber, attribSize, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    protected void renderBackground(GuiShader shader) {
        // todo in case renderBackground would be called from node that doesn't have a background quad
        // after node dev delete or profile and check how it affects the framerate with big amount of nodes
        if (vao == -1)
            throw new RuntimeException("This node doesn't have a background quad");

        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        shader.loadColor(backgroundColor);
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }

    public abstract void render(GuiShader shader, Matrix4f parentModel);

    public void handleEvents() {
        for (var child : children) {
            child.handleEvents();
        }
    }

    public void addChild(Node child) {
        child.parent = this;
        children.add(child);
    }

    public void addChildren(Node... childrenArr) {
        for (var child : childrenArr) {
            child.parent = this;
            children.add(child);
        }
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setBackgroundColor(Vector3f backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getSize() {
        return size;
    }

    public Node getParent() {
        return parent;
    }

    public Vector3f getBackgroundColor() {
        return backgroundColor;
    }
}
