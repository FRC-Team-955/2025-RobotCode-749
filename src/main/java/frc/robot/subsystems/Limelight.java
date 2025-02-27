package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.Arrays;

public class Limelight extends SubsystemBase {
    private static final NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

    public Limelight() {

    }

    public static double getTx() {
        return limelightTable.getEntry("tx").getDouble(0.0);
    }


    public static boolean hasTarget() {
        return limelightTable.getEntry("tv").getDouble(0) == 1;
    }

    public static double[] getTargetIDs() {
        return limelightTable.getEntry("tid").getDoubleArray(new double[0]);
    }

    public static Integer getFirstVisibleTag(int[] targetIDs) {
        double[] detectTags = getTargetIDs();
        for (double tag : detectTags) {
            int tagID = (int) tag;
             if
             (Arrays.stream(targetIDs).anyMatch(id -> id == tagID)) {

                return tagID;
            }
        }
        return  null;
    }
}

