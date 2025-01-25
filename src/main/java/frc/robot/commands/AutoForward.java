package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

// Originally own by Justin edited by Mark, command for new auto
public class AutoForward extends Command {
    private final CANDriveSubsystem driveSubsystem;
    private final double distance;
    private double encoderSetpoint;

    public AutoForward(CANDriveSubsystem driveSubsystem, double distance) {
        this.driveSubsystem = driveSubsystem;
        this.distance = distance + 5; //distance + 5 is = want move 5
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        encoderSetpoint = driveSubsystem.currentDistance() + distance;
        System.out.println("started");
    }
    @Override
    public void execute() {
        driveSubsystem.setSpeed(0.5, 0.5);
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.setSpeed(0.0, 0.0);
        System.out.println("ended");
    }
    @Override
    public boolean isFinished() {
        if (driveSubsystem.currentDistance() > encoderSetpoint) {
            return true;
        }
        else
            return false;
        }
    }
