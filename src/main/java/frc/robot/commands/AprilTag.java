package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;

public class AprilTag extends Command {

    public enum RobotState {
        IDENTIFYTAG, APPROACH, FORWARD, ROTATELR, CENTERPID
    }

    // Autonomous state variables and timer
    private RobotState autostate;
    private final Timer autotimer;
    private long tagid;
    private boolean tagL;

    // Reference to the drive subsystem (assumed to have an arcadeDrive method and sensor access)
    private final CANDriveSubsystem drive;

    // Example threshold for encoder velocity (adjust as necessary)
    private double thresh = 0.5;

    // Variables used in state logic, initialized from NetworkTables.
    // For a double array value, use getDoubleArray(...) with an empty default array.
    private double[] targetpose = NetworkTableInstance.getDefault()
            .getTable("limelight")
            .getEntry("targetpose_robotspace")
            .getDoubleArray(new double[6]);
    // 'tx' reading (assumed to be updated periodically; you may need to refresh these values in execute)
    private double tx = NetworkTableInstance.getDefault()
            .getTable("limelight")
            .getEntry("tx")
            .getDouble(0);
    // 'tz' reading from which we compute the absolute value as within_sonic_range.
    private double within_sonic_range =  NetworkTableInstance.getDefault()
            .getTable("limelight")
            .getEntry("ty")
            .getDouble(0);
    public AprilTag(CANDriveSubsystem drive) {
        this.drive = drive;
        addRequirements(drive);

        autostate = RobotState.IDENTIFYTAG;
        autotimer = new Timer();
        autotimer.start();
    }

    @Override
    public void initialize() {
        autotimer.reset();
        autotimer.start();
    }

    @Override
    public void execute() {
        // Update vision values from NetworkTables if needed
        targetpose = NetworkTableInstance.getDefault()
                .getTable("limelight")
                .getEntry("targetpose_robotspace")
                .getDoubleArray(new double[0]);
        tx = NetworkTableInstance.getDefault()
                .getTable("limelight")
                .getEntry("tx")
                .getDouble(0);
        within_sonic_range = Math.abs(NetworkTableInstance.getDefault()
                .getTable("limelight")
                .getEntry("tz")
                .getDouble(0));

        // Determine if a valid target is found (make sure targetpose has at least 3 elements)
        boolean target_found = (targetpose != null && targetpose.length > 2 && targetpose[2] != 0);

        if (autostate == RobotState.IDENTIFYTAG) {
            SmartDashboard.putString("State", "Identify Tag");
            if (autotimer.get() > 1.5 && target_found) {
                autostate = RobotState.FORWARD;
                tagid = NetworkTableInstance.getDefault()
                        .getTable("limelight")
                        .getEntry("tid")
                        .getInteger(0);
            } else {
                double timerValue = autotimer.get();
                if (timerValue < 1) {
                    drive.ArcadeDrive(drive,() -> 0.5, () -> 0.0) ;
                } else if (timerValue < 1.5) {
                    drive.ArcadeDrive(drive, () -> 0, () -> -0.35);
                } else if (timerValue < 2) {
                    drive.ArcadeDrive(drive,() ->0, () ->0);
                } else if (timerValue < 2.5) {
                    drive.ArcadeDrive(drive,() ->0, () ->-0.35);
                } else if (timerValue < 3) {
                    drive.ArcadeDrive(drive,() ->0, () ->0);
                } else if (timerValue < 3.5) {
                    drive.ArcadeDrive(drive, () ->0, () ->-0.35);
                } else {
                    drive.ArcadeDrive(drive, () -> 0, () -> 0);
                }
            }
        } else if (autostate == RobotState.APPROACH) {
            SmartDashboard.putString("State", "Approach");
            drive.ArcadeDrive(drive, () ->0, () ->0);
            boolean leftSet = false;
            boolean rightSet = false;

            // Replace these calls with your actual sensor methods from your drive subsystem.
            if (Math.abs(drive.leftCurrentVelo()) > thresh) {
                drive.setSpeed(0.1, 0);
            } else {
                leftSet = true;
                drive.setSpeed(0, 0);
            }

            if (Math.abs(drive.rightCurrentVelo()) > thresh) {
                drive.setSpeed(0, 0.1);
            } else {
                rightSet = true;
                drive.setSpeed(0, 0);
            }

            // Conveyor control code was commented out in your original snippet.
        } else if (autostate == RobotState.FORWARD) {
            SmartDashboard.putString("State", "Forward");
            drive.ArcadeDrive(drive, () ->0.4, () ->0);
            if (target_found) {
                tagL = (tx < 0);
                // Transition condition – adjust the check on within_sonic_range as needed.
                if (within_sonic_range > 0) {
                    autostate = RobotState.CENTERPID;
                }
            } else {
                autostate = RobotState.ROTATELR;
            }
        } else if (autostate == RobotState.ROTATELR) {
            SmartDashboard.putString("State", "Rotation");
            if (tagL) {
                drive.ArcadeDrive(drive, () ->0, () ->-0.25);
            } else {
                drive.ArcadeDrive(drive, () ->0,() -> 0.25);
            }
            if (target_found) {
                autostate = RobotState.CENTERPID;
            }
        } else if (autostate == RobotState.CENTERPID) {
            SmartDashboard.putString("State", "Centering");
            // If tx is 0 (or use an appropriate null/invalid check), revert to rotation.
            if (tx == 0) {
                autostate = RobotState.ROTATELR;
                return;
            }
            // Additional centering logic can be implemented here.
        }
    }

    @Override
    public void end(boolean interrupted) {
        drive.ArcadeDrive(drive, () ->0,() -> 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}