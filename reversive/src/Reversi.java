// imports
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.awt.*;
public class Reversi extends JPanel implements Runnable
{


	//declaration 
	final private int SpaceSize=40;     //space size of the square
	final private int CellPadding=5;   //the gap to place the piece
	final private char board [][] = new char[9][9]; // the board set up
	private int x=2;// the starting number of black  piece
	private int o=2; // the starting number of white piece
	private int black=0;  //number of black piece win
	private int white=0;	//number of white piece win
	private int tie=0; //number of tie
	private int turn=0;  //turn counter // keep track of who's turn it is
	private int counter=0; // check to see if the board is fill up with pieces
	private boolean player;  // true for player vs comp.  false for player vs player
	private boolean gameOver=false;  // check to see if the game is over;
	private boolean  reset=true;//decide if there it is player vs computer or palyer vs player  : true = player vs comp ,   false = player vs player
	private boolean PNoMove =false; // PNoMove stands for player no more, if player has no move this will be change to true, use to see if the game should end
	private boolean P2NoMove =false; // P2NoMove stands for player 2 no more, if player 2 has no move this will be change to true, use to see if the game should end
	private boolean CNoMove =false; // CNoMove stands for computer no more, if computer has no move this will be change to true, use to see if the game should end
	private  String firstP=" "; // if player is true, then first p = player,second p = comp.
	private String secondP=" "; // if player is false  firstP=black and secondP=white
	private JTextArea score = new JTextArea("");//textarea to store the score of the current game
	private JTextArea points = new JTextArea("");//shows number of black and white peice
	private JTextArea turns = new JTextArea("");// shows who's turns it is, only shows up in player vs player mode
	
