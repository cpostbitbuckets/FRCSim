package frc.robot.simulator.sim;

import frc.robot.simulator.network.proto.RobotProto;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimMotControllerTest {

    @Test
    public void convertPIDValueToRadians() {
        // if we want 10% power at 8192 ticks (1 revolution) away
        // expect 10% power per radian
        assertEquals(.1 / (2 * Math.PI) / 10, SimMotController.convertPIDValueToRadians(.1 * 1023 / 8192, 2048, RobotProto.MotorConfig.Encoder.QuadEncoder), 0);
    }
}
