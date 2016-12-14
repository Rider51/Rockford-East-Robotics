package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.Disabled;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
        import com.qualcomm.robotcore.hardware.Servo;
        import com.qualcomm.robotcore.util.ElapsedTime;

        @TeleOp(name="Test", group="Iterative Opmode")

/**
 * Created by Corey- Team 8708 E-Rabotics on 12/13/2016.
 */
public class TEST extends LinearOpMode {

            //declare
            static final double     WHITE_THRESHOLD = 0.03;  // spans between 0.1 - 0.5 from dark to light
            OpticalDistanceSensor lightSensor;              // Alternative MR ODS sensor
            private ElapsedTime runtime = new ElapsedTime();

            @Override
            public void runOpMode() throws InterruptedException {

                //find them on hardware map
                lightSensor = hardwareMap.opticalDistanceSensor.get("sensor_ods");

                // turn on LED of light sensor.
                lightSensor.enableLed(true);

                while (!(isStarted() || isStopRequested())) {
                    // Display the light level while we are waiting to start
                    telemetry.addData("Light Level", lightSensor.getLightDetected());
                    telemetry.update();
                    idle();
                }

                waitForStart();
                runtime.reset();

                while (opModeIsActive() && (lightSensor.getLightDetected() < WHITE_THRESHOLD)) {

                    // Display the light level while we are looking for the line
                    telemetry.addData("Light Level",  lightSensor.getLightDetected());
                    telemetry.update();
                }
            }
        }

//black-ish - light level: 9.77
//white-ish - light level: 0.17
//red-ish - light level: 0.12
//blue-ish - light level: 0.04