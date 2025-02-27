// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SerialPort;

public class DriveSubsystem extends SubsystemBase {
  // Robot swerve modules
  private final SwerveModule m_frontLeft =
      new SwerveModule(
          DriveConstants.kFrontLeftDriveMotorPort,
          DriveConstants.kFrontLeftTurningMotorPort,
          DriveConstants.kFrontLeftDriveEncoderPort,
          DriveConstants.kFrontLeftTurningEncoderPort,
          DriveConstants.kFrontLeftDriveEncoderReversed,
          DriveConstants.kFrontLeftTurningEncoderReversed,
          "FrontLeft", DriveConstants.kFrontLeftOffset);


  private final SwerveModule m_rearLeft =
      new SwerveModule(
          DriveConstants.kRearLeftDriveMotorPort,
          DriveConstants.kRearLeftTurningMotorPort,
          DriveConstants.kRearLeftDriveEncoderPort,
          DriveConstants.kRearLeftTurningEncoderPort,
          DriveConstants.kRearLeftDriveEncoderReversed,
          DriveConstants.kRearLeftTurningEncoderReversed,
          "RearLeft", DriveConstants.kRearLeftOffset);

  private final SwerveModule m_frontRight =
      new SwerveModule(
          DriveConstants.kFrontRightDriveMotorPort,
          DriveConstants.kFrontRightTurningMotorPort,
          DriveConstants.kFrontRightDriveEncoderPort,
          DriveConstants.kFrontRightTurningEncoderPort,
          DriveConstants.kFrontRightDriveEncoderReversed,
          DriveConstants.kFrontRightTurningEncoderReversed,
          "FrontRight", DriveConstants.kFrontRightOffset);

  private final SwerveModule m_rearRight =
      new SwerveModule(
          DriveConstants.kRearRightDriveMotorPort,
          DriveConstants.kRearRightTurningMotorPort,
          DriveConstants.kRearRightDriveEncoderPort,
          DriveConstants.kRearRightTurningEncoderPort,
          DriveConstants.kRearRightDriveEncoderReversed,
          DriveConstants.kRearRightTurningEncoderReversed,
          "RearRight", DriveConstants.kRearRightOffset);
  
  

  // The gyro sensor
  private final AHRS m_gyro = new AHRS(SerialPort.Port.kMXP);

  //field
  private final Field2d m_field = new Field2d();

  // Odometry class for tracking robot pose
  SwerveDriveOdometry m_odometry =
     new SwerveDriveOdometry(DriveConstants.kDriveKinematics, m_gyro.getRotation2d());

  private SwerveModuleState[] swerveModuleStates;

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    SmartDashboard.putNumber("PID P Gain Input", 0);
    SmartDashboard.putData("field", m_field);
  }

  public void periodic() {
    // Update the odometry in the periodic block
    m_odometry.update(
      getHeading(),
      m_frontLeft.getState(),
      m_rearLeft.getState(),
      m_frontRight.getState(),
      m_rearRight.getState());
    m_frontLeft.periodic();
    m_frontRight.periodic();
    m_rearLeft.periodic();
    m_rearRight.periodic();
    SmartDashboard.putNumber("Heading", getHeading().getDegrees());
    m_field.setRobotPose(getPose());
  }

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
   public Pose2d getPose() {
     return m_odometry.getPoseMeters();
   }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
   public void resetOdometry(Pose2d pose) {
     m_odometry.resetPosition(pose, m_gyro.getRotation2d());
   }

  /**
   * Method to drive the robot using joystick info.
   *
   * @param xSpeed Speed of the robot in the x direction (forward).
   * @param ySpeed Speed of the robot in the y direction (sideways).
   * @param rot Angular rate of the robot.
   * @param fieldRelative Whether the provided x and y speeds are relative to the field.
   */
  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
    if (fieldRelative == true) {
      swerveModuleStates =
        DriveConstants.kDriveKinematics.toSwerveModuleStates(
          // fieldRelative
          ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, m_gyro.getRotation2d()));
    } else{
      swerveModuleStates =
        DriveConstants.kDriveKinematics.toSwerveModuleStates(
          new ChassisSpeeds(xSpeed, ySpeed, rot));
    }

    SwerveDriveKinematics.desaturateWheelSpeeds(
        swerveModuleStates, DriveConstants.kMaxSpeedMetersPerSecond);
    m_frontLeft.setDesiredState(swerveModuleStates[0]);
    m_rearLeft.setDesiredState(swerveModuleStates[1]);
    m_frontRight.setDesiredState(swerveModuleStates[2]);
    m_rearRight.setDesiredState(swerveModuleStates[3]);
  }

  /**
   * Sets the swerve ModuleStates.
   *
   * @param desiredStates The desired SwerveModule states.
   */
  public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.desaturateWheelSpeeds(
        desiredStates, DriveConstants.kMaxSpeedMetersPerSecond);
    m_frontLeft.setDesiredState(desiredStates[0]);
    m_rearLeft.setDesiredState(desiredStates[1]);
    m_frontRight.setDesiredState(desiredStates[2]);
    m_rearRight.setDesiredState(desiredStates[3]);
  }

  /** Resets the drive encoders to currently read a position of 0. */
  public void resetEncoders() {
    m_frontLeft.resetEncoders();
    m_rearLeft.resetEncoders();
    m_frontRight.resetEncoders();
    m_rearRight.resetEncoders();
  }

  /** Zeroes the heading of the robot. */
  public void zeroHeading() {
    m_gyro.zeroYaw();
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from -180 to 180
   */
  public Rotation2d getHeading() {
    return m_gyro.getRotation2d();
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return m_gyro.getRate() * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
  }
}
