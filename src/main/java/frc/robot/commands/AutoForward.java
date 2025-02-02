package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

// made by justin, command for auto
public class AutoForward extends Command {
    private final CANDriveSubsystem driveSubsystem;
    private final double targetDistance;
    private final PIDController pidControllerLeft;
    private final PIDController pidControllerRight;
    private double encoderSetpoint;
    private double speedLeft;
    private double speedRight;

    public AutoForward(CANDriveSubsystem driveSubsystem, double targetDistance) {
        this.driveSubsystem = driveSubsystem;
        this.pidControllerRight = new PIDController(0.01,0,0);
        this.pidControllerLeft = new PIDController(0.01, 0, 0);
        this.targetDistance = targetDistance;
        addRequirements(driveSubsystem);
    }
    @Override
    public void initialize() {
        pidControllerLeft.reset();
        pidControllerRight.reset();
        encoderSetpoint = driveSubsystem.currentDistance() + targetDistance;
        SmartDashboard.putNumber("encoderSetpoint", encoderSetpoint);
        System.out.println("started");
    }
    @Override
    public void execute() {
        this.speedLeft = pidControllerLeft.calculate(driveSubsystem.leftCurrentDistance(), encoderSetpoint);
        this.speedRight = pidControllerRight.calculate(driveSubsystem.rightCurrentDistance(),encoderSetpoint);

        driveSubsystem.setSpeed(speedLeft, speedRight);
        SmartDashboard.putNumber("leftPidOutput", speedLeft);

        SmartDashboard.putNumber("rightPidOutput", speedRight);
        //driveSubsystem.setSpeed(0.25,0.25);
    }
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.setSpeed(speedLeft, speedRight);
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