public class Star extends Piece{

    public Star(int x, int y, String color, int pieceNumber)
    {    
        super(x,y, color, "Star", pieceNumber);
        
    }
    @Override
    public boolean canMove(int x,int y){
        
        if(isDiagonal(x,y) || isHorizontal(x,y)){
            return true;
        }
    return false;
    }
    public boolean isDiagonal(int x,int y){
        if((x == getCurrX() - 1 || x == getCurrX() + 1 )  && (y == getCurrY() - 1 || y == getCurrY() + 1 ) ||
           (x == getCurrX() - 2 || x == getCurrX() + 2 )  && (y == getCurrY() - 2 || y == getCurrY() + 2 )){
            return true;
        }
        else return false;
    }
    public boolean isHorizontal(int x,int y){
        if((x == getCurrX() - 2 && y == getCurrY() || x == getCurrX() + 2 && y == getCurrY())||
           (x == getCurrX() - 1 && y == getCurrY() || x == getCurrX() + 1 && y == getCurrY())||
           (y == getCurrY() - 2 && x == getCurrX() || y == getCurrY() + 2 && x == getCurrX())||
           (y == getCurrY() - 1 && x == getCurrX() || y == getCurrY() + 1 && x == getCurrX())){
            return true;
        }
        else return false;
    }
    public boolean piecesInWay(int x, int y, Piece[][] otherPiece)
    {
    	//Vertical 4 situations
    	if(x == getCurrX() + 2 && y == getCurrY()) {
    		if(otherPiece[getCurrX() + 1][getCurrY()] !=null) {
    			return true;
    		}
    	}
    	
    	if(x == getCurrX() - 2 && y == getCurrY()) {
    		if(otherPiece[getCurrX() - 1][getCurrY()] !=null) {
    			return true;
    		}
    	}
    	
    	if(x == getCurrX() && y == getCurrY() + 2) {
    		if(otherPiece[getCurrX()][getCurrY() + 1] !=null) {
    			return true;
    		}
    	}
    	
    	if(x == getCurrX() && y == getCurrY() - 2) {
    		if(otherPiece[getCurrX()][getCurrY() - 1] !=null) {
    			return true;
    		}
    	}
    	
    	
    	// diagonal 4 situations
    	if(x == getCurrX() + 2 && y == getCurrY() + 2) {
    		if(otherPiece[getCurrX() + 1][getCurrY() + 1] !=null) {
    			return true;
    		}
    	}
    	
    	if(x == getCurrX() - 2 && y == getCurrY() - 2) {
    		if(otherPiece[getCurrX() - 1][getCurrY() - 1] !=null) {
    			return true;
    		}
    	}
    	
    	if(x == getCurrX() - 2 && y == getCurrY() + 2) {
    		if(otherPiece[getCurrX() - 1][getCurrY() + 1] !=null) {
    			return true;
    		}
    	}
    	
    	if(x == getCurrX() + 2 && y == getCurrY() - 2) {
    		if(otherPiece[getCurrX() + 1][getCurrY() - 1] !=null) {
    			return true;
    		}
    	}
    	return false;
    }
  
}