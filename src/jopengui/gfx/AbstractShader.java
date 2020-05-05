package jopengui.gfx;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL20C.glValidateProgram;

public abstract class AbstractShader {
    protected int programID;
    protected int vertexShaderID;
    protected int fragmentShaderID;

    protected Map<String, Integer> locations = new HashMap<>();
    protected static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public AbstractShader(String vertexName, String fragmentName) {
        vertexShaderID = loadShader("resources/shaders/" + vertexName + ".vert", GL_VERTEX_SHADER);
        fragmentShaderID = loadShader("resources/shaders/" + fragmentName + ".frag", GL_FRAGMENT_SHADER);
        programID = glCreateProgram();
        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);
        glLinkProgram(programID);
        glValidateProgram(programID);
    }

    public void loadInt(String name, int value) {
        glUniform1i(getLocation(name), value); }

    public void loadFloat(String name, float value) {
        glUniform1f(getLocation(name), value);
    }

    public void loadVector2f(String name, Vector2f vec) {
        glUniform2f(getLocation(name), vec.x, vec.y); }

    public void loadVector3f(String name, Vector3f vec) {
        glUniform3f(getLocation(name), vec.x, vec.y, vec.z);
    }

    public void loadBoolean(String name, boolean value) {
        glUniform1f(getLocation(name), value ? 1.0f : 0.0f);
    }

    public void loadMatrix(String name, Matrix4f mat4) {
        mat4.get(matrixBuffer);
        glUniformMatrix4fv(getLocation(name), false, matrixBuffer);
    }

    public void loadModel(Matrix4f model) {
        loadMatrix("model", model);
    }

    private int getLocation(String name) {
        if (locations.get(name) != null)
            return locations.get(name);

        locations.put(name, glGetUniformLocation(programID, name));
        return glGetUniformLocation(programID, name);
    }

    public void bind() {
        glUseProgram(programID);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void dispose() {
        glDetachShader(programID, vertexShaderID);
        glDetachShader(programID, fragmentShaderID);
        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);
        glDeleteProgram(programID);
    }

    private static int loadShader(String filepath, int type) {
        try {
            String shaderSource = new String(Files.readAllBytes(Paths.get(filepath)));
            int shaderID = glCreateShader(type);
            glShaderSource(shaderID, shaderSource);
            glCompileShader(shaderID);
            if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
                System.out.println(glGetShaderInfoLog(shaderID));
                System.err.println("Couldn't compile " + filepath + " shader");
                System.exit(-1);
            }

            return shaderID;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
