
package scenes;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent ;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.scene.shape.Rectangle;

import boats.*;
import boats.structs.*;
import players.*;

public abstract class GamePlayerResources extends VBox implements GamePlayerFunctions{
	
	//---------------------- ATRIBUTOS ----------------------
	private int sizeX, sizeY, qtdPos =0, health = 0;
	private int[] tableSize;
	private int shots[][];
	private int myBoats[][];

	private Shot receivedShot;
	
	private VBox board = new VBox();
	
	private EventHandler<? super MouseEvent> mouseHandler;
	
	private Embarcacao[] ships = new Embarcacao[10];
	
	private boolean player = false; 
	private boolean finishPositioning = false;
	
	private static boolean HPos = true;
	public static boolean control = true;
	//--------------------- CONSTRUTORES --------------------
    
	public GamePlayerResources(int sizeX, int sizeY, EventHandler<? super MouseEvent> mouseHandler){
        this(sizeX, sizeY, new int[]{10,10},mouseHandler);
    }
	
	public GamePlayerResources(int sizeX, int sizeY, int[] tableSize, EventHandler<? super MouseEvent> mouseHandler){
		
		setSizeX(sizeX);
		setSizeY(sizeY);
		setTableSize(tableSize);
		setMouseHandler(mouseHandler);
		
		shots = new int[getTableX()][getTableY()];
		myBoats = new int[getTableX()][getTableY()];
		control = true;
		
		getChildren().add(drawBoard(getSizeX(),getSizeY()));
		setPrefWidth(getSizeX());	
		setAlignment(Pos.CENTER);	
		
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
	}
	
	// ---------------------- SETTERS -----------------------
	
	private void setSizeX(int sizeX){
		this.sizeX = sizeX;
	}
	
	private void setSizeY(int sizeY){
		this.sizeY = sizeY;
	}
	
	private void setTableSize(int [] tableSize){
		this.tableSize = tableSize.clone();
	}

	private void setMouseHandler( EventHandler<? super MouseEvent> mouseHandler){
		this.mouseHandler = mouseHandler;
	}
	
	protected void setMyBoats(int[][] vector){
		this.myBoats = vector.clone();
		setHealth();
	}
	
	void setHealth(){
		for(int i = 0;i<myBoats.length;i++){
			for(int j = 0; j<myBoats[0].length;j++){
				this.health += myBoats[i][j];
			}
		} 
	}
	
	void setHealth(Coordinate shot){
		int lost = getMyBoats()[shot.getY()][shot.getX()];
		getMyBoats()[shot.getY()][shot.getX()] = -1;

		this.health-=lost; 
	}
	
	void setPlayerTime(boolean player){
		this.player = player;
	}

	
	// ---------------------- GETTERS -----------------------
	
	private int getSizeX(){
		return this.sizeX;
	}
	
	private int getSizeY(){
		return this.sizeY;
	}
	
	private int getTableX(){
		return tableSize[0];
	}
	
	private int getTableY(){
		return tableSize[1];
	}
	
	private int getTotalCell(){
		return (getTableX()*getTableY());
	}
	
	public int getHealth(){
		return this.health;
	}
	
	public int[][] getMyBoats(){
		return myBoats;
	}
	
	boolean playerTime(){
		return this.player;
	}
	
	Cell getCell(int x, int y) {
        return (Cell)((HBox)board.getChildren().get(y)).getChildren().get(x);
    }
	
