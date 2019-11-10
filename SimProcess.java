import java.util.Random;

public class SimProcess {
	private int pid;
	private String procName;
	private int totalInstructions;

	public SimProcess(int pid, String procName, int totalInstructions) {
		this.pid = pid;
		this.procName = procName;
		this.totalInstructions = totalInstructions;
	}

	public ProcessState execute(int i) {
		System.out.printf("Proc %s, PID: %d, executing instruction: %d\n", procName, pid, i+1);
		//this +1 is so that it doesn't execute one more than the total instructions
		if(i+1  >= totalInstructions) {
			return ProcessState.FINISHED;
		}
		Random random = new Random();
		double num = random.nextDouble();
		if(num <= .15) {
			return ProcessState.BLOCKED;
		}
		else {
			return ProcessState.READY;
		}
		
	}
	
	public int getPID() {
		return pid;
	}


}
