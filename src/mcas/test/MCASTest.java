//package edu.gmu.swe637.assignment6Test

import mcas.main.Mcas;
import mcas.main.McasTimer;
import mcas.main.McasTimerFake;
import mcas.main.TestTimer;

import org.junit.Before;
import org.junit.Test;

import edu.gmu.swe637.assignment6.Mcas.Command;
import edu.gmu.swe637.assignment6.Mcas.State;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//import edu.gmu.swe637.assignment6.Mcas.Command;
//import edu.gmu.swe637.assignment6.Mcas.State;


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
	
	@Before public void setUp() { 
		mcas = new Mcas();
	}

	//	Test requirements: 
	//
	//  1. TFFFTFT	D1
	//  2. TFFFFFT	D1
	//  3. FTFTFTT	D2  not feasible for D3 b/c will become inactive at D2
	//  4. FTFFTFT	D2  not feasible for D3 b/c will become inactive at D2
	//  5. FTFFFTT	D3
	//  6. FFTTFFT	D4
	//  7. FFTFTFT	D4
	//  8. FTFFFFT	D235
	//  9. FFTFFTT	D146
	// 10. FFTFFTF	D56
	// 11. FFTFFFT	D56
	// 12. TFFTFTT	D12346

	//	Note: Exactly one of the first 3 predicates (a, b, c) is always true.
	
	
	//  5. FTFFFTT	D3

	// Test p3 with condition FTFFFTT in which state is ACTIVE,
	// autopilot is on, flaps are up, AOA is under threshold 
	// and timer is expired
	@Test 
	public void mcasTest_5_FTFFFTT() { 
		
		//	a: state == State.INACTIVE
		//	b: state == State.ARMED
		//	c: state == State.ACTIVE
		//	d: autopilotOn
		//	e: flapsDown
		//	f: angleOfAttack > AOA_THRESHOLD
		//	g: timer.isExpired ( )
		
		// At start, state is INACTIVE, autopilot is off, flaps are up,
		// AOA is over threshold and timer is expired (TFFFFTT)
		
		// Initialize local variable to check returned command
		Command command = Command.NONE;

		// In initial call to trim() method), change state
		// from INACTIVE to ARMED
		// Start with TFFFFFT (state is INACTIVE) 
		// After D1 will have FTFFFFT (state is ARMED)
		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD - 1.0);
		
		// Check state
		assertEquals(State.ARMED, mcas.getState());
				
		// Condition is now FTFFFFT:
 		// State is ARMED, autopilot is off, flaps are up,
		// AOA is under threshold and timer is expired
		// Test p3 with condition FTFFTFT (set flaps to down)
		command = Command.NONE;
		command = mcas.trim(true, false, Mcas.AOA_THRESHOLD - 1.0);
		assertEquals(Command.NONE, command);
		assertEquals(State.ARMED, mcas.getState());
	}	

	
	//  6. FFTTFFT	D4

	// Test p4 with condition FFTTFFT in which state is ACTIVE,
	// autopilot is on, flaps are up, AOA is under threshold 
	// and timer is expired
	@Test 
	public void mcasTest_6_FFTTFFT() {
		
		//	a: state == State.INACTIVE
		//	b: state == State.ARMED
		//	c: state == State.ACTIVE
		//	d: autopilotOn
		//	e: flapsDown
		//	f: angleOfAttack > AOA_THRESHOLD
		//	g: timer.isExpired ( )
		
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
			Thread.sleep(McasTimer.FAKE_INTERVAL + 10); // wait (in milliseconds)
		} catch (InterruptedException e) { 
			System.err.println("Caught InterruptedException: " + e.getMessage());};
			
		// Condition is now FFTFFTT:
 		// State is ACTIVE, autopilot is off, flaps are up,
		// AOA is under threshold and timer is expired
		// Test p4 with condition FFTTFFT (set autopilot to on, AOA < threshold)
		command = Command.NONE;
		command = mcas.trim(true, false, Mcas.AOA_THRESHOLD - 1.0);
		assertEquals(Command.NONE, command);
		assertEquals(State.INACTIVE, mcas.getState());
	}	


	
	//  7. FFTFTFT	D4
	
	// Test p4 with condition FFTFTFT in which state is ACTIVE,
	// autopilot is off, flaps are down, AOA is under threshold 
	// and timer is expired
	@Test 
	public void mcasTest_7_FFTFTFT() {
		
		//	a: state == State.INACTIVE
		//	b: state == State.ARMED
		//	c: state == State.ACTIVE
		//	d: autopilotOn
		//	e: flapsDown
		//	f: angleOfAttack > AOA_THRESHOLD
		//	g: timer.isExpired ( )
		
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
			Thread.sleep(McasTimer.FAKE_INTERVAL + 10); // wait (in milliseconds)
		} catch (InterruptedException e) { 
			System.err.println("Caught InterruptedException: " + e.getMessage());};
			
		// Condition is now FFTFFTT:
 		// State is ACTIVE, autopilot is off, flaps are down,
		// AOA is under threshold and timer is expired
		// Test p4 with condition FFTFTFT (set autopilot to off, flaps to down, AOA < threshold)
		command = Command.NONE;
		command = mcas.trim(false, true, Mcas.AOA_THRESHOLD - 1.0);
		assertEquals(Command.NONE, command);
		assertEquals(State.INACTIVE, mcas.getState());
	}	



	// TODO; FIX #8
	//  8. FTFFFFT	D235

	// Test p2, p3, p5 with condition FTFFFFT, in which state is INACTIVE,
	// autopilot is off, flaps are up, AOA is under threshold 
	// and timer is expired
	@Test 
	public void mcasTest_8_FTFFFFT() { 
		
		//	a: state == State.INACTIVE
		//	b: state == State.ARMED
		//	c: state == State.ACTIVE
		//	d: autopilotOn
		//	e: flapsDown
		//	f: angleOfAttack > AOA_THRESHOLD
		//	g: timer.isExpired ( )

		// At start, state is INACTIVE, autopilot is off, flaps are up,
		// AOA is under threshold and timer is expired (TFFFFFT)
		
		// Initialize local variable to check returned command
		Command command = Command.NONE;

		// In initial call to trim() method, change state
		// from INACTIVE to ARMED and then to ACTIVE
		// Start with TFFFFFT (state is INACTIVE) 
		// After D1 will have FTFFFFT (state is ARMED)
		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD - 1.0);
		//  
		
		// Check state
		assertEquals(State.ARMED, mcas.getState());
		assertEquals(Command.NONE, command);
		
		// Condition is now FTFFFFT:
 		// State is ACTIVE, autopilot is off, flaps are UP,
		// AOA is under threshold and timer is expired
		// Test p2, p3, p5 with condition FFTFTFT (set autopilot to off, flaps to up, AOA < threshold)
		command = Command.NONE;
		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD - 1.0);
		assertEquals(Command.NONE, command);
		assertEquals(State.ARMED, mcas.getState());
	}	

	

	//  9. FFTFFTT	D146
		
	// Test p1, p4 and p6 with condition FFTFFTT, in which state is ACTIVE,
	// autopilot is off, flaps are up, AOA is over threshold 
	// and timer is expired
	@Test 
	public void mcasTest_9_FFTFFTT() throws InterruptedException { // D146
		
		//	a: state == State.INACTIVE
		//	b: state == State.ARMED
		//	c: state == State.ACTIVE
		//	d: autopilotOn
		//	e: flapsDown
		//	f: angleOfAttack > AOA_THRESHOLD
		//	g: timer.isExpired ( )

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
			Thread.sleep(McasTimer.FAKE_INTERVAL + 10); // wait (in milliseconds)
		} catch (InterruptedException e) { 
			System.err.println("Caught InterruptedException: " + e.getMessage());};
			
		// Condition is now FFTFFTT:
 		// State is ACTIVE, autopilot is off, flaps are up,
		// AOA is over threshold and timer is expired
		// Test p1, p4, p6 with this condition
		command = Command.NONE;
		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1.0);
		assertEquals(Command.DOWN, command);
		assertEquals(State.ACTIVE, mcas.getState());
	}	
	
	
	// 10. FFTFFTF	D56

	// Test p5 and p6 with condition FFTFFTF in which state is ACTIVE,
	// autopilot is off, flaps are up, AOA is over threshold 
	// and timer is not expired
	@Test 
	public void mcasTest_10_FFTFFTF() {
		
		//	a: state == State.INACTIVE
		//	b: state == State.ARMED
		//	c: state == State.ACTIVE
		//	d: autopilotOn
		//	e: flapsDown
		//	f: angleOfAttack > AOA_THRESHOLD
		//	g: timer.isExpired ( )
		
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
		// Test p5, p6 with this condition
		command = Command.NONE;
		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1.0);
		assertEquals(Command.NONE, command);
		assertEquals(State.ACTIVE, mcas.getState());
	}
	
	
	// 11. FFTFFFT	D56

	// Test p5 and p6 with condition FFTFFFT in which state is ACTIVE,
	// autopilot is off, flaps are up, AOA is under threshold 
	// and timer is expired
	@Test 
	public void mcasTest_11_FFTFFFT() {
		
		//	a: state == State.INACTIVE
		//	b: state == State.ARMED
		//	c: state == State.ACTIVE
		//	d: autopilotOn
		//	e: flapsDown
		//	f: angleOfAttack > AOA_THRESHOLD
		//	g: timer.isExpired ( )
		
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
			Thread.sleep(McasTimer.FAKE_INTERVAL + 10); // wait (in milliseconds)
		} catch (InterruptedException e) { 
			System.err.println("Caught InterruptedException: " + e.getMessage());};
			
		// Condition is now FFTFFTT:
 		// State is ACTIVE, autopilot is off, flaps are down,
		// AOA is under threshold and timer is expired
		// Test p5, p6 with condition FFTFFFT (set AOA < threshold)
		command = Command.NONE;
		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD - 1.0);
		assertEquals(Command.NONE, command);
		assertEquals(State.ARMED, mcas.getState());
	}	
	

	// 12. TFFTFTT	D12346
	
	// Test p1, p2, p3, p4 and p6 with condition FFTFFTT, in which state is ACTIVE,
	// autopilot is off, flaps are up, AOA is over threshold 
	// and timer is expired
	@Test 
	public void mcasTest_12_TFFTFTT() { // conditions persist for D1-D6
		
		//	a: state == State.INACTIVE
		//	b: state == State.ARMED
		//	c: state == State.ACTIVE
		//	d: autopilotOn
		//	e: flapsDown
		//	f: angleOfAttack > AOA_THRESHOLD
		//	g: timer.isExpired ( )
		
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

}

