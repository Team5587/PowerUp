package org.frc5587.robot2018.commands.drive;

import com.ctre.phoenix.motion.SetValueMotionProfile;

import org.frc5587.robot2018.Robot;
import org.frc5587.robot2018.subsystems.Drive;
import edu.wpi.first.wpilibj.command.Command;

public class MotionProfileRunner extends Command{
    Drive drive;
    boolean ready = false;

    public MotionProfileRunner(){
        drive = Robot.kDrive;
        requires(drive);
    }

    public void initialize(){
        drive.setProfileMode(SetValueMotionProfile.Disable);
        drive.enableBrakeMode(true);
    }

    public void execute(){
        if(!ready){
            ready = drive.isMPReady();
        }
        else{
            drive.setProfileMode(SetValueMotionProfile.Enable);
        }
        drive.sendDebugInfo();
        drive.sendMPDebugInfo();
    }

    public boolean isFinished(){
        return drive.isMPDone();
    }

    public void end(){
        drive.resetMP();
        drive.stop();
        drive.enableBrakeMode(false);
    }

    public void interrupted(){
        end();
    }
}