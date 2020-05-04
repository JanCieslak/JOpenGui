package jopengui.nodes;

import jopengui.gfx.GuiShader;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import utilClasses.Window;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Button extends Node {
    private Vector3f savedBackgroundColor;
    private boolean wasPressed = false;
    private Vector3f translation = new Vector3f();
    private Action onClick = () -> {};

    public Button(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    @Override
    public void handleEvents() {
        if (glfwGetMouseButton(Window.windowID, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS && !wasPressed) {
            wasPressed = true;
            DoubleBuffer mousePosX = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer mousePosY = BufferUtils.createDoubleBuffer(1);
            glfwGetCursorPos(Window.windowID, mousePosX, mousePosY);
            double mouseX = mousePosX.get(0);
            double mouseY = mousePosY.get(0);

            // Mouse-box Collision
            if (mouseX > translation.x + this.position.x && mouseX < translation.x + this.position.x + this.size.x &&
                mouseY > translation.y + this.position.y && mouseY < translation.y + this.position.y + this.size.y) {

                savedBackgroundColor = this.getBackgroundColor();
                this.setBackgroundColor(new Vector3f(savedBackgroundColor.x + 0.1f, savedBackgroundColor.y + 0.1f, savedBackgroundColor.z + 0.1f));

                onClick.invoke();
            }
        } else if (glfwGetMouseButton(Window.windowID, GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE && wasPressed) {
            if (savedBackgroundColor != null)
                this.setBackgroundColor(savedBackgroundColor);
            wasPressed = false;
        }
    }

    @Override
    public void render(GuiShader shader, Matrix4f origin, Node parent) {
        renderBackground(shader);

        origin.getTranslation(translation);
    }

    public void onButtonPressed(Action onClick) {
        this.onClick = onClick;
    }
}
