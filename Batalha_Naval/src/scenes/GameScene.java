package scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.geometry.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton ;
import javafx.scene.text.*;
import javafx.scene.layout.*;

import players.*;
import boats.structs.*;
import exceptions.*;

public class GameScene extends Tela{
    
    //---------------------- ATRIBUTOS ----------------------
	private boolean isPlayer;
	private boolean isVertical = true;
	
    private	int middleMarginValue = 250;
	
	private Text playerTurn = new Text("Turno: Jogador 1");
	private Text showHealth = new Text();
	
	private GamePlayerResources[] players = new GamePlayerResources[2];

    //--------------------- CONSTRUTORES --------------------
	
	public GameScene(Stage window){
		this(window,true);
	}
    
    public GameScene(Stage window, boolean isPlayer){
		super(window,1500,750);
        setIsPlayer(isPlayer);
		
		players[0] = new Player((getSizeX()/2)-(middleMarginValue/2), getSizeY(), event -> {setPlayer0Event(event);});
		
		if(isPlayer()){
			players[1] = new Player((getSizeX()/2)-(middleMarginValue/2), getSizeY(), event -> {setPlayer1Event(event);});
		}
		else{
			players[1] = new Computer((getSizeX()/2)-(middleMarginValue/2), getSizeY(), event -> {setComputerEvent(event);});
		}
			
	}
    
    // ---------------------- SETTERS -----------------------
	
	/*
	// Método que define a ação do player 0 caso exista ação do mouse;
	*/
	private void setPlayer0Event(MouseEvent event){
		if(GamePlayerResources.control){
			if(!players[0].finishPositioning()){
				setPositionEvent(event,0,!players[0].finishPositioning());
				playerTurn.setText("Turno: Jogador 1");
			}
		}
				
		if(players[1].playerTime() && !GamePlayerResources.control){
			if(players[0].getHealth() == 0 || players[1].getHealth() ==0)
				return;
			else{
				playerTurn.setText("Turno: Jogador 2");
				try{
					setGame(event, 0);
				} catch(TextException e){
					playerTurn.setText(e.getText());
				}
				showHealth.setText(getPlayers()[0].getHealth() + " " + getPlayers()[1].getHealth());
			}
		}
	}
	
	/*
	// Método que define a ação do player 1 caso exista ação do mouse;
	*/
	private void setPlayer1Event(MouseEvent event){
		if(GamePlayerResources.control){
			if(players[0].finishPositioning()){
				if(!players[1].finishPositioning()){
					setPositionEvent(event,1,players[0].finishPositioning());
					playerTurn.setText("Turno: Jogador 2");
				}
				else{
					GamePlayerResources.control = false;
					players[0].setPlayerTime(true);
				}
			}
		}
				
		if(players[0].playerTime() && !GamePlayerResources.control){
			if(players[0].getHealth() == 0 || players[1].getHealth() ==0)
				return;
			else{
				playerTurn.setText("Turno: Jogador 1");
				try{
					setGame(event, 1);
				} catch(TextException e){
					playerTurn.setText(e.getText());
				}
				showHealth.setText(getPlayers()[0].getHealth() + " " + getPlayers()[1].getHealth());
			}
		}
	}
	
	private void setComputerEvent(MouseEvent event){
		if(GamePlayerResources.control){
			if(players[0].finishPositioning()){
				GamePlayerResources.control = false;
				players[0].setPlayerTime(true);
			}
		}
					
		if(players[0].playerTime() && !GamePlayerResources.control){
			if(players[0].getHealth() == 0 || players[1].getHealth() ==0)
				return;
			else{
				playerTurn.setText("Turno: Jogador 1");
				try{
					setGame(event, 1);
				} catch(TextException e){
					playerTurn.setText(e.getText());
				}
				showHealth.setText(getPlayers()[0].getHealth() + " " + getPlayers()[1].getHealth());
			}
		}
	}
	
    
	private void setPositionEvent(MouseEvent event, int i, boolean playerTime){
		Cell cell = (Cell) event.getSource();
		
		if(event.getButton() == MouseButton.SECONDARY){
			setVertically();
			getPlayers()[i].viewPosition((int) cell.getX(),(int) cell.getY(),!isVertical(), Color.AQUA);
			getPlayers()[i].viewPosition((int) cell.getX(),(int) cell.getY(),isVertical(), Color.RED);
		}
					
		if(playerTime){
			if (event.getEventType() == MouseEvent.MOUSE_ENTERED)
				getPlayers()[i].viewPosition((int) cell.getX(),(int) cell.getY(),isVertical(), Color.RED);
			else if (event.getEventType() == MouseEvent.MOUSE_EXITED)
				getPlayers()[i].viewPosition((int) cell.getX(),(int) cell.getY(),isVertical(), Color.AQUA);
			else if(event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton() == MouseButton.PRIMARY)
				getPlayers()[i].setPosition((int) cell.getX(),(int) cell.getY(),isVertical(), Color.RED);
		}
	}
	
