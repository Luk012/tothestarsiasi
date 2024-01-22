package org.firstinspires.ftc.teamcode.system_controllers;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.globals.robotMap;

@Config
public class rightLatch_Controller{

    public enum rightLatchStatus
    {
        INITIALIZE,
        OPEN,
        CLOSE,
    }

    public rightLatch_Controller()
    {
        CS = rightLatchStatus.INITIALIZE;
                PS = rightLatchStatus.INITIALIZE;
    }

    public static rightLatchStatus CS = rightLatchStatus.INITIALIZE, PS = rightLatchStatus.INITIALIZE;

    public static double init = 0;
    public static double open = 0;
    public static double close = 1;

    public void update(robotMap r)
    {
        if(CS != PS || CS == rightLatchStatus.INITIALIZE)
        {
            switch (CS)
            {
                case INITIALIZE:
                {
                    r.right_latch.setPosition(init);
                    break;
                }

                case OPEN:
                {
                    r.right_latch.setPosition(open);
                    break;
                }

                case CLOSE:
                {
                    r.right_latch.setPosition(close);
                    break;
                }
            }

            PS = CS;
        }
    }
}