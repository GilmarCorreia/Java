package players;

import java.util.Random;
import java.lang.Math;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import boats.*;
import boats.structs.Coordinate;
import scenes.GamePlayerResources;

public class Computer extends GamePlayerResources implements GamePlayerFunctions{

	long [][] tabuleiro;
	long [][] campo;
	private int w, k;
	private int maiorX, maiorY;
    long maior = 0;
	private int row, col;
	private int num = 0;
	private Embarcacao[] ships = new Embarcacao[10];
	Coordinate ultimoTiro = new Coordinate(0,0);
	
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

	public Computer(int sizeX, int sizeY, EventHandler<? super MouseEvent> mouseHandler){
		this(sizeX,sizeY,10,10,mouseHandler);
	}
	
	public Computer(int sizeX, int sizeY, int row, int col, EventHandler<? super MouseEvent> mouseHandler){
		super(sizeX, sizeY, new int[]{row,col}, mouseHandler);
		setRowSize(row);
		setColSize(col);
		tabuleiro = new long [row][col];
		campo = new long [row][col];
	}
	
	@Override
	public boolean valida_posicao(Coordinate initial, Coordinate finals){
		int[][]campo = getMyBoats();
		
		int linha_ini=initial.getY();
		int coluna_ini=initial.getX();
		int linha_final =finals.getY();
		int coluna_final=finals.getX();
		
		boolean lado1=(linha_ini > 9 || coluna_ini > 9 || linha_final > 9 || coluna_final > 9);
		boolean lado2=(linha_ini > linha_final || coluna_ini > coluna_final);
		boolean lado3=(linha_ini < 0 || coluna_ini < 0|| linha_final < 0 || coluna_final < 0);
		
        if(lado1|| lado2|| lado3)
            return false;
			 
        if(linha_ini == linha_final && coluna_ini != coluna_final){
            for(int i = coluna_ini; i <= coluna_final  ; i++)
				if(campo[i][linha_ini] != 0)
					return false;
        }
        else if (coluna_ini == coluna_final && linha_ini != linha_final){ 
			for(int i = linha_ini; i <= linha_final ; i++)
				if(campo[coluna_ini][i] != 0)
					return false;
        }
         
        else{
            if(campo[linha_ini][coluna_ini] != 0)
				return false;
        }
          return true;
    }
	public void criaFrota(){
		for (int i=0; i<10; i++){
			if (Math.random() <= 0.5){
				ships[i].setVertical(false);
			}
			posicionar(ships[i])
		}
		return campo;
	}
	public void posicionar(Embarcacao barco){
		x = (int) (Math.random()*10);
		y = (int) (Math.random()*10);
		Coordinate inicio = new Coordinate(x,y);
		Coordinate fim;
		
		if (barco.getVertical() == true){
			fim = new Coordinate(x + barco.getTamanho() -1, y);
		}
		else{
			fim = new Coordinate(x, y + barco.getTamanho() -1);
		}
			
		if (valida_posicao(inicio, fim)){
			barco.setInicio(inicio.getX(), inicio.getY());
			barco.setFinal(fim.getX(), fim.getY());
			
			for(int i=0; i<barco.getTamanho(); i++){
				if (barco.getVertical() == true){
					campo[barco.getInicio()[0] + i][barco.getInicio()[1]] = barco.getTamanho();
				}
				else{
					campo[barco.getInicio()[0]][barco.getInicio()[1] + i] = barco.getTamanho();
				}
			}
		}
		else{
			posicionar(barco);
		}
	}
	
	public void probabilidade(){
		for(long i=0; i<200000; i++){
		    w =(int) (Math.random()*10);
		    k = (int) (Math.random()*10);
			this.tabuleiro[w][k]++;
		}
		
		imprimir();
	}
	
	public void imprimir(){
		for (int k=0; k<getRowSize(); k++){
			for (int p =0; p<getColSize(); p++){
				System.out.print(this.tabuleiro[k][p] + " ");
			}
				System.out.println(" ");
		}
		System.out.println(" ");
	}
	
