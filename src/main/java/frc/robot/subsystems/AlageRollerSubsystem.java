// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.util.function.DoubleSupplier;


public class AlageRollerSubsystem extends SubsystemBase {
  private final SparkMax algaeRollerMotor;

  public AlageRollerSubsystem() {
    algaeRollerMotor = new SparkMax(Constants.AlageRollerConstants.ALGAE_ROLLER_MOTOR_ID, MotorType.kBrushless);
    algaeRollerMotor.setCANTimeout(250);
    SparkMaxConfig rollerConfig = new SparkMaxConfig();
    rollerConfig.voltageCompensation(Constants.RollerConstants.ROLLER_MOTOR_VOLTAGE_COMP);
    rollerConfig.smartCurrentLimit(Constants.RollerConstants.ROLLER_MOTOR_CURRENT_LIMIT);
    algaeRollerMotor.configure(rollerConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
  }

  public void setAlgaeRollerMotor(double speed) {
    algaeRollerMotor.set(speed);
  }
  // Command to run the roller with joystick inputs
  public Command runRoller(
          AlageRollerSubsystem rollerSubsystem, DoubleSupplier forward, DoubleSupplier reverse) {
    return Commands.run(
        () -> algaeRollerMotor.set(forward.getAsDouble() - reverse.getAsDouble()), rollerSubsystem);
  }

}
