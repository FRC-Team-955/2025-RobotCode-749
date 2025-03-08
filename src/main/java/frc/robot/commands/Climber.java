package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.zClimberSubsystem;

public class Climber extends Command {
    private final zClimberSubsystem climberSubsystem;
    private double encoderSetpoint;
    private double speed;


    public Climber(zClimberSubsystem climberSubsystem, double speed) {
        this.climberSubsystem = climberSubsystem;
        this.speed = speed;
        addRequirements(climberSubsystem);
    }

    @Override
    public void initialize() {
        SmartDashboard.putNumber("climberEncoderSetpoint", encoderSetpoint);
    }

    @Override
    public void execute() {
        climberSubsystem.setSpeed(speed);
        SmartDashboard.putNumber("ClimberPidOutput", speed);

    }

    @Override
    public void end(boolean interrupted) {
        climberSubsystem.setSpeed(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}