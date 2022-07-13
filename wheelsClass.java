package org.firstinspires.ftc.teamcode.subAssembliesOpModes;



import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;




@TeleOp(name="wheelsClass", group="Linear Opmode")

public class wheelsClass extends LinearOpMode {

    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();
    public DcMotor rightA;
    public DcMotor rightB;
    public DcMotor leftA;
    public DcMotor leftB;
    public DcMotor shoulder;
    public CRServo carousel;
    public Servo joint;
    //Everything is in centimeters
    //public static double robotLength = 43.2;
    public static double robotWidth = 19.76;

    @Override

    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
//        rightA  = hardwareMap.get(DcMotor.class, "FR");
//        rightB  = hardwareMap.get(DcMotor.class, "BR");
//        leftA = hardwareMap.get(DcMotor.class, "FL");
//        leftB = hardwareMap.get(DcMotor.class, "BL");

//        leftA.setDirection(DcMotor.Direction.REVERSE);
//        leftB.setDirection(DcMotor.Direction.REVERSE);

//        runningEncoders();



        // Wait for the game to start (driver presses PLAY)
        setWheels(hardwareMap, "FR","RR","FL","RL");


        waitForStart();
        driveY("f",0.5, 155.7);
        //turnStationary("cc", 175, .7);

        //driveY("b",0.5,55);


        driveX("l",0.5,146.69);
        driveY("b",0.5, 155.7);
        driveX("r",0.5,50.420);

        // run until the end of the match (driver presses STOP)
        //DriveY("f" ,0.75, 20);
        //while (opModeIsActive()) {

        //DriveY("f", 1, 25.5555);
        //DriveX("l" ,1, 40);

        //stopDriving();
        //turn("cc",90, .75);
//        DriveY("b",.5, 80);




        // Show the elapsed game time and wheel power.
        //telemetry.addData("Status", "Run Time: " + runtime.toString());

