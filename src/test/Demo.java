package test;

import jopengui.nodes.Container;
import jopengui.gfx.GuiRenderer;
import org.joml.Vector3f;
import utilClasses.Window;

import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.opengl.GL11C.glClearColor;

public class Demo {
    public static void main(String[] args) {
        Window window = new Window("Demo", 1600, 800);

        Container container1 = new Container(0, 50, 400, 600);
        Container container2 = new Container(50, 50, 200, 200);
        Container container3 = new Container(50, 50, 50, 50);
        container1.setBackgroundColor(new Vector3f(0.4f));
        container2.setBackgroundColor(new Vector3f(0.5f));
        container3.setBackgroundColor(new Vector3f(0.6f));

        container1.addChild(container2);
        container2.addChild(container3);

        GuiRenderer guiRenderer = new GuiRenderer(List.of(container1));

        glClearColor(0.2f, 0.2f, 0.2f, 1.0f);

        double angle = 0.0;

        double lastTime = glfwGetTime();
        double previousTime = glfwGetTime();
        int frameCount = 0;

        while (!window.shouldClose()) {
            window.prepare();
            guiRenderer.handleEvents();

            double currentTime = glfwGetTime();
            double dt = currentTime - lastTime;
            lastTime = currentTime;

            // noice effects
//            container1.getPosition().x += Math.sin(Math.toRadians(angle)) * 300.0f * dt;
//            container2.getPosition().x += Math.sin(Math.toRadians(angle)) * 110.0f * dt;
//            container3.getPosition().x += Math.sin(Math.toRadians(angle)) * 70.0f * dt;
            guiRenderer.render();

            System.out.println(Math.sin(Math.toRadians(angle)));

            angle += 0.01;
            window.displayAndPollEvents();
            frameCount++;

            if (currentTime - previousTime >= 1.0) {
                glfwSetWindowTitle(Window.windowID, "Fps: " + frameCount);
                previousTime = currentTime;
                frameCount = 0;
            }
        }

        guiRenderer.dispose();
    }
}
