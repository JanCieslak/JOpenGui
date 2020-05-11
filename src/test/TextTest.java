package test;

import jopengui.nodes.Node;
import jopengui.nodes.Text;
import org.joml.Vector2f;
import utilClasses.Window;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TextTest extends AbstractTest {
    private Text fpsCounter;
    private int savedFrameCount = 0;

    public static void main(String[] args) throws Exception {
        AbstractTest.test(new TextTest());
    }

    @Override
    public List<Node> initialize() {
        final Text text = new Text(0, 0, "Text testing", new Font("consolas", Font.BOLD, 64));

        final Font consolas22 = new Font("consolas", Font.PLAIN, 22);

        final Text author = new Text(0, 0, "Jan Cieslak", consolas22);
        fpsCounter = new Text(15, author.getSize().y - author.getAscent(), "FPS: 0", consolas22);

        text.setPosition(new Vector2f(Window.WIDTH / 2.0f - text.getSize().x / 2.0f, Window.HEIGHT / 2.0f - text.getAscent() / 2.0f));
        author.setPosition(new Vector2f(Window.WIDTH - author.getSize().x - 15.0f, Window.HEIGHT - author.getSize().y));

        return List.of(text, fpsCounter, author);
    }

    @Override
    public void update(double dt) {
        if (frameCount == 0)
            fpsCounter.setText("FPS: " + savedFrameCount);
        savedFrameCount = frameCount;
    }
}
