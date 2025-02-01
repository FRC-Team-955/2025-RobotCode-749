package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANRollerSubsystem;

public class AutoRoller extends Command {
    private final CANRollerSubsystem rollerSubsystem;

    public AutoRoller(CANRollerSubsystem rollerSubsystem) {
        this.rollerSubsystem = rollerSubsystem;
        addRequirements(rollerSubsystem);
    }
    @Override
    public void initialize() {
        System.out.println("started");
    }
    @Override
    public void execute() {
        rollerSubsystem.setRollerMotor(0.4);

    }

    @Override
    public void end(boolean interrupted) {
        rollerSubsystem.setRollerMotor(0.0);
        System.out.println("ended");
    }
    @Override
    public boolean isFinished() {
        return false;
    }
}
