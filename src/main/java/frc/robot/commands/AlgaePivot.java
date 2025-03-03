package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.AlgaeSubSystem;

public class AlgaePivot extends Command {
    private final AlgaeSubSystem algaeSubSystem;
    private double encoderSetpoint;
    private final PIDController pidController;
    private double speed;


    public AlgaePivot(AlgaeSubSystem algaeSubSystem, double targetDistance) {
        this.algaeSubSystem = algaeSubSystem;
        this.pidController = new PIDController(2.6, 0.0, 0.0002);
        this.encoderSetpoint = targetDistance;
        addRequirements(algaeSubSystem);
    }

    @Override
    public void initialize() {
        pidController.reset();
        SmartDashboard.putNumber("algaeEncoderSetpoint", encoderSetpoint);
    }

    @Override
    public void execute() {
        //this.speed = pidController.calculate(algaeSubSystem.currentAlgaePivotEncoder(), encoderSetpoint);
        this.speed = pidController.calculate(algaeSubSystem.currentEncoderPivotPosition(), encoderSetpoint);
        algaeSubSystem.setSpeed(speed);
        SmartDashboard.putNumber("AlgaePivotPidOutput", speed);

    }

    @Override
    public void end(boolean interrupted) {
        algaeSubSystem.setSpeed(speed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
