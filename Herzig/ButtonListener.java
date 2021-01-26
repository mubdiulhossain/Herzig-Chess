import java.awt.event.*;

public class ButtonListener implements ActionListener
{
    private Board board; // to access board's methods
	// current location x y for 2d array
    private int currentX; 
    private int currentY;
    
    //All private static variable are to keep track of the clicks 
    private static boolean flag = true;
    private static int previousX;
    private static int previousY;
	private static int stateChangeCounter=0;
	
	public ButtonListener(String state, int numberOfturn) // sets the number of turn's according the state of game
	{
		if(state == "loadGame")
		{
			stateChangeCounter = numberOfturn; 
		}
		else
		{
			stateChangeCounter = 0;
		}		
	}
    public ButtonListener(Board board, int currentX, int currentY) // working with pressed locations from board
    {
        this.board = board;
        this.currentX = currentX;
        this.currentY = currentY;
    }
    public void actionPerformed(ActionEvent evt) // pressed button functionality will work on here based on player turn and their first and second clicked button
	{
    	if(board.getTurn()=="red")
    	{
    		if(flag)
        	{
        		firstClick();
        	}
        	else
        	{
        		play();
        	}
    	}
    	else
    	{
    		if(board.getTurn() == "green")
    		{
                if(flag)
                {
                    firstClick();
                }
                else
                {
                    play();
                }
            }
    	}
    }
    private void firstClick() // in first click program checks the first pressed button. if it's not null and the same as current player it will skip
    {
    	if(board.getAllPieces()[currentX][currentY]!=null&&board.getAllPieces()[currentX][currentY].getColor()==board.getTurn())
    	{
    		//System.out.println(board.getAllPieces()[currentX][currentY].getColor()+board.getAllPieces()[currentX][currentY].getName());
    		//stores the value of current X and Y to previous for later uses.
			previousX = currentX; 
    		previousY = currentY;
    		flag = false; // setting the flag as false means it will go in the play method
    	}
    }
    private void play() // takes and apply decision based on pressed button and receive information from model, takes decision based on it
    {
    	if(board.getAllPieces()[currentX][currentY]==null||(board.getAllPieces()[currentX][currentY].getColor()!=board.getAllPieces()[previousX][previousY].getColor()))
        {
    		//checks if the piece can move to the specific location from  previous point to current pressed points. 
			//also checks if there is any piece(s) in its way
    		if(board.getAllPieces()[previousX][previousY].canMove(currentX, currentY)&&!board.getAllPieces()[previousX][previousY].piecesInWay(currentX, currentY, board.getAllPieces()))
    		{
    			//if the pressed piece is a heart based on the player turn the gameOver method can be called
    			if(board.getAllPieces()[currentX][currentY] instanceof Heart)
            	{
    				board.updatePieces(currentX, currentY, (Piece)board.getAllPieces()[previousX][previousY]);
        			board.getAllPieces()[currentX][currentY].setCurrX(currentX);
        			board.getAllPieces()[currentX][currentY].setCurrY(currentY);
        			
        			board.btn[currentX][currentY].setIcon(board.getAllPieces()[currentX][currentY].loadImage());           	
        			board.updatePieces(previousX, previousY, null);
        			board.btn[previousX][previousY].setIcon(null);
    				if(board.getAllPieces()[currentX][currentY].getColor()== "red")
    				{
    					board.gameOver("red");
    				}
    				else
    				{
    					board.gameOver("green");
    				}
            	}
    			else
    			{
					
    				stateChangeCounter++; // increases the stateChangeCounter.
    				//System.out.println(stateChangeCounter);
					//updates the pieces.
    				board.updatePieces(currentX, currentY, (Piece)board.getAllPieces()[previousX][previousY]);
        			board.getAllPieces()[currentX][currentY].setCurrX(currentX);
        			board.getAllPieces()[currentX][currentY].setCurrY(currentY);
        			//when the pressed button is arrowbox 
        			if(board.getAllPieces()[currentX][currentY] instanceof ArrowBox)
               	 	{
        				if((ArrowBox)board.getAllPieces()[currentX][currentY]!=null)
               		 	{
        					((ArrowBox) board.getAllPieces()[currentX][currentY]).checkIsItOnTop();
               		 	} 
               	 	}
        			if(stateChangeCounter  == 4) // checking each 4 turn to change state for star and cross
        			{
        				board.changePiece();
        				stateChangeCounter = 0;
        			}
					//setting the icon
        			board.btn[currentX][currentY].setIcon(board.getAllPieces()[currentX][currentY].loadImage());           	
        			//making the previous button null
					board.updatePieces(previousX, previousY, null);
        			board.btn[previousX][previousY].setIcon(null);
        			changeTurn(); // changing turn
    			}
    		}
        }
		flag = true;
    }
    public static int getStateChangeCounter() //returning state counter
    {
    	return stateChangeCounter;
    }
    private void changeTurn()
    {
        if(board.getTurn() == "red")
        {
        	board.changeBoard(-1); //if the turn is green, -1 means change the orientation of the board for green down 
        }
        else
        {
            board.changeBoard(1); //if the turn is red, 1 means change the orientation of the board for red down
        }
    }
}