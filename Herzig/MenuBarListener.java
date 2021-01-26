import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class MenuBarListener implements ActionListener 
{
	private Board board;
	private ButtonListener listner;
	
    //To show the count for the state change
	
    private static int countedturn;
	
	JFileChooser fileChooser;
	public MenuBarListener(Board board)
	{
		fileChooser = new JFileChooser();
		this.board=board;
		
	}
	public void actionPerformed(ActionEvent event) 
	{
		
		if(event.getSource()==board.newGame)
		{
			//Dispose does NOT delete the object
			//it just wipes its content to be able to reset it
			//not violating the singlton pattern
			board.dispose();
			new ButtonListener("NewGame", -1);
			board.reset();
		}
		if(event.getSource()==board.saveGame && !check())
		{
			countedturn = listner.getStateChangeCounter();
			if (fileChooser.showSaveDialog(board) == JFileChooser.APPROVE_OPTION)
			{
				File file = fileChooser.getSelectedFile();
				PrintWriter writer;
				try {
					writer = new PrintWriter(file, "UTF-8");
					writer.println(board.getCurrentRotation());
					//This loop goes through all the pieces and writes
					//the needed data in the correct order to be able to load later on
					for(int i=0;i<board.getAllPieces().length;i++)
					{
						for(int j=0;j<board.getAllPieces()[0].length;j++)
						{
							if(board.getAllPieces()[i][j]!=null)
							{
								writer.println(i+","+j+":"+board.getAllPieces()[i][j].getColor()+"="+board.getAllPieces()[i][j].getName()+"-"+board.getAllPieces()[i][j].getPieceNumber());
							}
						}
					}
					writer.println("turn:"+ countedturn);
					writer.close();
				}
				catch (FileNotFoundException | UnsupportedEncodingException e)
				{
					e.printStackTrace();
				}
				
			}
		}
		if(event.getSource()==board.loadGame && !check())
		{
		
			if (fileChooser.showOpenDialog(board) == JFileChooser.APPROVE_OPTION)
			{
				int x = 0;
				File file = fileChooser.getSelectedFile();
				FileReader fr;
				try 
				{
					fr = new FileReader(file);
					BufferedReader br = new BufferedReader(fr);
					String line;
					try 
					{
						board.resetPieces();
						int rotation=1;
						//Goes through line by line
						//to be able to read the saved file
						//according to the protocol governed in the save method
						while((line = br.readLine()) != null)
						{
							if(line.length()==1||line.length()==2)
							{
								rotation=Integer.parseInt(line);
							}
							else if(line.length() == 6 || line.length() == 7)
							{
								String[] info;
								info = line.split(":");
								x = Integer.parseInt(info[1]);
								
							}
							else
							{
								String[] namesAndCoOrds, coOrdinates, colorName, colorNamePiece;
								String value1, value2 = null;
								int coOrdinatesX = 0;
								int coOrdinatesY = 0;
								int pieceNo=0;
								namesAndCoOrds = line.split(":"); 
								
								value1 = namesAndCoOrds[0];
								value2 = namesAndCoOrds[1];
								
								coOrdinates = value1.split(",");
								colorNamePiece = value2.split("-");
								colorName = colorNamePiece[0].split("=");
								
								coOrdinatesX = Integer.parseInt(coOrdinates[0]);
								coOrdinatesY = Integer.parseInt(coOrdinates[1]);
								pieceNo = Integer.parseInt(colorNamePiece[1]);
								board.loadGame(coOrdinatesX, coOrdinatesY, pieceNo, colorName[0], colorName[1]);
							}	
						}
						new ButtonListener("loadGame", x);
						
						board.changeBoard(rotation);
					} 
					catch (IOException e)
					{
						e.printStackTrace();
					}
				} 
				catch (FileNotFoundException e1) 
				{
					e1.printStackTrace();
				}
			}
		}
		if(event.getSource()==board.exit)
		{
			System.exit(0);
		}
		if(event.getSource() == board.rules)
		{
			String text = "- The Arrow Box can only move forward, 1 or 2 steps. If it reaches the end of the board, it turns around and starts heading back the other way.\r\n" + 
					"- The Star can move one or two steps in any direction.\r\n" + 
					"- The Cross can only move diagonally, but can go any distance.\r\n" + 
					"- The Heart can move only one step in any direction.\r\n" + 
					"- After 4 turns, a Star will turn into a Cross, and a Cross will turn into a Star.\r\n" +
					"- The game ends when the heart is captured by the other side.";
		    JOptionPane.showMessageDialog(board,text, "Game rules",
		    JOptionPane.NO_OPTION);
		}
	}
	public boolean check()
	{
		if(board.isGameOver() == true)
		{
			return true;
		}
		return false;		
	}
	
}
