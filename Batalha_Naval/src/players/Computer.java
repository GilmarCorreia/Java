package players;

import java.util.Random;
import java.lang.Math;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import boats.*;
import boats.structs.Coordinate;
import boats.structs.Shot;
import scenes.GamePlayerResources;

public class Computer extends GamePlayerResources implements GamePlayerFunctions{

	private int row, col, maiorX, maiorY;
	private int [][] battleField;
	private long [][] board;
	private long maior = 0;
	
	private Embarcacao[] ships = new Embarcacao[10];
	
	private Coordinate lastShot = new Coordinate(0,0);
	
	public Computer(int sizeX, int sizeY, EventHandler<? super MouseEvent> mouseHandler){
		this(sizeX,sizeY,10,10,mouseHandler);
	}
	
	public Computer(int sizeX, int sizeY, int row, int col, EventHandler<? super MouseEvent> mouseHandler){
		super(sizeX, sizeY, new int[]{row,col}, mouseHandler);
		setRowSize(row);
		setColSize(col);
		board = new long [row][col];

		ships[0] = new PortaAviao();
		ships[1] = new NavioTanque();
		ships[2] = new NavioTanque();
		ships[3] = new Contratorpedeiro();
		ships[4] = new Contratorpedeiro();
		ships[5] = new Contratorpedeiro();
		ships[6] = new Submarino();
		ships[7] = new Submarino();
		ships[8] = new Submarino();
		ships[9] = new Submarino();
		
		battleField = new int [row][col];
		battleField = criaFrota().clone();
		setMyBoats(battleField);
		probabilityMat();
	}
	
	
	@Override
	public boolean validatePosition(Coordinate initial, Coordinate finals){
		
		int initialRow=initial.getY();
		int initialCol=initial.getX();
		int finalRow =finals.getY();
		int finalCol=finals.getX();
		
		boolean side1=(initialRow > 9 || initialCol > 9 || finalRow > 9 || finalCol > 9);
		boolean side2=(initialRow > finalRow || initialCol > finalCol);
		boolean side3=(initialRow < 0 || initialCol < 0|| finalRow < 0 || finalCol < 0);
		
        if(side1|| side2|| side3)
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
	
	public int[][] criaFrota(){
		
		for (int i=0; i<10; i++){
			if (Math.random() <= 0.5){
				ships[i].setVertical(false);
			}
			posicionar(ships[i]);
		}
		
		return battleField;
	}
	
	public void posicionar(Embarcacao boat){
		int x = (int) (Math.random()*10);
		int y = (int) (Math.random()*10);
		Coordinate initial = new Coordinate(x,y);
		Coordinate end;
		
		if (boat.getVertical() == true){
			end = new Coordinate(x + boat.getTamanho() -1, y);
		}
		else{
			end = new Coordinate(x, y + boat.getTamanho() -1);
		}
		
		
		if (validatePosition(initial, end)){
			boat.setInicio(initial.getX(), initial.getY());
			boat.setFinal(end.getX(), end.getY());
			
			for(int i=0; i<boat.getTamanho(); i++){
				if (boat.getVertical() == true){
					battleField[boat.getInicio()[0] + i][boat.getInicio()[1]] = boat.getTamanho();
				}
				else{
					battleField[boat.getInicio()[0]][boat.getInicio()[1] + i] = boat.getTamanho();
				}
			}
		}
		else{
			posicionar(boat);
		}
	}
	
	public void probabilityMat(){
		for(long i=0; i<500000; i++){
		    int w =(int) (Math.random()*10);
		    int k = (int) (Math.random()*10);
			this.board[w][k]++;
		}
	}
	
	@Override
	public Coordinate shoot(){
		if(returnShot()==null){
		}
			
		if(returnShot()==Shot.SINK){
			this.board[maiorX][maiorY] = 2;
		}
		else if(returnShot()==Shot.HIT){
			this.board[maiorX][maiorY] = 1;
		}
		else if(returnShot()==Shot.MISS){
			this.board[maiorX][maiorY] = 0;
		}
		
		arrumaMatriz(returnShot());
		
		maior = 0;
		
		for (int i=0; i<getRowSize(); i++){
			for (int j=0; j<getColSize(); j++){
				if (this.board[i][j] > maior){
				    maior = this.board[i][j];
					maiorX = i;
					maiorY = j;
				}
			}
		}
		
        return new Coordinate(maiorX,maiorY);
	}
	
	public void arrumaMatriz(Shot shot){
		if (this.board[maiorX][maiorY] != 0){
			board[maiorX][maiorY] = 0;
			
			if (shot == Shot.HIT){
				if (maiorY != 0 && board[maiorX][maiorY-1] != 0 )
					board[maiorX][maiorY-1] += 1000;
				if (maiorY != 9 && board[maiorX][maiorY+1] != 0)
					board[maiorX][maiorY+1] += 1000;
				if (maiorX != 9 && board[maiorX+1][maiorY] != 0)
					board[maiorX+1][maiorY] += 1000;
				if (maiorX != 0 && board[maiorX-1][maiorY] != 0)
					board[maiorX-1][maiorY] += 1000;
				if (lastShot.getX() == maiorX){
					if (lastShot.getY() != 0 && board[lastShot.getX()][lastShot.getY()-1] != 0 )
						board[lastShot.getX()][lastShot.getY()-1] -= 1000;
					if (lastShot.getY() != 9 && board[lastShot.getX()][lastShot.getY()+1] != 0)
						board[lastShot.getX()][lastShot.getY()+1] -= 1000;
				}
				if (lastShot.getY() == maiorY){
					if (lastShot.getX() != 9 && board[lastShot.getX()+1][lastShot.getY()] != 0)
						board[lastShot.getX()+1][lastShot.getY()] -= 1000;
					if (lastShot.getX()!= 0 && board[lastShot.getX()-1][lastShot.getY()] != 0)
						board[lastShot.getX()-1][lastShot.getY()] -= 1000;
				}
			}
			if (shot == Shot.SINK){
				if (lastShot.getX() == maiorX){
					if (lastShot.getY() != 0 && board[lastShot.getX()][lastShot.getY()-1] != 0 )
						board[lastShot.getX()][lastShot.getY()-1] -= 1000;
					if (lastShot.getY() != 9 && board[lastShot.getX()][lastShot.getY()+1] != 0)
						board[lastShot.getX()][lastShot.getY()+1] -= 1000;
				}
				if (lastShot.getY() == maiorY){
					if (lastShot.getX() != 9 && board[lastShot.getX()+1][lastShot.getY()] != 0)
						board[lastShot.getX()+1][lastShot.getY()] -= 1000;
					if (lastShot.getX() != 0 && board[lastShot.getX()-1][lastShot.getY()] != 0)
						board[lastShot.getX()-1][lastShot.getY()] -= 1000;
				}
			}
			lastShot.setX(maiorX);
			lastShot.setY(maiorY);
		}
	}
	
	public void imprimir(int[][] vector){
		for (int k=0; k<getRowSize(); k++){
			for (int p =0; p<getColSize(); p++){
				System.out.print(vector[k][p] + " ");
			}
				System.out.println(" ");
		}
		System.out.println(" ");
	}
	
	public void imprimir(long[][] vector){
		for (int k=0; k<getRowSize(); k++){
			for (int p =0; p<getColSize(); p++){
				System.out.print(vector[k][p] + " ");
			}
				System.out.println(" ");
		}
		System.out.println(" ");
	}
	
	//-------------------------------SETTERS-------------------------------------------//
	public void setColSize(int col){
			this.col = col;
	}
	
	public void setRowSize(int row){
			this.row = row;
	}
	
	//-------------------------------GETTERS------------------------------------------/
	public int getRowSize(){
		return this.row;
	}
	
	public int getColSize(){
		return this.col;
	}
	
}