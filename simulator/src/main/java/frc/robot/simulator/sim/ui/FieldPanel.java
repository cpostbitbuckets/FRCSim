package frc.robot.simulator.sim.ui;

import frc.robot.simulator.sim.RobotPosition;
import frc.robot.simulator.sim.events.EventManager;
import frc.robot.simulator.sim.events.FieldRenderEvent;
import frc.robot.simulator.sim.field.wheeldisplacement.Field;
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
                    }

                    // figure out where this RobotPosition goes on our Field image
                    int width = (int) (0.3145536 * 2 / Field.metersPerPixelWidth);
                    int height = (int) (0.3145536 * 2 / Field.metersPerPixelHeight);
                    int x = (int) (robotPosition.x / Field.metersPerPixelWidth) + (int) Field.imageSize.x / 2;
                    int y = (int) Field.imageSize.y / 2 - (int) (robotPosition.y / Field.metersPerPixelHeight);
                    Rectangle robotRect = new Rectangle(x - width / 2, y - height / 2, width, height);

                    // rotate the graphics instance to draw this rect
                    g2d.rotate(robotPosition.heading, robotRect.x + robotRect.width / 2, robotRect.y + robotRect.height / 2);
                    g2d.fill(robotRect);
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.draw(robotRect);

                    // reset the g2d rotation for the next render
                    g2d.rotate(-robotPosition.heading, robotRect.x + robotRect.width / 2, robotRect.y + robotRect.height / 2);

                    EventManager.publish(new FieldRenderEvent(this, g2d, robotRect, robotPosition.heading));
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
