// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ElevatorPID;
import frc.robot.commands.Pivot;
import frc.robot.commands.AlgaePivot;
import frc.robot.commands.AlageRoller;
import frc.robot.commands.AutoRoller;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.CANRollerSubsystem;
import frc.robot.subsystems.ElevatorSubSystems;
import frc.robot.subsystems.PivotSubSystem;
import frc.robot.subsystems.AlgaeSubSystem;
import frc.robot.subsystems.AlageRollerSubsystem;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import java.util.List;
import java.lang.Math;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  // --- Subsystems from the first code ---
  private final DriveSubsystem swerveDriveSubsystem = new DriveSubsystem();

  // --- Subsystems from the second code ---
  private final CANRollerSubsystem rollerSubsystem = new CANRollerSubsystem();
  private final ElevatorSubSystems elevatorSubSystems = new ElevatorSubSystems();
  private final PivotSubSystem pivotSubSystem = new PivotSubSystem();
  private final AlgaeSubSystem algaeSubSystem = new AlgaeSubSystem();
  private final AlageRollerSubsystem alageRollerSubsystem = new AlageRollerSubsystem();
  private final AutoRoller autoRoller = new AutoRoller(rollerSubsystem, 0);

  // --- Controllers ---
  // Swerve drive uses a traditional XboxController.
  private final XboxController swerveDriverController = new XboxController(OIConstants.kDriverControllerPort);
  
  // The second code uses CommandXboxControllers.
  private final CommandXboxController driverCommandXboxController = new CommandXboxController(OperatorConstants.DRIVER_CONTROLLER_PORT);
  private final CommandXboxController operatorController = new CommandXboxController(OperatorConstants.OPERATOR_CONTROLLER_PORT);

  // --- Autonomous chooser ---
  private final SendableChooser<Command> autoChooser = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure button bindings (merging both files’ bindings)
    configureBindings();
    
    // Configure default commands for subsystems
    configureDefaultCommands();
    
    // Configure autonomous options
    configureAutoChooser();
    SmartDashboard.putData(autoChooser);
  }

  /**
   * Combine button bindings from both original files.
   */
  private void configureBindings() {
    // --- Bindings from the first code (Swerve drive) ---
    new JoystickButton(swerveDriverController, Button.kR1.value)
        .whileTrue(new RunCommand(() -> swerveDriveSubsystem.setX(), swerveDriveSubsystem));

    // --- Bindings from the second code ---
    operatorController.a().whileTrue(new AutoRoller(rollerSubsystem, Constants.RollerConstants.ROLLER_EJECT_VALUE));
    operatorController.b().whileTrue(new AutoRoller(rollerSubsystem, Constants.RollerConstants.ROLLER_SHOOT_VALUE));

    operatorController.leftTrigger().toggleOnTrue(new Pivot(pivotSubSystem, Constants.PivotConstants.intakePosition));
    
    operatorController.y().toggleOnTrue(Commands.parallel(
            new AlgaePivot(algaeSubSystem, Constants.AlgaeConstants.encoderSetpoint),
            new AlageRoller(alageRollerSubsystem, Constants.AlageRollerConstants.ALAGE_ROLLER_INTAKE)));
    
    operatorController.x().whileTrue(new AlageRoller(alageRollerSubsystem, Constants.AlageRollerConstants.AlAGE_ROLLER_SHOOT));
    
    // (Optional: if left bumper binding is needed, it can be added here)
    // operatorController.leftBumper().whileTrue(Commands.parallel());

    operatorController.rightTrigger().toggleOnTrue(new ElevatorPID(elevatorSubSystems, Constants.ElevatorConstants.encoderSetpoint));
    operatorController.rightBumper().toggleOnTrue(new ElevatorPID(elevatorSubSystems, Constants.ElevatorConstants.halfEncoderSetpoint));
  }

  /**
   * Set default commands for all subsystems.
   */
  private void configureDefaultCommands() {
    // --- Default command for the swerve drive subsystem (first code) ---
    swerveDriveSubsystem.setDefaultCommand(
        new RunCommand(() ->
            swerveDriveSubsystem.drive(
                -MathUtil.applyDeadband(swerveDriverController.getLeftY(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(swerveDriverController.getLeftX(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(swerveDriverController.getRightX(), OIConstants.kDriveDeadband),
                true),
            swerveDriveSubsystem));

  }

  /**
   * Configure autonomous command options by merging the autonomous routines.
   */
  private void configureAutoChooser() {
    // --- Option 1: Swerve Trajectory Autonomous from the first file ---
    TrajectoryConfig config = new TrajectoryConfig(
        AutoConstants.kMaxSpeedMetersPerSecond,
        AutoConstants.kMaxAccelerationMetersPerSecondSquared)
        .setKinematics(DriveConstants.kDriveKinematics);

    Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction.
        new Pose2d(0, 0, new Rotation2d(0)),
        // Interior waypoints.
        List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
        // End pose.
        new Pose2d(3, 0, new Rotation2d(0)),
        config);

    var thetaController = new ProfiledPIDController(
        AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints);
    thetaController.enableContinuousInput(-Math.PI, Math.PI);

    SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
        exampleTrajectory,
        swerveDriveSubsystem::getPose, // Pose supplier
        DriveConstants.kDriveKinematics,
        new PIDController(AutoConstants.kPXController, 0, 0),
        new PIDController(AutoConstants.kPYController, 0, 0),
        thetaController,
        swerveDriveSubsystem::setModuleStates,
        swerveDriveSubsystem);

    // Reset odometry for the swerve drive subsystem.
    swerveDriveSubsystem.resetOdometry(exampleTrajectory.getInitialPose());

    autoChooser.addOption("Swerve Trajectory", 
        swerveControllerCommand.andThen(() -> swerveDriveSubsystem.drive(0, 0, 0, false)));

    // // --- Option 2: Autonomous Forward from the second file ---
    // autoChooser.addOption("AutonomusFoward", new SequentialCommandGroup(
    //     new AutoForawrd(canDriveSubsystem, Constants.DriveConstants.distance),
    //     new ParallelCommandGroup(
    //         new ElevatorPID(elevatorSubSystems, Constants.ElevatorConstants.encoderSetpoint),
    //         new SequentialCommandGroup(
    //             new AutoRoller(rollerSubsystem, Constants.RollerConstants.ROLLER_EJECT_VALUE)))));

    // // --- Option 3: Autonomous Turn then Forward (second file) ---
    // autoChooser.addOption("AutonomousTurnthenFoward", new SequentialCommandGroup(
    //     new AutoTurn(canDriveSubsystem, Constants.DriveConstants.leftTurn, Constants.DriveConstants.rightTurn),
    //     new AutoForawrd(canDriveSubsystem, Constants.DriveConstants.distance),
    //     new ParallelCommandGroup(
    //         new ElevatorPID(elevatorSubSystems, Constants.ElevatorConstants.encoderSetpoint)),
    //     new SequentialCommandGroup(
    //         new AutoRoller(rollerSubsystem, Constants.RollerConstants.ROLLER_EJECT_VALUE))));

    // // --- Option 4: AprilTag Test (second file) ---
    // autoChooser.addOption("AprilTag Test", new SequentialCommandGroup(new AprilTag(canDriveSubsystem)));

    // // Set a default option (here we choose Autonomous Forward)
    // autoChooser.setDefaultOption("AutonomusFoward", new SequentialCommandGroup(
    //     new AutoForawrd(canDriveSubsystem, Constants.DriveConstants.distance),
    //     new ParallelCommandGroup(
    //         new ElevatorPID(elevatorSubSystems, Constants.ElevatorConstants.encoderSetpoint),
    //         new SequentialCommandGroup(
    //             new AutoRoller(rollerSubsystem, Constants.RollerConstants.ROLLER_EJECT_VALUE)))));
  }

  /**
   * Returns the selected autonomous command.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}