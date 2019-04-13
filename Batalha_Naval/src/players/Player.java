package players;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import boats.structs.Coordinate;
import boats.structs.Shot;
import scenes.GamePlayerResources;


public class Player extends GamePlayerResources implements GamePlayerFunctions{
    
	private int[][] battleField;
	
    public Player(int sizeX, int sizeY, EventHandler<? super MouseEvent> mouseHandler){
		super(sizeX, sizeY,mouseHandler);
	}
    
    @Override
	public boolean validatePosition(Coordinate initial, Coordinate finals){
		int[][]battleField = getMyBoats();
		
		int initialRow=initial.getY();
		int initialCol=initial.getX();
		int finalRow =finals.getY();
		int finalCol=finals.getX();
		
		boolean size1=(initialRow > 9 || initialCol > 9 || finalRow > 9 || finalCol > 9);
		boolean size2=(initialRow > finalRow || initialCol > finalCol);
		boolean size3=(initialRow < 0 || initialCol < 0|| finalRow < 0 || finalCol < 0);
		
        if(size1|| size2|| size3)
            return false;
			 
        if(initialRow == finalRow && initialCol != finalCol){
            for(int i = initialCol; i <= finalCol  ; i++)
				if(battleField[i][initialRow] != 0)
					return false;
        }
        else if (initialCol == finalCol && initialRow != finalRow){ 
			for(int i = initialRow; i <= finalRow ; i++)
				if(battleField[initialCol][i] != 0)
					return false;
        }
         
        else{
            if(battleField[initialRow][initialCol] != 0)
				return false;
        }
          return true;
    }    
	
	@Override
	public Shot checkShot(Coordinate shot){
		if(getMyBoats()[shot.getY()][shot.getX()] !=0){
			return Shot.HIT;
		}
		else{
			return Shot.MISS;
		}
	}
	
	@Override
	public Coordinate shoot(){
		return null;
	}
	
}