	//reference to these private variables
	public String getFirstP(){ 
		return firstP;//return firstP so that runReversi can refer to either player or black as the name
	} 
	public String getSecondP(){ 
		return secondP;//return secondP so that runReversi can refer to either computer or white as the name
	}
	 public int getWhite(){ 
		 return white;//runReversi can get the number of win that white has
	 } 
	 public int getBlack(){
		 return black;//runReversi can get the number of win that Black has
	 } 
	 public int getTie(){
		 return tie;//runReversi can get the number of tie game
	 } 
	 public void setPlayer(boolean a){ 
		 player=a;// from runReversi depends on the user, either player vs comp (true) or player vs player(false)
	 }
	 public void setScore(String a){ 
		 score.setText(a);//update the score
	 } 
	 public void setPoints(String a){ 
		 points.setText(a);//update the number of black and white piece
	 } 
	 public void setTurns(String a){
		 turns.setText(a);// shows who's turns it is, only comes up in player vs player mode
	 } 
	 public void setTurnsFG(Color a){
		 turns.setForeground(a); //set the foreground colour of turn
	 }
	 public void setTurnsBG(Color a){
		 turns.setBackground(a); // set the background colour of turn
	 } 
	 public JTextArea addPoint(){ 
		 return points; //runReversi use this to add it to the layout
	 }
	 public JTextArea addScore(){
		 return score; //runReversi use this to add it to the layout
	 }
	 public JTextArea addTurn(){
		 return turns; //runReversi use this to add it to the layout
	 } 
	 public void RESET(){
		 reset(); // if in runReversi restart button is push, this method is call, which restart the game
	 } 
	 //setting up button color etc, and the mouse click  - mouse listener
	public void clearBoard(){
		repaint(); //repaint everything
		for (int down = 0; down<9; down++){
        	for (int across = 0; across<9; across++){
        		board[down][across]=' ';  // make the game board all empty again
        	}
		}
	}
	public  Reversi(){ //open reversi
		score.setEditable(false);  // turn off the edit-able of score
		score.setBackground(Color.ORANGE);  // fill in the color of score
		points.setBackground(Color.BLACK); points.setForeground(Color.WHITE);// color for points
		points.setEditable(false);// turn points edit-able off
		turns.setBackground(Color.ORANGE);// set the turn's colour
		turns.setEditable(false); // turn off the Edit-able of turn, so that it is not write-able
        reset(); // reset the game on starting the game
        addMouseListener(  // mouse listener
            new MouseAdapter()
            {            
                public void mousePressed(MouseEvent e)
                {if((e.getX() / SpaceSize)>0 && (e.getX() / SpaceSize)<9 && (e.getY() / SpaceSize)>0 && (e.getY() / SpaceSize)<9){
                	move(e.getX() / SpaceSize, e.getY() / SpaceSize);//only runs if the mouse in inside the board,
                     												 //call move, which check if is valid the place where user clicked
                    }//close if
                }//close mouse event
            } //close mouse adapter
        ); //close mouse listener
       
    } //close reversi
	//reseting the game, make variable to default values
	private void reset(){
		repaint(); //repaint everything
		for (int down = 0; down<9; down++){
        	for (int across = 0; across<9; across++){
        		board[down][across]=' ';  // make the game board all empty again
        	}
		}
		
		gameOver=false; //is the game over?
		reset=true; //restarting the game
		firstP = (player) ? (firstP="Player"):(firstP="Black"); // in case the user press restart check which mode the user is in
		secondP = (player) ? (secondP="Comp"):(secondP="White");// so it knows what name to use for labels
		turns.setText('\n' +"" +""+"    TURNS");  // set the text
		turn=0;// reset the turn back to 0 again
		points.setText('\n' +""+'\n' +""+"    Black: " + 2 +'\n' + "    White: "+2); //the text of printing points
        	board[5][4]='x'; //set the default piece
        	board[4][5]='x';

        	board[4][4]='o';
        	board[5][5]='o';  
    		
	}
	// paint the Gui graphic interface
    public void paintComponent(Graphics g)// load paint in order to draw
    {	repaint();
    	 super.paintComponent(g);// load paint in order to draw oval, rect. etc.

     	/** Border for the game **/
     	Border  raisedbevel = BorderFactory.createRaisedBevelBorder();
     	Border  loweredbevel = BorderFactory.createLoweredBevelBorder();
     	
     	//Compound borders
     	Border compound1,compound2;
     	Border redline = BorderFactory.createLineBorder(Color.red);

     	//This creates a frame.
     	compound1 = BorderFactory.createCompoundBorder(
     				  raisedbevel, loweredbevel);
     	setBorder(compound1);

     	//Add a red outline to the frame.
     	compound2 = BorderFactory.createCompoundBorder(
     				  redline, compound1); 
     	setBorder(compound2);
     	/**  end of the Border **/
     	
     	
    	 g.setColor(Color.RED);
	        for(int StrightLine=1; StrightLine<10; StrightLine++){//draw the game board
	        	g.drawLine(40, SpaceSize * StrightLine, SpaceSize * 9, SpaceSize * StrightLine);//Draw horizontal Lines
	        	 g.drawLine(SpaceSize * StrightLine, 40, SpaceSize * StrightLine, SpaceSize * 9); //Draw Vertical Lines
	        }// close the drawing board for loop
	        if(reset==true){ reset=false;
			g.setColor(Color.BLACK);  //set the drawing colour to black   note black piece
	        //	board[5][4]='x';
	    		g.fillOval( SpaceSize * 5 + CellPadding, 
	            SpaceSize * 4 + CellPadding, 
	            SpaceSize - CellPadding * 2, 
	            SpaceSize - CellPadding * 2);
	       // 	board[4][5]='x';
	    		g.fillOval( SpaceSize * 4 + CellPadding, 
	    	            SpaceSize * 5 + CellPadding, 
	    	            SpaceSize - CellPadding * 2, 
	    	            SpaceSize - CellPadding * 2);
	        	
	    		g.setColor(Color.WHITE); //set the drawing colour to black   note white piece
	        //	board[4][4]='o';
	    		g.fillOval( SpaceSize * 4 + CellPadding, 
	    	            SpaceSize * 4 + CellPadding, 
	    	            SpaceSize - CellPadding * 2, 
	    	            SpaceSize - CellPadding * 2);
	        //	board[5][5]='o';
	    		g.fillOval( SpaceSize * 5 + CellPadding, 
	    	            SpaceSize * 5 + CellPadding, 
	    	            SpaceSize - CellPadding * 2, 
	    	            SpaceSize - CellPadding * 2);
	        }// close if

    	 else{   // the below double for loop is only use to paint the piece on the board, it use after a player or copmputer make a move.
    		 	 // note this paint paints over the piece, since the reversi flips other piece,  not just one piece, so it would be much faster
    		 	 // to code it this way, then putting the paint function in the flip method.
    		 for (int across = 1; across<9; across++){
    			 for (int down = 1; down<9; down++){
    				 if ( board[down][across] == 'x'){  //since 1 box could either be white or black, 
    					 g.setColor(Color.BLACK); 
    					 g.fillOval( SpaceSize * down + CellPadding, // since the piece is in the centre. the gap from start of box + cell padding 
    							 SpaceSize * across + CellPadding,  // equals the place where the peice are painted
    							 SpaceSize - CellPadding * 2, 
    							 SpaceSize - CellPadding * 2);
    				 }//close if
    				 else if ( board[down][across] == 'o'){ 
    					 g.setColor(Color.WHITE);
    					 g.fillOval( SpaceSize * down + CellPadding, 
    							 SpaceSize * across + CellPadding, 
    							 SpaceSize - CellPadding * 2,   //note SpaceSize(40) - cell padding(5*2)   / 2 edge
    							 SpaceSize - CellPadding * 2);
    				 }//close else if
    			 }//close innter for
    		 }//close outer for
    		 
 		char list []= new char[] {'1','2','3','4','5','6','7','8','A','B','C','D','E','F','G','H'};  // the label of the game
		int a = 0;//the counter use to go thought the list array
		g.setColor(Color.MAGENTA);// set the drawing colour
		for (int counter =65 ; counter<380; counter=counter+40){
			//String temp =Double.toString(counter);
			g.drawChars(list, a, 1, 25, counter); // draw the Vertical from 1-8
			++a; //add 1 to a
		}
		//a=8;
		for (int counter = 58 ; counter<340; counter=counter+40){
			//String temp =Double.toString(counter);
			g.drawChars(list, a, 1, counter, 35); // draw the horizontal from a-h
			++a;; //add 1 to a
		}
    	 }
    }  
    public void move (int down, int across){//takes in the integer which is divide by spaceSize now Corresponds to the game bord array 
		if (board[down][across]==' ' && turn%2==0 && ValidMove(down,across)==true && nomove()==false){
			PNoMove =false;  /// switch back to false
			turns.setText('\n' +"" +""+"    WHITE"); //set turn text to white // use as turn counter
			board[down][across]='x'; // if the move is valid, and it is user's turn and user does have a move // then register the move
			flip(down,across);       // flip the piece
			checking();				//calls back to checking
		}
		else if (board[down][across]==' ' && turn%2!=0 && ValidMove(down,across)==true && nomove()==false && player==false){
			// if the board is empty 
			P2NoMove =false; /// switch back to false
			turns.setText('\n' +"" +""+"    BLACK");  //set turn text to black // use as turn counter
			board[down][across]='o'; // if the move is valid, and it is user's turn and user does have a move // then register the move
			flip(down,across); 		// flip the piece
			checking();				//calls back to checking
		}
		else if(nomove()==true && counter!=64 && gameOver==false ){// check for no move and the game is not over
			JOptionPane.showMessageDialog(null, "NO SPOT TO PLAY, TURN SKIPPED","Turn Skip",JOptionPane.INFORMATION_MESSAGE);  //print the messege
			if(turn%2==0){PNoMove =true;turns.setText('\n' +"" +""+"    WHITE"); }// player 1 has no move
			else{P2NoMove =true;turns.setText('\n' +"" +""+"    BLACK"); } // player 2 has no move
			checking();} // calls back to checking
		else{
			if(gameOver==false) //else it is assume that the user click on a existing piece, out print the error messege
			JOptionPane.showMessageDialog(null, "Please enter a valid spot","Spot Already Taken",JOptionPane.YES_OPTION); 
		} 
		
	}//close method 
    private int endGame(){// use by the countpoints() to check if both player(computer) has no move,
    	if ( CNoMove ==true && PNoMove ==true ){JOptionPane.showMessageDialog(null, "You and computer both as no move to play, the game will now end","End Game",JOptionPane.INFORMATION_MESSAGE);   return 1; }
    	else if ( PNoMove ==true && P2NoMove ==true ){JOptionPane.showMessageDialog(null, "Both player has no move to play, the game will now end", "End Game",JOptionPane.INFORMATION_MESSAGE);   return 2;}
    	return 0;
    }
    private boolean nomove(){// checks to see if the current player or computer has a valid move
   		for(int a=1; a<9; a++){    // going through the array
   			for(int d=1; d<9; d++){
   				if(board[d][a]==' ' && ValidMove(d,a)==true ){return false;} // if there is a move , return false;
   			}
   		}//close outer for
   	return true; // else return true; as in true to no move to play.
   }
    private void computermove(){ //the computer method
    	 if(turn%2!=0 ){  // if it is computer's turn
    	int first = (int)(Math.random()*8+1); // generate 2 random numbers, use for random moves
    	int second =(int)(Math.random()*8+1);    	
    	if(board[first][second]==' ' && ValidMove(first,second)==true){ //if the 2 random moves are valid to play
    		CNoMove=false; // then reset the computer no move to false
    		board[first][second]='o'; // and register the move
    		flip(first,second);      // flip the pieces
    		checking();              // call back to checking

    	}
    	//if computer has no move, then out print the messege, and change computer no move to true and call back to checking
    	else if(nomove()==true){CNoMove=true;JOptionPane.showMessageDialog(null, "COMPUTER HAS NO MOVE, TURN SKIPPED","Turn Skip",JOptionPane.INFORMATION_MESSAGE); checking();}
    	else {computermove();} //else it is assume that the 2 random numbers are invalid, therefore we call this method again, as in a recurvise loop
    }
    
    }
    private void checking(){ //the brain of the program.
    	Graphics g= getGraphics();  
    	paintComponent(g);  // over-paints the game board etc..
       turn++;   // add 1 to turn counter
       countPoints();   // count up all the points 
       
       if (counter <64 && gameOver==false && player==true){ // too see if it is 1 player or 2 player and the game is not over
    	   
       countPoints();  // count the points again
       computermove();} // now we can call the computer's move
}//close method
    private boolean ValidMove(int down, int across){ // valid move
		char temp=' ';  boolean valid=false;
		if (turn%2==0){temp = 'x';} // if the turn is even then it is player 1's turn else it is computer or player 2's turn
		else {temp = 'o';}
		// this method check through every direction to see if the move is valid or in valid, even if 1 direction is valid, then the move is therefore valid
		// this method search through if there is a piece in the direction the user place, excluding the first 1.
		
		/** West **/
		for(int d=down-1; d>0; d=d-1){if(board[d][across]==temp && board[down-1][across]!=temp){ valid = true;break; } else if(board[d][across]==' ') {break;}} 
		/** East **/
		for (int d = down+1; d<9; d++){if(board[d][across]==temp&& board[down+1][across]!=temp){valid = true;break;} else if(board[d][across]==' ') {break;}} 
		/** North **/
		for(int a = across-1; a>0; a=a-1){if(board[down][a]==temp&& board[down][across-1]!=temp){valid = true;break; } else if(board[down][a]==' ') {break;}} 
		/** South **/
		for(int a = across+1; a<9; a++){if(board[down][a]==temp&& board[down][across+1]!=temp){valid = true;break; } else if(board[down][a]==' ') {break;}} 
		
		int d=down, a=across;
		/** North-West **/	
		d=d-1; a= a-1;
		while(d>0 &&  a>0){
			if (board[d][a]==temp&& board[down-1][across-1]!=temp){
				valid = true;break;
			}
			else if (board[d][a]==' '){
				break;
			}
			d=d-1;a=a-1;
		}
		/** North-East*/
		d=down+1; a=across-1;
		while(d<9 &&  a>0){
			if (board[d][a]==temp&& board[down+1][across-1]!=temp){
				valid = true;break;
			}
			else if (board[d][a]==' '){
				break;
			}
			d=d+1;a=a-1;
		}
		/** South-West*/
		d=down-1; a=across+1;
		while(d>0 &&  a<9){
			if (board[d][a]==temp&& board[down-1][across+1]!=temp){
				valid = true;break;
			}
			else if (board[d][a]==' '){
				break;
			}
			d=d-1;a=a+1;
		}
		/** South-East*/
		d=down+1; a=across+1;
		while(d<9 &&  a<9){
			if (board[d][a]==temp&& board[down+1][across+1]!=temp){
				valid = true;break;
			}
			else if (board[d][a]==' '){
				break;
			}
			d=d+1;a=a+1;
		}
		return valid;
	}
    private void flip(int down, int across){// almost same as above, just need to add a few extra line of code -- use the first piece found, and flip all the piece between
		char temp=' '; int last=0; // last, means the first piece it find in the direction
		if (turn%2==0){temp = 'x';} // same as above
		else {temp = 'o';}
		//find the last piece, and flip all the piece between the piece.
		/** West **/
		for(int d=down-1; d>0; d=d-1){last = 9;if(board[d][across]==temp&& board[down-1][across]!=temp){last=d;break; } else if(board[d][across]==' ') {last = down;break;}} 
		for(int d=down; d>last; d=d-1){board[d][across]=temp;}
		/** East **/
		for (int d = down+1; d<9; d++){last = 0;if(board[d][across]==temp&& board[down+1][across]!=temp){last=d;break; } else if(board[d][across]==' ') {last = down;break;}} 
		for (int d = down; d<last; d++){ board[d][across]  = temp;}
		/** North **/
		for(int a = across-1; a>0; a=a-1){last=9;if(board[down][a]==temp&& board[down][across-1]!=temp){last=a;break; } else if(board[down][a]==' ') {last = across;break;}} 
		for(int a = across; a>last; a=a-1){board[down][a]=temp;}
		/** South **/
		for(int a = across+1; a<9; a++){last=0;if(board[down][a]==temp&& board[down][across+1]!=temp){last=a;break; } else if(board[down][a]==' ') {last = across;break;}} 
		for(int a = across; a<last; a++){board[down][a]=temp;}
		
		int d=down, a=across,first=0,second=0;
		/** North-West  **/	
		d=d-1; a= a-1;
		first=9; second = 9;
		//finding piece
		while(d>0 &&  a>0){
			if (board[d][a]==temp){
				first=d; second = a;
				break;
			}
			else if (board[d][a]==' '){
				first=down; second = across;
				break;
			}
			d=d-1;a=a-1;
		}
		d=down-1; a=across-1;
		//flip the piece
		while(d>first &&  a>second){
			board[d][a]=temp;
			d=d-1;a=a-1;
		}
		/** North-East*/
		first=0; second = 9;
		d=down+1; a=across-1;
		//find the piece
		while(d<9 &&  a>0){
			if (board[d][a]==temp){
				first=d; second = a;
				break;
			}
			else if (board[d][a]==' '){
				first=down; second = across;
				break;
			}
			d=d+1;a=a-1;
		}
		d=down+1; a= across-1;
		//flip the piece
		while(d<first && a>second){
			board[d][a]=temp;
			d=d+1;a=a-1;
		}
		
		
		/** South-West*/
		first=9; second = 0;
		d=down-1; a=across+1;
		//find the piece
		while(d>0 &&  a<9){
			if (board[d][a]==temp){
				first=d; second = a;
				break;
			}
			else if (board[d][a]==' '){
				first=down; second = across;
				break;
			}
			d=d-1;a=a+1;
		}
		d=down-1; a= across+1;
		//flip the piece
		while(d>first &&  a<second){
			board[d][a]=temp;
			d=d-1;a=a+1;
		}
		
		/** South-East*/
		first=0; second = 0;
		d=down+1; a=across+1;
		//find the piece
		while(d<9 &&  a<9){
			if (board[d][a]==temp){
				first=d; second = a;
				break;
			}
			else if (board[d][a]==' '){
				first=down; second = across;
				break;
			}
			d=d+1;a=a+1;
		}
		d=down+1; a= across+1;
		//flip the piece
		while(d<first &&  a<second){
			board[d][a]=temp;
			d=d+1;a=a+1;
		}
		
	}

