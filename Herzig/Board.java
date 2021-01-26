import java.awt.*;
import javax.swing.*;


public class Board extends JFrame // board is a view extends JFrame
{
	private static Board instance = new Board();
	private boolean isOver; // is to check if the game is over
    JButton[][] btn = new JButton[8][5]; // 2d array of Jbutton which shows the image of pieces.
    JLabel currentTurnLabel,numberOfTurn; // Label to show number of turns and player's turn
	JMenuBar menuBar; // creating MenuBar
	private MenuBarListener menuListener;
	JMenu file, about; // menu
	JMenuItem newGame, saveGame, loadGame, exit, rules; // menu items
    private static int totalTurn; // to show the totalTurn
    private ButtonListener listener; 
	
    private Piece[][] locationID = new Piece[8][5]; // creating 2d piece array which correlate to buttons
    
	// creating 16 pieces
    private ArrowBox[] redArrow = new ArrowBox[3]; 
    private ArrowBox[] greenArrow = new ArrowBox[3];
    
    private Cross[] redCross = new Cross[4];
    private Cross[] greenCross = new Cross[4];
    
    private Star[] redStar = new Star[4];
    private Star[] greenStar = new Star[4];
    
    private Heart[] redHeart = new Heart[1];
    private Heart[] greenHeart = new Heart[1];
    
    private Piece[] piece = new Piece[16];
	
    //setting the first turn for red by defualt 
    private String turn = "green"; 
    
    private int currentRotation; // represents current rotation aka player turns.
    
    JPanel jp = new JPanel(new GridLayout(8,5,2,2)); // setting panels
    JPanel tp = new JPanel(new FlowLayout());
    
