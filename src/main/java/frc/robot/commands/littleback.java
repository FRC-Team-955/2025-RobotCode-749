package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

public final class littleback {
  // Example autonomous command which drives forward for 1 second.
  public static final Command exampleAuto(CANDriveSubsystem driveSubsystem) {
    return driveSubsystem.driveArcade(driveSubsystem, () -> -0.40, () -> 0.0).withTimeout(1.5);
  }
}