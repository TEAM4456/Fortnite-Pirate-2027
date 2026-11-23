// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// PS: Remy was here, you prob wont find this line of code until 2050 >:)

package frc.robot;

// Our own imports
import frc.robot.subsystems.SwerveDrive;
import frc.robot.commands.TeleopSwerve;

// External imports
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.config.RobotConfig;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  private final CommandXboxController driver = new CommandXboxController(0); // Represents main driver's xbox controller
  private final CommandXboxController second = new CommandXboxController(1); // Represents secondary xbox controller
  private final CommandXboxController backup = new CommandXboxController(2); // Represents backup xbox controller

  private final int translationAxis = XboxController.Axis.kLeftY.value;
  private final int strafeAxis = XboxController.Axis.kLeftX.value ;
  private final int rotationAxis = XboxController.Axis.kRightX.value;

  // The robot's subsystems and commands are defined here...

  private final SwerveDrive swerve = new SwerveDrive();
  
  private RobotConfig config;

  private final SendableChooser<Command> chooser;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // If we are red alliance, then the default Field view is good. Otherwise, we need
    //  to invert the translation and strafe axes, as well as the drive motors in SwerveDrive
    // boolean isRed = swerve.allianceIsRed();
    // int invertAxes = isRed ? 1 : -1;
    swerve.setDefaultCommand(
        new TeleopSwerve(
            swerve,
            () -> -driver.getRawAxis(translationAxis),
            () -> -driver.getRawAxis(strafeAxis),
            () -> -driver.getRawAxis(rotationAxis) / 2));

    // Set subsyetm default commands used to be here

    chooser = AutoBuilder.buildAutoChooser();
    
    // Configure the trigger bindings
    configureBindings();
    swerve.resetPose(null);
    
    SmartDashboard.putData("auto", chooser);
  }

  // ALL THE COMMANDS WERE HERE
  
  private void configureBindings() {

    chooser.setDefaultOption("nothing", null);
    // Chooser command choices used to be here

    // Button bindings used to be here

  }

  // Calls the SwerveDrive "updateOdometry" method. Then this method is called in "Robot.java" for actual use. This is therefore a bridge method
  public void updateOdometry() {
    swerve.updateOdometry();
  }

  // Look into this later
  /** Takes the selected Auto path in Smart Dashbooard and runs it when "autonomousInit" in "Robot.java" is first started 
   *  @return The command to run in autonomous */
  public Command getAutonomousCommand() {
    return chooser.getSelected();
  }
}
