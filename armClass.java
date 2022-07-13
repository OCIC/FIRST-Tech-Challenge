/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.subAssembliesOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

//Linear vs Iterative problem

@TeleOp(name="armClass", group="Linear")
public class armClass extends LinearOpMode {


    //Don''t use a CRServo - depends on power level in battery = many problems
    //public CRServo joint;
    public DcMotorEx joint;
    public Servo hand;
    public DcMotor shoulder;
    /*TPR - ticks per rev - unnecessary code
    static int TPR;
    static int ticksTop;
    static int ticksMiddle;
    static int ticksBottom;
    TPR - ticks per rev - unnecessary code*/
    static double currentHandPosition;

    static int jointTicks = 0;
    static int ticks = 0;

    //28 counts
    //1680 counts
    // 360 = 3 #
    //  given angle = x
    // x = 1680*3/360

    @Override
    public void runOpMode() throws InterruptedException {
        setArmHardware(hardwareMap, "hand","joint","shoulder");

        //setArmHardware(hardwareMap, "hand", "joint", "shoulder", "miniShoulder");
        telemetry.addData("Current shoulder position: ", shoulder.getCurrentPosition());



        waitForStart();
        //rotateJoint(0);
        //rotateJoint(260);
        rotateJoint(260);


        while(opModeIsActive()){
            shoulder.setPower(gamepad1.left_stick_y);

        }




        //openHand();

        //rotateJointToLevel(5);
        //closeHand();
        //closeHand();
        //sleep(1000);
        //openHand();
        /*while(opModeIsActive()){
            telemetry.addData("Current shoulder position: ", shoulder.getCurrentPosition());
            telemetry.addData("Current joint position: ", joint.getCurrentPosition());
            telemetry.update();

        }*/




            //rotateJointToLevel(45);
            //






    }

    //Servo1 is the servo with the fingers
    //Servo2 is the servo between the motor and Servo1
    public void setArmHardware(HardwareMap hwMap, String handName, String jointName, String shoulderName) {
        //Initialize servos
        shoulder = hwMap.get(DcMotor.class, shoulderName);
        hand = hwMap.servo.get(handName);
        hand.setDirection(Servo.Direction.REVERSE);

        joint = hwMap.get(DcMotorEx.class, jointName);
        joint.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shoulder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        joint.setDirection(DcMotorSimple.Direction.REVERSE);
        shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        joint.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //joint.setMode(DcMotor.RunMode.RESET_ENCODERS);
        //joint.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        //hand.setDirection(Servo.Direction.REVERSE);


    }

    //Hand Functions
    public void openHand() {

        //Get the current position of the hand
        //currentHandPosition = hand.getPosition();

        //telemetry.addData("Current position", currentHandPosition);


        //Because the setPosition only accepts positive values
        //we need the absolute value of the difference
        //This variable is the difference between the current and target position
        //
        //toTargetPositionOpen = Math.abs(0);
        //Open the arm 90 deg
        hand.setPosition(1);

        //Time to reach position



        //0 is the open position



        sleep(1000);
    }

    public void closeHand() {
        //Problem with servos, not really doing anything
        currentHandPosition = hand.getPosition();

        //Checking the value of each servo
        //telemetry.addData("Hand position", currentHandPosition);
        //The above return value 0-1
        //target value for closing the hand: 0.2
        //So if the hand is just slightly open the value it needs to move is x - 0.2


        hand.setPosition(0.4);

        //Wait for this amount of time so that the
        // servo reaches its position in time
        sleep(1000);
    }

    public void rotateJoint(double angle){
        jointTicks = (int)Math.round(angle*288*3/360);

        joint.setTargetPosition(jointTicks);

        joint.setPower(.25);

        joint.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        while(joint.isBusy()){
            telemetry.addData("Joint target ticks", jointTicks);
        }
        joint.setPower(0);


    }
    public void rotateShoulder(int level){
        //sjoint height = 17.5in
        //level 3 = 16
        //level 2 = 20cm
        //level 1 = 10cm

        /* int shoulderCurrentPosition = shoulder.getCurrentPosition();
        int targetPosition;
        if(level == 3){
            targetPosition = 40;

        }
        else if(level == 2){
            targetPosition = -179;
        }
        else{
            targetPosition = -182;
        }*/
        shoulder.setTargetPosition(0);
        shoulder.setPower(.2);
        while(shoulder.isBusy()){

        }
        shoulder.setPower(0);
        shoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);

    }
    //Rotate the middle servo to a level
    public void rotateJointToLevel(double angle) {
        double TPR = 288;
        jointTicks = jointTicks + (int)Math.round(angle * TPR/360);

        joint.setTargetPosition(jointTicks);

        joint.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        joint.setPower(.4);

        while(joint.isBusy()){
            telemetry.addData("Joint target ticks", jointTicks);
            telemetry.update();
        }
        joint.setPower(0);


        //while(joint.isBusy()){

        //telemetry.update();
        //}//
        /*while(joint.isBusy()){
            telemetry.addData("Ticks", ticks );
            telemetry.addData("velocity", joint.getVelocity() + " ticks per second");
            telemetry.addData("Ticks", joint.getCurrentPosition());
            telemetry.addData("Reached position", !joint.isBusy());
            telemetry.update();
        }*/


        /*joint.setTargetPosition(0);
        joint.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        joint.setVelocity(TPR/6);
        while(joint.isBusy()){
            telemetry.addData("velocity", joint.getVelocity() + " ticks per second");
            telemetry.addData("Ticks", joint.getCurrentPosition());
            telemetry.addData("Reached position", !joint.isBusy());
            telemetry.update();
        }*/



        //We should know the angle to get to each level
        //Try do do it on paper
        /*switch (level) {

            case 1:
                toLevelPosition = 0.5*Math.acos(32.5/38)/(Math.PI/2);
                targetPositionJoint = Math.abs(currentJointPosition - toLevelPosition);
                break;
            case 2:
                toLevelPosition = 0.5*Math.acos(18.53/38)/(Math.PI/2);
                targetPositionJoint = Math.abs(currentJointPosition - toLevelPosition);
                break;
            case 3:
                //The shorter part of the arm should be level with the third level
                //on the shipping hub
                //0.5 corresponds to a 90 degree rotation
                toLevelPosition = 0.5;
                targetPositionJoint = Math.abs(currentJointPosition - toLevelPosition);
                break;
        }
        //Set the position as required
        joint.setPosition(targetPositionJoint);

        sleep(500);*/
    }


    public void rotateJointToDefault(){
        joint.setTargetPosition(0);

        joint.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        joint.setPower(.5);

        while(joint.isBusy()){
            telemetry.addData("State", "Getting to position");

        }

        //sleep(500);
    }

    //A combination of movements that gets the arm
    // to a particular level
    public void positionArmToLevel(int level) {
            //We want to close the hand and to position the shorter piece to the level
            closeHand();

            rotateJointToLevel(level);

            /*
            openHand();

            closeHand();

            rotateJointToDefault();
            */
    }

        //Encoder junk methods
        //public void resetEncoder(){

        //shoulder.setMode(DcMotor.RunMode.RESET_ENCODERS);
        //}

        ///public void runningEncoder() {
        //  shoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);

        //}

        //public void stopDriving() {

        //shoulder.setPower(0);
        //}

}