//package edu.gmu.swe637.assignment6Test

import mcas.main.Mcas;
import mcas.main.McasTimer;
import mcas.main.McasTimerFake;
import mcas.main.TestTimer;
//import org.junit.Before;
//import org.junit.Test;
import org.junit.*;

import edu.gmu.swe637.assignment6.Mcas.Command;
import edu.gmu.swe637.assignment6.Mcas.State;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

//import edu.gmu.swe637.assignment6.Mcas;
//import swe673spring2021.src.main.java.edu.gmu.swe637.assignment6.Mcas.Command;
//import edu.gmu.swe637.assignment6.Mcas.State;
//import edu.gmu.swe637.assignment6.McasTimer;


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
    
    
    
    
    


    	Mcas mcas;
    	McasTimer timer;
    	
    	@Before public void setUp() { 
    		mcas = new Mcas();
    		timer = new McasTimer(0);
    	}

//    	Note: Exactly one of the first 3 predicates must be true for all tests.
    //
//    	a: state == State.INACTIVE
//    	b: state == State.ARMED
//    	c: state == State.ACTIVE
//    	d: autopilotOn
//    	e: flapsDown
//    	f: angleOfAttack > AOA_THRESHOLD
//    	g: timer.isExpired ( )
    	
    	@Test
    	public void mcasTestFFTFFTT() {			// FFTFFTT (ultimately)
    		// Ultimately: Test when state is ACTIVE, 
    		// autopilot is off, flaps are up,
    		// AOA is over threshold and timer is expired
    		
    		// At start, state is INACTIVE, autopilot is off, flaps are up,
    		// AOA is over threshold and timer is expired (TFFFFTT)
    		
    		// Initialize local variable to check command
    		Command command = Command.NONE;

    		// In one call to trim() method), change
    		// from INACTIVE to ARMED and then to ACTIVE.
    		
    		// Start with TFFFFTT (state is INACTIVE) 
    		// After D1 will have FTFFFTT (state is ARMED)
    		// After D3 will have FFTFFTT (state is ACTIVE)
    		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1);
    		
    		// Check state
    		assertEquals(State.ACTIVE, mcas.getState());
    		
    //?		// Check command
    //?		assertEquals(Command.DOWN, command);
    		
    		// wait longer than fake interval
    		try {
    			Thread.sleep(timer.getFakeInterval() + 1); // wait (in milliseconds)
    		} catch (InterruptedException e) { 
    			System.err.println("Caught InterruptedException: " + e.getMessage());};
    			
    		// Test D6 with FFTFFTT 
    		// State is ACTIVE, autopilot is off, flaps are up,
    		// AOA is over threshold and timer is expired
    		command = Command.NONE;
    		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1);
    		assertEquals(Command.DOWN, command);
    		assertEquals(State.ACTIVE, mcas.getState());
    	}	
    	
    	@Test
    	public void mcasTestFFTFFFT() {			// FFTFFFT (ultimately)
    		// Ultimately: Test when state is ACTIVE, 
    		// autopilot is off, flaps are up,
    		// AOA is under threshold and timer is expired

    		// At start, state is INACTIVE, autopilot is off, flaps are up,
    		// AOA is over threshold and timer is expired (TFFFFTT)
    		
    		// Initialize local variable to check command
    		Command command = Command.NONE;

    		// In one call to trim() method), change
    		// from INACTIVE to ARMED and then to ACTIVE.
    		
    		// Test p1 with TFFFFTT (state is INACTIVE) 
    		// and then p3 with FTFFFTT (state is ARMED)
    		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1);
    		assertEquals(Command.DOWN, command);
    		assertEquals(State.ACTIVE, mcas.getState());
    		
    		// wait longer than fake interval
    		try {
    			Thread.sleep(timer.getFakeInterval() + 1); // wait (in milliseconds)
    		} catch (InterruptedException e) { 
    			System.err.println("Caught InterruptedException: " + e.getMessage());};
    			
    		// Test p6 with FFTFFFT 
    		// State is ACTIVE, autopilot is off, flaps are up,
    		// AOA is under threshold and timer is expired
    		command = Command.NONE;
    		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD - 1);
    		assertEquals(Command.NONE, command);
    		assertEquals(State.ARMED, mcas.getState());
    	}	

    	
    	@Test
    	public void mcasTestFFTFFTF() {		// FFTFFTF (ultimately)
    		// State is INACTIVE, autopilot is off, flaps are up,
    		// AOA is over threshold and timer is expired
    		
    		// Initialize local variable to check command
    		Command command = Command.NONE;

    		// In one call to trim() method), change
    		// from INACTIVE to ARMED and then to ACTIVE.
    		// Test p1 with TFFFFTT (state is INACTIVE) 
    		// and then p3 with FTFFFTT (state is ARMED)
    		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1);
    		assertEquals(Command.DOWN, command);
    		assertEquals(State.ACTIVE, mcas.getState());
    				
    		// Test p6 with FFTFFTF (state is ACTIVE, timer not expired)
    		command = Command.NONE;
    		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1);
    		assertEquals(Command.NONE, command);
    		assertEquals(State.ACTIVE, mcas.getState());
    	}

    	@Test
    	public void mcasTest___TTF_() {
    		// Initialize local variable to check command
    		Command command = Command.DOWN;
    		command = mcas.trim(true, true, Mcas.AOA_THRESHOLD - 1);
    		assertEquals(Mcas.Command.NONE, command);
    		assertEquals(State.INACTIVE, mcas.getState());
    	}
    
    	Mcas mcas;
    	
    	@Before public void setUp() { 
    		mcas = new Mcas();
    	}


