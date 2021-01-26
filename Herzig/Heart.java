public class Heart extends Piece{
    //The possible moves with the condition of one move in any direction.
    public Heart(int x, int y, String color, int pieceNumber)
    {
    	super(x,y, color, "Heart", pieceNumber);
    }
    
    @Override
    public boolean canMove(int x,int y){
        
        if(isDiagonal(x,y) || isHorizontal(x,y) ){
            return true;
        }
         return false;
    }
    public boolean isDiagonal(int x,int y){
        if((x == getCurrX() - 1 || x == getCurrX() + 1 )  && (y == getCurrY() - 1 || y == getCurrY() + 1 )){
            return true;
        }
        else return false;
    }
    public boolean isHorizontal(int x,int y){
        if((x == getCurrX() - 1 && y == getCurrY() || x == getCurrX() + 1 && y == getCurrY())||
            (y == getCurrY() - 1 && x == getCurrX() || y == getCurrY() + 1 && x == getCurrX())){
            return true;
        }
        else return false;
    }
    @Override
    public boolean piecesInWay(int x, int y, Piece[][] otherPiece)
    {
    	return false;
    }
}
