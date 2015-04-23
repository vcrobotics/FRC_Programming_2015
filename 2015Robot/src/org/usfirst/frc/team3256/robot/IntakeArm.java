package org.usfirst.frc.team3256.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

public class IntakeArm {

	private Victor intake1;
	private Victor intake2;
	private Victor interiorIntake1;
	private Victor interiorIntake2;
	private Servo Stopper1;
	private Servo Stopper2;
	private DoubleSolenoid ChickenIntake1;
	private DoubleSolenoid Brake;
	//private DigitalInput BumperIntakeSwitchL;
	//private DigitalInput BumperIntakeSwitchR;
	private DigitalInput LimitSwitchIntakeL;
	private DigitalInput LimitSwitchIntakeR; 
	//private DoubleSolenoid ChickenIntake2;

	public IntakeArm(int intake1, int intake2, int interiorIntake1, int interiorIntake2, int ChickenIntake1A, int ChickenIntake1B, 
			int LimitSwitchL, int LimitSwitchR, int Stopper1, int Stopper2, int brake1,int brake2){
		this.intake1 = new Victor(intake1);
		this.intake2 = new Victor(intake2);
		this.interiorIntake1 = new Victor(interiorIntake1);
		this.interiorIntake2 = new Victor(interiorIntake2);
		this.ChickenIntake1 = new DoubleSolenoid(ChickenIntake1A, ChickenIntake1B);
		//this.BumperIntakeSwitchL = new DigitalInput(BumperIntakeL);
		//this.BumperIntakeSwitchR = new DigitalInput(BumperIntakeR);
		this.LimitSwitchIntakeL = new DigitalInput(LimitSwitchL);
		this.LimitSwitchIntakeR = new DigitalInput(LimitSwitchR);
		this.Stopper1 = new Servo(Stopper1);
		this.Stopper2 = new Servo(Stopper2);
		this.Brake = new DoubleSolenoid(brake1,brake2);
	}
	public void testSensors(){
		System.out.println(LimitSwitchIntakeL.get());
		System.out.println(LimitSwitchIntakeR.get());
		//System.out.println(BumperIntakeSwitchL.get());
		//System.out.println(BumperIntakeSwitchR.get());
	}

	public boolean bothLimitPressed(){
		boolean isPressed=!LimitSwitchIntakeL.get()&&!LimitSwitchIntakeR.get();
		//System.out.println(isPressed);
		return isPressed;
	}
	public void intakeSpitOutBox(boolean intake, boolean spitout, boolean stackin, boolean stackout,double speed, double insideSpeed){
		//ChickenIntake1.set(DoubleSolenoid.Value.kForward);
		if(intake&&!spitout&&!stackin&& !stackout){
			intake1.set(0.0);
			intake2.set(0.0);
			if(!LimitSwitchIntakeR.get()){
				interiorIntake1.set(-insideSpeed/2);
				interiorIntake2.set(insideSpeed/2);
			}else if(!LimitSwitchIntakeL.get()){
				interiorIntake1.set(0.0);
				interiorIntake2.set(0.0);
			}else{
				interiorIntake1.set(-insideSpeed);
				interiorIntake2.set(insideSpeed);
			}
		}else if(!stackin&& stackout&&!intake&&!spitout){
			//holdBoxes();
			if(LimitSwitchIntakeL.get()){
				interiorIntake1.set(0.0);
				interiorIntake2.set(0.0);
				intake1.set(0.0);
				intake2.set(0.0);
			}else{
				interiorIntake1.set(-insideSpeed);
				interiorIntake2.set(insideSpeed);
				intake1.set(0.0);
				intake2.set(0.0);
			}
		}else if(stackin&&!stackout){
			interiorIntake1.set(insideSpeed);
			interiorIntake2.set(-insideSpeed);
			intake1.set(speed);
			intake2.set(-speed);
		}else{
			intake1.set(0.0);
			intake2.set(0.0);
			interiorIntake1.set(0.0);
			interiorIntake2.set(0.0);
		}
		//interiorIntake.set(insideSpeed);
	}
	
	public void intakeInteriorBox(double speed, double insideSpeed){
		interiorIntake1.set(insideSpeed);
		interiorIntake2.set(insideSpeed);
	}
	public boolean holdBoxes(){
		ChickenIntake1.set(DoubleSolenoid.Value.kForward);
		return true;
		//	ChickenIntake2.set(DoubleSolenoid.Value.kForward);
	}
	public boolean releaseBoxes(){
		ChickenIntake1.set(DoubleSolenoid.Value.kReverse);
		return true;
		//	ChickenIntake2.set(DoubleSolenoid.Value.kReverse);
	}
	public boolean engageStop(){
		Stopper1.setAngle(95.0);
		Stopper2.setAngle(-95.0);
		return true;
	}
	public boolean releaseStop(){
		Stopper1.setAngle(0.0);
		Stopper2.setAngle(0.0);
		return true;
	}
	public boolean engageBrake(){
		Brake.set(DoubleSolenoid.Value.kForward);
		return true;
	}
	public boolean releaseBrake(){
		Brake.set(DoubleSolenoid.Value.kReverse);
		return true;
	}
	/*public static void LBRB(IntakeArm intake, XboxController operator){

		if(operator.getButtonLB() && !operator.getButtonRB()){
			intake.intakeInteriorBox(1.0, 0.25);
		}
		else if(operator.getButtonRB() && !operator.getButtonLB()){
			intake.intakeInteriorBox(-1.0, -0.25);
		}
		else if(operator.getButtonRB() && operator.getButtonLB()){
			intake.intakeInteriorBox(0.0,0.0);
			System.out.println("inner stopped");
		}
		else if(operator.getButtonY()){
			intake.intakeInteriorBox(0,0);
		}

	}

	public static void trigger(IntakeArm intake, XboxController operator){

		if(operator.getLeftTrigger() && !operator.getRightTrigger()){
			intake.intakeBox(1.0, 0.25);
		}

		else if(operator.getRightTrigger() && !operator.getLeftTrigger()){
			intake.intakeBox(-1.0, -0.5);
		}

		else if(operator.getLeftTrigger() && operator.getRightTrigger()){ //got rid of button b
			intake.intakeBox(0.0, 0.0);
			System.out.println("outer stopped");

		}
		
		else if(operator.getButtonX()){
			intake.intakeInteriorBox(0,0);
		}

	}*/


}
