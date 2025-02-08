package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PivotSubSystems extends SubsystemBase {

    private final SparkMax pivotMotor;

    private final RelativeEncoder pivotEncoder;

    public PivotSubSystems() {
        pivotMotor = new SparkMax(Constants.PivotConstants.ElEVATOR_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);

        pivotEncoder = pivotMotor.getEncoder();

        SparkMaxConfig config = new SparkMaxConfig();

        config.idleMode(SparkBaseConfig.IdleMode.kBrake);

        pivotMotor.configure(config, SparkBase.ResetMode.kNoResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
    }

    /*public boolean real() {
        if (topLimitSwitch.get()) return true;
        else return false;
    }*/

    public void setSpeed(double speed) {
        pivotMotor.set(-speed);
    }

    public double currentPivotEncoder() {
        return pivotEncoder.getPosition() * Constants.PivotConstants.gearRatio;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("pivotCurrentDistance", currentPivotEncoder() * Constants.PivotConstants.gearRatio);

    }
}

