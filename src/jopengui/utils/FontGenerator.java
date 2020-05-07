package jopengui.utils;

import jopengui.gfx.AbstractTexture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FontGenerator {
    public static Map<Character, CharInfo> generate(Font font, Charset charset, String fontFilename) {
        // temporary image just to get the font metrics
        final BufferedImage tempImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D gfx = tempImg.createGraphics();
        gfx.setFont(font);
        final FontMetrics fm = gfx.getFontMetrics();

        String chars = getAllAvailableChars(charset);
        int width = 0;
        int height = fm.getHeight();
        Map<Character, CharInfo> charMap = new HashMap<>();

        for (char ch : chars.toCharArray()) {
            final CharInfo charInfo = new CharInfo(width, fm.charWidth(ch));
            charMap.put(ch, charInfo);
            width += fm.charWidth(ch);
        }
        gfx.dispose();

        // make font texture
        final BufferedImage textureImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D textureGfx = textureImage.createGraphics();
        textureGfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        textureGfx.setFont(font);
        final FontMetrics textureFm = textureGfx.getFontMetrics();
        textureGfx.setColor(Color.WHITE);
        textureGfx.drawString(chars, 0, textureFm.getAscent());
        textureGfx.dispose();
        try {
            ImageIO.write(textureImage, "png", new File("resources/fonts/" + fontFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return charMap;
    }

    private static String getAllAvailableChars(Charset charset) {
        final CharsetEncoder encoder = charset.newEncoder();
        StringBuilder sb = new StringBuilder();
        for (char c = 0; c < Character.MAX_VALUE; c++) {
            if (encoder.canEncode(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
