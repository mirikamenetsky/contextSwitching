import java.util.Random;

public class SimProcessor {
	private SimProcess ref;

	// no arg construction
	public SimProcessor() {

	}

	// int values to represent four different registers and currentInstruction
	private int reg1, reg2, reg3, reg4, currInstruction;

	Random random = new Random();

	// create setters
	public void setSimProcess(SimProcess ref) {
		this.ref = ref;
	}

	public void setRegister1Value(int i) {
		reg1 = i;
	}

	public void setRegister2Value(int i) {
		reg2 = i;
	}

	public void setRegister3Value(int i) {
		reg3 = i;
	}

	public void setRegister4Value(int i) {
		reg4 = i;
	}

	public void setCurrInstruction(int currInstruction) {
		this.currInstruction = currInstruction;
	}

	// create getters
	public SimProcess getSimProcess() {
		return ref;
	}

	public int getCurrInstruction() {
		return currInstruction;
	}

	public int getRegister1Value() {
		return random.nextInt();
	}

	public int getRegister2Value() {
		return random.nextInt();
	}

	public int getRegister3Value() {
		return random.nextInt();
	}

	public int getRegister4Value() {
		return random.nextInt();
	}

	// create a method to execute the next instruction
	public ProcessState executeNextInstruction() {
		ProcessState ps = ref.execute(currInstruction);
		currInstruction++;
		return ps;
	}

}
