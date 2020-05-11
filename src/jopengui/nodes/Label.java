package jopengui.nodes;

import jopengui.gfx.GuiShader;
import jopengui.utils.Maths;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.awt.*;

public class Label extends Node {
    private Text text;
    private Font defaultFont = new Font("Consolas", Font.PLAIN, 32);

    public Label(float x, float y, float w, float h, String text) {
        super(x, y, w, h);
        this.text = new Text(0, 0, text, defaultFont);

        // todo text height (have to place text +5.0f because of wrong text height
        this.text.setPosition(new Vector2f(w / 2 - this.text.getSize().x / 2, h / 2 - (this.text.getSize().y / 2) + 5.0f));
    }

    @Override
    public void render(GuiShader shader, Matrix4f parentModel) {
        Matrix4f thisModel = Maths.createModelMatrix(parentModel, this.position, this.rotation, this.scale);
        shader.loadModel(thisModel);
        renderBackground(shader);

        text.render(shader, thisModel);
    }
}
