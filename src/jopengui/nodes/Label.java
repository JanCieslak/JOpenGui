package jopengui.nodes;

import jopengui.gfx.GuiShader;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL14C.GL_TEXTURE_LOD_BIAS;
import static org.lwjgl.opengl.GL30C.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

public class Label extends Node {
    private int textureID;

    public Label(float x, float y, float w, float h, String fontTextureName) {
        super(x, y, w, h);
        this.textureID = loadTexture(fontTextureName);
    }

    @Override
    public void render(GuiShader shader, Matrix4f parentModel) {

    }

    private int loadTexture(String fontTextureName) {
        int textureID = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer components = stack.mallocInt(1);

            stbi_set_flip_vertically_on_load(false);
            ByteBuffer image = stbi_load("resources/fonts/" + fontTextureName, w, h, components, 4);
            if (image == null)
                throw new RuntimeException("Failed to load a texture file!" + System.lineSeparator() + stbi_failure_reason());

            int width = w.get();
            int height = h.get();
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            glBindTexture(GL_TEXTURE_2D, 0);
        }

        return textureID;
    }
}
