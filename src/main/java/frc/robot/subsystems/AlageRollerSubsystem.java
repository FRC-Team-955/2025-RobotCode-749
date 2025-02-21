// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RollerConstants;

import java.util.function.DoubleSupplier;

/** Class to run the rollers over CAN */
public class AlageRollerSubsystem extends SubsystemBase {
  private final SparkMax algaeRollerMotor;

  public AlageRollerSubsystem() {
    // Set up the roller motor as a brushed motor
    algaeRollerMotor = new SparkMax(RollerConstants.ALGAE_ROLLER_MOTOR_ID, MotorType.kBrushed);
    // Set can timeout. Because this project only sets parameters once on
    // construction, the timeout can be long without blocking robot operation. Code
    // which sets or gets parameters during operation may need a shorter timeout.
    algaeRollerMotor.setCANTimeout(250);
    // Create and apply configuration for roller motor. Voltage compensation helps
    // the roller behave the same as the battery
    // voltage dips. The current limit helps prevent breaker trips or burning out
    // the motor in the event the roller stalls.

    SparkMaxConfig algaeRollerConfig = new SparkMaxConfig();
    algaeRollerConfig.voltageCompensation(RollerConstants.ALAGE_ROLLER_MOTOR_VOLTAGE_COMP);
    algaeRollerConfig.smartCurrentLimit(RollerConstants.ALAGE_ROLLER_MOTOR_CURRENT_LIMIT);
    algaeRollerMotor.configure(algaeRollerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
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
