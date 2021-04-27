import mcas.main.Mcas;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class McasTest_Final {
    Mcas m;

    @Before
    public void setUp(){
        m = new Mcas();
    }

    @Test
    public void test_D1_TFFFTFT() throws InterruptedException {
        /*
        //  *
              a	state == State.INACTIVE
        //    b	state == State.ARMED
        //    c	state == State.ACTIVE
        //    d	autopilotOn
        //    e	flapsDown
        //    f	angleOfAttack > AOA_THRESHOLD
        //    g	timer.isExpired ( )
        //
        * */

        Mcas.Command command;

        Thread.sleep(2000); //expire timer

        //TFFFTFT
        //Start - INACTIVE, flapsDown, timer expired
        command = m.trim(false, true, 1);
        assertEquals(m.getState(), Mcas.State.INACTIVE);
        assertEquals(command, Mcas.Command.NONE);
    }

    @Test
    public void test_D1_TFFFFFT() throws InterruptedException {
        /*
        //  *
              a	state == State.INACTIVE
        //    b	state == State.ARMED
        //    c	state == State.ACTIVE
        //    d	autopilotOn
        //    e	flapsDown
        //    f	angleOfAttack > AOA_THRESHOLD
        //    g	timer.isExpired ( )
        //
        * */

        Mcas.Command command;

        Thread.sleep(2000); //expire timer

        //TFFFFFT
        //Start - INACTIVE, timer expired
        command = m.trim(false, false, 1);
        assertEquals(m.getState(), Mcas.State.ARMED);
        assertEquals(command, Mcas.Command.NONE);
    }

    @Test
    public void test_D2_FTFTFTT() throws InterruptedException {
        /*
        //  *
              a	state == State.INACTIVE
        //    b	state == State.ARMED
        //    c	state == State.ACTIVE
        //    d	autopilotOn
        //    e	flapsDown
        //    f	angleOfAttack > AOA_THRESHOLD
        //    g	timer.isExpired ( )
        //
        * */

        Mcas.Command command;

        Thread.sleep(2000); //expire timer

        //Set to ARMED
        command = m.trim(false, false, 1);
        assertEquals(m.getState(), Mcas.State.ARMED);
        assertEquals(command, Mcas.Command.NONE);

        //FTFTFTT
        //ARMED, autopilotOn, aOA >, timer expired
        command = m.trim(true, false, 1111);
        assertEquals(m.getState(), Mcas.State.INACTIVE);
        assertEquals(command, Mcas.Command.NONE);
    }

    @Test
    public void test_D2_FTFFTFT() throws InterruptedException {
        /*
        //  *
              a	state == State.INACTIVE
        //    b	state == State.ARMED
        //    c	state == State.ACTIVE
        //    d	autopilotOn
        //    e	flapsDown
        //    f	angleOfAttack > AOA_THRESHOLD
        //    g	timer.isExpired ( )
        //
        * */

        Mcas.Command command;

        //Set to ARMED
        command = m.trim(false, false, 1);
        assertEquals(m.getState(), Mcas.State.ARMED);
        assertEquals(command, Mcas.Command.NONE);

        Thread.sleep(2000); //expire timer

        //FTFFTFT
        //ARMED, autopilotOn, aOA >, timer expired
        command = m.trim(false, true, 1);
        assertEquals(m.getState(), Mcas.State.INACTIVE);
        assertEquals(command, Mcas.Command.NONE);
    }

    @Test
    public void test_D3_FTFFFTT() throws InterruptedException {
        /*
        //  *
              a	state == State.INACTIVE
        //    b	state == State.ARMED
        //    c	state == State.ACTIVE
        //    d	autopilotOn
        //    e	flapsDown
        //    f	angleOfAttack > AOA_THRESHOLD
        //    g	timer.isExpired ( )
        //
        * */

        Mcas.Command command;

        //Set to ARMED
        command = m.trim(false, false, 1);
        assertEquals(m.getState(), Mcas.State.ARMED);
        assertEquals(command, Mcas.Command.NONE);

        Thread.sleep(2000); //expire timer

        //FTFFFTT
        //ARMED, aOA >, timer expired
        command = m.trim(false, false, 111);
        assertEquals(m.getState(), Mcas.State.ACTIVE);
        assertEquals(command, Mcas.Command.DOWN);
    }

    @Test
    public void test_D4_FFTTFFT() throws InterruptedException {
        /*
        //  *
              a	state == State.INACTIVE
        //    b	state == State.ARMED
        //    c	state == State.ACTIVE
        //    d	autopilotOn
        //    e	flapsDown
        //    f	angleOfAttack > AOA_THRESHOLD
        //    g	timer.isExpired ( )
        //
        * */

        Mcas.Command command;

        //Set to ARMED
        command = m.trim(false, false, 1111);
        assertEquals(m.getState(), Mcas.State.ACTIVE);
        assertEquals(command, Mcas.Command.DOWN);

        Thread.sleep(2000); //expire timer

        //FFTTFFT
        //ACTIVE, autopilotOn, timer expired
        command = m.trim(true, false, 1111);
        assertEquals(m.getState(), Mcas.State.INACTIVE);
        assertEquals(command, Mcas.Command.NONE);
    }
}