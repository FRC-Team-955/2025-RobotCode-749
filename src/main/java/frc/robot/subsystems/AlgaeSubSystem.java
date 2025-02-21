//package frc.robot.subsystems;
//
//import com.revrobotics.AbsoluteEncoder;
//import com.revrobotics.RelativeEncoder;
//import com.revrobotics.spark.SparkBase;
//import com.revrobotics.spark.SparkLowLevel;
//import com.revrobotics.spark.SparkMax;
//import com.revrobotics.spark.config.SparkBaseConfig;
//import com.revrobotics.spark.config.SparkMaxConfig;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.Constants;
//
//public class AlgaeSubSystem extends SubsystemBase {
//    private final SparkMax algaePivot;
//    private final AbsoluteEncoder algaePivotEncoder;
//
//    public AlgaeSubSystem() {
//       algaePivot = new SparkMax(Constants.AlgaeConstants.Algae_Intake_ID, SparkLowLevel.MotorType.kBrushless);
//       algaePivotEncoder = algaePivot.getAbsoluteEncoder();
//        SparkBaseConfig config = new SparkMaxConfig();
//        config.idleMode(SparkBaseConfig.IdleMode.kBrake);
//        algaePivot.configure(config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
//    }
//
//
//    public void setSpeed(double speed) {
//        algaePivot.set(speed);
//    }
//
//
//    @Override
//    public void periodic() {
//        SmartDashboard.putNumber("currentAlgaePivotEncoder", currentAlgaePivotEncoder());
//    }
//    public double currentAlgaePivotEncoder() {
//        return algaePivotEncoder.getPosition();
//    }
//}
