package frc.robot.simulator.sim.field.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

/**
 * A physics body  of the field, based on the 2d image
 * TODO: Move this image to meters stuff into a separate class, it doesn't belong in a Physics Body
 */
public class Field extends PhysicsBody {
    public static final float INCHES_TO_METERS = 0.0254f;

    public static final Vector2 imageSize = new Vector2(540/2, 1134/2);
    public static final Vector2 topLeft = new Vector2(25/2, 96/2);
    public static final Vector2 bottomRight = new Vector2(514/2, 1040/2);

    // feet per pixel is width in feet divided by width of inner field pixels
    public static final float ftPerPixelWidth = 26.9375f / (bottomRight.x - topLeft.x);
    public static final float inchesPerPixelWidth = ftPerPixelWidth * 12;
    public static final float metersPerPixelWidth = inchesPerPixelWidth * INCHES_TO_METERS;
    public static final float ftPerPixelHeight = 52.4375f / (bottomRight.y - topLeft.y);
    public static final float inchesPerPixelHeight = ftPerPixelHeight * 12;
    public static final float metersPerPixelHeight = inchesPerPixelHeight * INCHES_TO_METERS;

    // set the size of the field, in meters, to the size of the image, based on what
    // we know of the inner size in feet. yikes
    public static final float width = inchesPerPixelWidth * imageSize.x * INCHES_TO_METERS;
    public static final float height = inchesPerPixelHeight * imageSize.y * INCHES_TO_METERS;

    public Field(World world, float x, float y, float width, float height) {
        super(world, x, y, width, height);
    }

    /**
     * Override body def to be a static. We don't want our walls or structures to
     * move (this time)
     */
    protected void initBodyDef() {
        bodyDef.type = BodyDef.BodyType.StaticBody;
    }

    @Override
    protected void initFixtureDef() {
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0;
    }

    /**
     * This is a bit of a hack, but composite shapes override createShapes() and
     * don't call createShape
     */
    @Override
    protected Shape createShape() {
        throw new IllegalArgumentException("Composite shapes don't use createShape");
    }

    /**
     * The various borders are all in pixels (from the image), then translated into
     * meters
     */
    @Override
    protected List<Shape> createShapes() {
        PolygonShape topBorder = new PolygonShape();
        float topBorderHeight = topLeft.y * metersPerPixelHeight;
        Vector2 topCenter = new Vector2(0, height / 2 - topBorderHeight / 2);
        topBorder.setAsBox(width / 2, topBorderHeight / 2, topCenter, 0);

        PolygonShape bottomBorder = new PolygonShape();
        float bottomBorderHeight = (imageSize.y - bottomRight.y) * metersPerPixelHeight;
        Vector2 bottomCenter = new Vector2(0, -height / 2 + bottomBorderHeight / 2);
        bottomBorder.setAsBox(width / 2, bottomBorderHeight / 2, bottomCenter, 0);

        PolygonShape leftBorder = new PolygonShape();
        float leftBorderWidth = topLeft.x * metersPerPixelWidth;
        Vector2 leftCenter = new Vector2(-width / 2 + leftBorderWidth / 2, 0);
        leftBorder.setAsBox(leftBorderWidth / 2, height / 2, leftCenter, 0);

        PolygonShape rightBorder = new PolygonShape();
        float rightBorderWidth = (imageSize.x - bottomRight.x) * metersPerPixelWidth;
        Vector2 rightCenter = new Vector2(width / 2 - rightBorderWidth / 2, 0);
        rightBorder.setAsBox(rightBorderWidth / 2, height / 2, rightCenter, 0);


        List<Shape> shapes = new ArrayList<>();
        shapes.add(topBorder);
        shapes.add(bottomBorder);
        shapes.add(leftBorder);
        shapes.add(rightBorder);

        return shapes;
    }

    public static Vector2 getFieldMetersForPixels(int x, int y) {
        return new Vector2(x * metersPerPixelWidth, y * metersPerPixelHeight);
    }

}
