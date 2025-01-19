package frc.robot.commands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CANDriveSubsystem;

// made by justin, command for auto
public class AutoForward extends Command {
    private final CANDriveSubsystem driveSubsystem;
    private double encoderSetpoint;
    public static double rotationsToRadians(double rotations) {
        return Units.rotationsToRadians(rotations) * 3.0 * Math.PI;
    }
    public AutoForward(CANDriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);
    }
    @Override
    public void initialize() {
        encoderSetpoint =  driveSubsystem.encoderRotations() + rotationsToRadians(Constants.DriveConstants.distance);
        System.out.println("started");
    }
    @Override
    public void execute() {
        driveSubsystem.driveArcade(driveSubsystem, () -> 0.25, () -> 0.0);
    }
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.driveArcade(driveSubsystem, () -> 0.0, () -> 0.0 );
        System.out.println("ended");
    }
    @Override
    public boolean isFinished() {
        if ( rotationsToRadians(Constants.DriveConstants.distance) > encoderSetpoint)
            return true;
            else
                return false;
        }
    }
