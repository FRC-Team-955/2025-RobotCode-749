package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CANRollerSubsystem;

import java.util.Timer;

public class AutoRoller extends Command {
    private final CANRollerSubsystem rollerSubsystem;
    //private final Timer timer;

    public AutoRoller(CANRollerSubsystem rollerSubsystem) {
        this.rollerSubsystem = rollerSubsystem;
        addRequirements(rollerSubsystem);
    }
    @Override
    public void initialize() {
    }
    @Override
    public void execute() {
        rollerSubsystem.setRollerMotor(Constants.RollerConstants.ROLLER_EJECT_VALUE);

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
