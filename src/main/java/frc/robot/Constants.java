// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static final class TestFixtureConstants {
    public static final int kDriveMotorPort = 1;
    public static final int kTurningMotorPort = 2;
    public static final int kAngleEncoderPort = 0;
    public static final int kfalconEncoderCPR = 2048;
    public static final double kSteeringGearRatio = 12.8;
    public static final double kEncoderTicksPerRevolution = kfalconEncoderCPR * kSteeringGearRatio;
    public static final double kTestVelocity = 5;
	  public static int kCANCoderID = 21;
  }

  public static final class DriveConstants {
    public static final int kFrontLeftDriveMotorPort = 3;
    public static final int kRearLeftDriveMotorPort = 1;
    public static final int kFrontRightDriveMotorPort = 5;
    public static final int kRearRightDriveMotorPort = 7;

    public static final int kFrontLeftTurningMotorPort = 4;
    public static final int kRearLeftTurningMotorPort = 2;
    public static final int kFrontRightTurningMotorPort = 6;
    public static final int kRearRightTurningMotorPort = 8;

    public static final int kFrontLeftTurningEncoderPort = 2;
    public static final int kRearLeftTurningEncoderPort = 1;
    public static final int kFrontRightTurningEncoderPort = 3;
    public static final int kRearRightTurningEncoderPort = 4; 

    public static final boolean kFrontLeftTurningEncoderReversed = false;
    public static final boolean kRearLeftTurningEncoderReversed = true;
    public static final boolean kFrontRightTurningEncoderReversed = false;
    public static final boolean kRearRightTurningEncoderReversed = true;

    public static final int kFrontLeftDriveEncoderPort = 0;
    public static final int kRearLeftDriveEncoderPort = 0;
    public static final int kFrontRightDriveEncoderPort = 0;
    public static final int kRearRightDriveEncoderPort = 0;

    public static final boolean kFrontLeftDriveEncoderReversed = false;
    public static final boolean kRearLeftDriveEncoderReversed = true;
    public static final boolean kFrontRightDriveEncoderReversed = false;
    public static final boolean kRearRightDriveEncoderReversed = true;

    public static final double kTrackWidth = 0.5;
    // Distance between centers of right and left wheels on robot
    public static final double kWheelBase = 0.7;
    // Distance between front and back wheels on robot
    public static final SwerveDriveKinematics kDriveKinematics =
        new SwerveDriveKinematics(
            new Translation2d(kWheelBase / 2, kTrackWidth / 2), //front left
            new Translation2d(-1*kWheelBase / 2, kTrackWidth / 2), //rear left
            new Translation2d(kWheelBase / 2, -1*kTrackWidth / 2), //front right
            new Translation2d(-1*kWheelBase / 2, -1*kTrackWidth / 2)); //rear right

    public static final boolean kGyroReversed = false;

    // These are example values only - DO NOT USE THESE FOR YOUR OWN ROBOT!
    // These characterization values MUST be determined either experimentally or theoretically
    // for *your* robot's drive.
    // The RobotPy Characterization Toolsuite provides a convenient tool for obtaining these
    // values for your robot.
    public static final double ksVolts = 1;
    public static final double kvVoltSecondsPerMeter = 2;
    public static final double kaVoltSecondsSquaredPerMeter = 0.15;
    public static final double kWheelHeight = .1;//meters

    public static final double kMaxSpeedMetersPerSecond = 3;

    public static final double kFrontRightOffset = -2.998932440316429;
    public static final double kFrontLeftOffset = 0.796136928912 - Math.PI;
    public static final double kRearRightOffset = -1.0507768370;
    public static final double kRearLeftOffset = (-0.069029 + Math.PI) % Math.PI;
  }

  public static final class ModuleConstants {
    public static final double kMaxModuleAngularSpeedRadiansPerSecond = 0.5 * Math.PI;
    public static final double kMaxModuleAngularAccelerationRadiansPerSecondSquared = 0.5 * Math.PI;
    public static final double kPModuleTurningController = 4.3;

    public static final double kPModuleDriveController = 1;
    public static final int kfalconEncoderCPR = 2048;
    public static final double kSteeringGearRatio = 12.8;
    public static final double kEncoderTicksPerRevolution = kfalconEncoderCPR * kSteeringGearRatio;
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 1.125;
    public static final double kMaxAccelerationMetersPerSecondSquared = 1.5;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kPXController = 2;
    public static final double kPYController = 2;
    public static final double kPThetaController = 2;

    // Constraint for the motion profilied robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
        new TrapezoidProfile.Constraints(
            kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
  }
}
