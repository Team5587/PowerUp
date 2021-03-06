package org.frc5587.robot2018;

/**
 * Constants is a central place in which to store all of the constants having to
 * do with the robot and its subsystems. Where applicable, the constants are
 * stored within subclasses that describe the subsystem with which the constant
 * is associated and/or used.
 */
public class Constants {

    public static boolean compressorEnabled = true;

    public static final class Drive {
        // set to zero to skip waiting for confirmation, set to nonzero to wait and
        // report to DS if action fails
        public static final int kTimeoutMs = 10;

        public static final double kMaxVelocity = 2500; // measured in STU

        public static final double kVCompSaturation = 12.0;

        public static final int minBufferCount = 10;

        public static final int stuPerInch = 215;

        public static final int stuPerRev = 4050;

        public static final int wheelDiameter = 6;

        // Safety limits
        public static final double minPercentOut = 0,
            maxPercentBw = 1,
            maxPercentFw = 1; 

        // PIDF Constants
        public static final double[] leftPIDs = {
            0.01, // kP
            0.0, // kI
            0.01, // kD
            0.000327 * 1023 // kF
        };
        public static final double[] rightPIDs = {
            0.01, // kP
            0.0, // kI
            0.01, // kD
            0.000317 * 1023 // kF
        };

        public static final double[] pathfinderPIDVALeft = {
            0.04, // kP
            0.0, // kI/**/
            0.0, // kD
            0.000327 * stuPerInch / 10f, // kV
            0.0001 * stuPerInch / 10f // kA
        };
        public static final double[] pathfinderPIDVARight = {
            0.04, // kP
            0.0, // kI
            0.0, // kD
            0.000317 * stuPerInch / 10f, // kV
            0.0001 * stuPerInch / 10f // kA
        };

		public static double gyrokP = 0.00;
    }
    
    public static final class Elevator {
        // Which PID slot to pull gains from. Starting 2018, you can choose from 0,1,2
        // or 3.
        public static final int kSlotIdx = 0;
        // Talon SRX/Victor SPX will supported multiple (cascaded) PID loops. For now we
        // just want the primary one
        public static final int kPIDLoopIdx = 0;
        // set to zero to skip waiting for confirmation, set to nonzero to wait and
        // report to DS if action fails
        public static final int kTimeoutMs = 10;

        public static final double vCompSaturation = 12.0;

        
        // 775pro lift code below
        // public static final double holdPercent = 0.2;

        // // The tolerance for the target position (see Elevator.isDone())
        // public static final double kDeadband = 200;
        // //PID Constants
        // public static final double kF = .2,
        // kP = 0.1,
        // kI = 0.0,
        // kD = 0.13;
        // //Safety limits
        // public static final double minPercentOut = 0,
        // maxPercentBw = .2,
        // maxPercentFw = 1;

        // //System Constraints
        // public static final int maxVelocity = 4200, //measured in native units/100ms
        // maxAcceleration = 8000; //measured in native units/100ms/sec
        // End of 775pro lift code


        // MiniCIM lift code below
        public static final double holdPercent = 0.2;

        // The tolerance for the target position (see Elevator.isDone())
        public static final double kDeadband = 200;
        // PID Constants
        public static final double kF = .58, kP = 0.03, kI = 0.0, kD = 0.05;

        // Safety limits
        public static final double minPercentOut = 0, maxPercentBw = .6, maxPercentFw = 1;

        // System Constraints
        public static final int maxVelocity = 2500; // measured in native units/100ms
        public static final int maxAcceleration = 2500; // measured in native units/100ms/sec
        //End of MiniCIM lift code


        // Unit Conversion
        public static final int stuPerInch = 940;

        // Hall effect sensor height in native units MEASURED FROM BOTTOM OF CUBE
        public static final int hallHeight = 31500;

        // Height to various features in inches
        public static final double scaleHeight = 77;
        public static final double barHeight = 55;
        public static final double switchHeight = 25;
        public static final double carryHeight = 7.25;
        public static final double intakeHeight = 0;
    }
}
