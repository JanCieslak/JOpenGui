package test;

import jopengui.nodes.Label;
import jopengui.nodes.Node;
import jopengui.nodes.Text;
import org.joml.Vector3f;
import utilClasses.Window;

import java.util.List;

public class LabelTest extends AbstractTest {
    private Label label;

    public static void main(String[] args) throws Exception {
        AbstractTest.test(new LabelTest());
    }

    @Override
    public List<Node> initialize() {
        label = new Label(Window.WIDTH / 2 - 400 / 2, Window.HEIGHT / 2 - 50 / 2, 400, 50, "Testing Label Node");
        label.setBackgroundColor(new Vector3f(0.4f));

        return List.of(label);
    }

    @Override
    public void update(double dt) {

    }
}
