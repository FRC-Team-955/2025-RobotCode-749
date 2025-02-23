package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

// made by justin, command for auto
public class AutoForward extends Command {
    private final CANDriveSubsystem driveSubsystem;
    private final double targetDistance;
    //private final PIDController pidControllerLeft;
    private final PIDController pidController;
    private double speed;
    //private final PIDController pidControllerRight;
    private double encoderSetpoint;
    private double speedLeft;
    private double speedRight;
    public AutoForward(CANDriveSubsystem driveSubsystem, double targetDistance) {
        this.driveSubsystem = driveSubsystem;
        //this.pidControllerRight = new PIDController(0.015,0,0);
        //this.pidControllerLeft = new PIDController(0.015, 0, 0);
        this.pidController = new PIDController(0.0025,0,0);
        this.targetDistance = targetDistance;
        addRequirements(driveSubsystem);
    }
    @Override
    public void initialize() {
        //pidControllerLeft.reset();
        pidController.reset();
        //pidControllerRight.reset();
        encoderSetpoint = driveSubsystem.currentDistance() + targetDistance;

        this.speed = pidController.calculate(driveSubsystem.currentDistance(), encoderSetpoint);
        SmartDashboard.putNumber("encoderSetpoint", encoderSetpoint);
    }
    @Override
    public void execute() {
        //this.speedLeft = pidControllerLeft.calculate(driveSubsystem.leftCurrentDistance(), encoderSetpoint);
        //this.speedRight = pidControllerRight.calculate(driveSubsystem.rightCurrentDistance(),encoderSetpoint);
        //this.speed = pidController.calculate(driveSubsystem.currentDistance(), encoderSetpoint);
        //driveSubsystem.setSpeed(speedLeft, speedRight);

        driveSubsystem.setSpeed(speed, speed);
        //SmartDashboard.putNumber("leftPidOutput", speedLeft);

        //SmartDashboard.putNumber("rightPidOutput", speedRight);

        SmartDashboard.putNumber("PidOutput", speed);
    }
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.setSpeed(speed, speed);
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