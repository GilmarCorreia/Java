import java.util.Random;
import java.lang.Math;
import embarcacoes.*;

public class PlayerPessoa{
	
	Embarcacao[][] campo;
	private int row, col;
	
	public PlayerPessoa(){
		this(10,10);
	}
	
	public PlayerPessoa(int row, int col){
		setRowSize(row);
		setColSize(col);
		campo = new Embarcacao[row][col];
	}
	
	public void posicionar(Embarcacao barco, int[] inicio, int[] fim){
		barco.setInicio(inicio);
		barco.setFinal(fim);
	}
	
	// public int[] realizarTiro(int[] coord) NAO SEI COMO VAI SER ISSO NA INTERFACE
	
	public int receberTiro(int[] coord){
		if (this.campo[coord[0]][coord[1]] == null){
				System.out.println("Water!!!");
				return -1;
		}
		else{
			if  (this.campo[coord[0]][coord[1]].getSaude() == 0){
				System.out.println(this.campo[coord[0]][coord[1]].getTipo() + "afundou!!!");
				return 2;
			}
			else{
				this.campo[coord[0]][coord[1]].setSaude();
				System.out.println("Acertou uma embarcacao!");
				return 1;
			}
		}
	}
	
	//-----------------------------------------GETTERS---------------------------------------//
		public int getRowSize(){
			return this.row;
		}
		
		public int getColSize(){
			return this.col;
		}
		
	//----------------------------------------SETTERS----------------------------------------//
		
		public void setColSize(int col){
				this.col = col;
		}
		
		public void setRowSize(int row){
				this.row = row;
		}
}