package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubSystems;

public class Elevator extends Command {
    private final ElevatorSubSystems elevatorSubSystems;
    private final PIDController pidController;
    private double speedElevator;
    private double encodersetHeight;
    private final double targetHeight;

    public Elevator(ElevatorSubSystems elevatorSubSystems, double targetHeight) {
            this.elevatorSubSystems = new ElevatorSubSystems();
            this.pidController = new PIDController(0.001, 0, 0);
            this.targetHeight = targetHeight;
            //this.encoderHeight = elevatorSubSystems.currentEleveatorEncoder();
            addRequirements(elevatorSubSystems);
        }
        @Override
        public void initialize() {
            pidController.reset();
            encodersetHeight = elevatorSubSystems.currentEleveatorEncoder() + targetHeight;
        }

        @Override
        public void execute() {
            this.speedElevator = pidController.calculate(elevatorSubSystems.currentEleveatorEncoder(), encodersetHeight);
            elevatorSubSystems.setMotorSpeed(speedElevator);
            SmartDashboard.putNumber("speedOfElevator", speedElevator);
            SmartDashboard.putNumber("currentPosition", elevatorSubSystems.currentEleveatorEncoder());
        }

        @Override
        public void end(boolean interrupted) {
            elevatorSubSystems.setMotorSpeed(speedElevator);
        }

        @Override
        public boolean isFinished() {
            if (elevatorSubSystems.real()) {
                return true;
            }
            else
                return false;
        }
    }
