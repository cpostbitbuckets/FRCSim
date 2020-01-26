package frc.robot.simulator.sim.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConversionUtilsTest {

    @Test
    public void testRadiansToTicks() {
        assertEquals(8192, ConversionUtils.radiansToTicks(2*Math.PI, 8192));
        assertEquals(4096, ConversionUtils.radiansToTicks(Math.PI, 8192));
        assertEquals(81920, ConversionUtils.radiansToTicks(10*2*Math.PI, 8192));
    }

    @Test
    public void testTicksToRadians() {
        assertEquals(2*Math.PI, ConversionUtils.ticksToRadians(8192, 8192), 0);
    }

    @Test
    public void testPerSecondToPer100ms() {
        // 20k ticks per 100ms is 200k ticks per second
        assertEquals(1, ConversionUtils.perSecondToPer100ms(10), 0);
    }

    @Test
    public void testPer100msToPerSecond() {
        // 20k ticks per 100ms is 200k ticks per second
        assertEquals(10, ConversionUtils.per100msToPerSecond(1), 0);
    }

}
