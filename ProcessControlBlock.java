import java.util.Random;

public class ProcessControlBlock {
	private SimProcess ref;
	// int values to represent four different registers and currentInstruction
	private int reg1, reg2, reg3, reg4, currInstruction;
	
	Random random = new Random();

	public ProcessControlBlock(SimProcess ref) {
		this.ref = ref;
	}

	// create getters
	public SimProcess getSimProcess() {
		return ref;
	}

	public int getCurrInstruction() {
		return currInstruction;
	}
	
	public int getRegister1Value() {
		return reg1; 
	}
	
	public int getRegister2Value() {
		return reg2;
	}
	
	public int getRegister3Value() {
		return reg3;
	}
	
	public int getRegister4Value() {
		return reg4;
	}


	// create setters
	public void setCurrInstruction(int currInstruction) {
		this.currInstruction = currInstruction;
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

}
