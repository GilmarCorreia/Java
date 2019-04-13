package boats.structs;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color; 
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import players.*;

public class Cell extends Rectangle{
	
	//---------------------- ATRIBUTOS ----------------------
	
	private int sizeX,sizeY;
	private Coordinate coord;
	private Rectangle cell;
	private Color color;
	private boolean thereIsABoat;
	private boolean shot;
	
	//--------------------- CONSTRUTORES --------------------
	
	public Cell(int x, int y, int sizeX, int sizeY){
		super(x,y,sizeX,sizeY);
		super.setFill(Color.AQUA);
		super.setStroke(Color.BLACK);
		setThereIsABoat(false);
		setShot(false);
	}
	
	// ---------------------- SETTERS -----------------------
	
	public void setThereIsABoat(boolean thereIsABoat){
		this.thereIsABoat = thereIsABoat;
	}
	
	public void setShot(boolean hittedBoat){
		this.shot = hittedBoat;
	}
	// ---------------------- GETTERS -----------------------
	
	public boolean getThereIsABoat(){
		return this.thereIsABoat;
	}
	
	public boolean getShot(){
		return this.shot;
	}
	
	//----------------------- MÉTODOS -----------------------
	
}