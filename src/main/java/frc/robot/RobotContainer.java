// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.AlageRoller;
import frc.robot.commands.AlgaePivot;
import frc.robot.commands.AutoAlign;
import frc.robot.commands.AutoBack;
import frc.robot.commands.AutoForawrd;
import frc.robot.commands.AutoRIght;
import frc.robot.commands.AutoRoller;
import frc.robot.commands.Autos;
import frc.robot.commands.Climber;
import frc.robot.commands.ElevatorPID;
import frc.robot.commands.PivotEncoder;
import frc.robot.subsystems.AlageRollerSubsystem;
import frc.robot.subsystems.AlgaeSubSystem;
import frc.robot.subsystems.CANDriveSubsystem;
import frc.robot.subsystems.CANRollerSubsystem;
import frc.robot.subsystems.ElevatorSubSystems;
import frc.robot.subsystems.PivotEncoderSubSystem;
import frc.robot.subsystems.zClimberSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

    // The robot's subsystems
    private final CANDriveSubsystem driveSubsystem = new CANDriveSubsystem();
    private final CANRollerSubsystem rollerSubsystem = new CANRollerSubsystem();
    private final ElevatorSubSystems elevatorSubSystems = new ElevatorSubSystems();
    //private final PivotSubSystem pivotSubSystem = new PivotSubSystem();
    private final AlgaeSubSystem algaeSubSystem = new AlgaeSubSystem();
    private final AlageRollerSubsystem alageRollerSubsystem = new AlageRollerSubsystem();
    private final PivotEncoderSubSystem pivotEncoderSubSystem = new PivotEncoderSubSystem();
    private final zClimberSubsystem climberSubsystem = new zClimberSubsystem();

    // The driver's controller
    private final CommandXboxController driverController = new CommandXboxController(
            OperatorConstants.DRIVER_CONTROLLER_PORT);

    // The operator's controller
    private final CommandXboxController operatorController = new CommandXboxController(
            OperatorConstants.OPERATOR_CONTROLLER_PORT);

    // The autonomous chooser
    private final SendableChooser<Command> autoChooser = new SendableChooser<>();

    /**
     * The container for the robot. Contains subsystems, OI devices, and
     * commands.
     */
    public RobotContainer() {
        configureBindings();
        SmartDashboard.putData(autoChooser);
        // Set the options to show up in the Dashboard for selecting auto modes. If you
        // add additional auto modes you can add additional lines here with
        // autoChooser.addOption
        autoChooser.setDefaultOption("Forward(WORK)", new SequentialCommandGroup(
                new AutoForawrd(driveSubsystem, Constants.DriveConstants.distance),
                new ParallelCommandGroup(
                        new AutoRoller(rollerSubsystem, Constants.RollerConstants.ROLLER_EJECT_VALUE))));
        /*autoChooser.addOption("ForwardAboEncoder(Idk)", new SequentialCommandGroup(
                new AutoForawrd(driveSubsystem, Constants.DriveConstants.distance),
                new ParallelCommandGroup(
                        new AutoRoller(rollerSubsystem, Constants.RollerConstants.ROLLER_EJECT_VALUE))));*/


 /* new AutoForawrd(driveSubsystem, Constants.DriveConstants.distance),
        new ParallelCommandGroup(
                new ElevatorPID(elevatorSubSystems, Constants.ElevatorConstants.encoderSetpoint),
                new SequentialCommandGroup(
                        new AutoRoller(rollerSubsystem, Constants.RollerConstants.ROLLER_EJECT_VALUE)))));*/

 /* autoChooser.addOption("TurnGO", new SequentialCommandGroup(
                Autos.exampleAuto(driveSubsystem),
                new AutoForawrd(driveSubsystem, Constants.DriveConstants.distance),
                new ParallelCommandGroup(
                        new AutoRoller(rollerSubsystem,Constants.RollerConstants.ROLLER_EJECT_VALUE))));
         */
        autoChooser.addOption("TurnRight(work)", (new SequentialCommandGroup(
                Autos.exampleAuto(driveSubsystem),
                new AutoForawrd(driveSubsystem, 50))));
        autoChooser.addOption("TurnLeft(work)", new SequentialCommandGroup(
                AutoRIght.exampleAuto(driveSubsystem),
                new AutoForawrd(driveSubsystem, 50)));
        //new AutoTurn(driveSubsystem, Constants.DriveConstants.leftTurn, Constants.DriveConstants.rightTurn)));
        // new AutoForawrd(driveSubsystem, Constants.DriveConstants.distance),
        //new ElevatorPID(elevatorSubSystems, Constants.ElevatorConstants.encoderSetpoint),
        //new SequentialCommandGroup(
        //new AutoRoller(rollerSubsystem, Constants.RollerConstants.ROLLER_EJECT_VALUE))));*/

        autoChooser.addOption("FowardONLY(work)", new SequentialCommandGroup(
                new AutoForawrd(driveSubsystem, Constants.DriveConstants.distance)));

        autoChooser.addOption("FandB(IDK)", new SequentialCommandGroup(
                new AutoForawrd(driveSubsystem, Constants.DriveConstants.distance),
                new ParallelCommandGroup(
                        new AutoRoller(rollerSubsystem, Constants.RollerConstants.ROLLER_EJECT_VALUE),
                        new SequentialCommandGroup(
                                AutoBack.exampleAuto(driveSubsystem)))));

        autoChooser.addOption("AutoAlign", new SequentialCommandGroup(new AutoAlign(driveSubsystem)));

        autoChooser.addOption("Forward,TurnLeft,Align",
                new SequentialCommandGroup(
                        new AutoForawrd(driveSubsystem, Constants.DriveConstants.distance ),
                        AutoRIght.exampleAuto(driveSubsystem),
                        new AutoAlign(driveSubsystem),
                        new ParallelCommandGroup(
                                new ElevatorPID(elevatorSubSystems, Constants.ElevatorConstants.encoderSetpoint).withTimeout(1.5).andThen(
                                        new AutoRoller(rollerSubsystem, Constants.RollerConstants.ROLLER_EJECT_VALUE).withTimeout(1.5)))));

        autoChooser.addOption("Forward,TurnRight,Align",
                new SequentialCommandGroup(
                        new AutoForawrd(driveSubsystem, Constants.DriveConstants.distance),
                        Autos.exampleAuto(driveSubsystem),
                        new AutoAlign(driveSubsystem),
                        new ParallelCommandGroup(
                                new ElevatorPID(elevatorSubSystems, Constants.ElevatorConstants.encoderSetpoint).withTimeout(1.5).andThen(
                                        new AutoRoller(rollerSubsystem, Constants.RollerConstants.ROLLER_EJECT_VALUE).withTimeout(1.5)))));

        autoChooser.addOption("AutoAlign with score",
                new SequentialCommandGroup(
                        new AutoAlign(driveSubsystem),
                        new ParallelCommandGroup(
                                new ElevatorPID(elevatorSubSystems, Constants.ElevatorConstants.encoderSetpoint).withTimeout(1.5).andThen(
                                        new AutoRoller(rollerSubsystem, Constants.RollerConstants.ROLLER_EJECT_VALUE).withTimeout(1.5)))));

        autoChooser.addOption("AutoAlign with two score",
                new SequentialCommandGroup(
                        new AutoAlign(driveSubsystem),
                        new ParallelCommandGroup(
                                new ElevatorPID(elevatorSubSystems, Constants.ElevatorConstants.encoderSetpoint).withTimeout(2).andThen(
                                        new AutoRoller(rollerSubsystem, Constants.RollerConstants.ROLLER_EJECT_VALUE).withTimeout(1.5))),
                        new SequentialCommandGroup(
                                AutoBack.exampleAuto(driveSubsystem),
                                AutoRIght.exampleAuto(driveSubsystem),
                                new ParallelCommandGroup(
                                        new PivotEncoder(pivotEncoderSubSystem, Constants.PivotConstants.intakePosition).andThen(
                                                new AutoAlign(driveSubsystem))),
                                new SequentialCommandGroup(
                                        new WaitCommand(2),
                                        AutoBack.exampleAuto(driveSubsystem),
                                        Autos.exampleAuto(driveSubsystem),
                                        new AutoAlign(driveSubsystem),
                                        new PivotEncoder(pivotEncoderSubSystem, Constants.PivotConstants.lvTwoAndThreeEncoderSetpoint).andThen(
                                                new AutoRoller(rollerSubsystem, Constants.RollerConstants.ROLLER_EJECT_VALUE))))));

        //new ParallelCommandGroup(
        //new AutoForawrd(driveSubsystem, Constants.DriveConstants.backDistance))));
        //new AutoTurn(driveSubsystem, Constants.DriveConstants.leftTurn, Constants.DriveConstants.rightTurn);
    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be
     * created via the {@link Trigger#Trigger(BooleanSupplier)} constructor with
     * an arbitrary predicate, or via the named factories in {@link
     * CommandGenericHID}'s subclasses for      {@link
     * CommandXboxController
     * Xbox}/{@link CommandPS4Controller
     * PS4} controllers or      {@link CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {
        // Set the A button to run the "runRoller" command from the factory with a fixed
        // value ejecting the gamepiece while the button is held
//    operatorController.a()
//            .whileTrue(rollerSubsystem.runRoller(rollerSubsystem, () -> Constants.RollerConstants.ROLLER_EJECT_VALUE, () -> 0));
        operatorController.a().whileTrue(new AutoRoller(rollerSubsystem, Constants.RollerConstants.ROLLER_EJECT_VALUE));
        // operatorController.a().whileFalse(new AutoRoller(rollerSubsystem, 0));
        operatorController.b().whileTrue((new AutoRoller(rollerSubsystem, Constants.RollerConstants.ROLLER_SHOOT_VALUE)));
        // operatorController.b().whileFalse(new AutoRoller(rollerSubsystem, 0));

        operatorController.povLeft().whileTrue(new Climber(climberSubsystem, 1));
        operatorController.povLeft().whileFalse(new Climber(climberSubsystem, 0));
        operatorController.povRight().whileTrue(new Climber(climberSubsystem, -1));
        operatorController.povRight().whileFalse(new Climber(climberSubsystem, 0));

        /*operatorController.leftTrigger().toggleOnTrue(
                new Pivot(pivotSubSystem, Constants.PivotConstants.intakePosition));*/
        operatorController.leftTrigger().toggleOnTrue(
                new PivotEncoder(pivotEncoderSubSystem, Constants.EncoderPivot.intakePosition)
        );
        operatorController.povUp().toggleOnTrue(
                new AutoAlign(driveSubsystem)
        );

        /*operatorController.leftBumper().toggleOnTrue(
               Commands.parallel(
                       new AlgaePivot(algaeSubSystem, Constants.AlgaeConstants.newEncoderSetpoint),
                       new AlageRoller(alageRollerSubsystem, Constants.AlageRollerConstants.ALAGE_ROLLER_INTAKE)
               )
       );*/
        //Abo encoder
        /*operatorController.leftBumper().toggleOnTrue(
                new Pivot(pivotSubSystem, -8.0)
        );*/

 /*operatorController.y().toggleOnTrue(
                new AlgaePivot(algaeSubSystem, Constants.AlgaeConstants.encoderSetpoint));
       operatorController.x().whileTrue(
                new AlageRoller(alageRollerSubsystem, Constants.AlageRollerConstants.ALAGE_ROLLER_INTAKE));*/
        operatorController.y().toggleOnTrue(Commands.parallel(
                new AlgaePivot(algaeSubSystem, Constants.AlgaeConstants.encoderSetpoint),
                new AlageRoller(alageRollerSubsystem, Constants.AlageRollerConstants.ALAGE_ROLLER_INTAKE)));
        operatorController.x().whileTrue(
                new AlageRoller(alageRollerSubsystem, Constants.AlageRollerConstants.AlAGE_ROLLER_SHOOT)
        );

//       driverController.x().whileTrue(new AlgaePivot(algaeIntakeSubSystem,Constants.AlgaeIntakeConstants.encoderSetpoint));
//       driverController.rightBumper().toggleOnTrue(new ElevatorPID(elevatorSubSystems, Constants.ElevatorConstants.halfEncoderSetpoint));
        operatorController.rightTrigger().toggleOnTrue(new ElevatorPID(elevatorSubSystems, Constants.ElevatorConstants.Orginal));
        operatorController.rightBumper().toggleOnTrue(new ElevatorPID(elevatorSubSystems, Constants.ElevatorConstants.halfEncoderSetpoint));
        operatorController.leftBumper().toggleOnTrue(new ElevatorPID(elevatorSubSystems, Constants.ElevatorConstants.encoderSetpoint));
        // operatorController.leftBumper().toggleOnTrue(new AutoRoller(rollerSubsystem, 0.15));

        // Set the default command for the drive subsystem to the command provided by
        // factory with the values provided by the joystick axes on the driver
        // controller. The Y axis of the controller is inverted so that pushing the
        // stick away from you (a negative value) drives the robot forwards (a positive
        // value)
        driveSubsystem.setDefaultCommand(
                driveSubsystem.driveArcade(
                        driveSubsystem, () -> Constants.DriveConstants.speedFactor * -operatorController.getLeftY(),
                        () -> Constants.DriveConstants.turningFactor * -operatorController.getRightX()));

        elevatorSubSystems.setDefaultCommand(new ElevatorPID(elevatorSubSystems, Constants.ElevatorConstants.encoderSetpoint));
        rollerSubsystem.setDefaultCommand(new AutoRoller(rollerSubsystem, 0.2));
        algaeSubSystem.setDefaultCommand(new AlgaePivot(algaeSubSystem, Constants.AlgaeConstants.original));//-0.1
        // pivotSubSystem.setDefaultCommand(new Pivot(pivotSubSystem, Constants.PivotConstants.lvTwoAndThreeEncoderSetpoint)); //-16 og
        alageRollerSubsystem.setDefaultCommand(new AlageRoller(alageRollerSubsystem, -0.1));
        pivotEncoderSubSystem.setDefaultCommand(new PivotEncoder(pivotEncoderSubSystem,
                Constants.EncoderPivot.lv2a3Position));

        /*lageRollerSubsystem.setDefaultCommand((
                alageRollerSubsystem.runRoller(
                alageRollerSubsystem,
                () -> -operatorController.getLeftY(),
                () -> -operatorController.getLeftY())
        ));*/
        // Set the default command for the roller subsystem to the command from the
        // factory with the values provided by the triggers on the operator controller
//    rollerSubsystem.setDefaultCommand(
//        rollerSubsystem.runRoller(
//            rollerSubsystem,
//            () -> operatorController.getRightTriggerAxis(),
//            () -> operatorController.getLeftTriggerAxis()));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        return autoChooser.getSelected();

    }
}
