import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;




public class game implements ActionListener {

	private int n,m;
	private static JFrame frame;
	private JPanel choices;
	private static JButton easy,medium,hard,hint;
	private JLabel label;
	private Font font;
	public JButton[][] cards;
	public static int[][] cardsSelected;
	private board brd;
	private static int counter = 0;
	private static int prevVal=-1;
	private static int prevI=-1;
	private static int prevJ=-1;
	private static int correct = 0;
	private static int last1i,last1j,last2i,last2j;
	private static boolean sw = false;
	private static int mistakes = 0;

	
	public game() {
		// basics
		frame = new JFrame("Simple Memory Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1080, 900);
		frame.setLayout(new BorderLayout());
		
		// adding the elements and structuring them
		choices = new JPanel();
		easy = new JButton("Easy");
		medium = new JButton("Medium");
		hard = new JButton("Hard");
		hint = new JButton("Hint");
		choices.add(easy);
		choices.add(medium);
		choices.add(hard);
		label = new JLabel("<html>Hey there! This is a simple memory game coded in Java. <br/>Just pick your difficulty and the game will begin :)</html> ");
		font = new Font("Serif", Font.PLAIN, 25);
		label.setFont(font);
		easy.setFont(font);
		medium.setFont(font);
		hard.setFont(font);
		frame.add(label,BorderLayout.NORTH);
		frame.add(choices,BorderLayout.WEST);
		
		easy.addActionListener(this);
		medium.addActionListener(this);
		hard.addActionListener(this);
		hint.addActionListener(this);
		
		
		frame.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (game.easy == e.getSource()) {
			choices.removeAll();
	    	frame.remove(choices);
	    	label.setText("Have fun!");
	    	frame.repaint();
	    	brd = new board(0);
	    	
	    	choices.setLayout(new GridLayout(brd.n,brd.m));
	    	frame.add(choices,BorderLayout.CENTER);
	    	frame.add(hint,BorderLayout.EAST);
	    	
	    	cards = new JButton[brd.n][brd.m];
	    	cardsSelected = new int[brd.n][brd.m];
	    	
	    	for (int i = 0; i < brd.n; i++) {
	    		for (int j = 0; j < brd.m; j++) {
	    			cards[i][j] = new JButton();
	    			cards[i][j].addActionListener(this);
	    			choices.add(cards[i][j]);
	    			cardsSelected[i][j]=0;
				}
				
			}
		}else if(game.medium == e.getSource()) {
			choices.removeAll();
	    	frame.remove(choices);
	    	label.setText("Have fun!");
	    	frame.repaint();
	    	brd = new board(2);
	    	
	    	choices.setLayout(new GridLayout(brd.n,brd.m));
	    	frame.add(choices,BorderLayout.CENTER);
	    	frame.add(hint,BorderLayout.EAST);
	    	
	    	cards = new JButton[brd.n][brd.m];
	    	cardsSelected = new int[brd.n][brd.m];
	    	
	    	for (int i = 0; i < brd.n; i++) {
	    		for (int j = 0; j < brd.m; j++) {
	    			cards[i][j] = new JButton();
	    			cards[i][j].addActionListener(this);
	    			choices.add(cards[i][j]);
	    			cardsSelected[i][j]=0;
				}
				
			}
		}else if(game.hard == e.getSource()) {
			choices.removeAll();
	    	frame.remove(choices);
	    	label.setText("Have fun!");
	    	frame.repaint();
	    	brd = new board(4);
	    	
	    	choices.setLayout(new GridLayout(brd.n,brd.m));
	    	frame.add(choices,BorderLayout.CENTER);
	    	frame.add(hint,BorderLayout.EAST);
	    	
	    	cards = new JButton[brd.n][brd.m];
	    	cardsSelected = new int[brd.n][brd.m];
	    	
	    	for (int i = 0; i < brd.n; i++) {
	    		for (int j = 0; j < brd.m; j++) {
	    			cards[i][j] = new JButton();
	    			cards[i][j].addActionListener(this);
	    			choices.add(cards[i][j]);
	    			cardsSelected[i][j]=0;
				}
				
			}
		}else if(game.hint == e.getSource()) {
			
			//find button
			
			if(counter%2 != 0) {
				for (int i = 0; i < cards.length; i++) {
					for (int j = 0; j < cards[0].length; j++) {
						if((i != prevI || j != prevJ) && prevVal == brd.getBoard()[i][j]) {
							cards[i][j].setText("?");;
						}
					}
				}
			}
			
			
			
		}else { // its a card

			JButton clicked = (JButton) e.getSource();
			
			if(sw) {
				cards[last1i][last1j].setText(""); // this exists literally only because swing is awful and works sequentially
				cards[last2i][last2j].setText(""); // timer replacement
				sw = false;
			}
			
			//if we didnt match last two 2, then reset
			
			for (int i = 0; i < cards.length; i++) {
				for (int j = 0; j < cards[0].length; j++) {
					if(clicked == cards[i][j] && (i != prevI || j != prevJ) && cardsSelected[i][j] != 1) {
						int val = brd.getBoard()[i][j]; // get its value
						clicked.setText(Integer.toString(val)); // show it
						
						counter++; // u picked 1 card
						
						if(counter%2==0) {
							if(val == prevVal) {
								cardsSelected[prevI][prevJ] = 1;
								cardsSelected[i][j] = 1;
								correct++;
								prevVal = -1;
								prevI = -1;
								prevJ = -1;
							}else {
								mistakes++;
								last1i = i;
								last1j = j;
								last2i = prevI;
								last2j = prevJ;
								sw = true;
								prevVal = -1;
								prevI = -1;
								prevJ = -1;
								
							}
						}else {
							prevVal = val;
							prevI = i;
							prevJ = j;
						}
						}
					if(correct == ((cards.length * cards[0].length) /2)) {
						choices.removeAll();
				    	frame.remove(choices);
				    	frame.remove(hint);
				    	label.setText("You won!... You made " + mistakes +" mistakes! Thats quite mediocre.");
				    	frame.repaint();
						break;
					}
						
					}
				}
			}
			
		}
	
		
		    	
		    	
}
	
	


	