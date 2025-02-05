package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSubSystems  extends SubsystemBase {

private final SparkMax elevator;
private final DigitalInput topLimitSwitch;
    private final DigitalInput bottomLimitSwitch;
public ElevatorSubSystems() {
    topLimitSwitch = new DigitalInput(0);

    bottomLimitSwitch = new DigitalInput(1);

    elevator = new SparkMax(Constants.ElevatorConstants.ElEVATOR_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);


    public void setMotorSpeed(double speed) {
        if (speed > 0) {
            if (toplimitSwitch.get()) {
                // We are going up and top limit is tripped so stop
                motor.set(0);
            } else {
                // We are going up but top limit is not tripped so go at commanded speed
                motor.set(speed);
            }
        } else {
            if (bottomlimitSwitch.get()) {
                // We are going down and bottom limit is tripped so stop
                motor.set(0);
            } else {
                // We are going down but bottom limit is not tripped so go at commanded speed
                motor.set(speed);
            }
        }
    }
}
















}
