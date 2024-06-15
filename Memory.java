package chip8_main;

public class Memory {

	private static Memory instance;
	
	int[] memoire = new int[4096];
	
	int[] register = new int[16];
	
	private Memory() {
	}
	
	public static Memory getInstance() {
		if (instance == null) {
			instance = new Memory();
		}
		return instance;
	}

}
