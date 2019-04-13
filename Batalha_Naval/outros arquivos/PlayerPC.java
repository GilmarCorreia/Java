	import java.util.Random;
	import java.lang.Math;
	import embarcacoes.*;

	public class PlayerPC{

		long [][] tabuleiroProb;
		Embarcacao [][] campo;
		private int w, k, maiorX, maiorY, row, col, x, y ,num=0;
		long maior = 0;
		private boolean prob, ok = true;
		int [] coordI = new int[2];
		int [] coordF = new int[2];
		
		public PlayerPC(){
			this(10,10);
		}
		
		public PlayerPC(int row, int col){
			setRowSize(row);
			setColSize(col);
			tabuleiroProb = new long [row][col];
			campo = new Embarcacao[row][col];
		}
		
		public void criaBarco(Embarcacao barco){                          //Posiciona os barcos no tabuleiro
			
			//muito provavelmente essa parte da pra fazer diferente, tipo definir em outro lugar quando é vertical ou nao
			if (Math.random() <= 0.5 && barco instanceof Contratorpedeiro){
				barco = new Contratorpedeiro(true);
			}
			else {
				barco = new Contratorpedeiro(false);
			}
			if (Math.random() <= 0.5 && barco instanceof NavioTanque){
				barco = new NavioTanque(true);
			}
			else {
				barco = new NavioTanque(false);
			}
			if (Math.random() <= 0.5 && barco instanceof PortaAviao){
				barco = new PortaAviao(true);
			}
			else {
				barco = new PortaAviao(false);
			}
			if (Math.random() <= 0.5 && barco instanceof Submarino){
				barco = new Submarino(true);
			}
			else {
				barco = new Submarino(false);
			}
		}
		
		public void posicionar(Embarcacao barco){
			x = (int) (Math.random()*10);
			y = (int) (Math.random()*10);
			ok = true;
			barco.setInicio(new int[] {x, y});
	 
			if (barco.getVertical() == true){
				for (int i=0; i<barco.getTamanho(); i++){
					if(campo[x+i][y] != null){
						ok = false;
					}
					if (ok == true){
						barco.setFinal(new int[] {x + barco.getTamanho() -1, y});
						}
						//else colocar o tratamento de erro
				}
			}
			else {
				for (int i=0; i<barco.getTamanho(); i++){
					if(campo[x][y+i] != null){
						ok = false;
					}
					if (ok == true){
						barco.setFinal(new int[] {x, y + barco.getTamanho() -1});
					}
					//else colocar o tratamento de erro
				}
			}
		}
		
		public void probabilidade(){            //cria a matriz de probabilidade de tiro
			for(long i=0; i<200000; i++){
				w =(int) (Math.random()*10);
				k = (int) (Math.random()*10);
				//System.out.println("w" + w + " k " + k);
				this.tabuleiroProb[w][k]++;
			}
			
			imprimir();
		}
		
		public void imprimir(){
			for (int k=0; k<getRowSize(); k++){
				for (int p =0; p<getColSize(); p++){
					System.out.print(this.tabuleiroProb[k][p] + " ");
				}
					System.out.println(" ");
			}
			System.out.println(" ");
		}
		
		public int[] realizarTiro(){                //atira na maior probabilidade e devolve as coordenadas do tiro
			maior = 0;
			int[] maiorCoord = new int[2];
			
			for (int i=0; i<getRowSize(); i++){
				for (int j=0; j<getColSize(); j++){
					if (this.tabuleiroProb[i][j] > maior){
						maior = this.tabuleiroProb[i][j];
						maiorCoord[0] = i;
						maiorCoord[1] = j;
					}
				}
			}
			
			//Isso teria que receber o receberTiro() do adversario
			if (receberTiro(maiorCoord) == 1){
			
				this.tabuleiroProb[maiorCoord[0]][maiorCoord[1]] = 0;
				if (maiorCoord[0] != 9 && this.tabuleiroProb[maiorCoord[0]+1][maiorCoord[1]] != 1 && this.tabuleiroProb[maiorCoord[0]+1][maiorCoord[1]] != 0){
					this.tabuleiroProb[maiorCoord[0]+1][maiorCoord[1]] = this.tabuleiroProb[maiorCoord[0]+1][maiorCoord[1]] + 1000;
				}
				if (maiorCoord[0] != 0 && this.tabuleiroProb[maiorCoord[0]-1][maiorCoord[1]] != 1 && this.tabuleiroProb[maiorCoord[0]-1][maiorCoord[1]] != 0){
					this.tabuleiroProb[maiorCoord[0]-1][maiorCoord[1]] = this.tabuleiroProb[maiorCoord[0]-1][maiorCoord[1]] + 1000;
				}
				if (maiorCoord[1] != 9 && this.tabuleiroProb[maiorCoord[0]][maiorCoord[1] + 1] != 1 && this.tabuleiroProb[maiorCoord[0]][maiorCoord[1] + 1] != 0){
					this.tabuleiroProb[maiorCoord[0]][maiorCoord[1] + 1] = this.tabuleiroProb[maiorCoord[0]][maiorCoord[1] + 1] + 1000;
				}
				if (maiorCoord[1] != 0 && this.tabuleiroProb[maiorCoord[0]][maiorCoord[1] - 1] != 1 && this.tabuleiroProb[maiorCoord[0]][maiorCoord[1] - 1] != 0){
					this.tabuleiroProb[maiorCoord[0]][maiorCoord[1] - 1] = this.tabuleiroProb[maiorCoord[0]][maiorCoord[1] - 1] + 1000;
				}
			else if (receberTiro(maiorCoord) == 2)
				if (maiorCoord[0] != 9 && this.tabuleiroProb[maiorCoord[0]+1][maiorCoord[1]] != 1 && this.tabuleiroProb[maiorCoord[0]+1][maiorCoord[1]] != 0){
					this.tabuleiroProb[maiorCoord[0]+1][maiorCoord[1]] = this.tabuleiroProb[maiorCoord[0]+1][maiorCoord[1]] - 1000;
				}
				if (maiorCoord[0] != 0 && this.tabuleiroProb[maiorCoord[0]-1][maiorCoord[1]] != 1 && this.tabuleiroProb[maiorCoord[0]-1][maiorCoord[1]] != 0){
					this.tabuleiroProb[maiorCoord[0]-1][maiorCoord[1]] = this.tabuleiroProb[maiorCoord[0]-1][maiorCoord[1]] - 1000;
				}
				if (maiorCoord[1]!= 9 && this.tabuleiroProb[maiorCoord[0]][maiorCoord[1] + 1] != 1 && this.tabuleiroProb[maiorCoord[0]][maiorCoord[1] + 1] != 0){
					this.tabuleiroProb[maiorCoord[0]][maiorCoord[1] + 1] = this.tabuleiroProb[maiorCoord[0]][maiorCoord[1] + 1] - 1000;
				}
				if (maiorCoord[1] != 0 && this.tabuleiroProb[maiorCoord[0]][maiorCoord[1] - 1] != 1 && this.tabuleiroProb[maiorCoord[0]][maiorCoord[1] - 1] != 0){
					this.tabuleiroProb[maiorCoord[0]][maiorCoord[1] - 1] = this.tabuleiroProb[maiorCoord[0]][maiorCoord[1] - 1] - 1000;
				}
			} else {
				this.tabuleiroProb[maiorCoord[0]][maiorCoord[1]] = 0;
				System.out.println("water!!!");
			}
			
			return maiorCoord;
		}
			//recebe tiro do adversario no tabuleiro e retorna se acertou ou nao
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
		public boolean getAcerto(){
			return Math.random() < 0.5;
		}
		
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
