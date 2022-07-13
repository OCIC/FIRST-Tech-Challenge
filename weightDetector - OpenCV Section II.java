package org.firstinspires.ftc.teamcode.Pipelines;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY_INV;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class weightID extends OpenCvPipeline {
    /*
    ROI - region of interest

    Intuition behind the ordering of the points:

    Each face is a rectangle with
    four possible places where weights can sit.
    Each of these places is an area of interest
    we want to analyze

    Ex: Draw rectangle ABCD
     D______________C
     |              |
     |              |
     A--------------B

     The rectangle represents an entire side
     and the letters mark the parts where weights
     could be found

    A is SIDE_ROI_1
    B is SIDE_ROI_2
    C is SIDE_ROI_3
    D is SIDE_ROI_4

    The set of points in each rectangle were obtained
    through Gimp, the drawing software

    Search "Gimp pixel coordinates of an image"
    to do it yourself
     */

    //Left side rectangles
    Rect A_ROI_1 = new Rect(

            new Point(162, 50),
            new Point(186, 125)
    );

    Rect A_ROI_2 = new Rect(
            new Point(200,70),
            new Point(230, 160)
    );


    Rect B_ROI_1 = new Rect(
            new Point(240, 114),
            new Point(100,90)
    );

    Rect B_ROI_2 = new Rect(
            new Point(40, 130),
            new Point(60, 105)
    );

    //Top side rectangles
    Rect C_ROI_1 = new Rect(
            new Point(79, 90),
            new Point(45,92)
    );

    Rect C_ROI_2 = new Rect(
            new Point(180, 70),
            new Point(150,90)
    );

    Rect C_ROI_3 = new Rect(
            new Point(180, 70),
            new Point(150,90)
    );

    Rect U_ROI_4 = new Rect(
            new Point(180, 70),
            new Point(150,90)
    );

    //Right side rectangles
    Rect R_ROI_1 = new Rect(
            new Point(79, 90),
            new Point(45,92)
    );

    Rect R_ROI_2 = new Rect(
            new Point(180, 70),
            new Point(150,90)
    );

    Rect R_ROI_3 = new Rect(
            new Point(180, 70),
            new Point(150,90)
    );

    Rect R_ROI_4 = new Rect(
            new Point(180, 70),
            new Point(150,90)
    );
    //Rectangles cannot go off the screen = error

    Rect[] regions = {
            A_ROI_1, A_ROI_2,
            B_ROI_1, B_ROI_2,
            C_ROI_1, C_ROI_2
    };


    Mat[] subMats = new Mat[regions.length];
    double[] values = new double[regions.length];
    boolean[] comparisons = new boolean[regions.length];



    //Variables

    /*
    The decimal value for the percentage of white
    that must exist in an area to be considered a weight
    0.09 means 9% of the area analyzed should be white
    */
    static double THRESHOLD_VALUE = 0.15;
    public static int weightNum = 0;

    //Colors
    static Scalar GREEN = new Scalar(0, 255, 0);
    //Enabling telemetry
    Telemetry telemetry;



    public weightID(Telemetry telemetry) {
        this.telemetry = telemetry;
    }
    public enum weightType{
        WW,
        W,
        None
    }
    public weightType type;
    Mat YCrCb = new Mat();
    Mat thresholded = new Mat();
    Mat Cr = new Mat();


    @Override
    public Mat processFrame(Mat input) {

        //Converting the RGB image into the YCbCr or YUV color space
        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);

        //Here GRIP, a program that mimics OpenCV in a blocks format, was used
        // to find the channel that would be most ideal for our purposes
        Core.extractChannel(YCrCb, Cr, 1);

        //The values below were determined also by using GRIP
        Imgproc.threshold(Cr, thresholded, 132, 255, THRESH_BINARY_INV);

        for(int i = 0; i < regions.length; i++){
            subMats[i] = thresholded.submat(regions[i]);
        }
        //double value = Core.sumElems(subMats[0]).val[0]/regions[0].area()/255;
        for(int i = 0; i < regions.length; i++){
           values[i] = Core.sumElems(subMats[i]).val[0]/regions[i].area()/255;
        }
        //telemetry.addData("Values", values[0]);

        for(int i = 0; i < subMats.length; i++){
            subMats[i].release();
        }

        //A separate variable is needed to skip to the other ROIs
        int weights = 0;


        for(int i = 0; i < values.length; i++){
            if(values[i] > THRESHOLD_VALUE){

                weights++;
            }
        }/*
        /*Check the three cube sides*/
        //for(int i = 0; i < 3; i++){
            //There are four regions on each side that need checkinh
            //for(int k = 0; k < 4; k++){
                //Compare each region' value with the threshold value
                //comparisons[listJumper] = values[listJumper] > THRESHOLD_VALUE;
                //listJumper ++;
            //}


        //}
//Check to see how many times there is true in the list
        //for(int l = 0; l < comparisons.length; l++) {
            //if (comparisons[l] == true) {
              //  weightNum++;
           // }
       // }
        //switch(weightNum){
                /*case 0:
                    continue;
                case 1:
                    break;
                case 2:
                    break;
            }*/
        //telemetry.update();*/

        //Colored rectangles
        //Only used when looking how the rectangles for analysis turned out to be
        //on the original input picture
        for(int i = 0; i < regions.length; i++){
            Imgproc.rectangle(input, regions[i], GREEN, 1);
        }
        telemetry.addData("Value ", values[0]);
        telemetry.addData("Ws deected ", weights);
        telemetry.addData("Regions Length" , regions.length);
        telemetry.addData("Values list" , values);
        telemetry.addData("List", values == null);
        telemetry.update();

        if (weightNum == 0){
            type = weightType.None;

        }

        else if(weightNum == 1){
            type = weightType.W;

        }
        else{
            type = weightType.WW;

        }
        //Imgproc.rectangle(input, U_ROI_3, GREEN, 1);
        //Imgproc.rectangle(input, L_ROI_3, GREEN, 1);
        return input;



    }/*
        Create submat, smaller pictures for analysis
        Enter the areas where the weights can sit as inputs


        //Left side submats
        //Mat left1 = thresholded.submat(L_ROI_1);
        //Mat left2  = Thresholded.submat(LEFT_ROI_2);
        //Mat left3  = thresholded.submat(L_ROI_3);
        //Mat left4  = Thresholded.submat(LEFT_ROI_4);

        Upper side submats
        Mat up1  = thresholded.submat(U_ROI_1);
        Mat up2  = thresholded.submat(U_ROI_2);
        Mat up3  = thresholded.submat(U_ROI_3);
        Mat up4  = thresholded.submat(U_ROI_4);


        /*Right side submats
        Mat right1 = thresholded.submat(R_ROI_1);
        Mat right2  = Thresholded.submat(R_ROI_2);
        Mat right3  = thresholded.submat(R_ROI_3);
        Mat right4  = Thresholded.submat(R_ROI_4);


        // What percentage of each ROI became white

        // For left side
        //double leftValue1 = Core.sumElems(left1).val[0]/ L_ROI_1.area()/255;
        //double leftValue2 = Core.sumElems(left2).val[0]/LEFT_ROI_2.area()/255;
        //double leftValue3 = Core.sumElems(left3).val[0]/ L_ROI_3.area()/255;
        //double leftValue4 = Core.sumElems(left4).val[0]/LEFT_ROI_4.area()/255;

         For upper side
        double upValue1 = Core.sumElems(up1).val[0]/ U_ROI_1.area()/255;
        double upValue2 = Core.sumElems(up2).val[0]/ U_ROI_2.area()/255;
        double upValue3 = Core.sumElems(up3).val[0]/ U_ROI_3.area()/255;
        double upValue4 = Core.sumElems(up4).val[0]/ U_ROI_4.area()/255;




        /* For right side
        double rightValue1 = Core.sumElems(right1).val[0]/ R_ROI_1.area()/255;
        double rightValue2 = Core.sumElems(right2).val[0]/ R_ROI_2.area()/255;
        double rightValue3 = Core.sumElems(right3).val[0]/ R_ROI_3.area()/255;
        double rightValue4 = Core.sumElems(right4).val[0]/ R_ROI_4.area()/255;



        //VERY IMPORTANT: release submats = less CPU usage on the robot

        //Release all lefties
        //left1.release();
        //left2.release();
        //left3.release();
        //left4.release();

         Release all ups
        up1.release();
        up2.release();
        up3.release();
        up4.release();



        Release all rights
        right1.release();
        right2.release();
        right3.release();
        right4.release();


        In case something isn't going according to plan



        If there are two weights sitting in one face they are connected by an imaginary diagonal
        There are two such possible diagonals: AC or BD
         D______________C
         |              |
         |              |
         A--------------B


        //boolean LEFT_DIAGONAL_BOTH = leftValue1 > THRESHOLD_VALUE && leftValue3 > THRESHOLD_VALUE;
                //|| leftValue2 > THRESHOLD_VALUE && leftValue4 > THRESHOLD_VALUE;

        If there is only one weight in the face than there are four possible places that
        need to be checked for percentage of white. Only or statements can be used

        //boolean LEFT_DIAGONAL_ONE = leftValue1 > THRESHOLD_VALUE || leftValue3 > THRESHOLD_VALUE;
        //|| leftValue2 > THRESHOLD_VALUE || leftValue4 > THRESHOLD_VALUE



        boolean RIGHT_DIAGONAL_BOTH = rightValue1 > THRESHOLD_VALUE && rightValue3 > THRESHOLD_VALUE
                || rightValue2 > THRESHOLD_VALUE && rightValue4 > THRESHOLD_VALUE;

        boolean RIGHT_DIAGONAL_ONE = rightValue1 > THRESHOLD_VALUE || rightValue3 > THRESHOLD_VALUE
        || rightValue2 > THRESHOLD_VALUE || rightValue4 > THRESHOLD_VALUE;

        boolean UP_DIAGONAL_BOTH = upValue1 > THRESHOLD_VALUE && upValue3 > THRESHOLD_VALUE
                || upValue2 > THRESHOLD_VALUE && upValue4 > THRESHOLD_VALUE;

        boolean UP_DIAGONAL_ONE = upValue1 > THRESHOLD_VALUE || upValue3 > THRESHOLD_VALUE
        || upValue2 > THRESHOLD_VALUE || upValue4 > THRESHOLD_VALUE;


         LEFT_DIAGONAL_BOTH || RIGHT_DIAGONAL_BOTH || UP_DIAGONAL_BOTH
*/

}
