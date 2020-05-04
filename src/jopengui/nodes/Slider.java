package jopengui.nodes;

import jopengui.gfx.GuiShader;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import utilClasses.Window;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Slider extends Node {
    private boolean init = true;

    private float min;
    private float max;

    private float offset;
    private float maxOffset;

    // LINE
    private Rectangle line;
    private static final float INSET = 10.0f;
    private static final float LINE_THICKNESS = 4.0f;
    private static final Vector3f LINE_COLOR = new Vector3f(0.5f, 0.4f, 0.9f);

    // MARKER
    private Rectangle marker;
    private static final float MARKER_WIDTH = 10.0f;
    private static final float MARKER_HEIGHT = 20.0f;
    private static final Vector3f MARKER_COLOR = new Vector3f(0.8f, 0.7f, 0.5f);


    public Slider(float x, float y, float w, float h, float min, float max) {
        super(x, y, w, h);
        this.min = min;
        this.max = max;

        line = new Rectangle(x + INSET,y + h/2 - LINE_THICKNESS/2, w - INSET*2, LINE_THICKNESS);
        line.setBackgroundColor(LINE_COLOR);

        marker = new Rectangle(line.position.x - MARKER_WIDTH/2, line.position.y + LINE_THICKNESS/2 - MARKER_HEIGHT/2, MARKER_WIDTH, MARKER_HEIGHT);
        marker.setBackgroundColor(MARKER_COLOR);

        maxOffset = line.size.x;
    }

    @Override
    public void handleEvents() {
        if (glfwGetMouseButton(Window.windowID, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
            DoubleBuffer mousePosX = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer mousePosY = BufferUtils.createDoubleBuffer(1);
            glfwGetCursorPos(Window.windowID, mousePosX, mousePosY);
            double mouseX = mousePosX.get(0);
            double mouseY = mousePosY.get(0);

            Vector3f localTranslation = new Vector3f();
            localOrigin.getTranslation(localTranslation);
            System.out.println(marker.position.x);
            offset = (float) (mouseX - marker.position.x);
        }
    }

    @Override
    public void render(GuiShader shader, Matrix4f origin, Node parent) {
        bindLocation(shader, origin);
        renderBackground(shader);

        line.render(shader, new Matrix4f(localOrigin), null);

//        Matrix4f translate = new Matrix4f(localOrigin).translate(offset, 0, 0);
//        shader.loadMatrix("model", translate);


        marker.render(shader, new Matrix4f(localOrigin), null);
    }
}
