package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CANRollerSubsystem;

public class AutoRoller extends Command {
    private final CANRollerSubsystem rollerSubsystem;
    private double inOrOut;
    //private final Timer autotimer;
    private double TimerValuel;

    public AutoRoller(CANRollerSubsystem rollerSubsystem, double inOrOut) {
        this.rollerSubsystem = rollerSubsystem;
        this.inOrOut = inOrOut;
        //autotimer = new Timer();
        //this.TimerValuel = autotimer.get();
        //autotimer.reset();
        addRequirements(rollerSubsystem);
    }
    @Override
    public void initialize() {
    }
    @Override
    public void execute() {
        rollerSubsystem.setRollerMotor(inOrOut);
        //autotimer.start();
        //this.TimerValuel = autotimer.get();
    }

    @Override
    public void end(boolean interrupted) {
        rollerSubsystem.setRollerMotor(inOrOut);
    }
    @Override
    public boolean isFinished() {
        return false;
    }

    /*{if (TimerValuel >= 3) {
        return true;}
        else return false;}*/
}
