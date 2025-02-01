package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

// Originally own by Justin edited by Mark, command for new auto
public class AutoForward extends Command {
    private final CANDriveSubsystem driveSubsystem;
    private final double distance;

    public AutoForward(CANDriveSubsystem driveSubsystem, double distance) {
        this.driveSubsystem = driveSubsystem;
        this.distance = driveSubsystem.currentDistance() + distance;
        addRequirements(driveSubsystem);
    }
    @Override
    public void initialize() {
        System.out.println("started");
    }
    @Override
    public void execute() {
        driveSubsystem.setSpeed(0.3, 0.3);
    }


    @Override
    public void end(boolean interrupted) {
        driveSubsystem.setSpeed(0.0, 0.0);
        System.out.println("ended");
    }
    @Override
    public boolean isFinished() {
        if (driveSubsystem.currentDistance() > distance) {
            return true;
        }
        else
            return false;
        }
    }
