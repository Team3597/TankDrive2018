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
	
	public static Robot self;
	public static Alliance allianceColor = DriverStation.getInstance().getAlliance();
	public double start = 0;


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

	@Override
	public void autonomousInit() {
		//autonomousCommand = chooser.getSelected();
		autonomousCommand = SmartDashboard.getString("Autonomous Selector", centerAuto);
		System.out.println("Autonomous selected: " + autonomousCommand);
		System.out.println("Our alliance color is " + allianceColor);
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
					Robot.tankDrive(speedMultiplier * (RIGHTMOTOR),speedMultiplier * (-LEFTMOTOR));
//					rightWheels.set(-0.5); // Turns left
//					leftWheels.set(-0.5);
				}
				break;
			case centerAuto:
			default:
				System.out.println("Our alliance color is " + allianceColor);
				
				if (start == 0) {
					start = System.currentTimeMillis();
				}
				
				double time = System.currentTimeMillis();
				
				// TODO: Modify condition so it will switch from auto to teleOp (set public variable to System Time?)
				if (time <= start + 1000) {
//					rightWheels.set(-0.25);
//					leftWheels.set(0.25);
				} else if (time > start) {
//					rightWheels.set(0);
//					leftWheels.set(0);
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