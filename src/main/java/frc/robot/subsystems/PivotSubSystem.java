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

public class PivotSubSystem  extends SubsystemBase {

        private final SparkMax pivot;
        private final RelativeEncoder pivotEncoder;

        public PivotSubSystem() {

            pivot = new SparkMax(Constants.PivotConstants.PIVOT_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);

            pivotEncoder = pivot.getEncoder();

            SparkBaseConfig config = new SparkMaxConfig();
            config.idleMode(SparkBaseConfig.IdleMode.kBrake);
            pivot.configure(config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
        }

    public void setSpeed(double speed) {
        pivot.set(speed);
    }


    @Override
        public void periodic() {
            SmartDashboard.putNumber("currentPivotEncoder", currentPivotEncoder());
        }
        public double currentPivotEncoder() {
            return pivotEncoder.getPosition();
        }
    }

