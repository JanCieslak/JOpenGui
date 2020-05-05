package test;

import jopengui.nodes.Container;
import jopengui.nodes.Node;
import org.joml.Vector3f;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ContainerTest extends AbstractTest {
    private double angle = 0.0;
    private Container container1;
    private Container container2;
    private Container container3;

    public static void main(String[] args) throws Exception {
        AbstractTest.test(new ContainerTest());
    }

    @Override
    public List<Node> initialize() {
        container1 = new Container(50, 50, 400, 600);
        container2 = new Container(50, 50, 200, 200);
        container3 = new Container(50, 50, 50, 50);
        container1.setBackgroundColor(new Vector3f(0.4f));
        container2.setBackgroundColor(new Vector3f(0.5f));
        container3.setBackgroundColor(new Vector3f(0.6f));

        container1.addChild(container2);
        container2.addChild(container3);

        return List.of(container1);
    }

    @Override
    public void update(double dt) {
        container1.getPosition().x += Math.sin(Math.toRadians(angle)) * 300.0f * dt;
        container2.getPosition().x += Math.sin(Math.toRadians(angle)) * 110.0f * dt;
        container3.getPosition().x += Math.sin(Math.toRadians(angle)) * 70.0f * dt;

        angle += 0.01;
    }
}
