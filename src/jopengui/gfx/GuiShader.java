package jopengui.gfx;

import org.joml.Vector3f;

public class GuiShader extends AbstractShader {
    public GuiShader() {
        super("guiVertex", "guiFragment");
    }

    public void loadColor(Vector3f color) {
        loadVector3f("color", color);
    }
}
