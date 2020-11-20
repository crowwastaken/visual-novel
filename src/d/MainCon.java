package d;

import java.io.FileNotFoundException;

/*LIST OF DO
 * 
 * format of all data: choice, 1 - 3 are parts
 * 
 * maybe make a save file??
 * for SAVE
 * -REMEMBER TO KEEP TRACK OF WHERE THE LINE IN TXT FILE IS LEFT OFF
 * make log function
 * 
 * 
 * add text outline
 * remember to use labels for save and log buttons (as it can be replaced with image later on)
 * makes others
 * make confirmations for new game and quit
 * add scrolling to log
 * save log along with save?
 * 
 */

public class MainCon {
	
	static int level = 1;
	static String part = ""; 
	static int currentLine = 0;
	static String currentLog = "";
	static boolean newGame = true;
	
	public static void main(String[] args) {
		Func.wait = true;
		createMenu();
		createGame();
		// beginning
		// intro

		// loop start?

		// loop end?
	}
	
	public static void createMenu() {
		Window menu = new Window("menu");
		while(menu.isVisible()) {
			//when play button is pressed, menu.dispose(); in window class
			System.out.println("menu");
		}
	}
	
	public static void createGame() {
		Window game = new Window("game");
		if(newGame) {
			Func.DType(0, "0");
		}
		else if(!newGame) {
			//previous choice result
			System.out.println(part);
			Func.DType(level, part);
			level++;
		}
		while(game.isVisible()) {
			//choice
			Window.choicePan.setVisible(true);
			
			try {
				Func.choice(Func.fileReader(level, "choice"));
			} catch (FileNotFoundException e) {
				System.out.println("something broke or something");
			}
			
			while(Window.choicePan.isVisible()) {
				System.out.println("waiting for choice");
			}
			//choice result
			System.out.println(part);
			Func.DType(level, part);
			level++;
		}
	}
}
