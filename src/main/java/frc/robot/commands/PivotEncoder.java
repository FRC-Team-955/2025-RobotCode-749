package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlgaeSubSystem;
import frc.robot.subsystems.PivotEncoderSubSystem;

public class PivotEncoder extends Command {
    private final PivotEncoderSubSystem pivotSubSystem;
    private double encoderSetpoint;
    private final PIDController pidController;
    private double speed;


    public PivotEncoder(PivotEncoderSubSystem pivotSubSystem, double targetDistance) {
        this.pivotSubSystem = pivotSubSystem;
        this.pidController = new PIDController(6, 0.0, 0.0);
        this.encoderSetpoint = targetDistance;
        addRequirements(pivotSubSystem);
    }

    @Override
    public void initialize() {
        pidController.reset();
        SmartDashboard.putNumber("AbosuluteEncoderSetpoint", encoderSetpoint);
        SmartDashboard.putNumber("AbosouluteEncoderPosition", pivotSubSystem.currentEncoderPivotPosition());
    }

    @Override
    public void execute() {
        this.speed = pidController.calculate(pivotSubSystem.currentEncoderPivotPosition(), encoderSetpoint);
        //pivotSubSystem.setSpeed(0.0);
        pivotSubSystem.setSpeed(speed);
        SmartDashboard.putNumber("encoderPivotPidOutput", speed);
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