        //telemetry.update();
        //}
    }

    public void driveY(String direction ,double power, double distanceCM){
        resetEncoders();
        distanceCM = distanceCM * 538/ 31.4;
        int ticks = (int)Math.round(distanceCM);

        if(direction == "f"){
            rightA.setTargetPosition(ticks);
            rightB.setTargetPosition(ticks);
            leftA.setTargetPosition(ticks);
            leftB.setTargetPosition(ticks);


            rightA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        else if(direction == "b"){
            rightA.setTargetPosition(-ticks);
            rightB.setTargetPosition(-ticks);
            leftA.setTargetPosition(-ticks);
            leftB.setTargetPosition(-ticks);


            rightA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }


        rightA.setPower(power);
        rightB.setPower(power);
        leftA.setPower(power);
        leftB.setPower(power);



        while(rightA.isBusy() || rightB.isBusy() || leftA.isBusy() || leftB.isBusy()){

        }
        stopDriving();

        runningEncoders();
    }

    public void driveX(String direction,  double power, double distanceCM){
        resetEncoders();

        distanceCM = distanceCM * 538/ 31.4;
        //1.1 is due to testing
        int ticks = (int)Math.round(1.1*distanceCM);

        if (direction == "l"){
            rightA.setTargetPosition(ticks);
            leftB.setTargetPosition(ticks);


            leftA.setTargetPosition(-ticks);
            rightB.setTargetPosition(-ticks);

            rightA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        else if (direction == "r"){

            rightA.setTargetPosition(-ticks);
            leftB.setTargetPosition(-ticks);


            leftA.setTargetPosition(ticks);
            rightB.setTargetPosition(ticks);

            rightA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }



        rightA.setPower(power);
        leftB.setPower(power);

        leftA.setPower(power);
        rightB.setPower(power);


        while(rightA.isBusy() || rightB.isBusy() || leftA.isBusy() || leftB.isBusy()){

        }

        stopDriving();

        runningEncoders();

    }

    public void turnStationary(String clockDirection, double angleDegrees, double power){

        //Converting from degrees to radians

        resetEncoders();
        //57 degrees = 532 ticks
        //given angle = x ticks
        double angleRadians = angleDegrees * Math.PI/180;
        //double distance = angleRadians * robotWidth;
        //Define this to be clearer
        // equation: theta = leftTickCount/radius of robot
        //This will get you to some rotation and then an empirical value should
        //be added
        //
        //int ticks = (int)Math.round(distance * 538/ 31.4);

        int ticks = (int)Math.round(angleDegrees * 570/57);
        //How precise is it?
        //double radius = robotWidth/2;
        //int ticks = (int)Math.round(angleRadians*radius);

        //Change letters if necessary
        if(clockDirection == "cc"){
            rightA.setTargetPosition(ticks);
            rightB.setTargetPosition(ticks);


            leftA.setTargetPosition(-ticks);
            leftB.setTargetPosition(-ticks);

            rightA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        if(clockDirection == "c"){
            rightB.setTargetPosition(-ticks);
            leftA.setTargetPosition(-ticks);


            leftB.setTargetPosition(ticks);
            leftA.setTargetPosition(ticks);

            rightA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        rightA.setPower(power);
        leftB.setPower(power);

        leftA.setPower(power);
        rightB.setPower(power);

        while(rightA.isBusy() || rightB.isBusy() || leftA.isBusy() || leftB.isBusy()){

        }

        stopDriving();

        runningEncoders();


    }

    public void followPath(double power){
        double radius = 0;
        double time = getRuntime();
        double positionRightSide = Math.sqrt(Math.pow(radius, 2) - Math.pow(time, 2));

        double velocityRight = -time/positionRightSide;
    }
    //Not able to determine a nice input - output way for making it to a desired position
    //Angle always gets in the way
    /*public void DriveD(int quadrant, double power, double distanceVertical, double distanceHorizontal){

        resetEncoders();

        /*Calculate center point
        _____________
        |           |
        |     0     |
        |     |     |
        -------------
        0 - center point

        //Return robot angle in radians
        double angleInside = Math.atan(robotLength/robotWidth);

        double hypotenuse = Math.sqrt(
                Math.pow(distanceVertical, 2) + Math.pow(distanceHorizontal, 2));
        distanceCM = distanceCM * 538/ 31.4;
        int ticks = (int)Math.round(distanceCM);

        if (quadrant == 1){
            leftA.setTargetPosition(ticks);
            rightB.setTargetPosition(ticks);

            leftA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftA.setPower(power);
            rightB.setPower(power);
        }

        if (quadrant == 3){
            leftA.setTargetPosition(-ticks);
            rightB.setTargetPosition(-ticks);

            leftA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftA.setPower(power);
            rightB.setPower(power);
        }

        else if (quadrant == 2){
            rightA.setTargetPosition(ticks);
            leftB.setTargetPosition(ticks);

            rightA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            rightA.setPower(power);
            leftB.setPower(power);
        }

        else if (quadrant == 4){
            rightA.setTargetPosition(-ticks);
            leftB.setTargetPosition(-ticks);

            rightA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            rightA.setPower(power);
            leftB.setPower(power);
        }








        while(rightA.isBusy() || rightB.isBusy() || leftA.isBusy() || leftB.isBusy()){

        }

        stopDriving();

        runningEncoders();
    }
    */

    //Order of naming motors: front right, back right, front left, back left
    public void setWheels(HardwareMap hwMap, String name1, String name2, String name3, String name4) {
        //Initialize motors that control the wheels
        rightA  = hwMap.get(DcMotor.class, name1);
        rightB  = hwMap.get(DcMotor.class, name2);
        leftA = hwMap.get(DcMotor.class, name3);
        leftB = hwMap.get(DcMotor.class, name4);


        leftA.setDirection(DcMotor.Direction.REVERSE);
        leftB.setDirection(DcMotor.Direction.REVERSE);

//        telemetry.addData("Status", "Initialized");
//        telemetry.update();


    }

    public void resetEncoders(){
        rightA.setMode(DcMotor.RunMode.RESET_ENCODERS);
        rightB.setMode(DcMotor.RunMode.RESET_ENCODERS);
        leftA.setMode(DcMotor.RunMode.RESET_ENCODERS);
        leftB.setMode(DcMotor.RunMode.RESET_ENCODERS);


    }

    public void runningEncoders(){
        rightA.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        rightB.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        leftA.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        leftB.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
    }

    public void stopDriving(){
        rightA.setPower(0);
        rightB.setPower(0);
        leftA.setPower(0);
        leftB.setPower(0);


    }

    //Functions for teleOp
    public void setPowerXdirection(double power){
        //Set the power to all motors with a specified power
        rightA.setPower(power);
        leftB.setPower(power);

        leftA.setPower(-power);
        rightB.setPower(-power);
    }

    public void setPowerYdirection(double power){
        rightA.setPower(power);
        rightB.setPower(power);
        leftA.setPower(power);
        leftB.setPower(power);
    }



}