    private Board() // singleton Board Constructor
    {
		//initializing constructor.
		super("Herzig Chess"); // setting window title.
		//initializing label.
		currentTurnLabel = new JLabel(); 
        numberOfTurn = new JLabel();
        reset(); // initialize method to initialize every object. also act as new Game functionality
    }
	public static Board getInstance()
	{
		return instance;
	}	
	public void reset() // method to initialize everything from start
	{
		isOver = false; // setting game over false.
		currentRotation = 1; // setting rotation for red down
		menuBar = new JMenuBar(); // adding menuBar to board
		menuListener = new MenuBarListener(this);
        initMenuBar(); // method to initialize Menubar
        resetPieces(); // resetPieces indicates to set all locationID(Piece) to null
		initPiece(); // initialize sub class pieces
        changeBoard(currentRotation); //loading the buttons. current position is red down
		
		for(int p=0;p<piece.length;p++) // adding piece to locationID
        {
    		locationID[piece[p].getCurrX()][piece[p].getCurrY()] = piece[p];
    		btn[piece[p].getCurrX()][piece[p].getCurrY()].setIcon(piece[p].loadImage());
        }
		//adding and setting up layouts, view etc.
		tp.add(currentTurnLabel); 
		tp.add(numberOfTurn);
		jp.setBackground(new Color(49, 87, 117));
		
        this.setLayout(new BorderLayout());
        add(jp,BorderLayout.CENTER);
        add(tp,BorderLayout.NORTH);
        setSize(450,650);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	}
	private void initMenuBar() //initializing Menubar, Menu, Menu Item and adding them to the board
	{
		newGame= new JMenuItem("New Game");    
        saveGame= new JMenuItem("Save");    
        loadGame= new JMenuItem("Load");    
        exit= new JMenuItem("Exit");
        rules = new JMenuItem("Game Rules");
		
        newGame.addActionListener(menuListener);
        saveGame.addActionListener(menuListener);
        loadGame.addActionListener(menuListener);
        exit.addActionListener(menuListener);
        rules.addActionListener(menuListener);
                
        file = new JMenu("File");
        about = new JMenu("About");
        
        file.add(newGame);
        file.addSeparator();
        file.add(saveGame);
        file.add(loadGame);
        file.addSeparator();
        file.add(exit);
        
        about.add(rules);
		menuBar.add(file);
		menuBar.add(about);
		this.setJMenuBar(menuBar);
	}
	private void initPiece() // initialize the 16 pieces
    {
    	redArrow[0] = new ArrowBox(6,1, "red", 0);
        redArrow[1] = new ArrowBox(6,2, "red", 1);
        redArrow[2] = new ArrowBox(6,3, "red", 2);
        //player 2's greenArrow
        greenArrow[0] = new ArrowBox(1,1, "green", 0);
        greenArrow[1] = new ArrowBox(1,2, "green", 1);
        greenArrow[2] = new ArrowBox(1,3, "green", 2);
        
        redCross[0] = new Cross(7,1, "red", 0);
        redCross[1] = new Cross(7,3, "red", 1);
        redCross[2] = new Cross(-1,-1, "red", 2);
        redCross[3] = new Cross(-1,-1, "red", 3);
        
        greenCross[0] = new Cross(0,1, "green", 0);
        greenCross[1] = new Cross(0,3, "green", 1);
        greenCross[2] = new Cross(-1,-1, "green", 2);
        greenCross[3] = new Cross(-1,-1, "green", 3);
        
        redStar[0] = new Star(7,0, "red", 0);
        redStar[1] = new Star(7,4, "red", 1);
        redStar[2] = new Star(-1,-1, "red", 2);
        redStar[3] = new Star(-1,-1, "red", 3);
        
        greenStar[0] = new Star(0,0, "green", 0);
        greenStar[1] = new Star(0,4, "green", 1);
        greenStar[2] = new Star(-1,-1, "green", 2);
        greenStar[3] = new Star(-1,-1, "green", 3);
        
        redHeart[0] = new Heart(7,2, "red", 0);
        greenHeart[0] = new Heart(0,2, "green", 0);
        
		//adding those 16 sub pieces in an array of Piece
        for(int i=0;i<16;i++)
        {
        	if(i<3)
        	{
        		piece[i] = (ArrowBox)redArrow[i]; //upcasting and adding redArrows in pieces
        	}
        	if(i>2&&i<6)
        	{
        		piece[i] = (ArrowBox)greenArrow[i-3]; //upcasting and adding greenArrows in pieces
        	}
        	if(i>5&&i<8)
        	{
        		piece[i] = (Cross)redCross[i-6];
        	}
        	if(i>7&&i<10)
        	{
        		piece[i] = (Cross)greenCross[i-8];
        	}
        	if(i>9&&i<12)
        	{
        		piece[i] = (Star)redStar[i-10];
        	}
        	if(i>11&&i<14)
        	{
        		piece[i] = (Star)greenStar[i-12];
        	}
        	if(i>13&&i<15)
        	{
        		piece[i] = (Heart)redHeart[i-14];
        	}
        	if(i>14)
        	{
        		piece[i] = (Heart)greenHeart[i-15];
        	}
        }
    }
    private void updatePlayerTurn() // will show the message which player's turn it is
    {
    	totalTurn = listener.getStateChangeCounter(); // get number of the total turn by both players
    	if(currentRotation==1) 
    	{
    		currentTurnLabel.setForeground(Color.red);
    		currentTurnLabel.setText("Red's Turn  ");
    	}
    	else
    	{
    		if(currentRotation==-1)
    		{
    			currentTurnLabel.setForeground(new Color(0,153,0));
    			currentTurnLabel.setText("Green's Turn  ");
    		}
    	}
		numberOfTurn.setText("  Number Of Turn : " + String.valueOf(totalTurn)); // to show the totalTurn 
    }
    public Piece[][] getAllPieces() // returns the 2d array of locationID
    {
    	return locationID;
    }
    public void updatePieces(int i, int j, Piece p) // assign a piece to a specific locationID 
    {
    	locationID[i][j] = p;
    }
    
