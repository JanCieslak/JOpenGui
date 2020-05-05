package test;

import jopengui.nodes.Button;
import jopengui.nodes.Node;
import org.joml.Vector2f;
import org.joml.Vector3f;
import utilClasses.Window;

import java.util.List;
import java.util.Random;

public class ButtonTest extends AbstractTest {
    public static void main(String[] args) throws Exception {
        AbstractTest.test(new ButtonTest());
    }

    @Override
    public List<Node> initialize() {
        final Button button = new Button(Window.WIDTH / 2 - 100, Window.HEIGHT / 2 - 50, 200, 100);
        button.setBackgroundColor(new Vector3f(0.5f));
        button.onButtonPressed(() -> {
            button.setPosition(new Vector2f((float) Math.random() * (Window.WIDTH - button.getSize().x), (float) Math.random() * (Window.HEIGHT - button.getSize().y)));
        });
        return List.of(button);
    }

    @Override
    public void update(double dt) {

    }
}
