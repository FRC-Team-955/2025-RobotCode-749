package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

public class AutoAlign extends Command {
    private final CANDriveSubsystem drivetrain;
    private final PIDController turnPID;
    
    // PID Gains - Tune these for best results
    private static final double kP = 0.007;  // Proportional gain
    private static final double kI = 0.0;   // Integral gain
    private static final double kD = 0.001; // Derivative gain
    
    private static final double TX_TOLERANCE = 2; // Degrees, acceptable alignment error
    private static final double FORWARD_SPEED = 0.3; // Forward drive speed after aligning

    private boolean aligned = false; // Track alignment phase

    public AutoAlign(CANDriveSubsystem drivetrain) {
        this.drivetrain = drivetrain;
        this.turnPID = new PIDController(kP, kI, kD);
        
        turnPID.setTolerance(TX_TOLERANCE); // Set acceptable error range

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        System.out.println("AutoAlign Started");
        aligned = false; // Reset alignment status
    }

    @Override
    public void execute() {
        double tx = NetworkTableInstance.getDefault()
                .getTable("limelight-right")
                .getEntry("tx")
                .getDouble(0.0); // Get horizontal offset

        boolean hasTarget = (NetworkTableInstance.getDefault()
                .getTable("limelight-right")
                .getEntry("tv")
                .getDouble(0.0) == 1.0); // Check if a target is detected

        if (!hasTarget) {
            System.out.println("No target found");
            drivetrain.setSpeed(0.0, 0.0); // Stop movement
            return;
        }

        if (!aligned) {
            // **PHASE 1: ALIGNMENT**
            double turnSpeed = turnPID.calculate(tx, 0); // PID controller output
            turnSpeed = Math.max(-0.5, Math.min(0.5, turnSpeed)); // Clamp speed

            drivetrain.setSpeed(turnSpeed, -turnSpeed); // Rotate in place

            if (turnPID.atSetpoint()) {
                aligned = true; // Mark as aligned
                System.out.println("Alignment complete. Driving forward.");
            }
        } else {
            // **PHASE 2: DRIVE FORWARD**
            drivetrain.setSpeed(FORWARD_SPEED, FORWARD_SPEED);
        }
    }

    @Override
    public boolean isFinished() {
        // Stop when we lose sight of the target
        boolean hasTarget = (NetworkTableInstance.getDefault()
                .getTable("limelight-right")
                .getEntry("tv")
                .getDouble(0.0) == 1.0);

        return aligned && !hasTarget;

    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.driveArcade(drivetrain, () -> 0, () -> 0.2).withTimeout(1.0);
        System.out.println("AutoAlign Finished - Target lost.");
    }
}