    public String getTurn() // returns the current turn
    {
    	return turn;
    }
    public void setTurn(String turn) // setting the turn
    {
    	this.turn=turn;
    }
    public void loadGame(int x, int y, int pieceNo, String pieceColor,String pieceName) //updating the pieces based on loading a file
    {
    	if(pieceNo>1&&(pieceName.equals("Cross")||(pieceName.equals("Star")))) //extra pieces such as star and cross, Storing into location if found
    	{
    		if((pieceColor+pieceName).equals("redCross"))
        	{	
    			redCross[pieceNo].setCurrX(x);
        		redCross[pieceNo].setCurrY(y);
        		redCross[pieceNo].setPieceLocation(pieceNo);
        		locationID[x][y] = redCross[pieceNo];
        	}
    		if((pieceColor+pieceName).equals("greenCross"))
    		{
    			greenCross[pieceNo].setCurrX(x);
    			greenCross[pieceNo].setCurrY(y);
    			greenCross[pieceNo].setPieceLocation(pieceNo);
    			locationID[x][y] = greenCross[pieceNo];
    		}
    		if((pieceColor+pieceName).equals("redStar"))
    		{
    			redStar[pieceNo].setCurrX(x);
    			redStar[pieceNo].setCurrY(y);
    			redStar[pieceNo].setPieceLocation(pieceNo);
    			locationID[x][y] = redStar[pieceNo];
    		}
    		if((pieceColor+pieceName).equals("greenStar"))
    		{
    			greenStar[pieceNo].setCurrX(x);
    			greenStar[pieceNo].setCurrY(y);
    			greenStar[pieceNo].setPieceLocation(pieceNo);
    			locationID[x][y] = greenStar[pieceNo];
    		}
    	}
    	else //if no extra pieces found stores normally
    	{
    		for(int i=0;i<piece.length;i++)
        	{
        		String colorName = (piece[i].getColor()+piece[i].getName());
        		if((colorName.equals((pieceColor+pieceName)))&&(piece[i].getPieceNumber()==pieceNo))
        		{
        			piece[i].setCurrX(x);
        			piece[i].setCurrY(y);
        			locationID[x][y]=piece[i];
        			return;
        		}
        	}
    	}
    }
    public void resetPieces()
    {
    	for(int i=0;i<8;i++)
    	{
    		for(int j=0;j<5;j++)
    		{
    			locationID[i][j]=null;
    		}
    	}
    }
    public void changeBoard(int n) //opposites the board's button based on direction facing 
    { 
    	this.currentRotation=n;
    	updatePlayerTurn();
    	if(n==-1) // -1 is for green player
    	{
    		this.turn = "green";
    		jp.removeAll(); //removing everything from the panel
    		for(int i = 7; i>=0; i--)
        	{ 
    			for(int j = 4; j>=0; j--)
                {
                    btn[i][j] = new JButton();
                    btn[i][j].setBackground(Color.WHITE);
                    btn[i][j].addActionListener(new ButtonListener(this, i,j));
                    jp.add(btn[i][j]);
                    updateIcon(i, j, true);
                }
            }
    	}
    	if(n==1) // 1 is for red player
    	{
    		this.turn = "red";
    		jp.removeAll();
    		for(int i = 0; i<8; i++)
        	{
                for(int j = 0; j<5; j++)
                {
                    btn[i][j] = new JButton();
                    btn[i][j].setBackground(Color.WHITE);
                    btn[i][j].addActionListener(new ButtonListener(this, i,j));
                    jp.add(btn[i][j]);
                    updateIcon(i, j, false);
                }
            }
    	}
    }
    public void updateIcon(int i, int j, boolean reverse) // updates the icon on the button
    {
    	if(locationID[i][j] instanceof ArrowBox) // arrowbox is special because arrowbox's icon changes direction based on player turn. here the method fetches the reveresed icon
		{
			((ArrowBox) locationID[i][j]).boardReversed(reverse);
		}
        if(locationID[i][j]!=null) // if the location is null it will not set any icon.
        {
        	btn[i][j].setIcon(locationID[i][j].loadImage());
        }
    }
    public int getCurrentRotation() // gets the currentRotation
    {
    	return currentRotation;
    }
    public void changePiece() //important method to change the cross to star and vice versa
    {
    	for(int i=0;i<8;i++)
    	{
    		for(int j=0;j<5;j++)
        	{
        		if(locationID[i][j] instanceof Star)
        		{
        			stateChangeStar(i, j, ((Star) locationID[i][j]).getColor(), ((Star) locationID[i][j]).getPieceNumber());
        		}
        		else
        		{
        			if(locationID[i][j] instanceof Cross)
            		{
            			stateChangeCross(i, j, ((Cross) locationID[i][j]).getColor(), ((Cross) locationID[i][j]).getPieceNumber());
            		}
        		}
        	}
    	}
    }
	private void stateChangeStar(int i, int j, String color, int pieceNo) // changes the state of a specific location for star to cross
    {
    	if(color.equals("red"))
		{
			if(pieceNo<2)
			{
				redCross[pieceNo+2].setCurrX(i);
				redCross[pieceNo+2].setCurrY(j);
				redCross[pieceNo+2].setPieceLocation(pieceNo+2);
				locationID[i][j] = redCross[pieceNo+2];
			}
    		else
    		{
    			redCross[pieceNo-2].setCurrX(i);
    			redCross[pieceNo-2].setCurrY(j);
    			redCross[pieceNo-2].setPieceLocation(pieceNo-2);
    			locationID[i][j] = redCross[pieceNo-2];
    		}
		}
    	else
    	{
    		if(pieceNo<2)
        	{
       			greenCross[pieceNo+2].setCurrX(i);
       			greenCross[pieceNo+2].setCurrY(j);
       			greenCross[pieceNo+2].setPieceLocation(pieceNo+2);
       			locationID[i][j] = greenCross[pieceNo+2];
       		}
       		else
       		{
       			greenCross[pieceNo-2].setCurrX(i);
       			greenCross[pieceNo-2].setCurrY(j);
       			greenCross[pieceNo-2].setPieceLocation(pieceNo-2);
       			locationID[i][j] = greenCross[pieceNo-2];
       		}
    	}
    }
    private void stateChangeCross(int i, int j, String color, int pieceNo) // changes the state of a specific location for cross to star
    {
    	if(color.equals("red"))
		{
			if(pieceNo<2)
			{
				redStar[pieceNo+2].setCurrX(i);
				redStar[pieceNo+2].setCurrY(j);
				redStar[pieceNo+2].setPieceLocation(pieceNo+2);
				locationID[i][j] = redStar[pieceNo+2];
			}
    		else
    		{
    			redStar[pieceNo-2].setCurrX(i);
    			redStar[pieceNo-2].setCurrY(j);
    			redStar[pieceNo-2].setPieceLocation(pieceNo-2);
    			locationID[i][j] = redStar[pieceNo-2];
    		}
		}
    	else
    	{
    		if(pieceNo<2)
        	{
       			greenStar[pieceNo+2].setCurrX(i);
       			greenStar[pieceNo+2].setCurrY(j);
       			greenStar[pieceNo+2].setPieceLocation(pieceNo+2);
       			locationID[i][j] = greenStar[pieceNo+2];
       		}
       		else
       		{
       			greenStar[pieceNo-2].setCurrX(i);
       			greenStar[pieceNo-2].setCurrY(j);
       			greenStar[pieceNo-2].setPieceLocation(pieceNo-2);
       			locationID[i][j] = greenStar[pieceNo-2];
       		}
    	}
    }
    public void gameOver(String color) // checks the turn and finishes the game. showing results, disabling icons etc
    {
    	    isOver = true;
            for(int i = 0; i < 8; i++)
            {
            	for(int j = 0; j < 5; j++)
            	{
            		btn[i][j].setEnabled(false);
            	}
            }
            ImageIcon icon = new ImageIcon("trophy.png");
            if(color == "red")
            {
                  JOptionPane.showMessageDialog(null,"Congratulations!\r\nRed Won The Game","Game Over",JOptionPane.PLAIN_MESSAGE,icon);
            }
            else
			{
				JOptionPane.showMessageDialog(null,"Congratulations!\r\nGreen Won The Game","Game Over",JOptionPane.PLAIN_MESSAGE,icon);
			}           
    }
    public boolean isGameOver() // checks the gameOver state.
    {
    	return isOver;
    }
}