	private void setGame(MouseEvent event, int i) throws TextException{
		Cell cell = (Cell) event.getSource();
		Coordinate shootCoord = new Coordinate((int) cell.getX(),(int) cell.getY());
		
		if(players[0].getHealth() != 0 && players[1].getHealth() !=0){
			
			if(event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton() == MouseButton.PRIMARY && !cell.getShot()){
				
				if(getPlayers()[i].checkShot(shootCoord) == Shot.HIT){		
					getPlayers()[i].setPosition(shootCoord, Color.RED);
					getPlayers()[i].setHealth(shootCoord);
				}
				else{
					getPlayers()[i].setPosition(shootCoord, Color.BLUE);
					
					if(players[1] instanceof Player){
						getPlayers()[0].setPlayerTime(!getPlayers()[0].playerTime());
						getPlayers()[1].setPlayerTime(!getPlayers()[1].playerTime());
					}
					
					else if(players[1] instanceof Computer){
						do{
							try {
								setComputerMove(players[1].shoot());
								Thread.sleep(200);
							} catch (Exception e) {
							   e.printStackTrace();
							}
						} while(players[1].returnShot() == Shot.HIT  && players[0].getHealth() != 0);
					}
				}
			}
			else if(event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton() == MouseButton.PRIMARY && cell.getShot())
				throw new TextException("Atirou na mesma celula");
			
			if (event.getEventType() == MouseEvent.MOUSE_ENTERED)
				getPlayers()[i].viewPosition(shootCoord, Color.GREEN);
			else if (event.getEventType() == MouseEvent.MOUSE_EXITED)
				getPlayers()[i].viewPosition(shootCoord, Color.AQUA);
		}
		
		if(players[0].getHealth() == 0){
			if(players[1] instanceof Player)
				playerTurn.setText("Player 2 ganhou!");
			if(players[1] instanceof Computer)
				playerTurn.setText("Computer ganhou!");
		}
		
		else if(players[1].getHealth() == 0){
			playerTurn.setText("Player 1 ganhou!");
		}
	}
	
	private void setComputerMove(Coordinate shootCoord){
	
		if(players[0].getHealth() != 0 && players[1].getHealth() !=0){
			
			getPlayers()[1].receivedShot(getPlayers()[0].checkShot(shootCoord));
			
			if(getPlayers()[0].checkShot(shootCoord) == Shot.HIT){						
				getPlayers()[0].setPosition(shootCoord, Color.RED);
				getPlayers()[0].setHealth(shootCoord);
			}
			else{
				getPlayers()[0].setPosition(shootCoord, Color.BLUE);
			}	
		}
	}
	
	private void setVertically(){
		this.isVertical = !this.isVertical;
	}
	
    private void setIsPlayer(boolean isPlayer){
        this.isPlayer = isPlayer;
    }
    
    private boolean isPlayer(){
        return this.isPlayer;
    }
	
	private VBox setMiddleMargin(){
		VBox middleMargin = new VBox();
		middleMargin.setPrefWidth(middleMarginValue);
		middleMargin.setPrefHeight(getSizeY());
		
		VBox fstFill = new VBox();
		fstFill.setAlignment(Pos.CENTER);
		fstFill.setPrefHeight(350);
		
		playerTurn.setFont(Font.loadFont("file:resources/fonts/minecraft/Minecraft.ttf", 20));
		playerTurn.setFill(Color.WHITE);
		playerTurn.setTextAlignment(TextAlignment.CENTER);
		
		VBox scnFill = new VBox();
		scnFill.setAlignment(Pos.CENTER);
		scnFill.setPrefHeight(200);
		
		showHealth.setFont(Font.loadFont("file:resources/fonts/minecraft/Minecraft.ttf", 45));
		showHealth.setFill(Color.WHITE);
		showHealth.setTextAlignment(TextAlignment.CENTER);
		
		VBox thdFill = new VBox();
		thdFill.setAlignment(Pos.CENTER);
		thdFill.setPrefHeight(200);
		
		ButtonClass voltar = new ButtonClass("voltar",20,150,75);
		
		middleMargin.getChildren().addAll(fstFill,playerTurn,scnFill,showHealth,thdFill,voltar.getButton());
		middleMargin.setAlignment(Pos.CENTER);
		
		return middleMargin;
	}
    
	// ---------------------- GETTERS -----------------------
	
    private GamePlayerResources[] getPlayers(){
        return this.players;
    }
	
	private boolean isVertical(){
		return this.isVertical;
	}
    
	//----------------------- MÉTODOS -----------------------
	
	@Override
	public Scene createScene(){
		
        HBox game = new HBox();
        game.setAlignment(Pos.CENTER);
        game.setPrefWidth(getSizeX());
		game.setPrefHeight(getSizeY());

        game.getChildren().add(players[0]);
		game.getChildren().add(setMiddleMargin());
		game.getChildren().add(players[1]);
		
        StackPane mix = addSceneResources();
		mix.getChildren().add(game);
        
        return new Scene(mix,getSizeX(),getSizeY());
    }
	
}