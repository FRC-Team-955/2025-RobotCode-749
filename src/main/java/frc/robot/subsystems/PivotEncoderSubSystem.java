package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PivotEncoderSubSystem extends SubsystemBase {
    private final SparkMax pivot;
    //private final AbsoluteEncoder algaePivotEncoder;
    private final DutyCycleEncoder encoder;

    public PivotEncoderSubSystem() {
       pivot = new SparkMax(Constants.EncoderPivot.PIVOT_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);
      // algaePivotEncoder = algaePivot.getAbsoluteEncoder();
       encoder = new DutyCycleEncoder(3);
        SparkBaseConfig config = new SparkMaxConfig();
        //config.inverted(true);
        config.idleMode(SparkBaseConfig.IdleMode.kBrake);
        config.smartCurrentLimit(Constants.DriveConstants.DRIVE_MOTOR_CURRENT_LIMIT);
        pivot.configure(config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
    }


    public void setSpeed(double speed) {
        pivot.set(speed);
    }


    @Override
    public void periodic() {
        SmartDashboard.putNumber("pivotEncoder", currentEncoderPivotPosition());
        SmartDashboard.putNumber("IntakeSetPoint", Constants.EncoderPivot.intakePosition);
        SmartDashboard.putNumber("2,3LvSetPoint", Constants.EncoderPivot.lv2a3Position);
    }
    public double currentEncoderPivotPosition() {
        return encoder.get();
    }


    /*public double currentAlgaePivotEncoder() {
        return algaePivotEncoder.getPosition();
    }*/
}
