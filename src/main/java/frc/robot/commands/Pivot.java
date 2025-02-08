package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.PivotSubSystems;

public class Pivot extends Command {
    private final PivotSubSystems pivotSubsystem;
    private final double targetPosition;
    private final PIDController pidPivot;
    private double pivotEncoderSetpoint;
    private double speed;

    public Pivot(PivotSubSystems pivotSubsystem, double targetPosition) {
        this.pivotSubsystem = pivotSubsystem;
        this.pidPivot = new PIDController(0.001,0,0);
        this.targetPosition = targetPosition;
        addRequirements(pivotSubsystem);
    }
    @Override
    public void initialize() {
        pidPivot.reset();
        pidPivot.setTolerance(10, 10);
        pivotEncoderSetpoint = pivotSubsystem.currentPivotEncoder() + targetPosition;
        SmartDashboard.putNumber("pivotEncoderSetpoint", pivotEncoderSetpoint);
    }

    @Override
    public void execute() {
        this.speed = pidPivot.calculate(pivotSubsystem.currentPivotEncoder(),pivotEncoderSetpoint);

        pivotSubsystem.setSpeed(speed);
        SmartDashboard.putNumber("pivotPidOutput", speed);
    }
    @Override
    public void end(boolean interrupted) {
        pivotSubsystem.setSpeed(speed);
    }

    @Override
    public boolean isFinished() {
        if (pivotSubsystem.currentPivotEncoder() >= pivotEncoderSetpoint ) {
            return true;
        }
        else
            return false;
    }
}

