import mcas.main.Mcas;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MCASTest {
    Mcas m;

    @Before
    public void setUp(){
        m = new Mcas();
    }

    @Test //FTFTFF*
    public void testMcas1(){
        /*
        * a	state == State.INACTIVE
          b	state == State.ARMED
          c	state == State.ACTIVE
          d	autopilotOn
          e	flapsDown
          f	angleOfAttack > AOA_THRESHOLD
          g	timer.isExpired ( )
        * */

        Mcas.Command command;

        command = m.trim(false, false, 1);
        assertEquals(m.getState(), Mcas.State.ARMED);
        assertEquals(command, Mcas.Command.NONE);

        command = m.trim(true, false, 1);
        assertEquals(m.getState(), Mcas.State.INACTIVE);
        assertEquals(command, Mcas.Command.NONE);
    }

    @Test //FFTTF**
    public void testMcas2(){
        /*
        * a	state == State.INACTIVE
          b	state == State.ARMED
          c	state == State.ACTIVE
          d	autopilotOn
          e	flapsDown
          f	angleOfAttack > AOA_THRESHOLD
          g	timer.isExpired ( )
        * */
        Mcas.Command command;

        command = m.trim(false, false, 1);
        assertEquals(m.getState(), Mcas.State.ARMED);
        assertEquals(command, Mcas.Command.NONE);

        //D3
        command = m.trim(false, false, 15);
        assertEquals(m.getState(), Mcas.State.ACTIVE);
        assertEquals(command, Mcas.Command.DOWN);

        command = m.trim(true, false, 15);
        assertEquals(m.getState(), Mcas.State.INACTIVE);
        assertEquals(command, Mcas.Command.NONE);
    }

    @Test //FFTFT**
    public void testMcas3(){
        Mcas.Command command;

        command = m.trim(false, false, 1);
        assertEquals(m.getState(), Mcas.State.ARMED);
        assertEquals(command, Mcas.Command.NONE);

        //D3
        command = m.trim(false, false, 15);
        assertEquals(m.getState(), Mcas.State.ACTIVE);
        assertEquals(command, Mcas.Command.DOWN);

        command = m.trim(false, true, 15);
        assertEquals(m.getState(), Mcas.State.INACTIVE);
        assertEquals(command, Mcas.Command.NONE);
    }
}