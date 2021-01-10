/*
Copyright 2018 FIRST Tech Challenge Team 10603

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

//Based on Test4.java
package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Remove a @Disabled the on the next line or two (if present) to add this opmode to the Driver Station OpMode list,
 * or add a @Disabled annotation to prevent this OpMode from being added to the Driver Station
 */
@TeleOp

public class AutoTest_12042020 extends LinearOpMode {


    //create the capstone dropping servo
    private Servo Servo_Cap;
    //define the motors
    private DcMotor RF;
    private DcMotor RB;
    private DcMotor LF;
    private DcMotor LB;

    //declare a linear slide motor
    private DcMotor Slide_motor;

    //declare a shooter spinner motor
    private DcMotor Shooter_motor;

    //Declare the collector wheel motor(the M stands for motor)
    private DcMotor CollectorM;

    //Declare the unknown Servo
    private Servo Unknown_Servo;
    @Override
    public void runOpMode() {

        //map the motors
        Servo_Cap = hardwareMap.get(Servo.class, "Servo Cap");
        //RF_motor = hardwareMap.get(DcMotor.class, "Right Front");
        Slide_motor = hardwareMap.get(DcMotor.class, "Slide Motor");

        //define the wheels
        RF = hardwareMap.get(DcMotor.class, "Right Front");
        RB = hardwareMap.get(DcMotor.class, "Right Back");
        LF = hardwareMap.get(DcMotor.class, "Left Front");
        LB = hardwareMap.get(DcMotor.class, "Left Back");
        //set the wheels to use encoders
        RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        int ArmLoc;
        int ArmStart;
        ArmStart = Slide_motor.getCurrentPosition();


        //Hardwaremap the shooter motor
        Shooter_motor = hardwareMap.get(DcMotor.class,"Shooter motor");
        //set the shooter motor to use encoders
        Shooter_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //Hardwaremap the unknown servo
        Unknown_Servo = hardwareMap.get(Servo.class, "Unknown Servo");

        telemetry.addData("Status", "Initialized");

        //Hardwaremap CollectorM
        CollectorM = hardwareMap.get(DcMotor.class,"CollectorM");
        //set CollectorM to use encoders
        CollectorM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        //declare a variable for the triggers
        double Shooter_Power;
        Shooter_Power = 0;

        //Control of collectorM
        if(this.gamepad2.y) {
            CollectorM.setPower(1);
        } else if(this.gamepad2.a) {
            CollectorM.setPower(-1);
        } else {
            CollectorM.setPower(0);
        }

        if(this.gamepad2.x) {
            Unknown_Servo.setPosition(45);
        } else if(this.gamepad2.b) {
            Unknown_Servo.setPosition(0);
        }

        setdirectionalpower(0.0, 0.0, 0.0, LF, RF, RB, LB);





    }

    static void setdirectionalpower(double X_Movement, double Y_Movement, double Rotation, DcMotor LF, DcMotor RF, DcMotor RB, DcMotor LB) {
        LF.setPower((-(X_Movement + -Y_Movement)) + -Rotation);
        RF.setPower((-Y_Movement - X_Movement) + -Rotation);
        RB.setPower(((X_Movement + -Y_Movement)) + -Rotation);
        LB.setPower((-(-Y_Movement - X_Movement)) + -Rotation);
    }
}