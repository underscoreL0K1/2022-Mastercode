package frc.robot.Mechanisms;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Systems.BatteryMap;
import frc.robot.Systems.Vision;

public class Drivetrain {
    
    private final WPI_TalonFX m_leftMotorOne;
    private final WPI_TalonFX m_leftMotorTwo;
    private final WPI_TalonFX m_leftMotorThree;

    private final WPI_TalonFX m_RightMotorFour;
    private final WPI_TalonFX m_RightMotorFive;
    private final WPI_TalonFX m_RightMotorSix;

    private final MotorControllerGroup left;
    private final MotorControllerGroup right;

    private final DifferentialDrive drive;
    
    public static double steering;
    public static boolean manualSteering;

    public boolean neutralMode = true;

    double limeSteerCoefficient = .1;
    double piSteerCoefficient = .1;


    boolean dtRunning;
    
    public Drivetrain() {



      m_leftMotorOne = new WPI_TalonFX(1);
      m_leftMotorTwo = new WPI_TalonFX(2);
      m_leftMotorThree = new WPI_TalonFX(3);
  
      m_RightMotorFour = new WPI_TalonFX(4);
      m_RightMotorFive = new WPI_TalonFX(5);
      m_RightMotorSix = new WPI_TalonFX(6);


      m_leftMotorOne.setNeutralMode(NeutralMode.Brake);
      m_leftMotorTwo.setNeutralMode(NeutralMode.Brake);
      m_leftMotorThree.setNeutralMode(NeutralMode.Brake);
      
      m_RightMotorFour.setNeutralMode(NeutralMode.Brake);
      m_RightMotorFive.setNeutralMode(NeutralMode.Brake);
      m_RightMotorSix.setNeutralMode(NeutralMode.Brake);


      right = new MotorControllerGroup(m_RightMotorFour, m_RightMotorFive, m_RightMotorSix);
      left = new MotorControllerGroup(m_leftMotorThree, m_leftMotorOne, m_leftMotorTwo);

      left.setInverted(true);

      drive = new DifferentialDrive(right, left);

      
      manualSteering = true;
     


      }

    @SuppressWarnings("ParameterName")
    public void drive(double speed, double rotation) {

      
      if (manualSteering) steering = rotation * 0.65;
      
      drive.arcadeDrive(speed, steering);
      if(speed > .1 ||speed < -.1 || rotation > .1 || rotation < .1) dtRunning = true;
      else dtRunning= false; 
      BatteryMap.driveTrainMotors(m_RightMotorFive, m_RightMotorSix, m_leftMotorOne, m_leftMotorTwo,m_RightMotorFour,m_leftMotorThree, dtRunning);
    }

    public void brake(boolean braking){
      SmartDashboard.putBoolean("NeutralMode", neutralMode);
      if(braking) neutralMode = !neutralMode;
        if(!neutralMode){
        m_leftMotorOne.setNeutralMode(NeutralMode.Coast);
        m_leftMotorTwo.setNeutralMode(NeutralMode.Coast);
        m_leftMotorThree.setNeutralMode(NeutralMode.Coast);
        
        m_RightMotorFour.setNeutralMode(NeutralMode.Coast);
        m_RightMotorFive.setNeutralMode(NeutralMode.Coast);
        m_RightMotorSix.setNeutralMode(NeutralMode.Coast);
  
      }
      else{
      m_leftMotorOne.setNeutralMode(NeutralMode.Brake);
      m_leftMotorTwo.setNeutralMode(NeutralMode.Brake);
      m_leftMotorThree.setNeutralMode(NeutralMode.Brake);
      
      m_RightMotorFour.setNeutralMode(NeutralMode.Brake);
      m_RightMotorFive.setNeutralMode(NeutralMode.Brake);
      m_RightMotorSix.setNeutralMode(NeutralMode.Brake);
      }
    }


    public void targetLime(boolean On) {
      //manualSteering = !On;
      if (On) {
        steering = Vision.AngleFromTarget() * limeSteerCoefficient;
      }
    }

    public void TargetPi(boolean On) {
      //manualSteering = !On;
      if (On) {
        steering = Vision.AngleFromBall() * piSteerCoefficient;
      }
    }

    //public double Speed() {

      //double gearRatio = 1; //placeholder
      //double averageMotorSpeed = ((m_leftMotorOne.getSelectedSensorVelocity() + m_leftMotorTwo.getSelectedSensorVelocity() +) + (m_RightMotorFour.getSelectedSensorVelocity() + m_RightMotorFive.getSelectedSensorVelocity() + m_RightMotorSix.getSelectedSensorVelocity()/6));
      
      //double wheelSpeed = averageMotorSpeed/gearRatio; //placeholder


//      return wheelSpeed;  
  //  }

public void driveHalf(Boolean bleft, boolean bright){
  if(bleft){
    m_leftMotorOne.set(ControlMode.PercentOutput, 0.2);
        m_leftMotorTwo.set(ControlMode.PercentOutput, 0.2);
        m_leftMotorThree.set(ControlMode.PercentOutput, 0.2);
  }
  else{
    m_leftMotorOne.set(ControlMode.PercentOutput, 0);
        m_leftMotorTwo.set(ControlMode.PercentOutput, 0);
        m_leftMotorThree.set(ControlMode.PercentOutput, 0);}
  if(bright){
    m_RightMotorFour.set(ControlMode.PercentOutput, 0.2);
    m_RightMotorFive.set(ControlMode.PercentOutput, 0.2);
    m_RightMotorSix.set(ControlMode.PercentOutput, 0.2);
}
else{
  m_RightMotorFour.set(ControlMode.PercentOutput, 0);
  m_RightMotorFive.set(ControlMode.PercentOutput, 0);
  m_RightMotorSix.set(ControlMode.PercentOutput, 0);}
}



}
