package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

// Originally own by Justin edited by Mark, command for new auto
public class AutoForward extends Command {
    private final CANDriveSubsystem driveSubsystem;
    private final double distance;
    private double encoderSetpoint;
    PIDController pid = new PIDController(0.002, 0, 0);
    private double speed;
    private double slowSpeed;

    public AutoForward(CANDriveSubsystem driveSubsystem, double distance) {
        this.driveSubsystem = driveSubsystem;
        this.distance = distance;
        addRequirements(driveSubsystem);
    }


    @Override
    public void initialize() {
        System.out.println("started");
        pid.reset();
        pid.setTolerance(5, 10);
        encoderSetpoint = driveSubsystem.currentDistance() + distance;
        speed = (pid.calculate(driveSubsystem.currentDistance(), encoderSetpoint));
    }

    @Override
    public void execute() {
        driveSubsystem.setSpeed(speed, speed);
    }

    @Override
    public void end(boolean interrupted) {
        slowSpeed = (pid.calculate(driveSubsystem.currentDistance(), encoderSetpoint));
        driveSubsystem.setSpeed(slowSpeed, slowSpeed);
        //pid.atSetpoint();
        System.out.println("ended");
    }

    @Override
    public boolean isFinished() {
        if (pid.atSetpoint()) {
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
