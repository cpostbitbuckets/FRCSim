package frc.robot.simulator.sim.field.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class DriveBase extends PhysicsBody {

    // TODO: remove this
    private float maxDriveForce = 2f;

    public DriveBase(World world, float x, float y, float width, float height) {
        super(world, x, y, width, height);
    }

    @Override
    protected void initFixtureDef() {
        fixtureDef.density = 1;
        fixtureDef.friction = 1;
        fixtureDef.restitution = .2f;

    }

    @Override
    protected Shape createShape() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        return shape;
    }

    private Vector2 getForceVector(float factor) {
        Vector2 currentForwardNormal = body.getWorldVector(new Vector2(0, 1));
        Vector2 forceVector = currentForwardNormal.scl(maxDriveForce);
        forceVector = forceVector.scl(factor);
        return forceVector;
    }

    public void setFrontLeftMotorOutput(float motorOutput) {
        if (motorOutput != 0) {
            Vector2 forceVector = getForceVector(motorOutput);
            body.applyForce(forceVector, body.getWorldPoint(new Vector2(-width / 2, height / 2)), true);
        }
    }

    public void setFrontRightMotorOutput(float motorOutput) {
        if (motorOutput != 0) {
            Vector2 forceVector = getForceVector(motorOutput);
            body.applyForce(forceVector, body.getWorldPoint(new Vector2(width / 2, height / 2)), true);
        }
    }

    public void setRearLeftMotorOutput(float motorOutput) {
        if (motorOutput != 0) {
            Vector2 forceVector = getForceVector(motorOutput);
            body.applyForce(forceVector, body.getWorldPoint(new Vector2(-width / 2, -height / 2)), true);
        }
    }

    public void setRearRightMotorOutput(float motorOutput) {
        if (motorOutput != 0) {
            Vector2 forceVector = getForceVector(motorOutput);
            body.applyForce(forceVector, body.getWorldPoint(new Vector2(width / 2, -height / 2)), true);
        }
    }

    void updateFriction() {
        //lateral linear velocity
        float maxLateralImpulse = 3f;
        Vector2 lateralVelocity = getLateralVelocity().scl(-1f);
        Vector2 impulse = lateralVelocity.scl(body.getMass());
        if (impulse.len() > maxLateralImpulse)
            impulse.scl(maxLateralImpulse / impulse.len());
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);

        //angular velocity
        body.applyAngularImpulse(0.1f * body.getInertia() * -body.getAngularVelocity(), true);

        //forward linear velocity
        Vector2 currentForwardNormal = getForwardVelocity();
        float currentForwardSpeed = currentForwardNormal.len();
        float dragForceMagnitude = -2 * currentForwardSpeed;
        body.applyForce(currentForwardNormal.scl(dragForceMagnitude), body.getWorldCenter(), true);
    }
}
