package frc.robot.simulator.sim.field.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.List;

public abstract class PhysicsBody {
    protected Body body;
    protected World world;
    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;

    protected float width;
    protected float height;

    public PhysicsBody(World world, float x, float y, float width, float height) {
        this.world = world;
        this.width = width;
        this.height = height;

        // Create the BodyDef  We will fill them in with init fuctnions
        bodyDef = new BodyDef();
        initBodyDef();

        // now that we have a body def, create a body in the world
        body = world.createBody(bodyDef);

        // create a fixture def for our shapes
        fixtureDef = new FixtureDef();
        initFixtureDef();

        // create shapes and fixtures from them
        List<Shape> shapes = createShapes();
        createFixtures(shapes);

        // dispose the shapes
        for (Shape shape : shapes) {
            shape.dispose();
        }

        // move the body to x,y in world space
        body.setTransform(x, y, 0);

    }

    /**
     * Initialize the body def, override to do something other than setting it to a
     * DynamicBody
     */
    protected void initBodyDef() {
        bodyDef.type = BodyDef.BodyType.DynamicBody;
    }

    /**
     * Initialize the fixtureDef with friction, density and restitution values
     */
    protected abstract void initFixtureDef();

    /**
     * Create one or more shapes for this body's fixtures
     */
    protected List<Shape> createShapes() {
        List<Shape> shapes = new ArrayList<>();
        Shape shape = createShape();
        shapes.add(shape);
        return shapes;
    }

    protected abstract Shape createShape();

    /**
     * Create fixtures from every shape
     * @param shapes
     */
    protected void createFixtures(List<Shape> shapes) {
        for (Shape shape : shapes) {
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }
    }

    public void setTransform(float x, float y, float angle) {
        body.setTransform(x, y, angle);
    }

    public void setRotation(float angle) {
        body.setTransform(body.getTransform().getPosition().x, body.getTransform().getPosition().y, angle);
    }

    public Transform getTransform() {
        return body.getTransform();
    }

    /**
     * get the lateral velocity of a body as a new vector
     * @return
     */
    Vector2 getLateralVelocity() {
        Vector2 currentRightNormal = body.getWorldVector(new Vector2(1,0));
        float dot = currentRightNormal.dot(body.getLinearVelocity());
        return new Vector2(currentRightNormal.x * dot, currentRightNormal.y * dot);
    }

    /**
     * get the forward velocity of the body as a new vector
     * @return
     */
    Vector2 getForwardVelocity() {
        Vector2 currentForwardNormal = body.getWorldVector(new Vector2(0,1));
        float dot = currentForwardNormal.dot(body.getLinearVelocity());
        return new Vector2(currentForwardNormal.x * dot, currentForwardNormal.y * dot);
    }

}
