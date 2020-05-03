package frc.robot.simulator.sim.field.wheeldisplacement;

import edu.wpi.first.wpilibj.drive.Vector2d;

/**
 * A physics body  of the field, based on the 2d image
 * TODO: Move this image to meters stuff into a separate class, it doesn't belong in a Physics Body
 */
public class Field {
    public static final float INCHES_TO_METERS = 0.0254f;

    public static final Vector2d imageSize = new Vector2d(540/2, 1134/2);
    public static final Vector2d topLeft = new Vector2d(25/2, 96/2);
    public static final Vector2d bottomRight = new Vector2d(514/2, 1040/2);

    // feet per pixel is width in feet divided by width of inner field pixels
    public static final double ftPerPixelWidth = 26.9375f / (bottomRight.x - topLeft.x);
    public static final double inchesPerPixelWidth = ftPerPixelWidth * 12;
    public static final double metersPerPixelWidth = inchesPerPixelWidth * INCHES_TO_METERS;
    public static final double ftPerPixelHeight = 52.4375f / (bottomRight.y - topLeft.y);
    public static final double inchesPerPixelHeight = ftPerPixelHeight * 12;
    public static final double metersPerPixelHeight = inchesPerPixelHeight * INCHES_TO_METERS;

    // set the size of the field, in meters, to the size of the image, based on what
    // we know of the inner size in feet. yikes
    public static final double width = inchesPerPixelWidth * imageSize.x * INCHES_TO_METERS;
    public static final double height = inchesPerPixelHeight * imageSize.y * INCHES_TO_METERS;

}