	public void jogar(){
		maior = 0;
		
		for (int i=0; i<getRowSize(); i++){
			for (int j=0; j<getColSize(); j++){
				if (this.tabuleiro[i][j] > maior){
				    maior = this.tabuleiro[i][j];
					maiorX = i;
					maiorY = j;
				}
			}
		}
		
		if (this.tabuleiro[maiorX][maiorY] != 0){
			tabuleiro[maiorX][maiorY] = 0;
			
			if (verificarTiro(maiorX, maiorY) == 1){
				if (maiorY != 0 && tabuleiro[maiorX][maiorY-1] != 0 )
					tabuleiro[maiorX][maiorY-1] += 1000;
				if (maiorY != 9 && tabuleiro[maiorX][maiorY+1] != 0)
					tabuleiro[maiorX][maiorY+1] += 1000;
				if (maiorX != 9 && tabuleiro[maiorX+1][maiorY] != 0)
					tabuleiro[maiorX+1][maiorY] += 1000;
				if (maiorX != 0 && tabuleiro[maiorX-1][maiorY] != 0)
					tabuleiro[maiorX-1][maiorY] += 1000;
				if (ultimoTiro.getX() == maiorX){
					if (ultimoTiro.getY() != 0 && tabuleiro[ultimoTiro.getX][ultimoTiro.getY()-1] != 0 )
						tabuleiro[ultimoTiro.getX][ultimoTiro.getY()-1] -= 1000;
					if (ultimoTiro.getY() != 9 && tabuleiro[ultimoTiro.getX][ultimoTiro.getY()+1] != 0)
						tabuleiro[ultimoTiro.getX][ultimoTiro.getY()+1] -= 1000;
				}
				if (ultimoTiro.getY() == maiorY){
					if (ultimoTiro.getX != 9 && tabuleiro[ultimoTiro.getX+1][ultimoTiro.getY()] != 0)
						tabuleiro[ultimoTiro.getX+1][ultimoTiro.getY()] -= 1000;
					if (ultimoTiro.getX != 0 && tabuleiro[ultimoTiro.getX-1][ultimoTiro.getY()] != 0)
						tabuleiro[ultimoTiro.getX-1][ultimoTiro.getY()] -= 1000;
				}
			}
			if (verificarTiro(maiorX, maiorY) == 2){
				if (ultimoTiro.getX() == maiorX){
					if (ultimoTiro.getY() != 0 && tabuleiro[ultimoTiro.getX][ultimoTiro.getY()-1] != 0 )
						tabuleiro[ultimoTiro.getX][ultimoTiro.getY()-1] -= 1000;
					if (ultimoTiro.getY() != 9 && tabuleiro[ultimoTiro.getX][ultimoTiro.getY()+1] != 0)
						tabuleiro[ultimoTiro.getX][ultimoTiro.getY()+1] -= 1000;
				}
				if (ultimoTiro.getY() == maiorY){
					if (ultimoTiro.getX != 9 && tabuleiro[ultimoTiro.getX+1][ultimoTiro.getY()] != 0)
						tabuleiro[ultimoTiro.getX+1][ultimoTiro.getY()] -= 1000;
					if (ultimoTiro.getX != 0 && tabuleiro[ultimoTiro.getX-1][ultimoTiro.getY()] != 0)
						tabuleiro[ultimoTiro.getX-1][ultimoTiro.getY()] -= 1000;
				}
			}
			ultimoTiro.setX(maiorX);
			ultimoTiro.setY(maiorY);
		}
		else{
			jogar();
		}
		
        return new int[]{maiorX,maiorY};
	}
	
	public boolean getAcerto(){
		return Math.random() < 0.5;
	}
	public void setColSize(int col){
			this.col = col;
	}
	
	public void setRowSize(int row){
			this.row = row;
	}
	public int getRowSize(){
		return this.row;
	}
	
	public int getColSize(){
		return this.col;
	}
	
}