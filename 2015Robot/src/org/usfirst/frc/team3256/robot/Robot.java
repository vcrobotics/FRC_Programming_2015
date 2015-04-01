package org.usfirst.frc.team3256.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
//import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.vision.USBCamera;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the It`	erativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	DriveTrain drive;
	TeleopDrive robotDrive;
	AutoDrive robotAutoDrive;
	XboxController driver;
	XboxController operator;
	Compressor compressor;
	Gyro gyro;
	CameraServer camera = CameraServer.getInstance();
	//Talon arm1L, arm1R, pulley;
	IntakeArm intake;
	IntakeTotes intakeTotes;
	Elevator elevator;
	//SensorBase gyroChannel;
	//Encoder testEnc;
	//DriverStationLCD log;
	int teleopCounter = 0;
	int autoLoopCounter;
	int counter;
	boolean isPressed;
	private DigitalInput elevatorLimitSwitch;
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
    	operator= new XboxController(1);
    	compressor = new Compressor(0);
    	gyro = new Gyro(0);
    	camera.setQuality(50);
		camera.startAutomaticCapture("cam0");
    	
    	elevator =  new Elevator(Constants.ELEVATOR_MOTOR_PORT, Constants.ELEVATOR_MOTOR_PORT_2, Constants.ELEVATOR_ENCODER_PORT_1, 
    			Constants.ELEVATOR_ENCODER_PORT_2);
    	/*intake = new IntakeArm(Constants.INTAKE_ARM_1, Constants.INTAKE_ARM_2, Constants.INSIDE_INTAKE, 
    			Constants.CHICKEN_INTAKE_ARM_1A,Constants.CHICKEN_INTAKE_ARM_1B, Constants.BUMPER_SWITCH_INTAKE, 
    			Constants.LIMIT_SWITCH_INTAKE_L, Constants.LIMIT_SWITCH_INTAKE_R);*/
    	intake = new IntakeArm(Constants.INTAKE_ARM_1, Constants.INTAKE_ARM_2, Constants.INSIDE_INTAKE_1,Constants.INSIDE_INTAKE_2, 
    			Constants.CHICKEN_INTAKE_ARM_1A,Constants.CHICKEN_INTAKE_ARM_1B, Constants.LIMIT_SWITCH_INTAKE_L, Constants.LIMIT_SWITCH_INTAKE_R);
    	//intakeTotes = new IntakeTotes(Constants.INTAKE_ARM_1, Constants.INTAKE_ARM_2, Constants.INSIDE_INTAKE, 
    		 //	Constants.CHICKEN_INTAKE_ARM_1A,Constants.CHICKEN_INTAKE_ARM_1B, Constants.BUMPER_SWITCH_INTAKE, 
    			//Constants.LIMIT_SWITCH_INTAKE_L, Constants.LIMIT_SWITCH_INTAKE_R);
    	System.out.println("Hi");
    	compressor.setClosedLoopControl(true);
    	robotAutoDrive = new AutoDrive(drive, gyro);
    	//int counter = 10;
    	isPressed= false;
    	elevatorLimitSwitch = new DigitalInput(Constants.ELEVATOR_LIMIT);
    	//gyroChannel = new SensorBase(); 
    	//System.out.println("hi");
    	//pot = new AnalogPotentiometer(0);
    	//testEnc = new Encoder(0,1);
    	//compressor.setClosedLoopControl(true);
    	
    	
    
    	//compressor.start();
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	autoLoopCounter = 0;
    	drive.getLeftEncoder().reset();
    	drive.getRightEncoder().reset();
    	//gyro.reset();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	/*if(autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
		{
			robotAutoDrive.runDriveMotors(1.0);	// drive forwards half speed
    		
    		autoLoopCounter++;
		} else {
				robotAutoDrive.setFeet(0.0, 0.0, 0.0, gyro.getAngle());; 	// stop robot
		}*/    	//autoLoopCounter++;
    	//System.out.println(autoLoopCounter);
    	//robotAutoDrive.setFeet(-3.0, 0.6, 0.635, gyro.getAngle());
    	//System.out.println(robotAutoDrive.getLeftTicks(1.0));
    	//System.out.println(robotAutoDrive.getRightTicks(1.0));
    	//if(){}
    	//System.out.println("right encoder: "+drive.getRightEncoder().get());
        //System.out.println("left encoder: "+drive.getLeftEncoder().get());
    	//robotAutoDrive.setFeet(1.0, 0.25, 0.35, gyro.getAngle());
        robotAutoDrive.resetPID(robotAutoDrive.setFeetPID(7.0));
    	//robotAutoDrive.setTurnAngle(180.0, 0.6);
    	
    	
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    	//gyro.reset();
    	drive.getLeftEncoder().reset();
    	drive.getRightEncoder().reset();
    	//pulley.set(0.0);
    	//System.out.println("Right Encoder: "+robotAutoDrive.getRightTicks(5.0));
    	//System.out.println("Left Encoder: "+robotAutoDrive.getLeftTicks(5.0));
    	
    	elevator.getEncoder().reset();
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	//robotDrive.setShifterState(driver.getButtonRB());
    	teleopCounter++;
    	robotDrive.setShifterState(driver.getButtonRB());
    	
        robotDrive.arcadeDrive(driver.getLeftY(), -driver.getRightX());
        
        if(operator.getButtonX() ){
    		intake.releaseBoxes();//chicken flaps open
    		System.out.println("X is pressed");
    	}else if(operator.getButtonY()){
    		System.out.println("Y is pressed");
    		intake.holdBoxes();								//chicken flaps closed
    	}
        elevator.moveElevator(operator.getLeftY(), elevatorLimitSwitch.get(), teleopCounter);
        
       //System.out.println("right encoder: "+drive.getRightEncoder().get());
       //System.out.println("left encoder: "+drive.getLeftEncoder().get());
       intake.intakeSpitOutBox(operator.getButtonRB(), operator.getButtonLB(),operator.getButtonA(), operator.getButtonB(), 0.75, 0.5);
       //robotAutoDrive.resetPID(robotAutoDrive.setFeetPID(5.5));
       //System.out.println(robotAutoDrive.setFeetPID(3.0));
       //robotAutoDrive.setFeet(2.0, 0.0, 0.0, 0.0);
       //intake.testSensors();
    	
    }
    	
    	//System.out.println(drive.getLeftEncoder().get());
    	//System.out.println(drive.getRightEncoder().get());
    	
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
 
    	void startAutomaticCapture(USBCamera cam0){
    	}
    	
    
    }

