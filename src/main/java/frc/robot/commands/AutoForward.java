package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

// made by justin, command for auto
public class AutoForward extends Command {
    private final CANDriveSubsystem driveSubsystem;
    private final double targetDistance;
    private double encoderSetpoint;
    private final PIDController pidController;

    public AutoForward(CANDriveSubsystem driveSubsystem, double targetDistance) {
        this.driveSubsystem = driveSubsystem;
        this.targetDistance = targetDistance;
        this.pidController = new PIDController(0.005,0,0);
        pidController.setSetpoint(targetDistance);
        addRequirements(driveSubsystem);
    }
    @Override
    public void initialize() {
        pidController.reset();
        encoderSetpoint = driveSubsystem.currentDistance() + targetDistance;

        System.out.println("started");
    }
    @Override
    public void execute() {
        double speed = pidController.calculate(driveSubsystem.currentDistance());
        driveSubsystem.setSpeed(speed,speed);
        //driveSubsystem.setSpeed(0.3, 0.3);
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.setSpeed(0, 0);
        System.out.println("ended");
    }
    @Override
    public boolean isFinished() {
        if (driveSubsystem.currentDistance() > encoderSetpoint ) {
            return true;
        }
        else
            return false;
        }

    }
