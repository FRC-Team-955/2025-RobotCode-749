//package frc.robot.subsystems;
//
//import com.revrobotics.RelativeEncoder;
//import com.revrobotics.spark.SparkLowLevel;
//import com.revrobotics.spark.SparkMax;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.Constants;
//
//public class AlgaeSubSystem extends SubsystemBase {
//    private final SparkMax algaePivot;
//    private final RelativeEncoder algaePivotEncoder;
//
//    public AlgaeSubSystem() {
//
//
//        algaePivot = new SparkMax(Constants.AlgaeConstants.Algae_Intake_ID, SparkLowLevel.MotorType.kBrushless);
//        algaePivotEncoder = algaePivot.getEncoder();
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
//        return algaePivotEncoder.getPosition() * Constants.AlgaeConstants.gearRatio;
//    }
//}
//
//