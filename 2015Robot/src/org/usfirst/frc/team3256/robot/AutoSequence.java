package org.usfirst.frc.team3256.robot;

public class AutoSequence {
	 
	int stepReached =0;
	double[] feetArr = {12.0, 0.2, 1.0};
	public void mainRobotSequence(AutoDrive drive){
		boolean firstMove;
		if(stepReached <3){
			if(stepReached ==0){
				firstMove = drive.resetPID(drive.setFeetPID(feetArr[0], 0));
			}else if(stepReached==1){
				firstMove = drive.resetPID(drive.setFeetPID(feetArr[1], 0));
			}else{
				//System.out.println("moving back");
				firstMove = drive.resetPID(drive.setFeetPID(feetArr[2], 1));
			}
			System.out.println(firstMove);
			if(firstMove){
				stepReached++;
			}
		}
		
	}
	
	public void binRobotSequence(AutoDrive drive, IntakeArm arms){
		boolean firstMove;
		if(stepReached <4){
			if(stepReached==0){
				firstMove = drive.resetPID(drive.setFeetPID(feetArr[0], 0));
			}else{
				//System.out.println("moving back");
				firstMove = drive.resetPID(drive.setFeetPID(feetArr[1], 1));
			}
			System.out.println(firstMove);
			if(firstMove){
				stepReached++;
			}
		}
	}
}
