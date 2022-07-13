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

package org.firstinspires.ftc.teamcode.completeOpModes;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Pipelines.RedSquarePipeline;
import org.firstinspires.ftc.teamcode.subAssembliesOpModes.armClass;
import org.firstinspires.ftc.teamcode.subAssembliesOpModes.wheelsClass;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous(name="linearRedSquareOpMode", group="Linear Opmode")
public class linearRedSquareOpMode extends LinearOpMode {

    OpenCvWebcam webcam;
    public RedSquarePipeline.Square detectedSquare;

    wheelsClass wheels = new wheelsClass();
    armClass arm = new armClass();

    @Override
    public void runOpMode() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);


        RedSquarePipeline detector = new RedSquarePipeline(telemetry);

        webcam.setPipeline(detector);

        webcam.setMillisecondsPermissionTimeout(2500); // Timeout for obtaining permission is configurable. Set before opening.
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {

                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
                telemetry.addData("Camera State", "Opened");
                telemetry.update();
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
                telemetry.addData("Camera State", "not open");
                telemetry.update();
            }
        });

        while (!opModeIsActive()) {
            detectedSquare = detector.getSquare();
            //The camera stops and saves the last position of the duck/element

            /*if(detectedSquare == RedSquarePipeline.Square.Centre){
                telemetry.addData("Square", "Centre");
                telemetry.update();
            }*/



            //telemetry.addData("Duck found on ", detector.getSquare());


        }
        wheels.setWheels(hardwareMap, "FR","RR","FL","RL");
        arm.setArmHardware(hardwareMap,"hand","joint","shoulder");

        waitForStart();

        webcam.stopStreaming();
        arm.closeHand();


        wheels.driveX("r",0.25, 25);
        wheels.turnStationary("c", 80, .7);
        wheels.driveX("r",0.25, 50);

        //If the right square is detected do the following:
        if(detectedSquare == RedSquarePipeline.Square.Right){
            wheels.turnStationary("c", 80, .7);
            wheels.driveX("l",.45,50);
            arm.rotateJoint(175);
            wheels.driveY("f",.5,15);
            arm.openHand();
            arm.closeHand();
            arm.rotateJointToDefault();
            wheels.driveY("b",0.7,45);
            wheels.turnStationary("cc",90,0.5);
            wheels.driveX("l", 0.7, 27.5);

        }

        //If the middle square is detected do the following:
        if(detectedSquare == RedSquarePipeline.Square.Centre){
            wheels.driveX("r",0.25, 40);
            wheels.turnStationary("c", 95, .7);
            wheels.driveX("l",.7,34);
            arm.rotateJoint(230);
            wheels.driveY("f",.7,15);
            arm.openHand();
            wheels.driveY("b",.7, 30);
            arm.closeHand();
            arm.rotateJointToDefault();
            wheels.turnStationary("cc",95,.7);
            wheels.driveX("l",.7,30);

        }

        //If the left square is detected do the following:
        if(detectedSquare == RedSquarePipeline.Square.Left){
            wheels.driveX("r",0.25, 35);
            wheels.turnStationary("c", 95, .7);
            wheels.driveX("l",.7,40);
            arm.rotateJoint(260);
            wheels.driveY("f",.7,24);
            arm.openHand();
            wheels.driveY("b",.7, 30);
            arm.closeHand();
            arm.rotateJointToDefault();
            wheels.turnStationary("cc",95,.7);
            wheels.driveX("l",.7,32);
        }

        wheels.driveY("b", 1, 160);
        arm.closeHand();
        arm.rotateJointToDefault();
        
        while(opModeIsActive()){
            telemetry.addData("Square",  detectedSquare);
            telemetry.update();
        }
    }
        
        

}
