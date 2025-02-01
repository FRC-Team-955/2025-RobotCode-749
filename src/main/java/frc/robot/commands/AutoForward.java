package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

// Originally own by Justin edited by Mark, command for new PidAuto
public class AutoForward extends Command {
    private final CANDriveSubsystem driveSubsystem;
    private final double distance;
    private double encoderSetpoint;
    private final PIDController pid;
    //private double speed;
    //private double slowSpeed;

    public AutoForward(CANDriveSubsystem driveSubsystem, double distance) {
        this.driveSubsystem = driveSubsystem;
        this.distance = distance;
        this.pid = new PIDController(0.01, 0, 0);
        addRequirements(driveSubsystem);
    }


    @Override
    public void initialize() {
        //pid.setTolerance(5, 10);
        System.out.println("started");
        encoderSetpoint = driveSubsystem.currentDistance() + distance;
        pid.setSetpoint(encoderSetpoint);
        //speed = (pid.calculate(driveSubsystem.currentDistance(), encoderSetpoint));
        //slowSpeed = (pid.calculate(driveSubsystem.currentDistance(), encoderSetpoint));
    }

    @Override
    public void execute() {
        //pid.setSetpoint(encoderSetpoint);
        double speed = (pid.calculate(driveSubsystem.currentDistance(), encoderSetpoint));
        driveSubsystem.setSpeed(speed, speed);
        System.out.println("started");
    }

    @Override
    public void end(boolean interrupted) {
        //double slowSpeed = (pid.calculate(driveSubsystem.currentDistance(), encoderSetpoint));
        driveSubsystem.setSpeed(0, 0);
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
        /*if (driveSubsystem.currentDistance() > encoderSetpoint) {
            return true;
        }
        else
            return false;
        }*/
}
