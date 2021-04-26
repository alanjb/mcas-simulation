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

    @Test
    public void test_D6_FFTFFTT() throws InterruptedException {
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

        //Starter trim - set to ACTIVE
        command = m.trim(false, false, 1111);
        assertEquals(m.getState(), Mcas.State.ACTIVE);
        assertEquals(command, Mcas.Command.DOWN);

        Thread.sleep(2000); //timer expire

        //FFTFFTT - ACTIVE, d,e=false, f,g=true
        command = m.trim(false, false, 1111);
        assertEquals(m.getState(), Mcas.State.ACTIVE);
        assertEquals(command, Mcas.Command.DOWN);
    }

    @Test
    public void test_D4_FFTFFTT() throws InterruptedException {
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

        //Starter trim - set to ACTIVE
        command = m.trim(false, false, 1111);
        assertEquals(m.getState(), Mcas.State.ACTIVE);
        assertEquals(command, Mcas.Command.DOWN);

        //FFTFFTT - ACTIVE, d=true, e=false, f,g=true
        command = m.trim(true, false, 1111);
        assertEquals(m.getState(), Mcas.State.INACTIVE);
        assertEquals(command, Mcas.Command.NONE);
    }


    @Test
    public void test_D6_FFTFFTF() throws InterruptedException {
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

        //Starter trim - set to ACTIVE
        command = m.trim(false, false, 1111);
        assertEquals(m.getState(), Mcas.State.ACTIVE);
        assertEquals(command, Mcas.Command.DOWN);

        //FFTFFTT - ACTIVE, d,e=false, f=true, g=false
        command = m.trim(false, false, 1111);
        assertEquals(m.getState(), Mcas.State.ACTIVE);
        assertEquals(command, Mcas.Command.NONE);
    }

    @Test
    public void test_D6_FFTFFFT() throws InterruptedException {
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


        //can't do  command = m.trim(false, false, 1); as first trim because it will set state to ARMED
        // and if we call trim again with ARMED command = m.trim(false, false, 1) it won't reach D6 in proper state w/ ACTIVE



        //Starter trim - set to ACTIVE
        command = m.trim(false, false, 1111);
        assertEquals(m.getState(), Mcas.State.ACTIVE);
        assertEquals(command, Mcas.Command.DOWN);

        Thread.sleep(2000);

        //FFTFFFT - ARMED, d,e=false, f=false, g=true
        command = m.trim(false, false, 1);
        assertEquals(m.getState(), Mcas.State.ARMED);
        assertEquals(command, Mcas.Command.NONE);

    }

    @Test
    public void test_D6_TFFTFTT() throws InterruptedException { //infeasible for d6
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

        //can't do  command = m.trim(false, false, 1); as first trim because it will set state to ARMED
        // and if we call trim again with ARMED command = m.trim(false, false, 1) it won't reach D6 in proper state w/ ACTIVE


        //Starter trim - set to ACTIVE
        command = m.trim(false, false, 111);
        assertEquals(m.getState(), Mcas.State.ACTIVE);
        assertEquals(command, Mcas.Command.DOWN);

        Thread.sleep(2000);

        //FFTFFFT - ARMED, d,e=false, f=false, g=true
        command = m.trim(false, false, 111);
        assertEquals(m.getState(), Mcas.State.ARMED);
        assertEquals(command, Mcas.Command.NONE);

    }


}