    private void countPoints(){// count the points
    	x=0; o=0;counter=0;  // counts the piece of each colour
    	for (int a= 1; a<9; a++){
        	for (int d = 1; d<9; d++){
        		if (board[d][a]=='x'){x++;}
        		else if (board[d][a]=='o'){ o++;}
        		}}
        	points.setText('\n' +""+'\n' +""+"    Black: " + x +'\n' + "    White: "+o); // re print the the ratio 
        	
        	for(int a=1; a<9; a++){ // check to if the board is full or not
        		for(int d=1; d<9; d++){
		        		if (board[d][a]!=' '){
		        			counter++;}
		        		} 
		    }//close outer loop
        	if(player==true ){// the wining message //  1 player mode
        		
	 if ((o==0 && x>0)|| endGame()==1){ ++black;score.setText( '\n' +""+"    "+"Player"+": " + black +'\n' + "    "+"Comp"+": "+ white +'\n' + "    "+"Tie: "+tie);
			 	     JOptionPane.showMessageDialog(null, "YOU WIN!"); gameOver=true;}
	if (x==0 && o>0){white++;score.setText( '\n' +""+"    "+"Player"+": " + black +'\n' + "    "+"Comp"+": "+ white +'\n' + "    "+"Tie: "+tie);
					JOptionPane.showMessageDialog(null, "YOU LOSE!");gameOver=true;}
		      if(counter==64 || endGame()==1){gameOver=true;
		        	if (x>o){++black;score.setText( '\n' +""+"    "+"Player"+": " + black +'\n' + "    "+"Comp"+": "+ white +'\n' + "    "+"Tie: "+tie);
		        	JOptionPane.showMessageDialog(null, "YOU WIN!");}
		        	else if (x<o){++white;score.setText( '\n' +""+"    "+"Player"+": " + black +'\n' + "    "+"Comp"+": "+ white +'\n' + "    "+"Tie: "+tie);
		        	JOptionPane.showMessageDialog(null, "YOU LOSE!");}
		        	else if(x==o){++tie;score.setText( '\n' +""+"    "+"Player"+": " + black +'\n' + "    "+"Comp"+": "+ white +'\n' + "    "+"Tie: "+tie);
		        	JOptionPane.showMessageDialog(null, "TIE GAME!");}
		      }
		      
		        	}
        	else if(player==false){ // the wining message // 2 player mode
        		 if (o==0 && x>0){ black++;score.setText( '\n' +""+"    "+firstP+": " + black +'\n' + "    "+secondP+": "+ white +'\n' + "    "+"Tie: "+tie);
		 	     JOptionPane.showMessageDialog(null, "BLACK WIN!");gameOver=true;}
        		 if (x==0 && o>0){white++;score.setText( '\n' +""+"    "+firstP+": " + black +'\n' + "    "+secondP+": "+ white +'\n' + "    "+"Tie: "+tie);
				JOptionPane.showMessageDialog(null, "WHITE WIN!");gameOver=true;}
	      if(counter==64 || endGame()==2){gameOver=true;
	        	if (x>o){black++;score.setText( '\n' +""+"    "+firstP+": " + black +'\n' + "    "+secondP+": "+ white +'\n' + "    "+"Tie: "+tie);
	        	JOptionPane.showMessageDialog(null, "BLACK WIN!");}
	        	else if (x<o){white++;score.setText( '\n' +""+"    "+firstP+": " + black +'\n' + "    "+secondP+": "+ white +'\n' + "    "+"Tie: "+tie);
	        	JOptionPane.showMessageDialog(null, "WHITE WIN!");}
	        	else if(x==o) {tie++;score.setText( '\n' +""+"    "+firstP+": " + black +'\n' + "    "+secondP+": "+ white +'\n' + "    "+"Tie: "+tie);
	        	JOptionPane.showMessageDialog(null, "TIE GAME!");}
	      }
        	}
}

