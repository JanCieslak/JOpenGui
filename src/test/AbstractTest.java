package test;


import jopengui.gfx.GuiRenderer;
import jopengui.nodes.Node;
import utilClasses.Window;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.opengl.GL11C.glClearColor;

public abstract class AbstractTest {
    protected static void test(Object object) throws Exception {
        final Window window = new Window("Demo", 1600, 800);

        final Class<?> cls = object.getClass();
        final Method initialize = cls.getDeclaredMethod("initialize");
        final Method update = cls.getDeclaredMethod("update", double.class);

        final GuiRenderer renderer = new GuiRenderer((List<Node>) initialize.invoke(object));

        glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        double lastTime = glfwGetTime();
        double previousTime = glfwGetTime();
        int frameCount = 0;

        while (!window.shouldClose()) {
            window.prepare();
            renderer.handleEvents();

            final double currentTime = glfwGetTime();
            final double dt = currentTime - lastTime;
            lastTime = currentTime;

            update.invoke(object, dt);

            renderer.render();
            window.displayAndPollEvents();
            frameCount++;

            if (currentTime - previousTime >= 1.0) {
                glfwSetWindowTitle(Window.windowID, "Fps: " + frameCount);
                previousTime = currentTime;
                frameCount = 0;
            }
        }

        renderer.dispose();
    }

    public abstract List<Node> initialize();
    public abstract void update(double dt);
}
