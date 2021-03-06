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
package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

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

public class FinalProg1_2019 extends LinearOpMode {
     private Servo Servo_L;
     private Servo Servo_R;
     
     //create the capstone dropping servo
     private Servo Servo_Cap;
     //define the motors
     private DcMotor RF;
     private DcMotor RB;
     private DcMotor LF;
     private DcMotor LB;
     
     //declare a linear slide motor
     private DcMotor Slide_motor;

    @Override
    public void runOpMode() {
        
        //map the motors
       Servo_L = hardwareMap.get(Servo.class, "Left Grabber");
       Servo_R = hardwareMap.get(Servo.class, "Right Grabber");
       Servo_Cap = hardwareMap.get(Servo.class, "Servo Cap");
       //RF_motor = hardwareMap.get(DcMotor.class, "Right Front");
       Slide_motor = hardwareMap.get(DcMotor.class, "Slide Motor");
       
       //define the wheels
       RF = hardwareMap.get(DcMotor.class, "Right Front");
       RB = hardwareMap.get(DcMotor.class, "Right Back");
       LF = hardwareMap.get(DcMotor.class, "Left Front");
       LB = hardwareMap.get(DcMotor.class, "Left Back");

       RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       RB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       LB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       
       int ArmLoc;
       int ArmStart;
       ArmStart = Slide_motor.getCurrentPosition();
       
        telemetry.addData("Status", "Initialized");
 
        
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        //declare a variable for the triggers
        double LT;
        double RT;
        
        
        //set a default
        
        LT = 0;
        RT = 0;
        
        //create a servo maximum
        double ServoMax;
        
        //create a default for this too
        ServoMax = 0.7;
        
        //create a servo side offset
        double ServoOffset;
        
        //set a default
        ServoOffset = 0;
        
        //create an arm locking variable
        Boolean ArmLocked;
        
        //set a defualt
        
        ArmLocked = false;
        
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.addData("Servo R", Servo_R.getPosition());
            telemetry.addData("Servo L", Servo_L.getPosition());
            
            
            // TODO test if locked
            if (this.gamepad2.start){
                ArmLocked = true;
                
            }
            else if (this.gamepad2.left_bumper || this.gamepad2.right_bumper) {
                ArmLocked = false;
                
            }
            telemetry.addData("armLocked", ArmLocked);
            
            
            //set LT and RT to the Left and right triggers
            
            if (ArmLocked == false) {
             LT = this.gamepad2.left_trigger*1;
             RT = this.gamepad2.right_trigger*1;
            }
            else {
                RT = 1;
                LT = 1;
            }
            
            //change the servo max
            
            if (this.gamepad2.y && ArmLocked == false) {
                ServoMax = 0.6;
            }
            else if (this.gamepad2.x && ArmLocked == false) {
                ServoMax = 0.5;
            }
            else if (ArmLocked == false) {
                ServoMax = 0.7;
            }
            
            
            //change the servo side offsets
            
            
            if (ServoMax < 0.7) {
                ServoOffset = this.gamepad2.left_stick_x/5;
            }
            
            else if (ArmLocked == false) {
                ServoOffset = 0;
            }
            
            
            if (LT > ServoMax + ServoOffset) {
                
                LT = ServoMax + ServoOffset;
            }
            
            if (RT > ServoMax - ServoOffset) {
                RT = ServoMax - ServoOffset;
            }
            
            /*
            if (RT > 0.9) {
                RT = 0.9;
            }
            if (LT > 0.1) {
                LT = 0.1;
            }
            */
            //overide if locked
            
            
            
            //set the servo positions
            Servo_L.setPosition((LT*2));
            Servo_R.setPosition((1 - RT*2));
            
            //set the Capstone Servo position
            if(this.gamepad2.x){
            Servo_Cap.setPosition(10);
            } else if(this.gamepad2.b) {
                Servo_Cap.setPosition(0);
            }
            
           //move the motors
            //RF_motor.setPower(this.gamepad1.right_stick_y);
           /*
            ArmLoc = Slide_motor.getCurrentPosition();
            telemetry.addData("ArmLoc", ArmLoc);
            if (ArmLoc >= ArmStart) {
            Slide_motor.setPower((Math.pow(this.gamepad2.right_stick_y, 2))* -(this.gamepad2.right_stick_y / Math.abs(this.gamepad2.right_stick_y)));
            } else {
                Slide_motor.setPower(-1);
            }
            */
                        Slide_motor.setPower((Math.pow(this.gamepad2.right_stick_y, 2))* -(this.gamepad2.right_stick_y / Math.abs(this.gamepad2.right_stick_y)));

            //ArmLoc += (Math.pow(this.gamepad2.right_stick_y, 2))* -(this.gamepad2.right_stick_y / Math.abs(this.gamepad2.right_stick_y));
            //ArmLoc += RT;
            
            
            //driving code goes here
            //you need to do the driving code JEFFRY
            //I'm doing it ALLAN
            double speedModifier = (1- ((this.gamepad1.right_trigger + this.gamepad1.left_trigger)/1.5));
            double gamepad1LX = this.gamepad1.left_stick_x * speedModifier;
            double gamepad1LY = this.gamepad1.left_stick_y * speedModifier;
            double gamepad1RX = this.gamepad1.right_stick_x * speedModifier;
            
            
            LF.setPower((-(gamepad1LX + -gamepad1LY)) + -gamepad1RX);
            RF.setPower((-gamepad1LY - gamepad1LX) + -gamepad1RX);
            RB.setPower(((gamepad1LX + -gamepad1LY)) + -gamepad1RX);
            LB.setPower((-(-gamepad1LY - gamepad1LX)) + -gamepad1RX);
            
            
            
            
            telemetry.update();

        }
    }
}
