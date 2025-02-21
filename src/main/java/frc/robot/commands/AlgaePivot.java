package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.AlageRollerSubsystem;
import frc.robot.subsystems.AlgaeSubSystem;
import frc.robot.subsystems.CANRollerSubsystem;

public class AlgaePivot extends Command {
    private final AlgaeSubSystem algaeSubSystem;
    private double encoderSetpoint;
    private final PIDController pidController;
    private double speed;
    private double speed2;
    private final AlageRollerSubsystem rollerSubsystem;


    public AlgaePivot(AlgaeSubSystem algaeSubSystem, AlageRollerSubsystem rollerSubsystem, double speed2, double targetDistance) {
        this.algaeSubSystem = algaeSubSystem;
        this.pidController = new PIDController(0.00001, 0, 0);
        this.encoderSetpoint = targetDistance;
        addRequirements(algaeSubSystem);
        this.speed2 = speed2;
        this.rollerSubsystem = rollerSubsystem;
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
        rollerSubsystem.setAlgaeRollerMotor(speed2);
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
