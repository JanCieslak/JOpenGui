package jopengui.nodes;

import jopengui.gfx.GuiShader;
import jopengui.gfx.GuiTextShader;
import jopengui.gfx.GuiTextTexture;
import jopengui.utils.CharInfo;
import jopengui.utils.Maths;
import jopengui.utils.Utils;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import utilClasses.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;
import static org.lwjgl.opengl.GL20C.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30C.*;

public class Text extends Node {
    private GuiTextShader textShader;
    private GuiTextTexture textTexture;
    private Font font;
    private Charset charset;
    private int textVao = -1;
    private int vertexCount = 0;

    public Text(float x, float y, String text, Font font) {
        super(x, y);
        this.font = font;
        this.textShader = new GuiTextShader();

        textShader.bind();
        textShader.loadMatrix("projection", new Matrix4f().ortho(0, Window.WIDTH, Window.HEIGHT,0, -1.0f, 1.0f));
        textShader.unbind();

        loadMesh(text, font);
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
        glBindTexture(GL_TEXTURE_2D, textTexture.getTextureID());
        glDrawArrays(GL_TRIANGLES, 0, vertexCount);

        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        textShader.unbind();
        glDisable(GL_BLEND);

        shader.bind();
    }

    public void setText(String text) {
        loadMesh(text, font);
    }

    public void setFont(Font font) {
        this.font = font;
    }

    private void loadMesh(String text, Font font) {
        this.textTexture = new GuiTextTexture(font);

        List<Float> positions = new ArrayList<>();
        List<Float> texCoords = new ArrayList<>();

        float xOffset = 0;
        for (char c : text.toCharArray()) {
            final CharInfo charInfo = this.textTexture.getInfo(c);
            float charWidth = charInfo.width;
            float charStartX = charInfo.startX;

            // left-top
            positions.add(0.0f + xOffset);
            positions.add(0.0f);
            texCoords.add(charStartX / textTexture.getWidth());
            texCoords.add(0.0f);

            // right-bottom
            positions.add(0.0f + xOffset + charWidth);
            positions.add(0.0f + textTexture.getHeight());
            texCoords.add((charStartX + charWidth) / textTexture.getWidth());
            texCoords.add(1.0f);

            // left-bottom
            positions.add(0.0f + xOffset);
            positions.add(0.0f + textTexture.getHeight());
            texCoords.add(charStartX / textTexture.getWidth());
            texCoords.add(1.0f);

            // left-top
            positions.add(0.0f + xOffset);
            positions.add(0.0f);
            texCoords.add(charStartX / textTexture.getWidth());
            texCoords.add(0.0f);

            // right-bottom
            positions.add(0.0f + xOffset + charWidth);
            positions.add(0.0f + textTexture.getHeight());
            texCoords.add((charStartX + charWidth) / textTexture.getWidth());
            texCoords.add(1.0f);

            // right-top
            positions.add(0.0f + xOffset + charWidth);
            positions.add(0.0f);
            texCoords.add((charStartX + charWidth) / textTexture.getWidth());
            texCoords.add(0.0f);

            xOffset += charWidth;
        }

        // todo change height to the largest char in text if possible
        this.size = new Vector2f(xOffset, textTexture.getHeight());

        float[] positionsArr = Utils.floatListToArray(positions);
        float[] texCoordsArr = Utils.floatListToArray(texCoords);

        vertexCount = positions.size() / 2;

        if (textVao != -1) {
            glDeleteVertexArrays(textVao);
        }

        textVao = glGenVertexArrays();
        glBindVertexArray(textVao);
        storeDataInAttribList(0, 2, positionsArr);
        storeDataInAttribList(1, 2, texCoordsArr);
        glBindVertexArray(0);
    }

    public int getAscent() {
        return textTexture.getAscent();
    }
}
