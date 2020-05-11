package test;

import jopengui.nodes.Node;
import jopengui.nodes.Rectangle;
import jopengui.nodes.containers.NodeList;
import org.joml.Vector3f;
import utilClasses.Window;

import java.util.List;

public class NodeListTest extends AbstractTest {
    private NodeList nodeList;
    private Rectangle rect1;
    private Rectangle separator;
    private Rectangle rect2;

    public static void main(String[] args) throws Exception {
        AbstractTest.test(new NodeListTest());
    }

    @Override
    public List<Node> initialize() {
        nodeList = new NodeList(50, 50, 300, Window.HEIGHT - 100);
        rect1 = new Rectangle(50, 50, 100, 200);
        separator = new Rectangle(20, 0, 130, 10);
        rect2 = new Rectangle(20, 0, 100, 100);

        nodeList.setBackgroundColor(new Vector3f(0.4f));
        rect1.setBackgroundColor(new Vector3f(0.5f));
        separator.setBackgroundColor(new Vector3f(0.2f));
        rect2.setBackgroundColor(new Vector3f(0.6f));

        nodeList.addChildren(rect1, separator, rect2);
        return List.of(nodeList);
    }

    @Override
    public void update(double dt) {

    }
}
