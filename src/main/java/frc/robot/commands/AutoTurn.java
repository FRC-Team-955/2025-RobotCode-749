package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

// made by justin, command for auto
public class AutoTurn extends Command {
    private final CANDriveSubsystem driveSubsystem;
    private final double targetDistanceRight;
    private final double targetDistanceLeft;
    private final PIDController pidControllerLeft;
   // private final PIDController pidController;
    //private double speed;
    private final PIDController pidControllerRight;
    private double encoderSetpointRight;
    private double encoderSetpointLeft;
    private double speedLeft;
    private double speedRight;
    public AutoTurn(CANDriveSubsystem driveSubsystem, double targetDistanceLeft, double targetDistanceRight) {
        this.driveSubsystem = driveSubsystem;
        this.pidControllerRight = new PIDController(0.003,0,0);
        this.pidControllerLeft = new PIDController(0.003, 0, 0);
        //this.pidController = new PIDController(0.0025,0,0);
        this.targetDistanceRight = targetDistanceRight;
        this.targetDistanceLeft = targetDistanceLeft;
        addRequirements(driveSubsystem);
    }
    @Override
    public void initialize() {
        pidControllerLeft.reset();
        //pidController.reset();
        pidControllerRight.reset();
        encoderSetpointRight = driveSubsystem.rightCurrentDistance() + targetDistanceRight;
        encoderSetpointLeft = driveSubsystem.leftCurrentDistance() + targetDistanceLeft;

        this.speedRight = pidControllerRight.calculate(driveSubsystem.rightCurrentDistance(), encoderSetpointRight);
        this.speedLeft = pidControllerLeft.calculate(driveSubsystem.leftCurrentDistance(), encoderSetpointLeft);
        SmartDashboard.putNumber("encoderSetpointLeftWheel", encoderSetpointLeft);
        SmartDashboard.putNumber("encoderSetpointRightWheel", encoderSetpointRight);
    }
    @Override
    public void execute() {
        //this.speedLeft = pidControllerLeft.calculate(driveSubsystem.leftCurrentDistance(), encoderSetpoint);
        //this.speedRight = pidControllerRight.calculate(driveSubsystem.rightCurrentDistance(),encoderSetpoint);
        //this.speed = pidController.calculate(driveSubsystem.currentDistance(), encoderSetpoint);
        //driveSubsystem.setSpeed(speedLeft, speedRight);

        driveSubsystem.setSpeed(speedLeft, speedRight);
        //SmartDashboard.putNumber("leftPidOutput", speedLeft);

        //SmartDashboard.putNumber("rightPidOutput", speedRight);

        SmartDashboard.putNumber("PidOutput", speedRight);
        SmartDashboard.putNumber("PidOutput", speedLeft);
    }
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.setSpeed(speedLeft, speedRight);
    }
    @Override
    public boolean isFinished() {
        if (driveSubsystem.rightCurrentDistance() >= encoderSetpointRight &&
                driveSubsystem.leftCurrentDistance() >= encoderSetpointLeft) {
            return true;
        }
        else
            return false;
    }
}