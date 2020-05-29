package d;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class Func {
	//for text typing related
	static boolean run = true;
	static boolean wait = true;
	static String finishedText = "";
	
	//data related
	public static String localFile = System.getProperty("user.dir").replace('\\', '/');
	public static String textFile = localFile + "/texts/";
	public static String imageFile = localFile + "/images/";
	
	 //image and dialogue
	static String[] partToType;
	static String dialogueLineNum;
	public static void DType(int level, String part) {
		try {
			partToType = fileReader(level, part);
		} catch (FileNotFoundException FNFE) {
			System.out.println("failed");
		}
		//each line
		for(int u = MainCon.currentLine; u < partToType.length; u++) {
			MainCon.currentLine = u;
			
			String[] all = partToType[u].split(": ");
			
			String dialogueLineNum = all[2];
			

			try {
				diaImage(level, part, dialogueLineNum);
			} catch (IOException IOE) {
				System.out.println("failed");
			} catch (ArrayIndexOutOfBoundsException noImage) {
				System.out.println("forgot to include image for dia line");
			}
			
			//name
			Window.name.setText(all[0]);
			//dialogue
			String[] dia = all[1].split("");
			
			finishedText = all[1];
			String display = "";
			
			//every word
			for(int i = 0; i < dia.length; i++) {
				
				if(!run) {
					break;
				}
				
				try {
					System.out.println("typing");
					display += dia[i];
					Window.text.setText("<html>" + display + "</html>");
					Thread.sleep(60);
				}
				
				catch(Exception e) {
					
				}
				
			}
			
			wait = true;
			run = false;
			
			//wait func
			while(wait) {
				if(!wait) {
					break;
				}
				
				System.out.println("waiting");
			}
			Window.logText.setText(Window.logText.getText() + Window.name.getText() + ": " + finishedText + "<br/>");
			MainCon.currentLog = Window.logText.getText();
		}
		MainCon.currentLine = 0;
	}
	
	public static void CType(String[] choices) {
		String[] choiceText1 = choices[0].split("");
		String[] choiceText2 = choices[1].split("");
		String[] choiceText3 = choices[2].split("");
		String display1 = " ";
		String display2 = " ";
		String display3 = " ";
		int one = 0;
		int two = 0;
		int three = 0;
		boolean oneTyping = true;
		boolean twoTyping = true;
		boolean threeTyping = true;
		
		
		while(oneTyping ||  twoTyping || threeTyping){
			try {
				System.out.println("typingC");
				if(one < choiceText1.length) {
					Window.but1.setText(display1 += choiceText1[one]);
					one++;
				}
				else {
					oneTyping = false;
				}
				if(two < choiceText2.length) {
					Window.but2.setText(display2 += choiceText2[two]);
					two++;
				}
				else {
					twoTyping = false;
				}
				if(three < choiceText3.length) {
					Window.but3.setText(display3 += choiceText3[three]);
					three++;
				}
				else {
					threeTyping = false;
				}	
				Thread.sleep(15);
			}
			
			catch(Exception e) {
				
			}
			
		}
	}
	
	public static void choice(String[] choices) {
		Window.choicePan.setVisible(true);
		//mix up the choices
		String[] mchoice = new String[3];
		ArrayList<Integer> ar = new ArrayList<Integer>(Arrays.asList(0, 1, 2));
		//shuffle shuffle
		Collections.shuffle(ar);
		mchoice[0] = choices[ar.get(0)];
		Window.but1result = ar.get(0) + 1;
		mchoice[1] = choices[ar.get(1)];
		Window.but2result = ar.get(1) + 1;
		mchoice[2] = choices[ar.get(2)];
		Window.but3result = ar.get(2) + 1;
		CType(mchoice);
	}
	
	public static String[] fileReader(int level, String part) throws FileNotFoundException{
		ArrayList<String> text = new ArrayList<String>();
		
		
		File f = new File(textFile + level + "/" + part + ".txt");
		Scanner s = new Scanner(f);
		while(s.hasNextLine()) {
			text.add(s.nextLine());
		}
		s.close();
		String[] returnarr = text.toArray(new String[text.size()]);
		return returnarr;
	}
	
	public static void diaImage(int level, String part, String linePart) throws IOException {
		BufferedImage image = ImageIO.read(new File(imageFile + level + "/" + linePart +".png"));
		ImageIcon a = new ImageIcon(image);
		Window.image.setIcon(a);
	}
	
	public static void save() {
		FileWriter saveWriter;
		FileWriter logWriter;
		try {
			saveWriter = new FileWriter(Func.localFile + "/save.txt", false);
			saveWriter.write(MainCon.level + "\n" + MainCon.part + "\n" + MainCon.currentLine);
			logWriter = new FileWriter(Func.localFile + "/log.txt", false);
			logWriter.write(MainCon.currentLog);
			
			saveWriter.close();
			logWriter.close();
		} catch (IOException e) {
			
		}
	}
	
	public static void skip() {
		run = false;
		Window.text.setText("<html>" + finishedText + "</html>");
	}
	
	public static void saveReader(int saveLevel, int savePart, int saveLine, String saveLog) throws Exception {
		File saveFile = new File(Func.localFile + "/save.txt");
		File logFile = new File(Func.localFile + "/log.txt");
		System.out.println(saveFile);
		Scanner save = new Scanner(saveFile);
		saveLevel = save.nextInt();
		savePart = save.nextInt();
		saveLine = save.nextInt();
		Scanner log = new Scanner(logFile);
		saveLog = log.nextLine();

		MainCon.level = saveLevel;
		MainCon.part = Integer.toString(savePart);
		MainCon.currentLine = saveLine;
		MainCon.currentLog = saveLog;
		MainCon.newGame = false;
		save.close();
		log.close();
	}
}