//    	Test requirements: 
//    	{TFFTFTT, TFFFT--, TFFFFF-, FTFFF--, FTFTFT-, FTFFTF-, FFTTFTF, FFTFFTT, FFTFTFT}
    	
//    	1. TFFTFTT 
//    	2. TFFFT-- 
//    	3. TFFFFF- 
//    	4. FTFFF-- 
//    	5. FTFTFT- 
//    	6. FTFFTF- 
//    	7. FFTTFTF 
//    	8. FFTFFTT 
//    	9. FFTFTFT
    	
    	
    	//	Note: Exactly one of the first 3 predicates must be true for each test.
    	//
    	//	a: state == State.INACTIVE
    	//	b: state == State.ARMED
    	//	c: state == State.ACTIVE
    	//	d: autopilotOn
    	//	e: flapsDown
    	//	f: angleOfAttack > AOA_THRESHOLD
    	//	g: timer.isExpired ( )


//    	Note: Exactly one of the first 3 predicates must be true for each test.

//    	a: state == State.INACTIVE
//    	b: state == State.ARMED
//    	c: state == State.ACTIVE
//    	d: autopilotOn
//    	e: flapsDown
//    	f: angleOfAttack > AOA_THRESHOLD
//    	g: timer.isExpired ( )
    		
    	// Test D6/p6 with condition FFTFFTT, in which state is ACTIVE,
    	// autopilot is off, flaps are up, AOA is over threshold 
    	// and timer is expired
    	@Test 
    // 1
    	public void mcasTestD6FFTFFTT() {
    		// At start, state is INACTIVE, autopilot is off, flaps are up,
    		// AOA is over threshold and timer is expired (TFFFFTT)
    		
    		// Initialize local variable to check returned command
    		Command command = Command.NONE;

    		// In one call to trim() method), change state
    		// from INACTIVE to ARMED and then to ACTIVE
    		// Start with TFFFFTT (state is INACTIVE) 
    		// After D1 will have FTFFFTT (state is ARMED)
    		// After D3 will have FFTFFTF (state is ACTIVE timer is not expired)
    		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1.0);
    		
    		// Check state
    		assertEquals(State.ACTIVE, mcas.getState());
    				
    		// wait longer than fake interval for timer to expire
    		try {
    			Thread.sleep(McasTimer.FAKE_INTERVAL + 1); // wait (in milliseconds)
    		} catch (InterruptedException e) { 
    			System.err.println("Caught InterruptedException: " + e.getMessage());};
    			
    		// Condition is now FFTFFTT:
     		// State is ACTIVE, autopilot is off, flaps are up,
    		// AOA is over threshold and timer is expired
    		// Test D6 with this condition
    		command = Command.NONE;
    		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1.0);
    		assertEquals(Command.DOWN, command);
    		assertEquals(State.ACTIVE, mcas.getState());
    	}	
    	
    	
