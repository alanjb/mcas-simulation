import mcas.main.Mcas;
import mcas.main.McasTimer;
import mcas.main.McasTimerFake;
import mcas.main.TestTimer;
import org.junit.Test;

public class MCASTest {

    @Test
    public void test_D3(){
        /*
         * TT
         * TF
         * FT
         * */

        Mcas m = new Mcas();



    }

    @Test
    public void test_D4(){
        Mcas m = new Mcas();

        /*
        * (TTT)
        * (TTF)
        * (TFT)
        * TFF
        * FTT
        * FTF
        * FFT
        * */

        //FFF where INACTIVE, !autopilotOn && !flapsDown (starting point) to get to ARMED
        m.trim(false, false, 22);

        //FFF where ARMED, !autopilotOn && !flapsDown to get to ACTIVE
        m.trim(false, false, 22);

        //TTT, TFT; -- TTF where ACTIVE, autopilotOn,
        m.trim(true, false, 22);
    }

    @Test
    public void test_D5(){
        Mcas m = new Mcas();

        m.trim(false, false, 22); //FF where INACTIVE, aoA > Threshold
        m.trim(false, false, 5); // TT where ACTIVE, aoA <= Threshold
    }

    @Test
    public void test_D6(){
        //1. Reduce activation interval time, create a delay >= interval before isExpired() is called.
        //2. call1 reaches D3 and calls timer.set(),
        long time = 5;
        McasTimerFake fakeTimer = McasTimer.createFakeMcasTimer(time);
        Mcas m = new Mcas(fakeTimer);
        TestTimer t = new TestTimer(m);

        m.trim(false, false, 25);
        t.start();
    }
}