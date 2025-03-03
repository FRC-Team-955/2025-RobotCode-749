package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANRollerSubsystem;

public class AutoRoller extends Command {
    private final CANRollerSubsystem rollerSubsystem;
    private double inOrOut;

    public AutoRoller(CANRollerSubsystem rollerSubsystem, double inOrOut) {
        this.rollerSubsystem = rollerSubsystem;
        this.inOrOut = inOrOut;
        addRequirements(rollerSubsystem);
    }
    @Override
    public void initialize() {
    }
    @Override
    public void execute() {
        rollerSubsystem.setRollerMotor(inOrOut);

    }

    @Override
    public void end(boolean interrupted) {
        rollerSubsystem.setRollerMotor(0.0);
    }
    @Override
    public boolean isFinished() {
        return false;
    }
}
