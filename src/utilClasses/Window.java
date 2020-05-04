package utilClasses;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    public static long windowID;

    public static int WIDTH;
    public static int HEIGHT;

    public Window(String title, int width, int height) {
        GLFWErrorCallback.createPrint(System.out).set();

        WIDTH = width;
        HEIGHT = height;

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        windowID = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowID == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            glfwGetWindowSize(windowID, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    windowID,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(windowID);
        glfwSwapInterval(0);
        glfwShowWindow(windowID);

        GL.createCapabilities();

        glfwSetInputMode(windowID, GLFW_STICKY_KEYS, GLFW_TRUE);
        setupCallbacks();

        glViewport(0, 0, width, height);
    }

    public void displayAndPollEvents() {
        glfwSwapBuffers(windowID);
        glfwPollEvents();
    }

    public void prepare() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(windowID);
    }

    public void setupCallbacks() {
        GLFWWindowSizeCallback.create(((window, width, height) -> {
            WIDTH = width;
            HEIGHT = height;
            glViewport(0, 0, width, height);
        })).set(windowID);

        GLFWKeyCallback.create(((window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
                glfwSetWindowShouldClose(windowID, true);
        })).set(windowID);
    }
}
