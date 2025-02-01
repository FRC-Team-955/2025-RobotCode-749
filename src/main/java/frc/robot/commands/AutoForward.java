package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

// made by justin, command for auto
public class AutoForward extends Command {
    private final CANDriveSubsystem driveSubsystem;
    private final double targetDistance;
    private double encoderSetpoint;

    public AutoForward(CANDriveSubsystem driveSubsystem, double targetDistance) {
        this.driveSubsystem = driveSubsystem;
        this.targetDistance = targetDistance;
        addRequirements(driveSubsystem);
    }
    @Override
    public void initialize() {
        driveSubsystem.encoderReset();
        encoderSetpoint = driveSubsystem.currentDistance() + targetDistance;
        System.out.println("started");
    }
    @Override
    public void execute() {
        driveSubsystem.setSpeed(0.25,0.25);
    }
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.setSpeed(0, 0);
        System.out.println("ended");
    }
    @Override
    public boolean isFinished() {
        if (driveSubsystem.currentDistance() >= encoderSetpoint ) {
            return true;
        }
        else
            return false;
    }

}