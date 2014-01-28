//imports
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class Reversi_main extends JFrame //implements Runnable
{

	/**
	 * @param args
	 * Program : Reversi/Othello
	 * Author  : Wen Wei Lin
	 * program description : play a simple reversi game with the user, or player vs player
	 * 						 program has build in score, turn, and auto detect for no moves  
	 */
	private static boolean player=true; // use to see if there is 1 player or 2
	private  Reversi game; // instance of the other class
	private static JFrame frame = new JFrame("~Game Menu~"); // the menu of the selecting 1 or 2 player 
	private static JFrame Help  = new JFrame("~instructions~"); // the frame use to show the instructions
	private static JFrame GameMenu  = new JFrame("~Reversi/Othello~");// the frame use to play
    private JButton restartButton = new JButton("Restart");  //restart button
    private  static JButton Player = new JButton("1 player");  // 1 player button
    private static JButton Player2 = new JButton("2 player");// 2 player button
    private  static JButton exit = new JButton("Exit"); // exit button
    private static JTextArea text = new JTextArea (" "); // use to display the instructions
    private  static JButton back = new JButton("Back"); // back button, in order to go back
    private static JButton instructions  = new JButton("instructions"); // the instructions button
    private  JButton instructions1  = new JButton("Help"); //another instructions button, but with different size
    private static boolean getVisible =true; // check to see if the frame or the gameFrame is displaying
    

	public static void main(String[] args) { // main method
		// TODO Auto-generated method stub
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
		
			text.setEditable(false); // set text editable to false, so the user cant edit it
			text.setForeground(Color.RED);  // set the colour.
			text.setBackground(Color.BLACK);
                MENU(); // call the the main frame, the menu
//            }
//        });

        
	
		
	}
	/*************************************************** Menu Set Up **************************************************************************/
	private static void MENU() {
		getVisible=true; // true - the main frame - frame is showsing
        //Create and set up the window.
        //JFrame frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // default close
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane()); // add the compoents
        
        //Size and display the window.
        Insets insets = frame.getInsets();   // get the size border of the frame
        frame.setSize(500 + insets.left + insets.right,  // set the size of the Insets
                      430 + insets.top + insets.bottom);
        exit.addMouseListener(  // exit button
                new MouseAdapter() {
                    public void mousePressed(MouseEvent a)
                    {    
	      System.exit(0); // exit the game
                    }
               }
            );
        Player.addMouseListener( // player button
                new MouseAdapter() {
                    public void mousePressed(MouseEvent a)
                    {  player =true;   getVisible=false; frame.dispose();
                    	//1 player mode // the frame is now change to game frame which is false// dispose the frame, as we have no further use of it
                    	new Reversi_main();  //call RunReversi
                    	
                    }
               }
            );
        Player2.addMouseListener( // 2 player button
                new MouseAdapter() {
                    public void mousePressed(MouseEvent a)
                    {      player =false;  getVisible=false; frame.dispose();
                  //2 player mode // the frame is now change to game frame which is false// dispose the frame, as we have no further use of it
                    	new Reversi_main();//call RunReversi
                    }
               }
            );
        instructions.addMouseListener(  //  instructions button
                new MouseAdapter() {
                    public void mousePressed(MouseEvent a)
                    
                    {    frame.setVisible(false);   // hide the frame 
                    instructions(0);//calls  instructions method, 0 is use ,so it knows that the frame calls, it and when the back button is push, it will show the frame
                 
                    }
               }
            );
        frame.setVisible(true); // show the frame
    }
	private static void addComponentsToPane(Container pane) {//add the components
        pane.setLayout(null); // make the layout of the container null
        Player.setPreferredSize(new Dimension(150,80)); // size of each button
        Player2.setPreferredSize(new Dimension(150,80));
        exit.setPreferredSize(new Dimension(130,60));
        instructions.setPreferredSize(new Dimension(130,60));  // colour of each button
        pane.setBackground(Color.BLUE); Player.setBackground(Color.RED); Player2.setBackground(Color.RED); exit.setBackground(Color.GREEN); instructions.setBackground(Color.BLACK);
        //add it all to the container
        pane.add(Player);
        pane.add(Player2);
        pane.add(exit);
        pane.add(instructions);
        // place the buttons
        Insets insets = pane.getInsets(); // get the size border of the container
        Dimension size = Player.getPreferredSize();
        Player.setBounds(60+ insets.left, 100 + insets.top,
                     size.width, size.height);
        size = Player2.getPreferredSize();
        Player2.setBounds(270 + insets.left, 100 + insets.top,
                     size.width, size.height);
        size = exit.getPreferredSize();
        exit.setBounds(180 + insets.left, 310 + insets.top,
                     size.width, size.height);
        size = instructions.getPreferredSize();
        instructions.setBounds(180 + insets.left, 220 + insets.top,
                     size.width, size.height);
    }
	public static void instructions (final int Switch) { // the instructions frame or Help frame
		Point MenuPoint = frame.getLocation(); // store the location of the frame in Menu point
		Point gameMenuPoint = GameMenu.getLocation(); // store the location of the frame in game Menu point
		if (getVisible==true){Help.setLocation(MenuPoint);} // if frame called this method then over lap the frame with this frame
		else {Help.setLocation(gameMenuPoint);} // else overlap the game frame with this
		
		Help.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // default close
	        //Set up the content pane.
		
		Help.setLayout(null); // make the help frame layout null
		back.setPreferredSize(new Dimension(130,60)); //size of the back button and text
		text.setPreferredSize(new Dimension(400,245));
		Help.setBackground(Color.BLACK);  // colour of the help button
			
		Insets insets = Help.getInsets(); // get the size border of the help frame
		 back.setBackground(Color.BLACK); // colours for the buttons 
		 Help.add(back); Help.add(text);  // add the buttons and text
		   
	     Dimension size = back.getPreferredSize(); // set the size

	      back.setBounds(180 + insets.left, 280 + insets.top, // the placement
	                     size.width, size.height);
	      size = back.getPreferredSize();
	        
	      insets = text.getInsets();      // get the size of the border of the text
		  size = text.getPreferredSize();
		      
		  // the text message display to the user
		  text.setText("Reversi is a game which default is 8x8 grid. at the start of the game, there" + '\n' +
	 "are four piece already place on the grid they are at area (4,5)(5,4) and "+'\n' +"(4,4)(5,5) there are two black piece and two white piece."+'\n' + 
	 "The objective of this game" +
	  "is to get as many piece(your piece color, "+'\n' +"which is black in this program) as you can. the rule are"+ '\n' +
	 "1. for every move you make you piece must be able to flip the opponent, thus gaining you piece number"+ '\n' +
	 "2. the flip occurs at every direction  - n, nw,w,sw,s,se,e,ne;"+'\n' +
	"3. the piece flips up to the next you piece eg"+'\n' +
	"                        OX"+'\n' +
	"                        OXOU"+'\n' +
	"        U = user's move.  if user place there piece there on " +'\n' +
	"        the right O will be flip like below" +'\n' +
	"                        OX"+'\n' +
	"                        OXXX"+'\n'+
	"or vist http://homepages.tcp.co.uk/~pcsol/reversiTutorial.htm");
		  
		  text.setBounds(50 + insets.left, 30 + insets.top,  // place the text
		                     size.width, size.height);
		 
		  
        Help.getContentPane().setBackground(Color.BLACK);  // change the background of the frame
        //Size and display the window.
        
        Help.setSize(500 + insets.left + insets.right,  // set the size of the Help frame
                430 + insets.top + insets.bottom);
	      back.addMouseListener(   // the back button
	                new MouseAdapter() {
	                    public void mousePressed(MouseEvent a)
	                    {    Help.dispose(); // dispose the frame
	                    	if (Switch==0){frame.setVisible(true); getVisible=true; } // if the frame(main frame) call this, show the frame, and getVisible is true because main frame is showing
	                    	else {frame.setVisible(false);  // else disable the frame, and show the game frame.
	                    		GameMenu.setVisible(true);
	                    	}
	                    }
	               }
	            );
	       
	      Help.setVisible(true); // show the Help frame
	}
	/**************************************************************game interface*************************************************************/
	private Reversi_main(){ //setting up the game.
		
		GameMenu.setLayout(new BorderLayout()); // the game menu
		game = new Reversi();// instance of the other class
		// use to change the gamescore board name
		if(player==true){game.setPlayer(true);game.setScore( '\n' +""+"    "+"Player"+": " + 0 +'\n' + "    "+"Comp"+": "+ 0 +'\n' + "    "+"Tie: "+0);
		instructions1.setBackground(Color.BLACK); instructions1.setForeground(Color.WHITE);}
		else {game.setPlayer(false);game.setScore( '\n' +""+"    "+"Black"+": " + 0 +'\n' + "    "+"White"+": "+ 0 +'\n' + "    "+"Tie: "+0);
		}
		//setting up the colour and the size
   		game.setTurnsFG(Color.WHITE);game.setTurnsBG(Color.BLACK);
   		GameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // default close
    	GameMenu.setSize(500, 430);
       // add the other class which is a JPanel in the game menu
    	GameMenu.add(game, BorderLayout.CENTER);
    	//addings the buttons to the container
    	JPanel con2 = new JPanel();
    	con2.add(restartButton);
    	con2.add(game.addPoint()); 
    	con2.add(game.addScore());  
    	GameMenu.add(con2,BorderLayout.EAST);  
    	// if there is only 1 player, then dont displayer the turn text, since it is useless, and the set the colour, and setting the grid
    	if(player ==false){ con2.add(game.addTurn()); con2.add(instructions1); instructions1.setBackground(Color.ORANGE); 
    	con2.setLayout(new GridLayout (5,1));}
        else if (player==true){con2.add(instructions1);con2.setLayout(new GridLayout (4,1)); }
    	
    	game.setBackground(Color.GREEN); game.setForeground(Color.RED);  // the background of the game
    	restartButton.setBackground(Color.ORANGE); // restart button colour
        
    	/** end of border and colours*/
    	
    	
        restartButton.addMouseListener( // restart button
                new MouseAdapter() {
                    public void mousePressed(MouseEvent e)
                    {    	 
                	game.RESET(); //tells other class to set the game
                	if(player==true){ // keep the value true 
                		game.setPlayer(true);}
                		else if(player=false){game.setPlayer(false);}
                	//show the message
                	game.setScore( '\n' +""+"    "+game.getFirstP()+": " + game.getBlack() +'\n' + "    "+game.getSecondP()+": "+ game.getWhite() +'\n' + "    "+"Tie: "+game.getTie() );
                    }
               }
            );
        instructions1.addMouseListener( //the other instructions1 button, 
                new MouseAdapter() {
                    public void mousePressed(MouseEvent e)
                    {    	 
                    GameMenu.setVisible(false); // hide the game frame
                	instructions(1); //  show the Help frame.
                	
                    }
               }
            );
       // frame.setVisible(false);
        game.start(); // start the runablle in the other class
        GameMenu.setVisible(true); // show the game frame.
    
    }

	
	
	
	

}
