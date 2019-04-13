package boats;

import boats.structs.Coordinate;

public abstract class Embarcacao{

	private int tamanho;
	//private int saude;
	private int inicioX, inicioY;
	private int finalX, finalY;
	
	private boolean vertical;
	
	public Embarcacao(boolean vertical, int tamanho){
		this.vertical = vertical;
		this.tamanho = tamanho;
		//this.saude = tamanho;
	}

//-------------------------------SETTERS---------------------------------//	
	public void setInicio(int inicioX, int inicioY){
		this.inicioX = inicioX;
		this.inicioY = inicioY;
	}
	
	
	public void setFinal(int finalX, int finalY){
		this.finalX = finalX;
		this.finalY = finalY;
	}
	
	//public void setSaude(){
	//	this.saude--;
	//}	
	
	public void setVertical(boolean vertical){
		this.vertical = vertical;
	}
	
//--------------------------------GETTERS----------------------------------//
	public boolean getVertical(){
		return this.vertical;
	}
	
	public int[] getInicio(){
		 return new int[]{this.inicioX ,this.inicioY};
	}
	
	public int[] getFinal(){
		 return new int[]{this.finalX ,this.finalY};
	}
	
	//public int getSaude(){
	//	return this.saude;
	//}
        
    public int getTamanho(){
         return this.tamanho;
    }
	


}
