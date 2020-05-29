package d;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;


public class Window extends JFrame {
	
	//???? whats this
	private static final long serialVersionUID = 1L;
	
	//path choice addon because mixing choices
	static int but1result;
	static int but2result;
	static 	int but3result;
	
	JPanel main;
	int wRes = 1200;
	int hRes = 700;
	
	Font dialogueName = new Font("Comic Sans MS", Font.BOLD, 32);
	Font dialogueText = new Font("Comic Sans MS", Font.BOLD, 25);
	Font menuB = new Font("Comic Sans MS", Font.BOLD, 70);
	Font saveNLog = new Font("Comic Sans MS", Font.PLAIN, 17);	
	Font continueOrNew = new Font ("Comic Sans MS", Font.PLAIN, 22);
	
	
	//game objects
	static Color dialoguePanel = Color.white;
	static Color dialogueBorder = Color.black;
	static Color backgroundColor = Color.white;
	static Color nameColor = Color.black;
	static Color dialogueColor = Color.black;
	static Color buttonColor = new Color(240,240,240);
	static Color pressButtonColor = Color.gray;
	static Color saveBorder = Color.DARK_GRAY;
	static Color saveColor = Color.green;
	static Color logBorder = Color.DARK_GRAY;
	static Color logColor = Color.CYAN;
	
	static JLabel image;
	
	static JLabel text;
	static JLabel name;
	
	static JLabel but1;
	static JLabel but2;
	static JLabel but3;
	
	static JLabel logText;
	//for checking if choice is still visible and awaiting choice
	static JPanel choicePan;
	
