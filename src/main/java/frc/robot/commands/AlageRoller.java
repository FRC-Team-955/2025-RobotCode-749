package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlageRollerSubsystem;
import frc.robot.subsystems.CANRollerSubsystem;

public class AlageRoller extends Command {
    private final AlageRollerSubsystem alageRollerSubsystem;
    private double alageRollerSpeed;

    public AlageRoller(AlageRollerSubsystem rollerSubsystem, double alageRollerSpeed) {
        this.alageRollerSubsystem = rollerSubsystem;
        this.alageRollerSpeed = alageRollerSpeed;
        addRequirements(rollerSubsystem);
    }
    @Override
    public void initialize() {
    }
    @Override
    public void execute() {
        alageRollerSubsystem.setAlgaeRollerMotor(alageRollerSpeed);

    }

    @Override
    public void end(boolean interrupted) {
        alageRollerSubsystem.setAlgaeRollerMotor(0.0);
    }
    @Override
    public boolean isFinished() {
        return false;
    }
}
