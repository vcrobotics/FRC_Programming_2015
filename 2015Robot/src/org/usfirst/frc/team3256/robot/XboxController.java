package org.usfirst.frc.team3256.robot;


import edu.wpi.first.wpilibj.Joystick;
/**
 * Wrapper class for Xbox Controller
 * 
 * @author VCS Robotics
 */
public class XboxController {
    
    //make a stick
    private Joystick stick;
    private Joystick.RumbleType rumble;
    
    /**
     * Creates and XboxController object
     */ 
    public XboxController(int port) {
        stick = new Joystick(port);
    }
    public Joystick getJoystick(){
    	return stick;
    }
    public boolean getButtonA() {
        return stick.getRawButton(1);
    }
    public boolean getButtonB() {
        return stick.getRawButton(2);
    }
    public boolean getButtonX() {
        return stick.getRawButton(3);
    }
    public boolean getButtonY() {
        return stick.getRawButton(4);
    }
    public boolean getButtonLB() {
        return stick.getRawButton(5); 
    }
    public boolean getButtonRB() {
        return stick.getRawButton(6);
    }
    public boolean getButtonBack() {
        return stick.getRawButton(7);        
    }
    public boolean getButtonStart(){
        return stick.getRawButton(8);        
    }
    public boolean getButtonLeftStick() {
        return stick.getRawButton(9);
    }
    public boolean getButtonRightStick() {
        return stick.getRawButton(10);
    }
    
    public double getLeftY() {
        return stick.getRawAxis(1);
    }
    
    public double getLeftX() {
        return stick.getRawAxis(2);
    }
    
    public double getRightY() {
        return stick.getRawAxis(5);
    }
    
    public double getRightX() {
        return stick.getRawAxis(4);
    }
    public boolean getLeftTrigger() {
        return stick.getZ() > .5;
    }
    public boolean getRightTrigger() {
        return stick.getRawAxis(3) > .5;
    }
    public Joystick.RumbleType rumbleJoystick(){
    	return rumble.kLeftRumble;
    }
}