	public Window(String type) {
		super(type);
		setSize(1200, 700);
		if(type.equals("menu")) {
			
			main = new JPanel();
			JLabel mainImagePan = new JLabel();
			ImageIcon menuImage = null;
			try {
				menuImage = new ImageIcon(ImageIO.read(new File(Func.imageFile + "/menu.png")));
			} catch (IOException e1) {
				System.out.println("no image");
			}
			
			JPanel play = new JPanel();
			JPanel others = new JPanel();
			JPanel quit = new JPanel();
			JLabel playLabel = new JLabel("Play");
			JLabel othersLabel = new JLabel("Others");
			JLabel quitLabel = new JLabel("Quit");
			
			JPanel playChoice = new JPanel();
			JLabel continueLabel = new JLabel("Continue", JLabel.CENTER);
			JLabel newLabel = new JLabel("New game", JLabel.CENTER);
			
			main.setLayout(null);
			play.setLayout(new GridBagLayout());
			others.setLayout(new GridBagLayout());
			quit.setLayout(new GridBagLayout());
			playChoice.setLayout(null);
			
			main.setBounds(0, 0, wRes, hRes);
			main.setBackground(backgroundColor);
			
			mainImagePan.setBounds(0, 0, wRes, hRes);
			mainImagePan.setIcon(menuImage);
			
			int xPos = 80;
			int yPos = 400;
			int w = 300;
			int h = 130;
				
			int playChoicex = 35;
			int playChoicey = 45;
			int playChoicew = 135;
			int playChoiceh = 85;

			play.setBackground(buttonColor);
			others.setBackground(buttonColor);
			quit.setBackground(buttonColor);
			playChoice.setBackground(backgroundColor);
			
			play.setBounds(xPos, yPos, w, h);
			others.setBounds(xPos + w + 50, yPos, w, h);
			quit.setBounds(xPos + w * 2 + 100, yPos, w, h);
			playChoice.setBounds(xPos - 20, yPos - 20, w + 40, h + 40);
			continueLabel.setBounds(playChoicex, playChoicey, playChoicew, playChoiceh);
			newLabel.setBounds(playChoicex + playChoicew, playChoicey, playChoicew, playChoiceh);
			
			play.setBorder(new LineBorder(Color.black, 4));
			others.setBorder(new LineBorder(Color.black, 4));
			quit.setBorder(new LineBorder(Color.black, 4));
			playChoice.setBorder(new LineBorder(Color.black, 3));
			continueLabel.setBorder(new LineBorder(Color.DARK_GRAY, 5));
			newLabel.setBorder(new LineBorder(Color.GRAY, 5));
			
			playChoice.setVisible(false);
			
			playLabel.setFont(menuB);
			othersLabel.setFont(menuB);
			quitLabel.setFont(menuB);
			
			continueLabel.setFont(continueOrNew);
			newLabel.setFont(continueOrNew);
			
			
			MouseAdapter mL = new MouseAdapter() {
				//change button color
				JPanel pressPanel;
				
				public void mousePressed(MouseEvent e) {
					
					if(e.getSource() == play || e.getSource() == others || e.getSource() == quit){
						pressPanel = (JPanel) e.getSource();
						pressPanel.setBackground(pressButtonColor);
					}
				}
				
				public void mouseReleased(MouseEvent e) {
					
					if(playChoice.isVisible()) {
						play.setVisible(false);
						if(e.getSource() == continueLabel) {
							System.out.println("continue");
							int saveLevel = 0;
							int savePart = 0;
							int saveLine = 0;
							String saveLog = "";
							try {
								Func.saveReader(saveLevel, savePart, saveLine, saveLog);
								dispose();
								MainCon.createGame();
							} catch(Exception missingSave) {
								continueLabel.setText("No save");
								continueLabel.setForeground(Color.red);
								System.out.println("save file error");
							}
							
						}
						
						if(e.getSource() == newLabel) {
							try {
								FileWriter saveWriter = new FileWriter(Func.localFile + "/save.txt", false);
								saveWriter.write("0\n\n0");
								saveWriter.close();
								dispose();
								//MainCon.createGame();
							} catch (IOException e1) {
								System.out.println("save file error");
							}
							System.out.println("new");
						}
						
						else if(e.getSource() == mainImagePan || e.getSource() == others || e.getSource() == quit) {
							playChoice.setVisible(false);
							play.setVisible(true);
						}
					}
					
					if(e.getSource() == play){
						playChoice.setVisible(true);
						play.setBackground(buttonColor);
						//dispose();
					}
					
					if(e.getSource() == others){
						others.setBackground(buttonColor);
						System.out.println("credits to loren");
					}
					
					if(e.getSource() == quit){
						quit.setBackground(buttonColor);
						System.exit(0);
					}
					
					
				}
			};
			
			playChoice.add(continueLabel);
			playChoice.add(newLabel);
			
			play.add(playLabel);
			others.add(othersLabel);
			quit.add(quitLabel);
			//these are the buttons/panels
			play.addMouseListener(mL);//check for utton press
			others.addMouseListener(mL);
			quit.addMouseListener(mL);
			playChoice.addMouseListener(mL);
			continueLabel.addMouseListener(mL);
			newLabel.addMouseListener(mL);
			mainImagePan.addMouseListener(mL);
			
			
			main.add(playChoice);
			main.add(play);
			main.add(others);
			main.add(quit);
			main.add(mainImagePan);
			add(main);
			
		}
		
		if(type.equals("game")) {
			
			main = new JPanel();
			JPanel diaPan = new JPanel();
			choicePan = new JPanel();
			
			image = new JLabel("", SwingConstants.CENTER);
			
			name = new JLabel();
			text = new JLabel();
			
			but1 = new JLabel();
			but2 = new JLabel();
			but3 = new JLabel();
			
			
			
			logText = new JLabel("<html>", JLabel.LEFT);
			//choicePans are unnecessary, but it gives that click effect since label are unable to change bg color
			JPanel but1Pan = new JPanel();
			JPanel but2Pan = new JPanel();
			JPanel but3Pan = new JPanel();
			
			JScrollPane logPan = new JScrollPane();
			
			JLabel log = new JLabel(" Log");
			JLabel save = new JLabel(" Save");
			JLabel menu = new JLabel(" Menu");
			
			
			diaPan.setLayout(null);
			choicePan.setLayout(null);
			main.setLayout(null);
			logPan.setLayout(null);
			setLayout(null);
			
			main.setBounds(0, 0, wRes, hRes);
			main.setBackground(backgroundColor);
			
			image.setBounds(0, 0, wRes, hRes);
			image.setLayout(null);
			
			//panel dimensions
			int x = 800;
			int y = 300;
			int xPos = 200;
			int yPos = 400;
			
			diaPan.setBounds(xPos, yPos, x, y);
			diaPan.setBackground(Color.white);
			diaPan.setBorder(new LineBorder(dialogueBorder, 5));
			
			choicePan.setBounds(0, 0, x, y);
			choicePan.setBackground(dialoguePanel);
			choicePan.setBorder(new LineBorder(dialogueBorder, 5));
			choicePan.setVisible(false);
			
			logPan.setBounds(680, 20, 500, 300);
			logPan.setBorder(new LineBorder(Color.black, 5));
			logPan.setBackground(Color.pink);
			logPan.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			logPan.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			logPan.setVisible(false);
			
			logText.setForeground(Color.black);
			logText.setBounds(10, 5, 480, 288);
		    logText.setHorizontalAlignment(JLabel.LEFT);
		    logText.setVerticalAlignment(JLabel.BOTTOM);
			
			text.setBounds(35, 80, 750, 300);
			text.setForeground(dialogueColor);
			text.setFont(dialogueText);
		    text.setHorizontalAlignment(JLabel.LEFT);
		    text.setVerticalAlignment(JLabel.TOP);
			
			name.setBounds(45, -104, 750, 300);
			name.setForeground(nameColor);
			name.setFont(dialogueName);
			
			int xPosChoice = 60;
			int yPosChoice = 50;
			int w = 680;
			int h = 50;
			
			but1.setBounds(xPosChoice, yPosChoice, w, h);
			but1.setForeground(dialogueColor);
			but1.setText("");
			but1.setFont(dialogueText);
			but1Pan.setBounds(xPosChoice, yPosChoice, w, h);
			but1Pan.setBackground(buttonColor);
			but1Pan.setBorder(new LineBorder(dialogueBorder, 3));

			but2.setBounds(xPosChoice, yPosChoice + h + 20, w, h);
			but2.setForeground(dialogueColor);
			but2.setText("");
			but2.setFont(dialogueText);
			but2Pan.setBounds(xPosChoice, yPosChoice + h + 20, w, h);
			but2Pan.setBackground(buttonColor);
			but2Pan.setBorder(new LineBorder(dialogueBorder, 3));

			but3.setBounds(xPosChoice, yPosChoice + 2 * h + 40, w, h);
			but3.setForeground(dialogueColor);
			but3.setText("");
			but3.setFont(dialogueText);
			but3Pan.setBounds(xPosChoice, yPosChoice + 2 * h + 40, w, h);
			but3Pan.setBackground(buttonColor);
			but3Pan.setBorder(new LineBorder(dialogueBorder, 3));
			
			save.setBounds(1100, 555, 70, 45);
			save.setForeground(saveColor);
			save.setFont(saveNLog);
			save.setBorder(new LineBorder(saveBorder, 4));
			
			log.setBounds(1100, 500, 70, 45);
			log.setForeground(logColor);
			log.setFont(saveNLog);
			log.setBorder(new LineBorder(saveBorder, 4));
			
			menu.setBounds(5, 5, 70, 45);
			menu.setForeground(Color.BLACK);
			menu.setFont(saveNLog);
			menu.setBorder(new LineBorder(saveBorder, 4));
			
			MouseAdapter sideBut = new MouseAdapter() {
				
				public void mouseReleased(MouseEvent e) {
					
					if(e.getSource() == log) {
						if(logPan.isVisible()) {
							logPan.setVisible(false);
						}
						else if(!logPan.isVisible()) {
							logPan.setVisible(true);
						}
					}
					
					if(e.getSource() == save) {
						Func.save();
					}
					
					if(e.getSource() == menu) {
						Func.wait = false;
						dispose();
					}
				}
			};
			
			MouseAdapter diaL = new MouseAdapter() {
				
				public void mousePressed(MouseEvent e) {
					if(choicePan.isVisible()) {
						if(e.getSource() == but1) {
							but1Pan.setBackground(pressButtonColor);
						}
						
						if(e.getSource() == but2) {
							but2Pan.setBackground(pressButtonColor);
						}

						if(e.getSource() == but3) {
							but3Pan.setBackground(pressButtonColor);
						}
					}
				}
				
				public void mouseReleased(MouseEvent e) {
					
					if(choicePan.isVisible()) {
						if(e.getSource() == but1) {
							but1Pan.setBackground(buttonColor);
							MainCon.part = MainCon.part.concat(Integer.toString(but1result));
							choicePan.setVisible(false);
						}
						
						if(e.getSource() == but2) {
							but2Pan.setBackground(buttonColor);
							MainCon.part = MainCon.part.concat(Integer.toString(but2result));
							choicePan.setVisible(false);
						}

						if(e.getSource() == but3) {
							but3Pan.setBackground(buttonColor);
							MainCon.part = MainCon.part.concat(Integer.toString(but3result));
							choicePan.setVisible(false);
						}
					}
					
					if(!choicePan.isVisible()) {
						if(e.getSource() == diaPan) {
							if(!Func.run) {
								Func.wait = false;
								Func.run = true;
							}
							else {
								Func.skip();
							}
						}
					}
				}
			};
			
			//choicePan is big fat blue
			diaPan.add(choicePan);
			diaPan.add(name);
			diaPan.add(text);
			diaPan.addMouseListener(diaL);
			
			but1.addMouseListener(diaL);
			but2.addMouseListener(diaL);
			but3.addMouseListener(diaL);
			
			save.addMouseListener(sideBut);
			log.addMouseListener(sideBut);
			menu.addMouseListener(sideBut);
			
			logPan.add(logText);
			
			choicePan.add(but1);
			choicePan.add(but2);
			choicePan.add(but3);
			choicePan.add(but1Pan);
			choicePan.add(but2Pan);
			choicePan.add(but3Pan);
			
			main.add(logPan);
			main.add(save);
			main.add(log);
			main.add(menu);
			main.add(diaPan);
			main.add(image);
			add(main);
			
		}
		
		

		setVisible(true);
		//setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
