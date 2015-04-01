package org.usfirst.frc.team3256.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;

/**
 * Autonomous Drive
 * 
 * @author VCRobotics (Newman)
 */
public class AutoDrive {
	
	//conversion units
	private double leftFeetSlope = 1381;
	private double leftFeetB = -145;
	public double rightFeetSlope = 990;
	public double rightFeetB = 289;
    private double anglePerTick = 1.68 / 90.0;
    	
    	//encoders
    private Encoder rEnc, lEnc;
    private DriveTrain drive ;
    private Gyro gyro;
    private PID pidController;
	/**
	 * Constructs an AutoDrive object
	 * 
	 * @param lf motor port, lr motor port, rf motor port, rr motor port, shifter1 port, shifter2 port, rEnc1 port, rEnc2 port, 
	 * 	lEnc1 port, lEnc2 port
	 */
	public AutoDrive(DriveTrain drive, Gyro gyro) {
		//super(lf, lr, rf, rr, shifter1, shifter2);
		// TODO Auto-generated constructor stub
		this.drive = drive;
		this.gyro = gyro;
		//gyro.reset();
		this.gyro.initGyro();
    	this.gyro.reset();
    	this.gyro.setSensitivity(0.007);
		rEnc= drive.getRightEncoder();
		lEnc=drive.getLeftEncoder();
		pidController = new PID(0.155, 0.00025, 0.0, 0.0, 0.0);
	}
	
	public double getRightTicks(double feet){ //returning the number of ticks in the distance passed in with the slope intercept equation
		
		double x= rightFeetSlope*feet; 
		return x+= rightFeetB;
	}
	
	public double getLeftTicks(double feet){ //returning the number of ticks in the distance passed in with the slope intercept equation
		
		double x= leftFeetSlope*feet;
		return x+= leftFeetB;
		
	}
	public void runDriveMotors(double speed){
		drive.setLeftSpeed(speed);
		drive.setRightSpeed(speed);
	}
	/**
	 * Drives a set distance at a set speed
	 * 
	 * @param feet, speed
	 * 
	 */
	public void setFeet(double feet, double leftSpeed, double rightSpeed, double angle){ //go a certain distance
		double leftTicks = getLeftTicks(feet);
		double rightTicks = getRightTicks(feet);
		if (leftTicks < lEnc.get() && rightTicks < rEnc.get()) { 
			drive.setLeftSpeed(leftSpeed);
			drive.setRightSpeed(rightSpeed);
			System.out.println("False");
        } else if (leftTicks > lEnc.get() && rightTicks > rEnc.get()) {
        	drive.setLeftSpeed(-leftSpeed);
        	drive.setRightSpeed(-rightSpeed);
        	System.out.println("False");
        } else{
            /*drive.setLeftSpeed(0);
            drive.setRightSpeed(0);*/
            System.out.println("True");
        }
	}
	
	/**
	 * Turn a set angle at a set speed
	 * 
	 * @param angle, speed
	 */
	public void setTurnAngle(double angle, double speed){
		angle -= 33;
		System.out.println(gyro.getAngle());
		if(angle >0 && gyro.getAngle()<angle){
			drive.setLeftSpeed(-speed);
			drive.setRightSpeed(speed);
			System.out.println("Esta bien");
		}else if (angle<0 && gyro.getAngle() > angle){
			drive.setLeftSpeed(speed);
			drive.setRightSpeed(-speed);
			System.out.println("Hola");
		}else{
			drive.setLeftSpeed(0.0);
			drive.setRightSpeed(0.0);
			//gyro.reset();
		}
	            //arcadeDrive(0,0);
	}
	
	public boolean setFeetPID(double setpoint){
		double leftTicksSetpoint = getLeftTicks(setpoint);
		double leftOutput = pidController.calc(leftTicksSetpoint, lEnc.get());
		double rightTicksSetpoint = getRightTicks(setpoint);
		double rightOutput = pidController.calc(rightTicksSetpoint, rEnc.get());
		if(rightOutput > 0.5){
			rightOutput = 0.5;
		}else if(rightOutput <0.0){
			rightOutput = 0.0;
		}
		
		if(leftOutput>0.45){
			leftOutput = 0.45;
		}else if(leftOutput<0.0){
			leftOutput =0.0;
		}
		
		if(leftOutput !=0 && rightOutput !=0){
			//System.out.println(leftOutput);
			//System.out.println(rightOutput);
			drive.setLeftSpeed(-leftOutput);
			drive.setRightSpeed(-rightOutput);
			return false;
		}else{
			//System.out.println(leftOutput);
			//System.out.println(rightOutput);
			drive.setLeftSpeed(0.0);
			drive.setRightSpeed(0.0);
			return true;
		}
	}
	public void resetPID(boolean isReached){
		if(isReached){
			pidController.resetError();
		}
	}
}
