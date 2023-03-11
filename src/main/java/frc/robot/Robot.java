// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the manifest
 * file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private WPI_VictorSPX leftFrontMotor;
  private WPI_VictorSPX rightFrontMotor;
  private WPI_VictorSPX leftBackMotor;
  private WPI_VictorSPX rightBackMotor;
  private WPI_VictorSPX manipulatorMotor;

  private MotorControllerGroup leftMotorsGroup;
  private MotorControllerGroup rightMotorsGroup;
  private DifferentialDrive drivebase;

  private XboxController xbox;
  private Joystick joystick;

  private Timer timer;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    rightFrontMotor = new WPI_VictorSPX(1);
    rightBackMotor = new WPI_VictorSPX(2);
    leftFrontMotor = new WPI_VictorSPX(3);
    leftBackMotor = new WPI_VictorSPX(4);
    manipulatorMotor = new WPI_VictorSPX(5);

    leftMotorsGroup = new MotorControllerGroup(leftFrontMotor, leftBackMotor);
    rightMotorsGroup = new MotorControllerGroup(rightFrontMotor, rightBackMotor);
    drivebase = new DifferentialDrive(rightMotorsGroup, leftMotorsGroup);

    xbox = new XboxController(0);
    joystick = new Joystick(1);

    timer = new Timer();

    CameraServer.startAutomaticCapture();
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
    if (timer.get() < 1.0) {
      manipulatorMotor.set(-1.0);
    }
    
    if (timer.get() >= 1.0 && timer.get() < 3.0) {
      manipulatorMotor.stopMotor();

      // Climbing, somewhat successful?
      // double speedRight = 0.47;
      // double speedLeft = -0.44;

      // v1
      double speedRight = 0.27;
      double speedLeft = -0.20;

      rightFrontMotor.set(speedRight);
      rightBackMotor.set(speedRight);
      leftFrontMotor.set(speedLeft);
      leftBackMotor.set(speedLeft);
    }
    
    if (timer.get() >= 3.0) {
      drivebase.stopMotor(); // stop robot
      
      rightFrontMotor.stopMotor();
      rightBackMotor.stopMotor();
      leftFrontMotor.stopMotor();
      leftBackMotor.stopMotor();
    }
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
  }

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    drivebase.arcadeDrive(xbox.getRightX(), -xbox.getLeftY());

    manipulatorMotor.set(joystick.getRawAxis(1));

    // double inSpeed = 0.5;
    // double outSpeed = -1.0;
    // if (joystick.getRawButton(12)) {
    // manipulatorMotor.set(inSpeed);
    // } else if (xbox.getYButton()) {
    // manipulatorMotor.set(outSpeed);
    // } else {
    // manipulatorMotor.set(0);
    // }

    if (xbox.getBButton()) {
      manipulatorMotor.set(0);
    } else if (xbox.getAButton()) {
      manipulatorMotor.set(0.5);
    } else if (xbox.getYButton() || joystick.getRawButton(8)) {
      manipulatorMotor.set(-1.0);
    }

    if (joystick.getRawButton(10)) {
      manipulatorMotor.set(0);
    } else if (joystick.getRawButton(12)) { // IN SLOWER
      manipulatorMotor.set(0.3);
    } else if (joystick.getRawButton(11)) { // IN FASTER
      manipulatorMotor.set(0.6);
    } else if (joystick.getRawButton(8)) { // OUT SLOWER
      manipulatorMotor.set(-0.3);
    } else if (joystick.getRawButton(7)) { // OUT FASTER
      manipulatorMotor.set(-0.8);
    }

  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }
}
