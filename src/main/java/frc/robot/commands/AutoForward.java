package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

// made by justin, command for auto
public class AutoForward extends Command {
    private final CANDriveSubsystem driveSubsystem;
    private final double targetDistance;
    private double encoderSetpoint;
    //private final PIDController pidController;
    //private final double speed;
    //private final double error;
    //private final double kP;
    //private final double outputSpeed;

    public AutoForward(CANDriveSubsystem driveSubsystem, double targetDistance) {
        this.driveSubsystem = driveSubsystem;
        this.targetDistance = targetDistance;
        //this.error = encoderSetpoint - driveSubsystem.currentDistance();
        //this.pidController = new PIDController(0.0005,0,0);
        //this.speed = pidController.calculate(driveSubsystem.currentDistance());
        //this.kP = 0.00005;
        //this.outputSpeed = kP * error;
        addRequirements(driveSubsystem);
    }
    @Override
    public void initialize() {
        //pidController.reset();
        encoderSetpoint = driveSubsystem.currentDistance() + targetDistance;
        //pidController.setSetpoint(encoderSetpoint);
        System.out.println("started");
    }
    @Override
    public void execute() {
        //driveSubsystem.setSpeed(speed, speed);
        //double speed = pidController.calculate(driveSubsystem.currentDistance());
        driveSubsystem.setSpeed(0.2,0.2);
        //driveSubsystem.setSpeed(0.3, 0.3);
    }
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.setSpeed(0, 0);
        System.out.println("ended");
    }
    @Override
    public boolean isFinished() {
        if (driveSubsystem.currentDistance() > encoderSetpoint ) {
            return true;
        }
        else
            return false;
    }

}


        /*if (pidController.atSetpoint()) {
            return true;
        }
        else
            return false;
        }

    }*/

