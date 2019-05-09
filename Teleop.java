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

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Teleop", group="teleop")

public class Teleop extends OpMode
{
    // Declare OpMode members.
    ElapsedTime runtime = new ElapsedTime();

    //DriveTrain
    DcMotor left_motor = null;
    DcMotor right_motor = null;

    //Glyph Grabber
    Servo clawLeft;
    Servo clawRight;
    Servo JewelTip;
    Servo JewelExtend;
    DcMotor claw;

    ColorSensor colorSensor;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        //Drive Train
        left_motor  = hardwareMap.get(DcMotor.class, "left_motor");
        right_motor = hardwareMap.get(DcMotor.class, "right_motor");

        //Glyph Grabber
        claw = hardwareMap.get(DcMotor.class, "claw");
        clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        clawRight = hardwareMap.get(Servo.class, "clawRight");

        //Set Motor Directions
        left_motor.setDirection(DcMotor.Direction.REVERSE);
        right_motor.setDirection(DcMotor.Direction.FORWARD);
        claw.setDirection(DcMotor.Direction.FORWARD);

        JewelExtend = hardwareMap.servo.get("servotextend");
        JewelTip = hardwareMap.servo.get("servotip");
        colorSensor = hardwareMap.get(ColorSensor.class, "colorsensor");
        colorSensor.enableLed(false);

        JewelExtend.setPosition(0.5);

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Status", "Servo position is:" + clawLeft.getPosition());
        telemetry.addData("Status", "Servo position is:" + clawRight.getPosition());
    }


    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }


    @Override
    public void loop() {

        double leftPower;
        double rightPower;

        /* POV Mode uses left stick to go forward, and right stick to turn.

        Driving Code:
         */
        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.right_stick_x / 2;
        leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
        rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;
        left_motor.setPower(leftPower);
        right_motor.setPower(rightPower);


        //Glyph Z axis motor
        claw.setPower(gamepad2.right_stick_y);

       // if (gamepad1.a){
         //   claw.setPower(0.6);
        //} else if (gamepad1.b){
         //   claw.setPower(-0.6);
        //}

        claw.setPower(0);

        if (gamepad1.x){
            clawRight.setPosition(.20); //Open
            clawLeft.setPosition(.80);
        } else if (gamepad1.a){
            clawRight.setPosition(.10); //Closed
            clawLeft.setPosition(.95);
        }

        if (gamepad1.y){
            claw.setPower(0.5);
        } else if (gamepad1.b) {
            claw.setPower(-0.5);
        } else {
            claw.setPower(0);
        }

        if (gamepad1.dpad_up){
            JewelExtend.setPosition(0);
        } else if (gamepad1.dpad_down) {
            JewelExtend.setPosition(1);
        } else {
            JewelExtend.setPosition(0.5);
        }


       /* Glyph Grabber:
       if hold/click x on gamepad2, increase leftPosition and decrease rightPosition by increment
        (currently at 0.01)- then tell the servo to go to that position.
       servo to go to that position.

        if (gamepad2.left_trigger > 0.1 || gamepad1.left_trigger > 0.1){
            leftPosition += INCREMENT ; //LeftClose
        }
        if (gamepad2.right_trigger > 0.1 || gamepad1.right_trigger  > 0.04) {
            rightPosition -= INCREMENT  ; //RightClose
        }

        if (gamepad2.left_bumper || gamepad1.left_bumper){
            leftPosition -= INCREMENT ; //LeftOPen
        }
        if (gamepad2.right_bumper || gamepad1.right_bumper) {
            rightPosition += INCREMENT ; //RightOpen
        }

        if (leftPosition < leftMin ){
            leftPosition += INCREMENT;
        } else if (leftPosition > leftMax){
            leftPosition -= INCREMENT;
        }
 */


        //clawRight.setPosition(rightPosition); //goto servos positions
        //clawLeft.setPosition(leftPosition);
        JewelTip.setPosition(1);
        JewelExtend.setPosition(0.5);


        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.addData("Status", "LeftServo position is:" + clawLeft.getPosition());
        telemetry.addData("Status", "RightServo position is:" + clawRight.getPosition());


    }


    @Override
    public void stop() {
    }

}
