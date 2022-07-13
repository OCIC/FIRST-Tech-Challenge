package org.firstinspires.ftc.teamcode.Pipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class RedSquarePipeline extends OpenCvPipeline {

    static Scalar GREEN = new Scalar(0, 255, 0);
    Mat YCrCb = new Mat();
    Mat thresholded = new Mat();
    Mat Cr = new Mat();

    Rect LEFT_SQUARE = new Rect(
            new Point(0,60),
            new Point(83, 220)
    );

    Rect CENTRE_SQUARE = new Rect(
            new Point(122,50),
            new Point(210, 219)
    );

    Rect RIGHT_SQUARE = new Rect(
            new Point(247,50),
            new Point(317, 236)
    );


    Telemetry telemetry;
    public RedSquarePipeline(Telemetry telemetry) {
        this.telemetry = telemetry;
    }
    public enum Square{
        Left,
        Centre,
        Right

    }
    public Square square;
    public Square getSquare(){
        return square;
    }

    @Override
    public Mat processFrame(Mat input) {

        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);

        Core.extractChannel(YCrCb, Cr, 1);

        Imgproc.threshold(Cr, thresholded, 170, 61, Imgproc.THRESH_BINARY);

        Mat left = thresholded.submat(LEFT_SQUARE);
        Mat centre = thresholded.submat(CENTRE_SQUARE);
        Mat right = thresholded.submat(RIGHT_SQUARE);

        double leftValue = Core.sumElems(left).val[0]/LEFT_SQUARE.area()/255;
        double centreValue = Core.sumElems(centre).val[0]/CENTRE_SQUARE.area()/255;
        double rightValue = Core.sumElems(right).val[0]/RIGHT_SQUARE.area()/255;

        left.release();
        centre.release();
        right.release();
        //...
        boolean debugging = false;
        if (debugging){
            telemetry.addData("leftValue", leftValue);
            telemetry.addData("centreValue", centreValue);
            telemetry.addData("rightValue", rightValue);
        }

        if(centreValue < leftValue && centreValue < rightValue){
            square = Square.Centre;
            telemetry.addData("Square", " centre");
        }
        else if(rightValue < leftValue && rightValue < centreValue){
            square = Square.Right;
            telemetry.addData("Square", " right");
        }
        else if(leftValue < rightValue && leftValue < centreValue){
            square = Square.Left;
            telemetry.addData("Square", " left");
        }
        telemetry.update();

        Imgproc.rectangle(input, LEFT_SQUARE, GREEN, 1);
        Imgproc.rectangle(input, CENTRE_SQUARE, GREEN, 1);
        Imgproc.rectangle(input, RIGHT_SQUARE, GREEN, 1);
        return input;

    }
}
