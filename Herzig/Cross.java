public class Cross extends Piece 
{
	public Cross(int x, int y, String color, int pieceNumber)
	{
		super(x,y, color, "Cross", pieceNumber);
	}
	@Override
	public boolean canMove(int x, int y) 
	{
		   if(((x - y) == (getCurrX() - getCurrY())) || ((x + y) ==  (getCurrX() + getCurrY())))
	        {
			   return true;
	        }
		   return false;
	}
	
	@Override
	public boolean piecesInWay(int x, int y, Piece[][] otherPiece)
	{
		 int tempX = getCurrX();
         int tempY = getCurrY();
         
             if(x > getCurrX() && y > getCurrY())
             {
                 while((tempX != x - 1) && (tempY != y - 1 ))
                 {

                 	tempX++;
                 	tempY++;
                     if(otherPiece[tempX][tempY] != null)
                     {
                         return true;
                     }
                    
                 }
             }
             if(x < getCurrX() && y < getCurrY())
             {
                
                 while((tempX != x + 1) && (tempY != y + 1))
                 {
                 	tempX--;
                 	tempY--;
                     if(otherPiece[tempX][tempY] != null)
                     {
                        
                         return true;
                      
                     }
                    
                 }
             }
         
             if((getCurrX() < x) && (getCurrY() > y))
             {
                
                 while((tempX != x - 1) && (tempY != y + 1)) 
                 {
                   
                 	tempX++;
                 	tempY--;
                    
                     if(otherPiece[tempX][tempY] != null)
                     {
                         return true;
                     }
                    
                 }
                
             }
             if((getCurrX() > x) && (getCurrY() < y))
             {
                 while((tempX != x + 1) && (tempY != y - 1))
                 {
                 	tempX--;
                 	tempY++;
                     if(otherPiece[tempX][tempY] != null)
                     {
                         return true;
                     }
                    
                 }
             }
             return false;
         
	}
}
