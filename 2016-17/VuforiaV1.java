package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Code taken from "https://www.youtube.com/watch?v=2z-o9Ts8XoE"
 */
@Autonomous (name = "VuforiaV1")
public class VuforiaV1 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        // UNTESTED - Removing "R.id.cameraMonitorViewId' will disable camera screen -- Will Save Battery
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        // Replace "FRONT" with "BACK" for back camera use.
        params.vuforiaLicenseKey = "AWnApl7/////AAAAGVr6GA1CAU6Vn1CB77mswS0C9AekP74w6y/nT5rSA9BC1K/fQ77n17VUUqAQww+WnjL6jrLYv1dpCmxqFIJTn9oZgPBAeityUJ0ocLoMHUuvyIhRp/lzPXK+OhQCySDDaKAwvm8VnO9D0Cn3Xg7xTkmIFuwt2eDTqEMVTY0lDcwztY8UbDLzt0tDgc3w9Md2ZQo3QqY2xWPHwJRiBZR3atjaOvasQtqwnncXyEUPmZLhtDlxYsgd/GPuZct/MsBlIeD+PELnGf9tIeBymNVpcCsLF3iXcSROMzs8kUfyzTRrw5bBWPaulTYIyC/YrOHZapWJez1H0b1Lu0A9jf1Cpz2Pv/kmmfmd4T+9yIt+Z+PC\n";
        // Key found at https://vuforia.developer.com
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;
        // "AXES" also available as "TEAPOT", "BUILDINGS", and "NONE"

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);
        // Changing "4" can change the amount of pictures Vuforia will locate at once. Amount 1-4

        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Legos");
        beacons.get(3).setName("Gears");

        waitForStart();

        beacons.activate();

        while (opModeIsActive()) {
            for (VuforiaTrackable beac : beacons) {
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beac.getListener()).getPose();

                if(pose != null) {
                    VectorF translation =  pose.getTranslation();

                    telemetry.addData(beac.getName() + "-Translation", translation);

                    double degreesToTurn = Math.toDegrees(Math.atan2(translation.get(1), translation.get(2)));

                    telemetry.addData(beac.getName() + "-Degrees", degreesToTurn);

                }
            }
            telemetry.update();
        }

    }
}

