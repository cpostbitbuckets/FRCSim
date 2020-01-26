package frc.robot.simulator.sim;

public class SimException extends RuntimeException {
    public SimException() {
        super();
    }

    public SimException(String message) {
        super(message);
    }

    public SimException(String message, Throwable cause) {
        super(message, cause);
    }
}