    /******************************************************************** runable ****************************************************************/
	   // the animation text of reversi and othello
       int index=0;
	   Thread t = null; // create a thread
	 //  boolean threadSuspended; //
	   char list []= new char[] {' ','R','E','V','E','R','S','I',' ',' '};  // store the text in a array
	   char list1 []= new char[] {' ','O','T','H','E','L','L','O',' ',' '}; 
	   public void start() { // use to start the runnable of thread
	      if ( t == null ) { // if the thread is null
	         t = new Thread( this ); // create a new Thread
	       //  threadSuspended = false;
	         t.start(); // start the Thread
	      }
//	      else {
//	         if ( threadSuspended ) {
//	            threadSuspended = false;
//	            synchronized( this ) {
//	               notify();
//	            }
//	         }
//	      }
	   }
	   // Executed within the thread that this applet created.
	   public void run() { //running the Thread
		   while (true) {
	      try {	        
	            ++index;//add 1 to a
	            if(index==10){index=0;} // if the index reaches 10 reset to 0, so the cycle continues
	            repaint(6,20,5,360);    // only re paint a certain area in the game
	            repaint(380,20,5,360); 
	            Thread.sleep( 500 );  // interval given in milliseconds 
	         }
	      
	      catch (InterruptedException e) { }
	   }}
	   public void paint( Graphics g ) {  
		   super.paint(g);
	      if(index<=9){ // only change colour, if the index store a letter
		   switch (index){  // switching the colour
		   case 1:
			   g.setColor(Color.RED); break;
		   case 2:
			   g.setColor(Color.WHITE); break;
		   case 3:
			   g.setColor(Color.BLACK); break;
		   case 4:
			   g.setColor(Color.BLUE); break;
		   case 5:
			   g.setColor(Color.PINK); break;
		   case 6:
			   g.setColor(Color.ORANGE); break;
		   case 7:
			   g.setColor(Color.YELLOW); break;
		   case 8:
			   g.setColor(Color.CYAN); break;
		   default: break; // if the index has no letter, end switch
		   }} 
	      g.drawChars(list, index, 1, 10, (47*index)+10);  g.drawChars(list1, index, 1, 382, (47*index)+10); 
	      // print the letters 1 by 1
	      if(index==9){ // when the index is 9, which contains white space, paint all the letter out, in different colors, by using a for loop
	    	  for (int b =0 ; b<10; ++b){ // for loop, looping through the array
	    	  if(b<=10){
	    		  switch (b){ // switching colours
	    		   case 1:
	    			   g.setColor(Color.RED); break;
	    		   case 2:
	    			   g.setColor(Color.WHITE); break;
	    		   case 3:
	    			   g.setColor(Color.BLACK); break;
	    		   case 4:
	    			   g.setColor(Color.BLUE); break;
	    		   case 5:
	    			   g.setColor(Color.PINK); break;
	    		   case 6:
	    			   g.setColor(Color.ORANGE); break;
	    		   case 7:
	    			   g.setColor(Color.YELLOW); break;
	    		   case 8:
	    			   g.setColor(Color.CYAN); break;
	    		   default:break;
	    		   }
	    		g.drawChars(list, b, 1, 10, (47*b)+10);  // print out the letters
				g.drawChars(list1, b, 1, 382, (47*b)+10);
				} 
			} } 
	   }

	
}
