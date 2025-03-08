package frc.robot.subsystems;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class zClimberSubsystem extends SubsystemBase {
    private final TalonFX kraken;
    private final TalonFXConfiguration talonConfig;
    private final StatusSignal<Angle> position;

    public zClimberSubsystem() {
        kraken = new TalonFX(16);
        // algaePivotEncoder = algaePivot.getAbsoluteEncoder();
        position = kraken.getPosition();
        talonConfig = new TalonFXConfiguration();
        talonConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        talonConfig.Feedback.SensorToMechanismRatio = 640;
        talonConfig.TorqueCurrent.PeakForwardTorqueCurrent = 40;
        talonConfig.TorqueCurrent.PeakReverseTorqueCurrent = 40;
        talonConfig.CurrentLimits.StatorCurrentLimit = 40;
        talonConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        talonConfig.MotorOutput.Inverted = Constants.ClimberConstants.inverted
                ? InvertedValue.Clockwise_Positive
                : InvertedValue.CounterClockwise_Positive;
        kraken.getConfigurator().apply(talonConfig,0.25);
    }


    public void setSpeed(double speed) {
        kraken.set(speed);
        SmartDashboard.putNumber("Speed", kraken.getMotorVoltage().getValueAsDouble());
    }


    @Override
    public void periodic() {
        SmartDashboard.putNumber("krakenEncoder", currentEncoderClimberPosition());
    }
    public double currentEncoderClimberPosition() {
        return position.getValueAsDouble();

    }
}