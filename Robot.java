package org.usfirst.frc.team6871.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	
	Joystick gamingController = new Joystick(0);
	
	Compressor compression = new Compressor(0);
	
	DoubleSolenoid solenoidone = new DoubleSolenoid(0,1);
	DoubleSolenoid solenoidtwo = new DoubleSolenoid(2,3);
	
	Victor victor =   new Victor(0);//color
	Victor Ving   =   new Victor(1);//color
	Victor Wictor =   new Victor(2);//color
	Victor wing   =   new Victor(3);//color
	
	SpeedControllerGroup wrist = new SpeedControllerGroup(Wictor,Ving);
	DifferentialDrive mdrive = new DifferentialDrive(wing,wing);
	

	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
	}

	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}
	}

	@Override
	public void teleopPeriodic() {
		
	}

	public void testPeriodic() {
				
		double bodyspin = gamingController.getRawAxis(0);
		mdrive.tankDrive(bodyspin,bodyspin);

		boolean solenoidin = gamingController.getRawButton(1);
		boolean solenoidout = gamingController.getRawButton(2);
		if (!solenoidin && solenoidout) {
			solenoidone.set(DoubleSolenoid.Value.kForward);
			solenoidtwo.set(DoubleSolenoid.Value.kForward);
		}
		else if (solenoidin && !solenoidout){
			solenoidone.set(DoubleSolenoid.Value.kReverse);
			solenoidtwo.set(DoubleSolenoid.Value.kReverse);
		}
		else if (solenoidin && solenoidout) {
			solenoidone.set(DoubleSolenoid.Value.kOff);
			solenoidtwo.set(DoubleSolenoid.Value.kOff);
		}
		else if (!solenoidin && !solenoidout) {
			solenoidone.set(DoubleSolenoid.Value.kOff);
			solenoidtwo.set(DoubleSolenoid.Value.kOff);
		}
		boolean armup   = gamingController.getRawButton(3);
		boolean armdown = gamingController.getRawButton(4);
		
		double armspeed = 0.8;
		
		if(!armup && armdown) {
			victor.set(armspeed);
		}
		else if(armup && !armdown) {
			victor.set(-armspeed);
		}
		else if(armup && armdown) {
			victor.set(0);
		}
		else if(!armup && !armdown) {
			victor.set(0);
		}
		boolean wristup = gamingController.getRawButton(5);
		boolean wristdown = gamingController.getRawButton(6);
		
		double wristspeed = 0.9;
		if(!wristup && wristdown) {
			wrist.set(wristspeed);
		}
		else if (wristup && !wristdown) {
			wrist.set(-wristspeed);
		}
		else if(!wristup && !wristdown) {
			wrist.set(0);
		}
		else if(wristup && wristdown) {
			wrist.set(0);
		}

	}
}
