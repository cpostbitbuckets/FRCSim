package frc.robot.simulator.sim.ui;

import frc.robot.simulator.sim.RobotPosition;
import frc.robot.simulator.sim.field.box2d.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FieldPanel extends JPanel {
    private static final Logger log = LoggerFactory.getLogger(FieldPanel.class);

    private final Image fieldImage;
    private Map<RobotPosition.Type, RobotPosition> robotPositionsByType = new HashMap();


    public FieldPanel() throws IOException {
        try (InputStream stream = FieldPanel.class.getResourceAsStream("/2020-Field.png")) {
            fieldImage = ImageIO.read(stream);
        };

        setPreferredSize(new Dimension(fieldImage.getWidth(null), fieldImage.getHeight(null)));
    }

    @Override
    public void paint(Graphics graphics) {

        if (fieldImage == null) {
            return;
        }

        graphics.clearRect(0, 0, getWidth(), getHeight());
        graphics.setColor(getBackground());
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.drawImage(fieldImage, 0, 0, null);

        synchronized (this.robotPositionsByType) {

            for (RobotPosition robotPosition : robotPositionsByType.values()) {
                if (robotPosition != null) {
                    Graphics2D g2d = (Graphics2D) graphics;
                    switch (robotPosition.type) {

                        case WheelDisplacement:
                            g2d.setColor(Color.GREEN);
                            break;
                        case Box2d:
                            g2d.setColor(Color.RED);
                            break;
                        case HowardPhysics:
                            g2d.setColor(Color.BLUE);
                            break;
                    }

                    // figure out where this RobotPosition goes on our Field image
                    int width = (int) (0.3145536 * 2 / Field.metersPerPixelWidth);
                    int height = (int) (0.3145536 * 2 / Field.metersPerPixelHeight);
                    int x = (int) (robotPosition.x / Field.metersPerPixelWidth) + (int) Field.imageSize.x / 2;
                    int y = (int) Field.imageSize.y / 2 - (int) (robotPosition.y / Field.metersPerPixelHeight);
                    Rectangle rect2 = new Rectangle(x, y, width, height);

                    // rotate the graphics instance to draw this rect
                    g2d.rotate(robotPosition.heading, rect2.x + rect2.width / 2, rect2.y + rect2.height / 2);
                    g2d.draw(rect2);
                    g2d.fill(rect2);

                    // reset the g2d rotation for the next render
                    g2d.rotate(-robotPosition.heading, rect2.x + rect2.width / 2, rect2.y + rect2.height / 2);
                }
            }
        }
    }

    public void update(RobotPosition robotPosition) {
        synchronized (this.robotPositionsByType) {
            // for now, only show the wheel displacement sim
            if (robotPosition.type == RobotPosition.Type.WheelDisplacement) {
                this.robotPositionsByType.put(robotPosition.type, robotPosition);
            }
        }
    }
}
