package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Code taken from "https://www.youtube.com/watch?v=FLNnNbS5VJA"
 */
@Autonomous(name = "VuforiaV2")
public class VuforiaV2 extends LinearOpMode {

    VuforiaLocalizer vuforiaLocalizer;
    VuforiaLocalizer.Parameters parameters;
    VuforiaTrackables visionTargets;
    VuforiaTrackable target;
    VuforiaTrackableDefaultListener listener;

    OpenGLMatrix lastKnownLocation;
    OpenGLMatrix phoneLocaiton;

    public static final  String VUFORIA_KEY =  "AWnApl7/////AAAAGVr6GA1CAU6Vn1CB77mswS0C9AekP74w6y/nT5rSA9BC1K/fQ77n17VUUqAQww+WnjL6jrLYv1dpCmxqFIJTn9oZgPBAeityUJ0ocLoMHUuvyIhRp/lzPXK+OhQCySDDaKAwvm8VnO9D0Cn3Xg7xTkmIFuwt2eDTqEMVTY0lDcwztY8UbDLzt0tDgc3w9Md2ZQo3QqY2xWPHwJRiBZR3atjaOvasQtqwnncXyEUPmZLhtDlxYsgd/GPuZct/MsBlIeD+PELnGf9tIeBymNVpcCsLF3iXcSROMzs8kUfyzTRrw5bBWPaulTYIyC/YrOHZapWJez1H0b1Lu0A9jf1Cpz2Pv/kmmfmd4T+9yIt+Z+PC";


    public void runOpMode() throws InterruptedException
    {
        setVuforia();

        lastKnownLocation = createMatrix(0, 0, 0, 0, 0, 0);


        waitForStart();

        visionTargets.activate();


        while(opModeIsActive())
        {
            OpenGLMatrix latestLocation = listener.getUpdatedRobotLocation();

            if(latestLocation != null)
                lastKnownLocation = latestLocation;

            telemetry.addData("Tracking " + target.getName(), listener.isVisible() );
            telemetry.addData("Last Known Location", formatMatrix(lastKnownLocation));


            telemetry.update();
            idle();

        }
    }

    public void  setVuforia()
    {
        parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforiaLocalizer = ClassFactory.createVuforiaLocalizer(parameters);

        visionTargets = vuforiaLocalizer.loadTrackablesFromAsset("FTC_2016-17");

        target = visionTargets.get(0); //Corresponds to the order of targets in FTC_2016-17.xml
        target.setName("Wheels Target");
        target.setLocation(createMatrix(0, 500, 0, 90, 0, 90));

        phoneLocaiton = createMatrix(0, 225, 0, 90, 0,0);

        listener = (VuforiaTrackableDefaultListener) target.getListener();
        listener.setPhoneInformation(phoneLocaiton, parameters.cameraDirection);

    }
    public OpenGLMatrix createMatrix(float x, float y, float z, float u, float v, float w )
    {
        return OpenGLMatrix.translation(x, y, z).
                multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES,  u, v, w));

    }

    public String formatMatrix(OpenGLMatrix matrix)

    {
        return matrix.formatAsTransform();
    }

}