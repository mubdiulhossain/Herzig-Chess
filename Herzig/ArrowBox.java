import javax.swing.ImageIcon;

public class ArrowBox extends Piece 
{
	private boolean didItWentToTop; //Checks if should be reversed
	private boolean isReveresed;	//a flag for is it reversed or not
	public ArrowBox(int x, int y, String color, int pieceNumber)
	{
		super(x,y, color, "Arrow", pieceNumber);
		this.didItWentToTop=false;
		this.isReveresed = false;
	}
	@Override
	
	public boolean canMove(int x, int y) 
	{
		if((getCurrX()-1==x||getCurrX()-2==x)&&getCurrY()==y&&getColor()=="red"&&!didItWentToTop)
		{
			return true;
		}
		if((getCurrX()+1==x||getCurrX()+2==x)&&getCurrY()==y&&getColor()=="red"&&didItWentToTop)
		{
			return true;
		}
		if((getCurrX()+1==x||getCurrX()+2==x)&&getCurrY()==y&&getColor()=="green"&&!didItWentToTop)
		{
			return  true;
		}
		if((getCurrX()-1==x||getCurrX()-2==x)&&getCurrY()==y&&getColor()=="green"&&didItWentToTop)
		{
			return true;
		}
		return false;
	}
	@Override
	public boolean piecesInWay(int x, int y, Piece[][] otherPiece)
	{
		if(getCurrX()-2==x&&getColor()=="red")
		{
			if((otherPiece[getCurrX()-1][getCurrY()]!=null))
			{
				return true;
			}
		}
		
		if(getCurrX()+2==x&&getColor()=="green")
		{
			if((otherPiece[getCurrX()+1][getCurrY()]!=null))
			{
				return true;
			}
		}
		return false;
	}
	
	//Sepecialized method of load image to make sure the picture is facing
	//the right direction
	@Override
	public ImageIcon loadImage()
    {
		String icon;
		if(didItWentToTop&&!isReveresed)
		{
			icon = getColor()+getName()+"Invert.png";
		}
		else
		{
			if(didItWentToTop&&isReveresed)
			{
				icon = getColor()+getName()+".png";
			}
			else
			{
				if(!didItWentToTop&&isReveresed)
				{
					icon = getColor()+getName()+"Invert.png";
				}
				else
				{
					icon = getColor()+getName()+".png";
				}
			}
		}
		this.image = new ImageIcon(this.getClass().getResource(icon)).getImage();
    	scaledImage = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
	
	//setter for didItWentToTop to enforce the proper direction
	public void checkIsItOnTop()
	{
		if(getCurrX()==0||getCurrX()==7)
		{
			if(!didItWentToTop)
			{
				didItWentToTop=true;
			}
			else
			{
				didItWentToTop=false;
			}
		}
	}
	
	//setter for isReveresed 
	public void boardReversed(boolean isReveresed)
	{
		this.isReveresed = isReveresed;
	}
}
