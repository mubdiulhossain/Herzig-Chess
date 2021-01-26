import java.awt.*;
import javax.swing.*;

//Parent class that makes an abstract model for all concrete classes
public abstract class Piece{
    
    private String name;
    private int currX;
    private int currY;
    private String color;
    protected Image image;
    protected Image scaledImage;
    private int pieceNumber;
	
	//Main piece constructor
    public Piece(int currX, int currY, String color,String name, int pieceNumber)
    {
    	this.pieceNumber=pieceNumber;
    	this.currX=currX;
    	this.currY=currY;
    	this.name=name;
    	this.color = color;
    	this.image = new ImageIcon(this.getClass().getResource(color+name+".png")).getImage();
    }
	
	//Getters and setters
    public int getCurrX()
    {
    	return currX;
    }
	
    public int getCurrY()
    {
    	return currY;
    }
	
    public void setCurrX(int currX)
    {
    	this.currX=currX;
    }
	
    public void setCurrY(int currY)
    {
    	this.currY=currY;
    }
	
    public String getName()
    {
    	return name;
    }
	
    public String getColor()
    {
    	return color;
    }
	
    public int getPieceNumber()
    {
    	return pieceNumber;
    }
	
    public void setPieceLocation(int pieceNumber)
	{
		this.pieceNumber = pieceNumber;
	}
	
	//Methods
	
	//check if the particuler piece can move or not
    public abstract boolean canMove(int x,int y);

	//check if the particuler piece has a piece blocking its way
    public abstract boolean piecesInWay(int x, int y, Piece[][] otherPiece);
	
	//Returns the processed image
    public ImageIcon loadImage()
    {
    	scaledImage = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}