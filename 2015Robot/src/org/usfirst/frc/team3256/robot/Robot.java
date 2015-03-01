package org.usfirst.frc.team3256.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
//import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	DriveTrain drive;
	TeleopDrive robotDrive;
	AutoDrive robotAutoDrive;
	XboxController driver;
	Compressor compressor;
	Gyro gyro;
	//Talon arm1L, arm1R, pulley;
	IntakeArm intake;
	Elevator elevator;
	//SensorBase gyroChannel;
	//Encoder testEnc;
	//DriverStationLCD log;
	int autoLoopCounter;
	//AnalogPotentiometer pot;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	drive = new DriveTrain(Constants.LEFT_FRONT_PORT, Constants.LEFT_REAR_PORT, 
    			Constants.RIGHT_FRONT_PORT, Constants.RIGHT_REAR_PORT, Constants.RIGHT_ENCODER_PORT_1, 
    			Constants.RIGHT_ENCODER_PORT_2, Constants.LEFT_ENCODER_PORT_1,
    			Constants.LEFT_ENCODER_PORT_2, Constants.LEFT_SHIFTER_PORT, 
    			Constants.RIGHT_SHIFTER_PORT);
    	robotDrive= new TeleopDrive(drive);
    	driver= new XboxController(0);
    	compressor = new Compressor(0);
    	gyro = new Gyro(0);
    	elevator =  new Elevator(Constants.ELEVATOR_MOTOR_PORT, 4);
    	intake = new IntakeArm(Constants.INTAKE_ARM_1, Constants.INTAKE_ARM_2, Constants.INSIDE_INTAKE, 
    			Constants.CHICKEN_INTAKE_ARM_1A, Constants.CHICKEN_INTAKE_ARM_1B,
    			Constants.CHICKEN_INTAKE_ARM_2A, Constants.CHICKEN_INTAKE_ARM_2B );
    	System.out.println("Hi");
    	//compressor.s
    	robotAutoDrive = new AutoDrive(drive, gyro);
    	//gyroChannel = new SensorBase();
    	//System.out.println("hi");
    	//pot = new AnalogPotentiometer(0);
    	//testEnc = new Encoder(0,1);
    	compressor.setClosedLoopControl(true);
    	
    	
    
    	//compressor.start();
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	autoLoopCounter = 0;
    	gyro.reset();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	/*if(autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
		{
			myRobot.drive(-0.5, 0.0); 	// drive forwards half speed
			autoLoopCounter++;
			} else {
			myRobot.drive(0.0, 0.0); 	// stop robot
		}*/
    	//robotAutoDrive.setFeet(3.0, 0.6, 0.635, gyro.getAngle());
    	robotAutoDrive.setTurnAngle(180.0, 0.6);
    	
    	
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    	gyro.reset();
    	drive.getLeftEncoder().reset();
    	drive.getRightEncoder().reset();
    	//pulley.set(0.0);
    	//System.out.println("Right Encoder: "+robotAutoDrive.getRightTicks(5.0));
    	//System.out.println("Left Encoder: "+robotAutoDrive.getLeftTicks(5.0));
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	//robotDrive.setShifterState(driver.getButtonRB());
    	
        //robotDrive.arcadeDrive(driver.getLeftY(), -driver.getRightX());
        
    	if(driver.getButtonLB()){
    		intake.intakeBox(1.0, 0.5);
    	}else if(driver.getButtonRB()){
    		intake.spitOutBox(-1.0, -0.5);
    	}else if(driver.getButtonA()){
    		intake.holdBoxes();
    	}else if(driver.getButtonB()){
    		intake.releaseBoxes();
    	}
    	
    	elevator.moveElevator(driver.getLeftY());
    	
    	/*if(driver.getButtonB()){
    		//compressor.start();
    		drive.setRightSpeed(1.0);
    	}else if(driver.getButtonA()){
    		drive.setRightSpeed(-1.0);
    	}*/
        //System.out.println(gyro);
    	System.out.println(gyro.getAngle());
    	
    	//System.out.println(drive.getLeftEncoder().get());
    	//System.out.println(drive.getRightEncoder().get());
    }
    	
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
