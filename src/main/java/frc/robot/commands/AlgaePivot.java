package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.AlgaeSubSystem;
import frc.robot.subsystems.CANRollerSubsystem;

public class AlgaePivot extends Command {
    private final AlgaeSubSystem algaeSubSystem;
    private double encoderSetpoint;
    private final PIDController pidController;
    private double speed;
    private final CANRollerSubsystem rollerSubsystem;
    private double inOrOut;


    public AlgaePivot(AlgaeSubSystem algaeSubSystem, CANRollerSubsystem rollerSubsystem, double inOrOut, double targetDistance) {
        this.algaeSubSystem = algaeSubSystem;
        this.pidController = new PIDController(0.000001, 0, 0);
        this.encoderSetpoint = targetDistance;
        addRequirements(algaeSubSystem);
        this.rollerSubsystem = rollerSubsystem;
        this.inOrOut = inOrOut;
        addRequirements(rollerSubsystem);
    }

    @Override
    public void initialize() {
        pidController.reset();
        SmartDashboard.putNumber("algaeEncoderSetpoint", encoderSetpoint);
        SmartDashboard.putNumber("AlgetPivotPosition", encoderSetpoint);
    }

    @Override
    public void execute() {
        //this.speed = pidController.calculate(algaeSubSystem.currentAlgaePivotEncoder(), encoderSetpoint);
        this.speed = pidController.calculate(algaeSubSystem.currentEncoderPivotPosition(), encoderSetpoint);
        algaeSubSystem.setSpeed(speed);
        rollerSubsystem.setAlgaeRollerMotor(inOrOut);
        SmartDashboard.putNumber("AlgaePivotPidOutput", speed);

    }

    @Override
    public void end(boolean interrupted) {
        algaeSubSystem.setSpeed(speed);
        rollerSubsystem.setAlgaeRollerMotor(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
