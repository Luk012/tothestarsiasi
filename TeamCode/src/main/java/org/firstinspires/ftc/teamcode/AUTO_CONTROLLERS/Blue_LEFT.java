package org.firstinspires.ftc.teamcode.AUTO_CONTROLLERS;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.globals.InverseKinematics;
import org.firstinspires.ftc.teamcode.globals.robotMap;
import org.firstinspires.ftc.teamcode.system_controllers.fourBar_Controller;
import org.firstinspires.ftc.teamcode.system_controllers.lift_Controller;
import org.firstinspires.ftc.teamcode.system_controllers.storageAngle_Controller;
import org.firstinspires.ftc.teamcode.system_controllers.storage_Controller;

import java.io.BufferedReader;


public class Blue_LEFT {
    public enum autoControllerStatus
    {
        NOTHING,
        SCORE_PRELOAD,
        SCORE_PRELOAD_LIFT,
        SCORE_PRELOAD_SYSTEMS,
        INTER_COLLECT,
        SCORE_PRELOAD_DONE,
        COLLECT,
        COLLECT_SYSTEMS,
        COLLECT_DONE,
        SCORE,
        INTER_SCORE,
        INTER_SCORE_PRELAOD,
        SCORE_LIFT,
        SCORE_SYSTEMS,
        SCORE_DONE,

    }
    public static autoControllerStatus CurrentStatus = autoControllerStatus.NOTHING, PreviousStatus = autoControllerStatus.NOTHING;

    ElapsedTime pruple_prelaod = new ElapsedTime();
    ElapsedTime yellow_preload = new ElapsedTime();
    ElapsedTime scorev2 = new ElapsedTime();
    ElapsedTime timer = new ElapsedTime();
    ElapsedTime inter = new ElapsedTime();

    public void update(robotMap r, lift_Controller lift, InverseKinematics ik, fourBar_Controller fourbar, storage_Controller storage, storageAngle_Controller storageAngle)
    {
        switch (CurrentStatus)
        {


            case SCORE_PRELOAD:
            {
                ik.updateServoPositions();
                CurrentStatus = autoControllerStatus.INTER_SCORE_PRELAOD;
                break;

            }

            case INTER_SCORE_PRELAOD:
        {
            fourbar.CS = fourBar_Controller.fourbarStatus.INTER;
            storage.CS = storage_Controller.storageStatus.INTER;
            inter.reset();
            CurrentStatus = autoControllerStatus.SCORE_PRELOAD_LIFT;
            break;
        }

            case SCORE_PRELOAD_LIFT:
            {
                if(inter.seconds() > 0.1)
                {
                    lift.pid =1;
                    lift.CS = lift_Controller.liftStatus.UP;
                    scorev2.reset();
                    CurrentStatus = autoControllerStatus.SCORE_PRELOAD_SYSTEMS;
                }
                break;
            }

            case SCORE_PRELOAD_SYSTEMS:
            {
                if(scorev2.seconds() > 0.2)
                {
                    fourbar.CS = fourBar_Controller.fourbarStatus.IK;

                }
                if(scorev2.seconds() > 0.4)
                {
                    storage.CS = storage_Controller.storageStatus.IK;
                }
                if(scorev2.seconds() > 0.8)
                {
                    CurrentStatus = autoControllerStatus.SCORE_PRELOAD_DONE;
                }
                break;
            }

            case COLLECT:
            {
                storageAngle.rotation_i = 2;
                lift.pid= 0;
                lift.CS = lift_Controller.liftStatus.DOWN;
                scorev2.reset();
                CurrentStatus = autoControllerStatus.INTER_COLLECT;
                break;
            }

            case INTER_COLLECT:
            {
                if(scorev2.seconds() > 0.3)
                { fourbar.CS = fourBar_Controller.fourbarStatus.INTER;
                storage.CS = storage_Controller.storageStatus.INTER;
                inter.reset();
                CurrentStatus = autoControllerStatus.COLLECT_SYSTEMS;}
                break;
            }

    case COLLECT_SYSTEMS:
    {
        if(inter.seconds() > 0.35) {
            fourbar.CS = fourBar_Controller.fourbarStatus.COLLECT;
            storage.CS = storage_Controller.storageStatus.COLLECT;
        }

        if(inter.seconds() > 0.7)
        {
            CurrentStatus = autoControllerStatus.COLLECT_DONE;
        }
        break;
    }

            case SCORE:
            {
                ik.updateServoPositions();
                inter.reset();
                CurrentStatus = autoControllerStatus.INTER_SCORE;
                break;
            }

            case INTER_SCORE:
            {fourbar.CS = fourBar_Controller.fourbarStatus.INTER;
                storage.CS = storage_Controller.storageStatus.INTER;
                inter.reset();
                CurrentStatus = autoControllerStatus.SCORE_LIFT;
                break;
            }

            case SCORE_LIFT:
            {
                if(inter.seconds() > 0.1)
                {
                    lift.pid =1;
                    lift.CS = lift_Controller.liftStatus.UP;
                    scorev2.reset();
                    CurrentStatus = autoControllerStatus.SCORE_SYSTEMS;
                }
                break;
            }

            case SCORE_SYSTEMS:
            {
                if(scorev2.seconds() > 0.2)
                {
                    fourbar.CS = fourBar_Controller.fourbarStatus.IK;

                }
                if(scorev2.seconds() > 0.4)
                {
                    storage.CS = storage_Controller.storageStatus.IK;
                }
                if(scorev2.seconds() > 0.8)
                {
                    CurrentStatus = autoControllerStatus.SCORE_DONE;
                }
                break;
            }

        }
        PreviousStatus = CurrentStatus;
    }
}

