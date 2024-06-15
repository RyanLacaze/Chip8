package chip8_main;
import java.io.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class chip8_app extends Application {
	
	// initialisation de l'attribut "fileRom"
	static public String fileRom;
	
	public int height = 32;
	public int widht = 64;
	public int[][] screen = new int[widht][height];
	
	public VBox test = new VBox();
	
	// ajouter un tableau sur la fenêtre
    public Canvas testCanvas = new Canvas(widht * 8, height * 15);
    
    public GraphicsContext testGC = testCanvas.getGraphicsContext2D();
	
    // nettoyer l'écran 
	public void clearScreen() {
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < widht; x++) {
				screen[y][x] = 0;
			}
		}
	}
	
	// Interface graphique
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        
        testCanvas.setFocusTraversable(true);
        
        // dimensions de l'interface graphique
        stage.setMaxHeight(height * 15);
        stage.setMinHeight(height * 15);
        stage.setMaxWidth(widht * 8);
        stage.setMinWidth(widht * 8);
        
        screen[8][12] = 1;
        
        test.getChildren().add(testCanvas);
        
        Scene scene = new Scene(test);
        
        stage.setScene(scene);
        stage.show();
        
        printScreen();
    }
    
    // dessiner sur la fenêtre
    public void printScreen() {
    	
    	for(int x = 0; x < screen.length; x++) {
			for(int y = 0; y < screen[x].length; y++) {
				if (screen[x][y] == 1) {
	    			testGC.setFill(Color.WHITE);
	    		}
	    		else {
					testGC.setFill(Color.BLACK);
				}
				testGC.fillRect(x * 8, y * 15, 8, 15);
    		}
    	}
    }
    
    static public void LoadFile() {
    	try
        {
          // Le fichier d'entrée
          File file = new File("C:\\Users\\ryanl\\Downloads\\c8games\\PONG");
          
          // Créer l'objet File Reader
          FileReader fr = new FileReader(file); 
          
          // Créer l'objet BufferedReader        
          BufferedReader br = new BufferedReader(fr);  
          
          StringBuffer sb = new StringBuffer();    
          String line;
          while((line = br.readLine()) != null)
          {
            // ajoute la ligne au buffer
            sb.append(line);      
            sb.append("\n");     
          }
          fr.close();    
          System.out.println("Contenu du fichier: ");
          
          // Afficher les données du fichier
          System.out.println(sb.toString());
          
          // "fileRom" égale aux données du fichier
          fileRom = sb.toString();
        }
        catch(IOException e)
        {
          e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	// Appeler la methode "LoadFile"
    	LoadFile();
        
    	// Créer l'objet memoire via l'instance de la classe "Memory"
        Memory memoire = Memory.getInstance();
        
        // Mettre les données du fichier à la bonne place dans la mémoire
        for (int i = 512; i-512 < fileRom.length(); i++) {
            memoire.memoire[i] = fileRom.charAt(i-512);
		}
        
        // Parcourir et afficher les données du fichier dans la mémoire ainsi que les index de celle-ci 
        for (int i = 0; i < memoire.memoire.length; i++) {
        	
        	// Mettre les données de la mémoire en héxadécimale
        	String dataFile = Integer.toHexString(memoire.memoire[i]);
        	
        	// Mettre les index de la mémoire en héxadécimale
        	String indexMemory = Integer.toHexString(i);
        	
        	System.out.println(dataFile + " est a " + indexMemory);
		}
        
        launch();
    }
}