//    	a: state == State.INACTIVE
//    	b: state == State.ARMED
//    	c: state == State.ACTIVE
//    	d: autopilotOn
//    	e: flapsDown
//    	f: angleOfAttack > AOA_THRESHOLD
//    	g: timer.isExpired ( )
    	
    	// Test D6/p6 with condition FFTFFTF in which state is ACTIVE,
    	// autopilot is off, flaps are up, AOA is over threshold 
    	// and timer is not expired
    	@Test 
    // 2
    	public void mcasTestD6FFTFFTF() {
    		// At start, state is INACTIVE, autopilot is off, flaps are up,
    		// AOA is over threshold and timer is expired (TFFFFTT)
    		
    		// Initialize local variable to check returned command
    		Command command = Command.NONE;

    		// In one call to trim() method), change state
    		// from INACTIVE to ARMED and then to ACTIVE
    		// Start with TFFFFTT (state is INACTIVE) 
    		// After D1 will have FTFFFTT (state is ARMED)
    		// After D3 will have FFTFFTF (state is ACTIVE timer is not expired)
    		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1.0);
    		
    		// Check state
    		assertEquals(State.ACTIVE, mcas.getState());

    		// Condition is now FFTFFTF:
     		// State is ACTIVE, autopilot is off, flaps are up,
    		// AOA is over threshold and timer is not expired
    		// Test D6 with this condition
    		command = Command.NONE;
    		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1.0);
    		assertEquals(Command.NONE, command);
    		assertEquals(State.ACTIVE, mcas.getState());
    	}
    	
    	
//    	a: state == State.INACTIVE
//    	b: state == State.ARMED
//    	c: state == State.ACTIVE
//    	d: autopilotOn
//    	e: flapsDown
//    	f: angleOfAttack > AOA_THRESHOLD (12.0)
//    	g: timer.isExpired ( )
    	
//    	trim(autopilotOn, flapsDown, angleOfAttack)
//    	trim(d, e, AOA)
    	
    	// Test D6/p6 with FFTFFFT in which state is ACTIVE,
    	// autopilot is off, flaps are up, AOA is under threshold 
    	// and timer is expired
    	@Test 
    // 3
    	public void mcasTestD6FFTFFFT() {
    		// At start, state is INACTIVE, autopilot is off, flaps are up,
    		// AOA is over threshold and timer is expired (TFFFFTT)
    		
    		// Initialize local variable to check returned command
    		Command command = Command.NONE;

    		// In one call to trim() method), change state
    		// from INACTIVE to ARMED and then to ACTIVE
    		// Start with TFFFFTT (state is INACTIVE) 
    		// After D1 will have FTFFFTT (state is ARMED)
    		// After D3 will have FFTFFTF (state is ACTIVE timer is not expired)
    		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1.0);
    		
    		// Check state
    		assertEquals(State.ACTIVE, mcas.getState());
    				
    		// wait longer than fake interval for timer to expire
    		try {
    			Thread.sleep(McasTimer.FAKE_INTERVAL + 1); // wait (in milliseconds)
    		} catch (InterruptedException e) { 
    			System.err.println("Caught InterruptedException: " + e.getMessage());};
    			
    		// Condition is now FFTFFTT:
     		// State is ACTIVE, autopilot is off, flaps are down,
    		// AOA is under threshold and timer is expired
    		// Test D6 with condition FFTFFFT (set AOA < threshold)
    		command = Command.NONE;
    		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD - 1.0);
    		assertEquals(Command.NONE, command);
    		assertEquals(State.ARMED, mcas.getState());
    	}	
    	

//    	a: state == State.INACTIVE
//    	b: state == State.ARMED
//    	c: state == State.ACTIVE
//    	d: autopilotOn
//    	e: flapsDown
//    	f: angleOfAttack > AOA_THRESHOLD
//    	g: timer.isExpired ( )
    		
    	// Test D6/p6 with condition FFTFFTT, in which state is ACTIVE,
    	// autopilot is off, flaps are up, AOA is over threshold 
    	// and timer is expired
    	@Test 
    // 4
    	public void mcasTestD123456TFFTFTT() { // conditions persist for D1-D6
    		// At start, state is INACTIVE, autopilot is on, flaps are up,
    		// AOA is over threshold and timer is expired (TFFTFTT)
    		
    		// Initialize local variable to check returned command
    		Command command = Command.NONE;

    		// Start with TFFTFTT 
    		// Conditions persist for D1-D6
    		command = mcas.trim(true, false, Mcas.AOA_THRESHOLD + 1.0);
    		
    		// Check state
    		assertEquals(State.INACTIVE, mcas.getState());
    		assertEquals(Command.NONE, command);
    	}	

