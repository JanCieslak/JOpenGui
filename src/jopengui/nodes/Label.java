package jopengui.nodes;

import jopengui.gfx.GuiShader;
import jopengui.gfx.GuiTexture;
import jopengui.utils.Maths;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Label extends Node {
    private GuiTexture texture;
    private String text;

    public Label(float x, float y, float w, float h, String text, String fontTextureName, int cols, int rows) {
        super(x, y, w, h);
        this.text = text;
        this.texture = new GuiTexture("fonts/" + fontTextureName);
        loadMesh(text, cols, rows);
    }

    @Override
    public void render(GuiShader shader, Matrix4f parentModel) {
        Matrix4f thisModel = Maths.createModelMatrix(parentModel, this.position, this.rotation, this.scale);
        shader.loadModel(thisModel);
        renderBackground(shader);


    }

    private void loadMesh(String text, int cols, int rows) {
        byte[] chars = text.getBytes(StandardCharsets.ISO_8859_1);
        int numChars = chars.length;

        List<Vector2f> positions = new ArrayList<>();
        List<Vector2f> texCoords = new ArrayList<>();

        float tileWidth = texture.getWidth() / (float) cols;
        float tileHeight = texture.getHeight() / (float) rows;

        for (int i = 0; i < numChars; i++) {

        }
    }


}
