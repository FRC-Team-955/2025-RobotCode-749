// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class DriveConstants {
    public static final int LEFT_LEADER_ID = 1;
    public static final int LEFT_FOLLOWER_ID = 2;
    public static final int RIGHT_LEADER_ID = 3;
    public static final int RIGHT_FOLLOWER_ID = 4;
    public static final double gearRatio = 1 / 8.46;
    public static final double distance = 72.0;//84 OG //70.0 Old auto
    public static final double backDistance = 7.0;
    public static final double wheelDiameter = 6.0;
    public static final double encoderCPR = 42.0;
    public static final double speedFactor = 1.0;
    public static final double turningFactor = 0.75;
    public static final double distancePerPulse = (Math.PI * wheelDiameter) / (encoderCPR * gearRatio);
    public static final double rightTurn = 20;
    public static final double leftTurn = -20;

    public static final int DRIVE_MOTOR_CURRENT_LIMIT = 60;
  }
  public static final class ElevatorConstants {
    public static final int ElEVATOR_MOTOR_ID = 12;
    public static final double Orginal = 0;
    //public static final double gearRatio =  1 / 20;
    public static final double encoderSetpoint = 110; // lvel three 110 //112 is the top if 0 is the bottom
    public static final double halfEncoderSetpoint = 37; // level two
  }
public static final class AlgaeConstants{
    public static final int Algae_Intake_ID = 10;
    //public static final double gearRatio = 0.0;
    public static final double encoderSetpoint = 0.215; //intake
  public static final double original = 0.13; //taken
//FIND NEW ENCODERSETPOINT FOR ALGAEPIVOT
}


  public static final class PivotConstants {
    public static final int PIVOT_MOTOR_ID = 9;
   // public static final double gearRatio = 1 / 75;
    public static final double intakePosition = -17;
    public static final double lvTwoAndThreeEncoderSetpoint = -8.5; //lv 2 and 3 position
    //public static final double intakePosition = -17.0;
   // public static final double lvTwoAndThreeEncoderSetpoint = -8.0; //lv 2 and 3 position og
    //public static final double bottomEncoderSetpoint = -16.0; //intake position
    //public static final double topEncoderSetpoint = -32.0;

  }
  public static final class AlageRollerConstants {
    public static final int ALGAE_ROLLER_MOTOR_ID = 11;
    public static final int ALAGE_ROLLER_MOTOR_CURRENT_LIMIT = 60;
    public static final double ALAGE_ROLLER_MOTOR_VOLTAGE_COMP = 10;
    public static final double ALAGE_ROLLER_INTAKE = -1.0;
    public static final double AlAGE_ROLLER_SHOOT = 1.0;
  }

  public static final class RollerConstants {
    public static final int ROLLER_MOTOR_ID = 7;
    public static final int ROLLER_MOTOR_CURRENT_LIMIT = 60;
    public static final double ROLLER_MOTOR_VOLTAGE_COMP = 10;
    public static final double ROLLER_EJECT_VALUE = -0.85; //-0.75 //shooting it out -
    public static final double Autonomuse_ROLLER_EJECT_VALUE = -0.21;
    public static final double ROLLER_SHOOT_VALUE= 0.2; // intakeing it in +
  }

  public static final class OperatorConstants {
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final int OPERATOR_CONTROLLER_PORT = 1;
  }
}