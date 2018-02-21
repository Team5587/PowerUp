package org.frc5587.robot2018;

public class Constants {

    public static boolean compressorEnabled = true;

    public static final class Drive {
        // set to zero to skip waiting for confirmation, set to nonzero to wait and report to DS if action fails
        public static final int kTimeoutMs = 10;

        public static final double kMaxVelocity = 0.0;

        public static final double kVCompSaturation = 12.0;

        public static final int minBufferCount = 10;

        public static final int stuPerInch = 215;

        public static final int stuPerRev = 4050;

        public static final int wheelDiameter = 6;

        //Safety limits
        public static final double minPercentOut = 0,
            maxPercentBw = 1,
            maxPercentFw = 1; 

        //PIDF Constants
        public static final double[] leftPIDs = {
            0.01,	//kP
            0.0,	//kI
            0.01,	//kD
            0.000327 * 1023 	//kF
        };
        public static final double[] rightPIDs = {
            0.01,	//kP
            0.0,	//kI
            0.01,	//kD
            0.000317 * 1023    //kF
        };

        public static final double[] pathfinderPIDVALeft = {
            0.04,    //kP
            0.0,    //kI
            0.0,    //kD
            0.000327 * stuPerInch / 10f,    //kV
            0.0001 * stuPerInch / 10f   //kA
        };
        public static final double[] pathfinderPIDVARight = {
            0.04,    //kP
            0.0,    //kI
            0.0,    //kD
            0.000317 * stuPerInch / 10f,    //kV
            0.0001 * stuPerInch / 10f     //kA
        };


		public static double gyrokP = 0.00;
    }
    public class Elevator {
        // Which PID slot to pull gains from. Starting 2018, you can choose from 0,1,2 or 3.
        public static final int kSlotIdx = 0;
        // Talon SRX/Victor SPX will supported multiple (cascaded) PID loops. For now we just want the primary one
        public static final int kPIDLoopIdx = 0;
        // set to zero to skip waiting for confirmation, set to nonzero to wait and report to DS if action fails
        public static final int kTimeoutMs = 10;


        public static final double vCompSaturation = 12.0;

        public static final double holdPercent = 0.25;

        // The tolerance for the target position (see Elevator.isDone())
        public static final double kDeadband = 400;
        //PID Constants
        public static final double kF = .67,
            kP = 0.02,
            kI = 0.0,
            kD = 0.0;
        //Safety limits
        public static final double minPercentOut = 0,
            maxPercentBw = .6,
            maxPercentFw = 1;
        //System Constraints
        public static final int maxVelocity = 2500, //measured in native units/100ms
            maxAcceleration = 1600; //measured in native units/100ms/sec
        //Unit Conversion
        public static final int stuPerInch = 940;

        //Hall effect sensor height in native units MEASURED FROM BOTTOM OF cube
        // 
        public static final int hallHeight = 34310;

        //Height to place on scale in inches
        public static final double scaleHeight = 75;
        public static final double intakeHeight = 1;
        public static final double switchHeight = 25;
    }
}
