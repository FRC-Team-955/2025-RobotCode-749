package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CANRollerSubsystem;
import frc.robot.subsystems.ElevatorSubSystems;

public class ElevatorPID extends Command {

        private final ElevatorSubSystems elevatorSubSystems;
        private final PIDController pidController;
        private double elevatorEncoderSetpoint;

        private double speed;
        private double setPoint;

    public ElevatorPID(ElevatorSubSystems elevatorSubSystems, double setPoint) {
            this.elevatorSubSystems = elevatorSubSystems;
            this.pidController = new PIDController(0.0001,0,0);
            this.setPoint = setPoint;
            addRequirements(elevatorSubSystems);
        }
        @Override
        public void initialize() {
            pidController.reset();
            elevatorEncoderSetpoint = elevatorSubSystems.currentElevatorEncoder() + setPoint;
            SmartDashboard.putNumber("desiredDistance", elevatorEncoderSetpoint);
            System.out.println("started");
        }
        @Override
        public void execute() {
            this.speed = pidController.calculate(elevatorSubSystems.currentElevatorEncoder(), elevatorEncoderSetpoint);
            elevatorSubSystems.setMotorSpeed(speed);
            SmartDashboard.putNumber("elevatorSpeedOutput", speed);


        }

        @Override
        public void end(boolean interrupted) {
            elevatorSubSystems.setMotorSpeed(speed);
            System.out.println("ended");
        }
        @Override
        public boolean isFinished() {
            return false;
        }
    }

