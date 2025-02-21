//// Copyright (c) FIRST and other WPILib contributors.
//// Open Source Software; you can modify and/or share it under the terms of
//// the WPILib BSD license file in the root directory of this project.
//
//package frc.robot.subsystems;
//
//
//import com.revrobotics.spark.SparkLowLevel.MotorType;
//import com.revrobotics.spark.SparkMax;
//import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.Constants.RollerConstants;
//
//
//  public class AlageRollerSubsystem extends SubsystemBase {
//  private final SparkMax algaeRollerMotor;
//
//  public AlageRollerSubsystem() {
//    algaeRollerMotor = new SparkMax(RollerConstants.ALGAE_ROLLER_MOTOR_ID, MotorType.kBrushed);
//  }
//
//  @Override
//  public void periodic() {
//  }
//
//  public void setAlgaeRollerMotor(double speed) {
//    algaeRollerMotor.set(speed);
//  }
//  // Command to run the roller with joystick inputs
//  /*public Command runRoller(
//          AlageRollerSubsystem rollerSubsystem, DoubleSupplier forward, DoubleSupplier reverse) {
//    return Commands.run(
//        () -> algaeRollerMotor.set(forward.getAsDouble() - reverse.getAsDouble()), rollerSubsystem);
//  }*/
//
//}
