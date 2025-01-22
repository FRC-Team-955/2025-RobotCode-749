package frc.robot.commands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CANDriveSubsystem;

// made by justin, command for auto
public class AutoForward2 extends Command {
    private final CANDriveSubsystem driveSubsystem;
    private final double targetDistance;
    public AutoForward2(CANDriveSubsystem driveSubsystem, double targetDistance) {
        this.driveSubsystem = driveSubsystem;
        this.targetDistance = targetDistance;
        addRequirements(driveSubsystem);
    }
    @Override
    public void initialize() {
        System.out.println("started");
    }
    @Override
    public void execute() {
        driveSubsystem.driveArcade(driveSubsystem, () -> 0.25, () -> 0.0);
    }
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.driveArcade(driveSubsystem, () -> 0.0, () -> 0.0 );
        System.out.println("ended");
    }
    @Override
    public boolean isFinished() {
        return -driveSubsystem.currentDistance() <= -targetDistance;
    }
}
