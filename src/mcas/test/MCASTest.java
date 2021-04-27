import mcas.main.Mcas;
import mcas.main.McasTimer;

import org.junit.Before;
import org.junit.Test;

import mcas.main.Mcas.Command;
import mcas.main.Mcas.State;

import static org.junit.Assert.assertEquals;

public class MCASTest {
	Mcas mcas;
	
	@Before public void setUp() { 
		mcas = new Mcas();
	}

	//	Test requirements: 
	//
	//  1. TFFFTFT	D1
	//  2. TFFFFFT	D1
	//  3. FTFTFTT	D2
	//  4. FTFFTFT	D2
	//  5. FTFFFTT	D3
	//  6. FFTTFFT	D4
	//  7. FFTFTFT	D4
	//  8. FTFFFFT	D235
	//  9. FFTFFTT	D146
	// 10. FFTFFTF	D56
	// 11. FFTFFFT	D56
	// 12. TFFTFTT	D12346

	//	Note: Exactly one of the first 3 predicates (a, b, c) is always true.
	
	
	//  1. TFFFTFT	D1

	// Test p1 with condition TFFFTFT 
	// (state INACTIVE, autopilot off, flaps down,
	// AOA under threshold, timer expired)
	@Test 
	public void mcasTest_1_TFFFTFT() { 
		
		//	a: state == State.INACTIVE
		//	b: state == State.ARMED
		//	c: state == State.ACTIVE
		//	d: autopilotOn
		//	e: flapsDown
		//	f: angleOfAttack > AOA_THRESHOLD
		//	g: timer.isExpired ( )
				
		// Declare local variable to check returned command
		Command command;

		// After call to trim() method will still have TFFFTFT 
		// (no command and state is INACTIVE)
		// Call trim() with TFFFTFT (state INACTIVE, autopilot off, flaps down,
		// AOA < threshold, timer expired)
		command = mcas.trim(false, true, Mcas.AOA_THRESHOLD - 1.0);
		
		// Check no command and state INACTIVE
		assertEquals(Command.NONE, command);
		assertEquals(State.INACTIVE, mcas.getState());
	}	

	
	//  2. TFFFFFT	D1

	// Test p1 with condition TFFFFFT
	// (state INACTIVE, autopilot off, flaps up,
	// AOA under threshold, timer expired)
	@Test 
	public void mcasTest_2_TFFFFFT() { 
		
		//	a: state == State.INACTIVE
		//	b: state == State.ARMED
		//	c: state == State.ACTIVE
		//	d: autopilotOn
		//	e: flapsDown
		//	f: angleOfAttack > AOA_THRESHOLD
		//	g: timer.isExpired ( )
		
		// Declare local variable to check returned command
		Command command;
		
		// After call to trim() method will have FTFFFFT 
		// (no command and state is ARMED)
		// Call trim() with TFFFFFT
		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD - 1.0);
		
