package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSubSystems  extends SubsystemBase {

private final SparkMax elevator;

private final DigitalInput topLimitSwitch;

private final DigitalInput bottomLimitSwitch;

private final RelativeEncoder elevatorEncoder;

public ElevatorSubSystems() {
    topLimitSwitch = new DigitalInput(0);

    bottomLimitSwitch = new DigitalInput(1);

    elevator = new SparkMax(Constants.ElevatorConstants.ElEVATOR_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);

    elevatorEncoder = elevator.getEncoder();

    SparkMaxConfig config = new SparkMaxConfig();

    config.idleMode(SparkBaseConfig.IdleMode.kBrake);

    elevator.configure(config, SparkBase.ResetMode.kNoResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
}

    public boolean real() {
    if (topLimitSwitch.get()) return true;
    else return false;
    }

    public void setMotorSpeed(double speed) {
        if (speed > 0) {
            if (topLimitSwitch.get()) {
                // We are going up and top limit is tripped so stop
                elevator.set(0);
            } else {
                // We are going up but top limit is not tripped so go at commanded speed
                elevator.set(speed);
            }
        } else {
            if (bottomLimitSwitch.get()) {
                // We are going down and bottom limit is tripped so stop
                elevator.set(0);
            } else {
                // We are going down but bottom limit is not tripped so go at commanded speed
                elevator.set(speed);

            }
        }
    }

    public double currentEleveatorEncoder() {
        return elevatorEncoder.getPosition() * Constants.ElevatorConstants.gearRatio;
    }

        @Override
            public void periodic() {
                SmartDashboard.putNumber("leftCurrentDistance", currentEleveatorEncoder() * Constants.ElevatorConstants.gearRatio);

    }
}