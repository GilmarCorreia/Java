package boats.structs;

public class Coordinate{
	
	//---------------------- ATRIBUTOS ----------------------
	
	private int x,y;
	
	//--------------------- CONSTRUTORES --------------------
	
	public Coordinate(int x, int y){
		setX(x);
		setY(y);
	}
	
	// ---------------------- SETTERS -----------------------
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	// ---------------------- GETTERS -----------------------
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	
}