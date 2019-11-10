import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Miri
 *
 */
public class ContextSwitch {

	static SimProcess currProcess;
	static ProcessControlBlock currPCB;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		SimProcessor processor = new SimProcessor();

		final int QUANTUM = 5;

		ArrayList<SimProcess> ready = new ArrayList<SimProcess>();
		ArrayList<SimProcess> blocked = new ArrayList<SimProcess>();
		ArrayList<ProcessControlBlock> pcbReady = new ArrayList<ProcessControlBlock>();
		ArrayList<ProcessControlBlock> pcbBlocked = new ArrayList<ProcessControlBlock>();

		ready.add(new SimProcess(1, "Word", 150));
		ready.add(new SimProcess(2, "Access", 150));
		ready.add(new SimProcess(3, "Excel", 150));
		ready.add(new SimProcess(4, "Quickbooks", 150));
		ready.add(new SimProcess(5, "Chrome", 150));
		ready.add(new SimProcess(6, "Firefox", 150));
		ready.add(new SimProcess(7, "SQL Server", 150));
		ready.add(new SimProcess(8, "Eclipse", 150));
		ready.add(new SimProcess(9, "Snipping tool", 150));
		ready.add(new SimProcess(10, "Edge", 150));

		for (SimProcess temp : ready) {
			pcbReady.add(new ProcessControlBlock(temp));
		}
		// create quantum counter
		int qCounter = 0;

		// the current pcb and process will always be taken from the beginning of the
		// lists
		currProcess = ready.remove(0);
		currPCB = pcbReady.remove(0);

		// create a loop with 3000 iterations and start with the first process
		for (int i = 1; i <= 3000; i++) {

			System.out.printf("Step %d ", i);
			processor.setSimProcess(currProcess);
			if (currProcess != null) {

				ProcessState state = processor.executeNextInstruction();

				switch (state) {
				case READY:

					qCounter++;
					if (qCounter == QUANTUM) {

						// display quantum expiration
						System.out.println("***Quantum Expired***");

						// put the process and pcb back on the ready list and perform a contact switch
						ready.add(currProcess);
						pcbReady.add(currPCB);
						contextSwitch(processor, ready, pcbReady);

						// set the quantum counter back to 0
						qCounter = 0;
					}
					break;

				case BLOCKED:

					// display process block
					System.out.println("***Process Blocked***");

					// add the process and pcb to the blocked list
					blocked.add(currProcess);
					pcbBlocked.add(currPCB);

					// perform a context switch
					contextSwitch(processor, ready, pcbReady);

					// set the quantum counter back to 0
					qCounter = 0;

					break;

				case FINISHED:

					// display process completion
					System.out.println("***Process Completed***");
					contextSwitch(processor, ready, pcbReady);
					break;

				}

				// invoke method to wake up blocked processes with 30% probability
				wakeUp(blocked, ready, pcbBlocked, pcbReady);

			} else {

				System.out.println("***Processor is idling***");

				// invoke method to wake up blocked processes with 30% probability
				if (!blocked.isEmpty()) {
					wakeUp(blocked, ready, pcbBlocked, pcbReady);
				}
				// then, if there is a process that is ready, continue with a context switch
				if (!ready.isEmpty()) {
					contextSwitch(processor, ready, pcbReady);
				}
			}
		}
	}

	/**
	 * 
	 * @param processor
	 * @param ready
	 * @param pcbReady
	 */
	public static void contextSwitch(SimProcessor processor, ArrayList<SimProcess> ready,
			ArrayList<ProcessControlBlock> pcbReady) {
		if (currProcess != null) {
			saveProcessInfo(processor);
		}
		if (ready.isEmpty()) {
			currProcess = null;
			currPCB = null;
			return;
		}
		currProcess = ready.remove(0);
		currPCB = pcbReady.remove(0);
		restoreProcessInfo(processor);
	}

	/**
	 * 
	 * @param processor
	 */
	public static void restoreProcessInfo(SimProcessor processor) {

		System.out.printf("Context switch: Restoring process: %d\n", currProcess.getPID());
		int currInstruction = currPCB.getCurrInstruction();
		int reg1 = currPCB.getRegister1Value();
		int reg2 = currPCB.getRegister2Value();
		int reg3 = currPCB.getRegister3Value();
		int reg4 = currPCB.getRegister4Value();

		processor.setCurrInstruction(currInstruction);
		processor.setRegister1Value(reg1);
		processor.setRegister2Value(reg2);
		processor.setRegister3Value(reg3);
		processor.setRegister4Value(reg4);

		System.out.printf("\t\tInstruction: %d - R1: %d, R2: %d, R3: %d, R4: %d\n", currInstruction, reg1, reg2, reg3,
				reg4);

	}

	/**
	 * 
	 * @param processor
	 */
	public static void saveProcessInfo(SimProcessor processor) {
		System.out.printf("Context switch: Saving process: %d\n", currProcess.getPID());
		int currInstruction = processor.getCurrInstruction();
		int reg1 = processor.getRegister1Value();
		int reg2 = processor.getRegister2Value();
		int reg3 = processor.getRegister3Value();
		int reg4 = processor.getRegister4Value();

		currPCB.setCurrInstruction(currInstruction);
		currPCB.setRegister1Value(reg1);
		currPCB.setRegister2Value(reg2);
		currPCB.setRegister3Value(reg3);
		currPCB.setRegister4Value(reg4);

		System.out.printf("\t\tInstruction: %d - R1: %d, R2: %d, R3: %d, R4: %d\n", currInstruction, reg1, reg2, reg3,
				reg4);
	}

	/**
	 * 
	 * @param blocked
	 * @param ready
	 * @param pcbBlocked
	 * @param pcbReady
	 */
	private static void wakeUp(ArrayList<SimProcess> blocked, ArrayList<SimProcess> ready,
			ArrayList<ProcessControlBlock> pcbBlocked, ArrayList<ProcessControlBlock> pcbReady) {

		Random random = new Random();
		for (int i = 0; i < blocked.size(); i++) {
			if (random.nextDouble() <= .30) {
				SimProcess temp = blocked.remove(i);
				ProcessControlBlock temp2 = pcbBlocked.remove(i);
				ready.add(temp);
				pcbReady.add(temp2);
			}
		}

	}

}
