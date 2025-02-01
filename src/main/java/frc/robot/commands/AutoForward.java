package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

// made by justin, command for auto
public class AutoForward extends Command {
    private final CANDriveSubsystem driveSubsystem;
    private final double targetDistance;
    private final PIDController pidController;
    private double encoderSetpoint;
    private double speed;

    public AutoForward(CANDriveSubsystem driveSubsystem, double targetDistance) {
        this.driveSubsystem = driveSubsystem;
        this.pidController = new PIDController(-0.0002, 0, 0);
        this.targetDistance = targetDistance;
        addRequirements(driveSubsystem);
    }
    @Override
    public void initialize() {
        pidController.reset();
        //driveSubsystem.encoderReset();
        encoderSetpoint = driveSubsystem.currentDistance() + targetDistance;
        System.out.println("started");
    }
    @Override
    public void execute() {
        this.speed = pidController.calculate(driveSubsystem.currentDistance(), encoderSetpoint);
        driveSubsystem.setSpeed(speed, speed);
        //driveSubsystem.setSpeed(0.25,0.25);
    }
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.setSpeed(speed, speed);
        //driveSubsystem.setSpeed(0, 0);
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