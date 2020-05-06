package test;

import jopengui.nodes.Text;
import jopengui.nodes.Node;

import java.util.List;

public class TextTest extends AbstractTest {
    private Text text;

    public static void main(String[] args) throws Exception {
        AbstractTest.test(new TextTest());
    }

    @Override
    public List<Node> initialize() {
        text = new Text(100, 100, 100, 100, "Siemson");
        return List.of(text);
    }

    @Override
    public void update(double dt) {

    }
}
