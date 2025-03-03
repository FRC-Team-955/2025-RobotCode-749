package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.PivotSubSystem;

public class Pivot extends Command {


    private final PivotSubSystem pivotSubSystem;

    private double encoderSetpoint;
    private final PIDController pidController;

    private double speed;

    public Pivot(PivotSubSystem pivotSubSystem, double targetDistance) {
        this.pivotSubSystem = pivotSubSystem;
        this.pidController = new PIDController(0.06,0,0);
        this.encoderSetpoint = targetDistance;
        addRequirements(pivotSubSystem);
    }
    @Override
    public void initialize() {
        pidController.reset();
        SmartDashboard.putNumber("pivotEncoderSetpoint", encoderSetpoint);
    }
    @Override
    public void execute() {
        this.speed = pidController.calculate(pivotSubSystem.currentPivotEncoder(), encoderSetpoint);
        pivotSubSystem.setSpeed(speed);
        SmartDashboard.putNumber("pivotPidOutput", speed);
    }
    @Override
    public void end(boolean interrupted) {
            pivotSubSystem.setSpeed(speed);
    }
    @Override
    public boolean isFinished() {
        return false;
    }
}
