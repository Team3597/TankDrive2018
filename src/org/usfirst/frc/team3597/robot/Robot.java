package org.usfirst.frc.team3597.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Relay;

 class Robot extends IterativeRobot {
	private DifferentialDrive Robot;
	
	public static Joystick Controller;
	public static boolean buttonValueA;
	public static boolean buttonValueB;
	public static final int LEFTMOTOR = 1;
	public static final int RIGHTMOTOR = 0;
	Talon motor3;
	public static float speedMultiplier = (float) 1.0;


	@Override
	public void robotInit() {
		System.out.println("Robot Ready!");
		CameraServer.getInstance().startAutomaticCapture();
		Robot = new DifferentialDrive(new Spark(LEFTMOTOR), new Spark(RIGHTMOTOR));
		motor3 = new Talon(9);
		Controller = new Joystick(1); //Setting up controller to correct port (1)
		speedMultiplier = 0.8f;
	}

    long autoStart = 0;
    @Override
    public void autonomousInit() {
        autoStart = System.currentTimeMillis();
    }
    
    @Override
    public void autonomousPeriodic() {
        // Speed = Motor Speed (-1 to 1). Timeout = amount of time to move (in seconds)
        double timeout = 2;
        if (System.currentTimeMillis() - autoStart < (timeout * 1000)) {
 //       	AutoTankRight.set(.5);
 //       	AutoTankLeft.set(.5);
        }
    }

	@Override
	public void teleopPeriodic() {
		buttonValueA = Controller.getRawButton(2);
		buttonValueB = Controller.getRawButton(3);
		
		if (buttonValueA) {
			motor3.set(-1.0);
		} else if (!buttonValueA) {
			motor3.set(0);
		}
	
	
		if (buttonValueB) {
			motor3.set(1.0);
		} else if (buttonValueB){
			motor3.set(0);
		}
	
		Robot.tankDrive(speedMultiplier * (Controller.getRawAxis(3)),speedMultiplier * (Controller.getRawAxis(1)));
	}
}