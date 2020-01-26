# Subsystems

Subsystems are basic building block of robot code. In this lesson we are going to build a new elevator Subsystem with Commands to raise and lower the elevator both manually and aligned to specific levels.

## Goals

- Create a new Subsystem package
- Create an elevator Subsystem
- Create an Idle Command
- Create a Command to raise the elevator to 3 different levels
- Create a Command to manually control the elevator

## Tips

- A java package is just a folder

### Motor Config Tips

- `motor.configFactoryDefault()` resets a motor's config to default settings and should always be called on initialization
- `motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0)` will tell a motor that it has an encoder to record position
- `motor.configMotionCruiseVelocity(40000);` and `motor.configMotionAcceleration(6000);` will tell a motor how fast to spin and how fast to accelerate to a speed
- `motor.follow(otherMotor);` will tell a motor to follow a 'leader' motor. You only have to configure the leader

* `motor.set(ControlMode.MotionMagic, targetPosition);` will tell a motor to move to a position, in ticks

### Subsystems

### Commands

Commands are the basic building blocks of Subsystems. They represent a single command to a robot, like 'hold position' or 'open claw'.

- Command should extend the Command class.
- Commands `require` subsystems. Any required subsystems should be passed in as a constructor argument
- Command's start() function is called to add a command to the scheduler
- Some commands finish, others run until interrupted

```java
public class IdleCommand extends Command {
    // ... your code here
}
```
