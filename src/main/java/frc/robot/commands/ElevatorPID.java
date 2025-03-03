package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubSystems;

public class ElevatorPID extends Command {

        private final ElevatorSubSystems elevatorSubSystems;
        private final PIDController pidController;
        private final double elevatorEncoderSetpoint;
        private double speed;

    public ElevatorPID(ElevatorSubSystems elevatorSubSystems, double setPoint) {
            this.elevatorSubSystems = elevatorSubSystems;
            this.pidController = new PIDController(0.075,0,0);
            this.elevatorEncoderSetpoint = setPoint;
            addRequirements(elevatorSubSystems);
        }
        @Override
        public void initialize() {
            pidController.reset();
            SmartDashboard.putNumber("desiredDistance", elevatorEncoderSetpoint);
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
        }
        @Override
        public boolean isFinished() {

                return false;
        }
    }

