package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;
import frc.robot.subsystems.Limelight;

public class AutoAlign extends Command {


    private final PIDController turnPID;
    private final Limelight limelight;
    private final int[] targetIDs;
    private Integer currentTargetID = null;
    private final CANDriveSubsystem driveSubsystem;

    public AutoAlign(Limelight limelight, CANDriveSubsystem driveSubsystem,int...targetIDs) {
        this.driveSubsystem = driveSubsystem;
        this.turnPID = new PIDController(0.04, 0, 0.002);
        turnPID.setTolerance(1.0);
        this.targetIDs = targetIDs;
        this.limelight = limelight;
        addRequirements(limelight);
    }

    @Override
    public void initialize() {
        SmartDashboard.putString("AutoAlign",java.util.Arrays.toString(targetIDs));
    }

    @Override
    public void execute() {

        currentTargetID = Limelight.getFirstVisibleTag(targetIDs);
        if (currentTargetID != null) {

            double turnSpeed = turnPID.calculate(Limelight.getTx(), 0);
            turnSpeed = Math.copySign(Math.min(Math.abs(turnSpeed),0.5),turnSpeed);
            double finalTurnSpeed = turnSpeed;
            driveSubsystem.driveArcade(driveSubsystem, () -> 0.0,() -> finalTurnSpeed);
        } else {
            driveSubsystem.driveArcade(driveSubsystem, () ->  0.0, () -> 0.0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.driveArcade(driveSubsystem,() ->  0.0, () -> 0.0);
        SmartDashboard.putNumber("AutoAlign finished. : " + currentTargetID, currentTargetID);
    }

    @Override
    public boolean isFinished() {
        return currentTargetID != null && turnPID.atSetpoint();

    }
}