	public void printMyBoats(){
		for(int i = 0;i<myBoats.length;i++){
			for(int j = 0; j<myBoats[0].length;j++){
				System.out.print(myBoats[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	
	public void printMyShots(){
		for(int i = 0;i<shots.length;i++){
			for(int j = 0; j<shots[0].length;j++){
				System.out.print(shots[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
    //----------------------- MÉTODOS -----------------------
	
	private HBox setUpMargin(int upMarginValue){
		HBox upMargin = new HBox();
		upMargin.setPrefHeight(upMarginValue);
		upMargin.setAlignment(Pos.CENTER);
		return upMargin;
	}
	
	private HBox setDownMargin(int downMarginValue){
		HBox downMargin = new HBox();
		downMargin.setPrefHeight(downMarginValue);
		downMargin.setAlignment(Pos.CENTER);
		return downMargin;
	}
	
	private VBox setTable(int sizeXWithMargin, int sizeYWithMargin){

		VBox playerTable = new VBox();
		
		for(int y=0;y<getTableY();y++){    
            HBox row = new HBox();
            for (int x=0; x<getTableX();x++){
				Cell cell= new Cell(x,y,sizeXWithMargin/getTableX(),sizeYWithMargin/getTableY());
				cell.setOnMouseEntered(this.mouseHandler);
				cell.setOnMouseExited(this.mouseHandler);
				cell.setOnMouseClicked(this.mouseHandler);
				row.getChildren().add(cell);
            }
            playerTable.getChildren().add(row);
        }
		this.board = playerTable;
		
		playerTable.setAlignment(Pos.CENTER); 
		
		return playerTable;
	}
	
	private HBox setMiddle(int sizeYWithMargin, int horizontalMarginValue){
		
		HBox middleBox = new HBox();
		middleBox.setPrefHeight(sizeYWithMargin);
	
		VBox horizontalMargin = new VBox();
		horizontalMargin.setPrefWidth(horizontalMarginValue);
		horizontalMargin.setAlignment(Pos.CENTER);	
		
		int sizeXWithMargin = (getSizeX()-horizontalMarginValue);
			
		VBox playerSpace = setTable(sizeXWithMargin,sizeYWithMargin);
		playerSpace.setAlignment(Pos.CENTER);
		
		if(this.HPos){
			middleBox.getChildren().addAll(horizontalMargin,playerSpace);
		}
		else{
			middleBox.getChildren().addAll(playerSpace,horizontalMargin);
		}
		this.HPos = !this.HPos;
		
		middleBox.setAlignment(Pos.CENTER);
		
		return middleBox;
	}
	
	private VBox setMargins(int upMarginValue, int downMarginValue, int horizontalMarginValue){
		VBox playerSceneWithMargin = new VBox();
		
		playerSceneWithMargin.getChildren().add(setUpMargin(upMarginValue));
		int sizeYWithMargin = (getSizeY()-upMarginValue-downMarginValue);
		playerSceneWithMargin.getChildren().add(setMiddle(sizeYWithMargin,horizontalMarginValue));
		playerSceneWithMargin.getChildren().add(setDownMargin(downMarginValue));
		playerSceneWithMargin.setAlignment(Pos.CENTER);
		
		return playerSceneWithMargin;
	}
	
    VBox drawBoard(int sizeX, int sizeY){

		VBox playerTable = setMargins(150,50,80);
		playerTable.setAlignment(Pos.CENTER);
        playerTable.setPrefWidth(sizeX);
		playerTable.setPrefHeight(sizeY);
		
        return playerTable;
    } 
	
	void viewPosition(int x, int y, boolean vertical, Color color){

		Coordinate initial;
		Coordinate finals;
		if(vertical){
			initial = new Coordinate(y,x);
			finals = new Coordinate((y+ships[qtdPos].getTamanho()-1),x);
		}
		else{	
			initial = new Coordinate(y,(x-ships[qtdPos].getTamanho()+1));
			finals = new Coordinate(y,x);

		}
		
		ships[qtdPos].setInicio(initial.getY(),initial.getX());
		ships[qtdPos].setFinal(finals.getY(),finals.getX());
			
		Cell cell;
		
		if(validatePosition(initial,finals)){
			if(vertical){
				for(int i = y; i < y + ships[qtdPos].getTamanho(); i++){
					cell = getCell(x, i);
					if(!cell.getThereIsABoat())
						cell.setFill(color);
				}
			}
			else{
				for(int i = x; i > x - ships[qtdPos].getTamanho(); i--){
					cell = getCell(i, y);
					if(!cell.getThereIsABoat())
						cell.setFill(color);
				}
			}
		}
	}
	
	void viewPosition(Coordinate shot, Color color){
		Cell cell = getCell(shot.getX(),shot.getY());
		if(!cell.getShot())
			cell.setFill(color);
	}
		
	void setPosition(Coordinate shot, Color color){
		Cell cell = getCell(shot.getX(),shot.getY());
		cell.setShot(true);
		cell.setFill(color);
		shots[shot.getY()][shot.getX()]=1;
	}
	
	void setPosition(int x, int y, boolean vertical, Color color){
		
		Coordinate initial;
		Coordinate finals;
		if(vertical){
			initial = new Coordinate(y,x);
			finals = new Coordinate((y+ships[qtdPos].getTamanho()-1),x);
		}
		else{	
			initial = new Coordinate(y,(x-ships[qtdPos].getTamanho()+1));
			finals = new Coordinate(y,x);
		}
		
		if(validatePosition(initial,finals)){				
			Cell cell;
			
			if(vertical){
				for(int i = y; i < y + ships[qtdPos].getTamanho(); i++){
					myBoats[i][x] = ships[qtdPos].getTamanho();
					cell = getCell(x, i);
					cell.setThereIsABoat(true);
					cell.setFill(color);
				}
			}
			else{
				for(int i = x; i > x - ships[qtdPos].getTamanho(); i--){
					myBoats[y][i] = ships[qtdPos].getTamanho();
					cell = getCell(i, y);
					cell.setThereIsABoat(true);
					cell.setFill(color);
				}
			}
			qtdPos++;
		}
	}
	
	void clear(){
		for(int i = 0;i<myBoats.length;i++){
			for(int j = 0; j<myBoats[0].length;j++){
				Cell cell = getCell(i, j);
				cell.setFill(Color.AQUA);
			}
		}
	}
		
	boolean finishPositioning(){
		if(qtdPos == ships.length && !this.finishPositioning){
			setHealth();
			clear();
			this.finishPositioning = !this.finishPositioning;
		}
		
		return this.finishPositioning;
	}
	
	void receivedShot(Shot shot){
		this.receivedShot = shot;
	}
	
	public Shot returnShot(){
		return this.receivedShot;
	}
	
}