		// Check no command and state ARMED
		assertEquals(Command.NONE, command);
		assertEquals(State.ARMED, mcas.getState());
	}	

	
	//  3. FTFTFTT	D2

	// Test p2 with condition FTFTFTT
	// (state ARMED, autopilot on, flaps up,
	// AOA over threshold, timer expired)
	@Test 
	public void mcasTest_3_FTFTFTT() { 
		
		//	a: state == State.INACTIVE
		//	b: state == State.ARMED
		//	c: state == State.ACTIVE
		//	d: autopilotOn
		//	e: flapsDown
		//	f: angleOfAttack > AOA_THRESHOLD
		//	g: timer.isExpired ( )
		
		// Declare local variable to check returned command
		Command command;

		// After call to trim() method will have FTFFFFT 
		// (no command and state is ARMED)
		// Call trim with TFFFFFT
		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD - 1.0);
		
		// Check no command and state ARMED
		assertEquals(Command.NONE, command);
		assertEquals(State.ARMED, mcas.getState());
				
		// Condition is now FTFFFFT:
 		// Test p2 with condition FTFTFTT (set autopilot on, AOA > threshold)
		// After call to trim() method will have TFFTFTT 
		// (no command and state is INACTIVE)
		// Call trim with FTFTFTT
		command = mcas.trim(true, false, Mcas.AOA_THRESHOLD + 1.0);
		
		// Check no command and state INACTIVE
		assertEquals(Command.NONE, command);
		assertEquals(State.INACTIVE, mcas.getState());
	}	

	
	//  4. FTFFTFT	D2

	// Test p2 with condition FTFFTFT
	// (state ARMED, autopilot off, flaps down,
	// AOA under threshold, timer expired)
	@Test 
	public void mcasTest_4_FTFFTFT() { 
		
		//	a: state == State.INACTIVE
		//	b: state == State.ARMED
		//	c: state == State.ACTIVE
		//	d: autopilotOn
		//	e: flapsDown
		//	f: angleOfAttack > AOA_THRESHOLD
		//	g: timer.isExpired ( )
		
		// Initialize local variable to check returned command
		Command command;

		// After call to trim() method will have FTFFFFT 
		// (no command and state is ARMED)
		// Call trim with TFFFFFT
		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD - 1.0);
		
		// Check no command and state ARMED
		assertEquals(Command.NONE, command);
		assertEquals(State.ARMED, mcas.getState());
				
		// Condition is now FTFFFFT:
		// Test p2 with condition FTFFTFT (set flaps to down)
 		// After call to trim() method will have TFFFTFT 
		// (no command and state is INACTIVE)
		// Call trim with FTFFTFT
		command = mcas.trim(false, true, Mcas.AOA_THRESHOLD - 1.0);

		// Check no command and state INACTIVE
		assertEquals(Command.NONE, command);
		assertEquals(State.INACTIVE, mcas.getState());
	}	

	
	//  5. FTFFFTT	D3

	// Test p3 with condition FTFFFTT 	
	// (state ARMED, autopilot off, flaps up,
	// AOA over threshold, timer expired)
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
		// AOA is under threshold and timer is expired (TFFFFFT)
		
		// Initialize local variable to check returned command
		Command command;

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
		// Test p3 with condition FTFFFTT (set AOA > threshold)
		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1.0);
		assertEquals(Command.DOWN, command);
		assertEquals(State.ACTIVE, mcas.getState());
	}	

	
	//  6. FFTTFFT	D4

	// Test p4 with condition FFTTFFT 
	// (state ACTIVE, autopilot on, flaps down,
	// AOA under threshold, timer expired)
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
		Command command;

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
		Command command;

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
		
		// Declare local variable to check returned command
		Command command;

		// In initial call to trim() method, change state
		// from INACTIVE to ARMED and then to ACTIVE
		// Start with TFFFFFT (state is INACTIVE) 
		// After D1 will have FTFFFFT (state is ARMED)
		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD - 1.0);
		
		// Check state
		assertEquals(State.ARMED, mcas.getState());
		assertEquals(Command.NONE, command);
		
		// Condition is now FTFFFFT:
 		// State is ACTIVE, autopilot is off, flaps are UP,
		// AOA is under threshold and timer is expired
		// Test p2, p3, p5 with condition FFTFTFT (set autopilot to off, flaps to up, AOA < threshold)
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

		// Initialize local variable to check returned command
		Command command;

		// After call to trim() method will have FFTFFTF 
		// (DOWN command and state is ACTIVE)
		// After D1 will have FTFFFTT (state is ARMED)
		// After D3 will have FFTFFTF (state is ACTIVE, timer is not expired)
		// Call trim() with TFFFFTT
		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1.0);
		
		// Check DOWN command and state ACTIVE
		assertEquals(Command.DOWN, command);
		assertEquals(State.ACTIVE, mcas.getState());
		
		// Condition is now FFTFFTF		
		// wait longer than fake interval for timer to expire
		try {
			Thread.sleep(McasTimer.FAKE_INTERVAL + 10); // wait (in milliseconds)
		} catch (InterruptedException e) { 
			System.err.println("Caught InterruptedException: " + e.getMessage());};
			
		// Condition is now FFTFFTT:
		// Test p1, p4, p6 with FFTFFTT
		// Call trim() with FFTFFTT
		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1.0);

		// Check DOWN command and state ACTIVE
		assertEquals(Command.DOWN, command);
		assertEquals(State.ACTIVE, mcas.getState());
	}	
	
	
	// 10. FFTFFTF	D5, D6

	// Test p5 and p6 with condition FFTFFTF
	@Test 
	public void mcasTest_10_FFTFFTF() {
		
		//	a: state == State.INACTIVE
		//	b: state == State.ARMED
		//	c: state == State.ACTIVE
		//	d: autopilotOn
		//	e: flapsDown
		//	f: angleOfAttack > AOA_THRESHOLD
		//	g: timer.isExpired ( )
		
		// Initialize local variable to check returned command
		Command command;

		// After call to trim() method will have FFTFFTF 
		// (DOWN command and state is ACTIVE)
		// After D1 will have FTFFFTT (state is ARMED)
		// After D3 will have FFTFFTF (state is ACTIVE, timer is not expired)
		// Call trim() with TFFFFTT
		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1.0);
		
		// Check DOWN command and state ACTIVE
		assertEquals(Command.DOWN, command);
		assertEquals(State.ACTIVE, mcas.getState());

		// Condition is now FFTFFTF
		// Test p5, p6 with FFTFFTF
		// Call trim() with FFTFFTF
		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1.0);
		
		// Check no command and state ARMED
		assertEquals(Command.NONE, command);
		assertEquals(State.ACTIVE, mcas.getState());
	}
	
	
	// 11. FFTFFFT	D5, D6

	// Test p5 and p6 with condition FFTFFFT
	@Test 
	public void mcasTest_11_FFTFFFT() {
		
		//	a: state == State.INACTIVE
		//	b: state == State.ARMED
		//	c: state == State.ACTIVE
		//	d: autopilotOn
		//	e: flapsDown
		//	f: angleOfAttack > AOA_THRESHOLD
		//	g: timer.isExpired ( )
				
		// Initialize local variable to check returned command
		Command command;
		
		// After call to trim() method will have FFTFFTF 
		// (DOWN command and state is ACTIVE)
 		// After D1 will have FTFFFTT (state is ARMED)
		// After D3 will have FFTFFTF (state is ACTIVE, timer is not expired)
		// Call trim() with TFFFFTT
		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD + 1.0);
		
		// Check DOWN command and state ACTIVE
		assertEquals(Command.DOWN, command);
		assertEquals(State.ACTIVE, mcas.getState());
				
		// Condition is now FFTFFTF
		// wait longer than fake interval for timer to expire
		try {
			Thread.sleep(McasTimer.FAKE_INTERVAL + 10); // wait (in milliseconds)
		} catch (InterruptedException e) { 
			System.err.println("Caught InterruptedException: " + e.getMessage());};
			
		// Condition is now FFTFFTT
			
		// After call to trim() method will have FTFFFFT 
		// (no command and state is ARMED)
	 	// Call trim() with FFTFFFT
		command = mcas.trim(false, false, Mcas.AOA_THRESHOLD - 1.0);
		
		// Check no command and state ARMED
		assertEquals(Command.NONE, command);
		assertEquals(State.ARMED, mcas.getState());
	}	
	

	// 12. TFFTFTT	D1, D2, D3, D4, D6
	
	// Test p1, p2, p3, p4 and p6 with condition TFFTFTT
	@Test 
	public void mcasTest_12_TFFTFTT() { // conditions persist for D1-D6
		
		//	a: state == State.INACTIVE
		//	b: state == State.ARMED
		//	c: state == State.ACTIVE
		//	d: autopilotOn
		//	e: flapsDown
		//	f: angleOfAttack > AOA_THRESHOLD
		//	g: timer.isExpired ( )
				
		// Initialize local variable to check returned command
		Command command;

		// After call to trim() method will still have TFFFTFT 
		// (no command and state is INACTIVE)
		// Call trim() with TFFFTFT
		command = mcas.trim(true, false, Mcas.AOA_THRESHOLD + 1.0);
		
		// Check no command and state INACTIVE
		assertEquals(Command.NONE, command);
		assertEquals(State.INACTIVE, mcas.getState());
	}
}