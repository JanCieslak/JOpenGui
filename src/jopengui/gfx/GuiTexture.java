package jopengui.gfx;

import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_2D;
import static org.lwjgl.stb.STBImage.*;

public class GuiTexture {
    private int textureID;
    private int width;
    private int height;

    public GuiTexture(String textureFilepath) {
        this.textureID = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer components = stack.mallocInt(1);

            stbi_set_flip_vertically_on_load(false);
            ByteBuffer image = stbi_load("resources/" + textureFilepath, w, h, components, 4);
            if (image == null)
                throw new RuntimeException("Failed to load a texture file!" + System.lineSeparator() + stbi_failure_reason());

            width = w.get();
            height = h.get();
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            glBindTexture(GL_TEXTURE_2D, 0);
        }
    }

    public int getTextureID() {
        return textureID;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
