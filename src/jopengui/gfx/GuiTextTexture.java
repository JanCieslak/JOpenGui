package jopengui.gfx;

import jopengui.utils.CharInfo;
import jopengui.utils.FontGenerator;

import java.awt.*;
import java.nio.charset.Charset;
import java.util.Map;

public class GuiTextTexture extends AbstractTexture {
    private Map<Character, CharInfo> charMap;

    public GuiTextTexture(Font font, Charset charset) {
        charMap = FontGenerator.generate(font, charset, "temp.png");
        loadTexture("fonts/temp.png");
    }

    public CharInfo getInfo(Character c) {
        return charMap.get(c);
    }
}
