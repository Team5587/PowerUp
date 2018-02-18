/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc5587.robot2018.subsystems;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.frc5587.lib.TitanDrive;
import org.frc5587.lib.TitanDrive.DriveSignal;
import org.frc5587.robot2018.Constants;
import org.frc5587.robot2018.RobotMap;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Drive extends Subsystem {

	TalonSRX leftMaster, rightMaster;
	VictorSPX leftSlave, rightSlave;
	TitanDrive driveHelper;

	MotionProfileStatus[] statuses = {new MotionProfileStatus(), new MotionProfileStatus()};

	public Drive(){
		driveHelper = new TitanDrive();
		//initialize Talons
		leftMaster = new TalonSRX(RobotMap.Drive.leftMaster);
		rightMaster = new TalonSRX(RobotMap.Drive.rightMaster);
		leftSlave = new VictorSPX(RobotMap.Drive.leftSlave);
		rightSlave = new VictorSPX(RobotMap.Drive.rightSlave);

		//invert right side
		rightMaster.setInverted(true);
		rightSlave.setInverted(true);

		//Set the slaves to mimic the masters
		leftSlave.follow(leftMaster);
		rightSlave.follow(rightMaster);

		//Enable Voltage Compensation
		rightMaster.configVoltageCompSaturation(Constants.Drive.kVCompSaturation, Constants.Drive.kTimeoutMs);
		rightMaster.enableVoltageCompensation(true);
		leftMaster.configVoltageCompSaturation(Constants.Drive.kVCompSaturation, Constants.Drive.kTimeoutMs);
		leftMaster.enableVoltageCompensation(true);

		rightMaster.configPeakOutputForward(1, Constants.Drive.kTimeoutMs);
		rightMaster.configPeakOutputReverse(-1, Constants.Drive.kTimeoutMs);

	}

	public class ProcessProfileRunnable implements java.lang.Runnable {
	    public void run(){
			leftMaster.processMotionProfileBuffer();
			rightMaster.processMotionProfileBuffer();
		}
	}

	public Notifier profileNotifer = new Notifier(new ProcessProfileRunnable());

	public void resetMP(){
		leftMaster.clearMotionProfileHasUnderrun(Constants.Drive.kTimeoutMs);
		leftMaster.clearMotionProfileTrajectories();
		leftMaster.changeMotionControlFramePeriod(10);
		leftMaster.configMotionProfileTrajectoryPeriod(10, Constants.Drive.kTimeoutMs);
		leftMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.Drive.kTimeoutMs);
		leftMaster.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);


		rightMaster.clearMotionProfileHasUnderrun(Constants.Drive.kTimeoutMs);
		rightMaster.clearMotionProfileTrajectories();
		rightMaster.changeMotionControlFramePeriod(10);
		rightMaster.configMotionProfileTrajectoryPeriod(10, Constants.Drive.kTimeoutMs);
		rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.Drive.kTimeoutMs);
		rightMaster.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
	}

	public void updateStatus(){
		leftMaster.getMotionProfileStatus(statuses[0]);
		rightMaster.getMotionProfileStatus(statuses[1]);
	}

	public MotionProfileStatus[] getStatuses(){
		return statuses;
	}

	public boolean isMPReady(){
		boolean leftReady = getStatuses()[0].btmBufferCnt > Constants.Drive.minBufferCount;
		boolean rightReady = getStatuses()[0].btmBufferCnt > Constants.Drive.minBufferCount;
		return leftReady && rightReady;
	}

	public void queuePoints(TrajectoryPoint[][] trajectories){
		for(TrajectoryPoint point : trajectories[0]){
			leftMaster.pushMotionProfileTrajectory(point);
		}
		for(TrajectoryPoint point : trajectories[1]){
			rightMaster.pushMotionProfileTrajectory(point);
		}
	}

	public void setProfileMode(SetValueMotionProfile mpMode){
		leftMaster.set(ControlMode.MotionProfile, mpMode.value);
		rightMaster.set(ControlMode.MotionProfile, mpMode.value);
	}

	/**
	 * Send PIDF constants to master talons
	 * @param slotIdx Which slot to push values to
	 */
	private void fillPIDFSlot(int slotIdx){
		leftMaster.config_kP(slotIdx, Constants.Drive.leftPIDs[0], 0);
		leftMaster.config_kI(slotIdx, Constants.Drive.leftPIDs[1], 0);
		leftMaster.config_kD(slotIdx, Constants.Drive.leftPIDs[2], 0);
		leftMaster.config_kF(slotIdx, Constants.Drive.leftPIDs[3], 0);

		rightMaster.config_kP(slotIdx, Constants.Drive.rightPIDs[0], 0);
		rightMaster.config_kI(slotIdx, Constants.Drive.rightPIDs[1], 0);
		rightMaster.config_kD(slotIdx, Constants.Drive.rightPIDs[2], 0);
		rightMaster.config_kF(slotIdx, Constants.Drive.rightPIDs[3], 0);
	}

	public void vbusCurve(double throttle, double curve, boolean isQuickTurn){
		DriveSignal d = driveHelper.curvatureDrive(throttle, curve, isQuickTurn);
		
		leftMaster.set(ControlMode.PercentOutput, d.left);
		rightMaster.set(ControlMode.PercentOutput, d.right);
	}

	public void vbusArcade(double throttle, double turn){
		DriveSignal d = driveHelper.arcadeDrive(throttle, turn);
		
		leftMaster.set(ControlMode.PercentOutput, d.left);
		rightMaster.set(ControlMode.PercentOutput, d.right);
	}

	public void velocityCurve(double throttle, double curve, boolean isQuickTurn){
		DriveSignal d = driveHelper.curvatureDrive(throttle, curve, isQuickTurn);
		
		leftMaster.set(ControlMode.Velocity, d.left * Constants.Drive.kMaxVelocity);
		rightMaster.set(ControlMode.Velocity, d.right * Constants.Drive.kMaxVelocity);
	}

	public void velocityArcade(double throttle, double turn){
		DriveSignal d = driveHelper.arcadeDrive(throttle, turn);

		leftMaster.set(ControlMode.Velocity, d.left * Constants.Drive.kMaxVelocity);
		rightMaster.set(ControlMode.Velocity, d.right * Constants.Drive.kMaxVelocity);
	}

	public double getLeftPosition(){
		return leftMaster.getSelectedSensorPosition(0);
	}
	
	public double getRightPosition(){
		return rightMaster.getSelectedSensorPosition(0);
	}

	public double getLeftVelocity(){
		return leftMaster.getSelectedSensorPosition(0);
	}

	public double getRightVelocity(){
		return rightMaster.getSelectedSensorPosition(0);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		//setDefaultCommand(new CurveDrive());
	}
}
