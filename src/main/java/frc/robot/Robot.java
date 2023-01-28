// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
// import com.ctre.phoenix.motorcontrol.can.TalonSRX;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private WPI_TalonSRX leftFrontMotor;
  private WPI_TalonSRX rightFrontMotor;
  private WPI_TalonSRX leftBackMotor;
  private WPI_TalonSRX rightBackMotor;

  private MotorControllerGroup leftMotorsGroup;
  private MotorControllerGroup rightMotorsGroup;
  private DifferentialDrive drivebase;

  private Joystick joystick;

  private Timer timer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    leftFrontMotor = new WPI_TalonSRX(1);
    leftBackMotor = new WPI_TalonSRX(2);
    rightFrontMotor = new WPI_TalonSRX(3);
    rightBackMotor = new WPI_TalonSRX(4);

    leftMotorsGroup = new MotorControllerGroup(leftFrontMotor, leftBackMotor);
    rightMotorsGroup = new MotorControllerGroup(rightFrontMotor, rightBackMotor);
    drivebase = new DifferentialDrive(leftMotorsGroup, rightMotorsGroup);

    joystick = new Joystick(0);

    timer = new Timer();
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    timer.reset();
    timer.start();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (timer.get() < 2.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      drivebase.arcadeDrive(0.5, 0.0, false);
    } else {
      drivebase.stopMotor(); // stop robot
    }
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    drivebase.arcadeDrive(joystick.getRawAxis(4), joystick.getRawAxis(1));
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
