package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANRollerSubsystem;

public class AutoRoller extends Command {
    private final CANRollerSubsystem rollerSubsystem;
    //private final double second = 1;
    private final double rotation;
    private double rotationSetpoint;

    public AutoRoller(CANRollerSubsystem rollerSubsystem, double rotation) {
        this.rollerSubsystem = rollerSubsystem;
        this.rotation = rotation;
        addRequirements(rollerSubsystem);
    }

    @Override
    public void initialize() {
        System.out.println("started");
        rotationSetpoint = rollerSubsystem.currentRotation() + rotation;
    }

    @Override
    public void execute() {
        rollerSubsystem.setRollerMotor(0.4);
        //rollerSubsystem.runRoller(rollerSubsystem, () -> 0.4, () -> 0).withTimeout(second);
        }

    @Override
    public void end(boolean interrupted) {
        rollerSubsystem.setRollerMotor(0.0);
    }

    @Override
    public boolean isFinished() {
        if (rollerSubsystem.currentRotation() > rotationSetpoint) {
            return true;
        }
        else
            return false;
    }
}
