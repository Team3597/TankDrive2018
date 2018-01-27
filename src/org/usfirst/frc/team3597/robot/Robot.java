package org.usfirst.frc.team3597.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	private DifferentialDrive Robot;
	public static Joystick Controller;
	public static boolean buttonValueA;
	public static boolean buttonValueB;
	public static final int LEFTMOTOR = 1;
	public static final int RIGHTMOTOR = 0;
	Talon motor3;
	public static float speedMultiplier = (float) 1.0;
	final String leftAuto = "Left Starting Position";
	final String centerAuto = "Center Starting Position";
	final String rightAuto = "Right Starting Position";
	String autonomousCommand;
	SendableChooser<String> chooser = new SendableChooser<>();
	
//	public static Robot self;
	public static Alliance allianceColor = DriverStation.getInstance().getAlliance();
//	public double start = 0;


	@Override
	public void robotInit() {
		System.out.println("Robot Ready!");
		CameraServer.getInstance().startAutomaticCapture();
		System.out.println("Our alliance color is " + allianceColor);
		Robot = new DifferentialDrive(new Spark(LEFTMOTOR), new Spark(RIGHTMOTOR));
		motor3 = new Talon(9);
		Controller = new Joystick(1); //Setting up controller to correct port (1)
		speedMultiplier = (float) 0.8;

	}
    long autoStart = 0;

	@Override
	public void autonomousInit() {
		//autonomousCommand = chooser.getSelected();
		autonomousCommand = SmartDashboard.getString("Autonomous Selector", centerAuto);
		System.out.println("Autonomous selected: " + autonomousCommand);
		System.out.println("Our alliance color is " + allianceColor);
		autoStart = System.currentTimeMillis();
	}
	
	@Override
	public void autonomousPeriodic() {
//		System.out.println("Our alliance color is " + allianceColor);
		Scheduler.getInstance().run(); // Is this necessary? TODO: If it doesn't work, try commenting this out!
		switch (autonomousCommand) {
			case leftAuto:
				// Go straight
				//String light = "Blue";//sensor.getColor(); (modify to take input from light sensor)
				if (/*light == */allianceColor != null) {
					// Implement a while loop like the default, but FIXED (loop can end)
				} else {
					// Same type of loop as above (timer as a general time but if statements for individual instructions? Cleaner code!)
//					Robot.tankDrive(speedMultiplier * (RIGHTMOTOR),speedMultiplier * (LEFTMOTOR));
//					rightWheels.set(-0.5); // Turns left
//					leftWheels.set(-0.5);
				}
				break;
			case centerAuto:
			default:
//				System.out.println("Our alliance color is " + allianceColor);
				
				
		        double speed = .3, timeout = 2;
		        if (System.currentTimeMillis() - autoStart < (timeout * 1000)) {
		            java.util.Arrays.stream((new Object[]{ LEFTMOTOR })).forEach((Object s) -> ((edu.wpi.first.wpilibj.SpeedController)s).set(speed));
		            java.util.Arrays.stream((new Object[]{ RIGHTMOTOR })).forEach((Object s) -> ((edu.wpi.first.wpilibj.SpeedController)s).set(-speed));
				}
				
				break;
			case rightAuto:
				
				break;
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