//    	a: state == State.INACTIVE
//    	b: state == State.ARMED
//    	c: state == State.ACTIVE
//    	d: autopilotOn
//    	e: flapsDown
//    	f: angleOfAttack > AOA_THRESHOLD
//    	g: timer.isExpired ( )
    		
    	// Test D6/p6 with condition FFTFFTT, in which state is ACTIVE,
    	// autopilot is off, flaps are up, AOA is over threshold 
    	// and timer is expired
    	@Test 
    // 5
    	public void mcasTestD5TFFTFFT() { // conditions persist for D1-D6
    		// At start, state is INACTIVE, autopilot is on, flaps are up,
    		// AOA is under threshold and timer is expired (TFFTFFT)
    		
    		// Initialize local variable to check returned command
    		Command command = Command.NONE;

    		// Start with TFFTFFT 
    		// Conditions persist for D1-D6
    		command = mcas.trim(true, false, Mcas.AOA_THRESHOLD - 1.0);
    		
    		// Check state
    		assertEquals(State.INACTIVE, mcas.getState());
    		assertEquals(Command.NONE, command);
    	}	

    	
    	
    	
//    	// Test condition FFTFFFT in which state is ACTIVE,
//    	// autopilot is off, flaps are down, AOA is under threshold 
//    	// and timer is expired
//    	@Test
//    	public void mcasTestFFTFFFT() {
//    		// At start, state is INACTIVE, autopilot is off, flaps are up,
//    		// AOA is over threshold and timer is expired (TFFFFFT)
//    		
//    		// Initialize local variable to check returned command
//    		Command command = Command.NONE;
    //
//    		// In one call to trim() method), change state
//    		// from INACTIVE to ARMED and then to ACTIVE
//    		// Start with TFFFFFT (state is INACTIVE) 
//    		// After D1 will have FTFFFFT (state is ARMED)
//    		// After D3 will have FFTFFFT (state is ACTIVE)
//    		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1);
//    		
//    		// Check state
//    		assertEquals(State.ACTIVE, mcas.getState());
//    				
//    		// wait longer than fake interval for timer to expire
//    		try {
//    			Thread.sleep(McasTimer.FAKE_INTERVAL + 1); // wait (in milliseconds)
//    		} catch (InterruptedException e) { 
//    			System.err.println("Caught InterruptedException: " + e.getMessage());};
//    			
//    		// Condition is now FFTFFFT:
//     		// State is ACTIVE, autopilot is off, flaps are up,
//    		// AOA is under threshold and timer is expired
//    		// Test D6 with this condition
//    		command = Command.NONE;
//    		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD - 1.0);
//    		assertEquals(Command.NONE, command);
//    		assertEquals(State.ARMED, mcas.getState());
//    	}	
    //	
    	// Is the following feasible?
    	
    	// Test condition FTFFFTT in which state is ARMED,
    	// autopilot is off, flaps are up, AOA is over threshold 
    	// and timer is expired
    	@Test
    	public void mcasTestFTFFFTT() {
    		// At start, state is INACTIVE, autopilot is off, flaps are up,
    		// AOA is over threshold and timer is expired (TFFFFTT)
    		
    		// Initialize local variable to check returned command
    		Command command = Command.NONE;

    		// In one call to trim() method), change state
    		// from INACTIVE to ARMED and then to ACTIVE
    		// Start with TFFFFTT (state is INACTIVE) 
    		// After D1 will have FTFFFTT (state is ARMED)
    		// After D3 will have FFTFFTF (state is ACTIVE)
    		// Note: this test checks INDIRECTLY for the intermediate state by checking the final result.
    		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1);
    		
    		// Check command and state
    		assertEquals(Command.DOWN, command);
    		assertEquals(State.ACTIVE, mcas.getState());
    	}	

    	
    	// Is the following feasible?
    	
    	// Test condition TFFFFTT in which state is INACTIVE,
    	// autopilot is off, flaps are up, AOA is over threshold 
    	// and timer is expired
    	@Test
    	public void mcasTestTFFFFTT() {
    		// At start, state is INACTIVE, autopilot is off, flaps are up,
    		// AOA is over threshold and timer is expired (TFFFFTT)
    		
    		// Initialize local variable to check returned command
    		Command command = Command.NONE;

    		// In one call to trim() method), change state
    		// from INACTIVE to ARMED and then to ACTIVE
    		// Start with TFFFFTT (state is INACTIVE) 
    		// After D1 will have FTFFFTT (state is ARMED)
    		// After D3 will have FFTFFTF (state is ACTIVE, timer is not expired)
    		// Note: this test checks INDIRECTLY for the intermediate state by checking the final result.
    		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1.0);
    		
    		// Check command and state
    		assertEquals(Command.DOWN, command);
    		assertEquals(State.ACTIVE, mcas.getState());
    	}

    
}