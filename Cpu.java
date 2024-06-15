package chip8_main;
import javafx.application.Application; 
import java.math.*;

public class Cpu {
	
	private static Cpu instance;
	
	public chip8_app my_app = new chip8_app();
	
	Memory memoire = Memory.getInstance();
	public int v0 = memoire.register[1];
	public int vx = memoire.register[2];
	public int vy = memoire.register[3];
	public int vf = memoire.register[4];
	
	public int i = memoire.register[15] + memoire.register[16];
	
	public int pc = 512;
	
	public int resultPc1 = memoire.memoire[pc];
    public int resultPc2 = memoire.memoire[pc+1];
    public int afterShift = resultPc1 << 8;
    public int result = afterShift ^ resultPc2;
	
	public void opcode() {
		
		vx = memoire.memoire[1536];
		vf = memoire.memoire[1538];
		
		while(true) {
			
			opcode();
			
			switch(result) {
	        
	        case 0x00e0:
	    		// Clear the display
	    		my_app.clearScreen();
	    		pc = pc + 2;
	    			
	    		break;
	    		    
	    	case 0x1 :
	    		// Jump to location nnn
	    		int nnn = result;
	    		nnn = memoire.memoire[result];
	    			
	    		break;
	    		
	    	case 0x3 :
	    		// Skip next instruction if Vx = kk
	    		int skip1 = result;
	    		if (vx == skip1) {
	    			pc = pc + 2;
	    		}
	    			
	    		break;
	    		
	    	case 0x4 :
	    		// Skip next instruction if Vx = kk
	    		int skip2 = result;
	    		if (vx != skip2) {
	    			pc = pc + 2;
	    		}
	    			
	    		break;
	    		
	    	case 0x5 :
	    		// Skip next instruction if Vx = Vy
	    		if (vx == vy) {
	    			pc = pc + 2;
	    		}
	    			
	    		break;
	    		
	    	case 0x6 :
	    		// Set Vx = kk
	    		int set1 = result;
	    		if (vx == set1) {
	    			vx = memoire.memoire[result];
	    		}
	    			
	    		break;
	    		
	    	case 0x7 :
	    		// Set Vx = Vx + kk
	    		vx = vx + memoire.memoire[result];
	    			
	    		break;
	    		
	    	case 0x8xy0 :
	    		// Set Vx = Vy
	    		vx = vy;
	    			
	    		break;
	    		
	    	case 0x8xy1 :
	    		// Set Vx = Vx OR Vy
	    		int vxorvy1 = vx | vy;
	    		if (vxorvy1 == vx) {
	    			vx = vxorvy1;
	    		}
	    			
	    		break;
	    		
	    	case 0x8xy2 :
	    		// Set Vx = Vx AND Vy
	    		int vxorvy2 = vx & vy;
	    		if (vxorvy2 == vx) {
	    			vx = vxorvy2;
	    		}
	    			
	    		break;
	    		
	    	case 0x8xy3 :
	    		// Set Vx = Vx XOR Vy
	    		int vxorvy3 = vx ^ vy;
	    		if (vxorvy3 == vx) {
	    			vx = vxorvy3;
	    		}
	    			
	    		break;
	    		
	    	case 0x8xy4 :
	    		// Set Vx = Vx + Vy, set VF = carry
	    		int addition = vx ^ vy;
	    		if (addition > 255) {
	    			vf = 1;
	    		}
	    		else {
	    			vf = 0;
	    		}
	    			
	    		break; // must be complete later
	    		
	    	case 0x8xy5 :
	    		// Set Vx = Vx - Vy, set VF = NOT borrow
	    		if (vx > vy) {
	    			vf = 1;
	    		}
	    		else {
	    			vf = 0;
	    		}
	    		vx = vx - vy;
	    			
	    		break;
	    		
	    	case 0x8xy6 :
	    		// Set Vx = Vx SHR 1
	    		if (vx > vy) {
	    			vf = 1;
	    		}
	    		else {
	    			vf = 0;
	    		}
	    		vx = vx / 2;
	    			
	    		break; // must be complete later
	    		
	    	case 0x8xy7 :
	    		// Set Vx = Vy - Vx, set VF = NOT borrow
	    		if (vy > vx) {
	    			vf = 1;
	    		}
	    		else {
	    			vf = 0;
	    		}
	    		vx = vy - vx;
	    			
	    		break;
	    		
	    	case 0x8xyE :
	    		// Set Vx = Vx SHL 1
	    		if (vy > vx) {
	    			vf = 1;
	    		}
	    		else {
	    			vf = 0;
	    		}
	    		vx = vx * 2;
	    			
	    		break; // must be complete later
	    		
	    	case 0x9xy0 :
	    		// Skip next instruction if Vx != Vy
	    		if (vx != vy) {
	    			pc = pc + 2;
	    		}
	    			
	    		break;
	    		
	    	case 0xA :
	    		// Set I = nnn
	    		int onlynnn = result;
	    		i = onlynnn;
	    			
	    		break;
	    		
	    	case 0xB :
	    		// Jump to location nnn + V0
	    		int nnnplusv0 = result;
	    		nnnplusv0 = nnnplusv0 + v0;
	    			
	    		break;
	    		
	    	case 0xC :
	    		// Set Vx = random byte AND kk
	    		int randomNum = (int)(Math.random() * ((255) + 1));
	    		int onlykk = result;
	    		onlykk = onlykk & randomNum;
	    			
	    		break;
	    		
	    	case 0xD :
	    		// Display n-byte sprite starting at memory location I at (Vx, Vy), set VF = collision
	    		int randomNum = (int)(Math.random() * ((255) + 1));
	    		int onlykk = result;
	    		onlykk = onlykk & randomNum;
	    			
	    		break;
	    		  
	    	default:
	    		System.out.println("Error");
	    	}
		}
	}
	
	
	private Cpu() {
	}
	
	public static Cpu getInstance() {
		if (instance == null) {
			instance = new Cpu();
		}
		return instance;
	}
	
}
