import mcas.main.Mcas;
import mcas.main.McasTimer;
import mcas.main.McasTimerFake;
import mcas.main.TestTimer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MCASTest {

    @Test
    public void test_1(){

//        a	state == State.INACTIVE
//        b	state == State.ARMED
//        c	state == State.ACTIVE
//        d	autopilotOn
//        e	flapsDown
//        f	angleOfAttack > AOA_THRESHOLD
//        g	timer.isExpired ( )

        Mcas m = new Mcas();

        //TFFFFFF
        Mcas.Command command = m.trim(false, false, 2); //Covers T

        assertEquals(m.getState(), Mcas.State.ARMED);
        assertEquals(command, Mcas.Command.NONE);

        //FTFTFFF
        Mcas.Command command2 = m.trim(true, false, 2);
        assertEquals(m.getState(), Mcas.State.INACTIVE);
        assertEquals(command, Mcas.Command.NONE);
    }

    @Test
    public void test_2(){
//        a	state == State.INACTIVE
//        b	state == State.ARMED
//        c	state == State.ACTIVE
//        d	autopilotOn
//        e	flapsDown
//        f	angleOfAttack > AOA_THRESHOLD
//        g	timer.isExpired ( )

        Mcas m = new Mcas();

        //TFFFFTF
        Mcas.Command command = m.trim(false, false, 22); //D1

        assertEquals(m.getState(), Mcas.State.ARMED);
        assertEquals(command, Mcas.Command.NONE);

        //FTFFFTF
        Mcas.Command command2 = m.trim(false, false, 22);//D3
//
////        assertEquals(m.getState(), Mcas.State.ACTIVE);
////        assertEquals(command2, Mcas.Command.DOWN);
//
//        //FFTTFTF
        Mcas.Command command3 = m.trim(true, false, 22);

    }

    @Test
    public void test_3(){
//        a	state == State.INACTIVE
//        b	state == State.ARMED
//        c	state == State.ACTIVE
//        d	autopilotOn
//        e	flapsDown
//        f	angleOfAttack > AOA_THRESHOLD
//        g	timer.isExpired ( )

        Mcas m = new Mcas();

        //TFFFFTF
        Mcas.Command command = m.trim(false, false, 22);

        assertEquals(command, Mcas.Command.DOWN);
        assertEquals(m.getState(), Mcas.State.ACTIVE);

        Mcas.Command command2 = m.trim(false, false, 5); // TT where ACTIVE, aoA <= Threshold - D5

        assertEquals(command2, Mcas.Command.NONE);
        assertEquals(m.getState(), Mcas.State.ARMED);

    }

    @Test
    public void test_4() throws InterruptedException { //FFTFTFTT

//        a	state == State.INACTIVE
//        b	state == State.ARMED
//        c	state == State.ACTIVE
//        d	autopilotOn
//        e	flapsDown
//        f	angleOfAttack > AOA_THRESHOLD
//        g	timer.isExpired ( )


        //1. Reduce activation interval time, create a delay >= interval before isExpired() is called.
        //2. Reduce activation interval time, call1 reaches D3 and calls timer.set(), delay time for call2 and will reach isExpired and execute D6

        //abcdefg
        //FFTFTFTT

        /*
        * TTT
        * TTF
        * TFT
        * FTT
        * */
        Mcas.Command command;
        Mcas.Command command2;
        long time = 500;
        McasTimerFake fakeTimer = McasTimer.createFakeMcasTimer(time);
        Mcas m = new Mcas(fakeTimer);

        command = m.trim(false, false, 25);

        assertEquals(m.getState(), Mcas.State.ARMED);
        assertEquals(command, Mcas.Command.DOWN);

        Thread.sleep(5000);
        command2 = m.trim(false, false, 25);

        assertTrue(fakeTimer.isExpired());
        assertEquals(m.getState(), Mcas.State.ACTIVE);
        assertEquals(command2, Mcas.Command.DOWN);
    }
}