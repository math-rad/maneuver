// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
//import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.security.cert.PKIXBuilderParameters;
import java.util.function.Supplier;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class Maneuver extends CommandBase {
  private final Drivetrain m_drivetrain;
  private final Supplier<Double> m_xaxisSpeedSupplier;
  private final Supplier<Double> m_yaxisRotateSupplier;
  private final CommandXboxController control;
  private final HolonomicDriveController h_control;

  /**
   * Creates a new ArcadeDrive. This command will drive your robot according to
   * the speed supplier
   * lambdas. This command does not terminate.
   *
   * @param drivetrain          The drivetrain subsystem on which this command
   *                            will run
   * @param xaxisSpeedSupplier  Lambda supplier of forward/backward speed
   * @param yaxisRotateSupplier Lambda supplier of rotational speed
   * @param leftButton          Left joystick down
   */
  public Maneuver(
      Drivetrain drivetrain,
      Supplier<Double> xaxisSpeedSupplier,
      Supplier<Double> yaxisRotateSupplier,
      CommandXboxController commandXbox) {
    m_drivetrain = drivetrain;
    m_xaxisSpeedSupplier = xaxisSpeedSupplier;
    m_yaxisRotateSupplier = yaxisRotateSupplier;
    control = commandXbox;
    h_control = new HolonomicDriveController(
        new PIDController(1, 0, 0),
        new PIDController(1, 0, 0),
        new ProfiledPIDController(1, 0, 0,
            new TrapezoidProfile.Constraints(6.28, 3.14)));
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // System.out.print(control.hashCode());

    double x = m_xaxisSpeedSupplier.get();
    double y = m_yaxisRotateSupplier.get();
    var pt = new Translation2d(x, y);
    double speed;
    double angle;

    if (pt.getAngle().getDegrees() == m_drivetrain.getGyroAngleZ()) {
      speed = pt.getNorm() * (y >= 0 ? -.5)
    }
    
    speed = pt.getNorm() * (y >= 0 ? -.5 : .5);
    // speed = pt.getNorm();
    m_drivetrain.arcadeDrive(speed, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; // This is a default command
  }
}
