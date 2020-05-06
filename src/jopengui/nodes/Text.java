package jopengui.nodes;

import jopengui.gfx.GuiShader;
import jopengui.gfx.GuiTextShader;
import jopengui.gfx.GuiTextTexture;
import jopengui.utils.CharInfo;
import jopengui.utils.Maths;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import utilClasses.Window;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;
import static org.lwjgl.opengl.GL20C.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;

public class Text extends Node {
    private GuiTextShader textShader;
    //private GuiTexture texture;
    private GuiTextTexture texture;
    private String text;
    private int textVao;

    public Text(float x, float y, String text) {
        super(x, y);
        this.text = text;
        this.textShader = new GuiTextShader();

        textShader.bind();
        textShader.loadMatrix("projection", new Matrix4f().ortho(0, Window.WIDTH, Window.HEIGHT,0, -1.0f, 1.0f));
        textShader.unbind();

        final Font font = new Font("consolas", Font.PLAIN, 70);
        this.texture = new GuiTextTexture(font, StandardCharsets.ISO_8859_1);

        final CharInfo charInfo = this.texture.getInfo('J');
        float charWidth = charInfo.width;
        float charStartX = charInfo.startX;

        float[] positions = new float[] {
                0.0f, 0.0f,
                0.0f + charWidth, 0.0f + texture.getHeight(),
                0.0f, 0.0f + texture.getHeight(),

                0.0f, 0.0f,
                0.0f + charWidth, 0.0f + texture.getHeight(),
                0.0f + charWidth, 0.0f
        };

        float[] texCoords = new float[] {
                charStartX / texture.getWidth(), 0.0f,
                (charStartX + charWidth) / texture.getWidth(), 1.0f,
                charStartX / texture.getWidth(), 1.0f,

                charStartX / texture.getWidth(), 0.0f,
                (charStartX + charWidth) / texture.getWidth(), 1.0f,
                (charStartX + charWidth) / texture.getWidth(), 0.0f,
        };

        textVao = glGenVertexArrays();
        glBindVertexArray(textVao);
        storeDataInAttribList(0, 2, positions);
        storeDataInAttribList(1, 2, texCoords);
        glBindVertexArray(0);
    }

    @Override
    public void render(GuiShader shader, Matrix4f parentModel) {
        Matrix4f thisModel = Maths.createModelMatrix(parentModel, this.position, this.rotation, this.scale);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        textShader.bind();
        textShader.loadMatrix("model", thisModel);

        glBindVertexArray(textVao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
        glDrawArrays(GL_TRIANGLES, 0, 6);

        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        textShader.unbind();
        glDisable(GL_BLEND);

        shader.bind();
    }


}
