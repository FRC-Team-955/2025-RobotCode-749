package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

public final class AutoTurn {
  // Example autonomous command which drives forward for 1 second.
  public static final Command autoTurn(CANDriveSubsystem driveSubsystem, double turnValue) {
    return driveSubsystem.driveArcade(driveSubsystem, () -> 0.0, () -> turnValue).withTimeout(1.0);
  }
}