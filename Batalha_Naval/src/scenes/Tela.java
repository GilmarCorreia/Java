package scenes;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;

public abstract class Tela{
	//---------------------- ARGUMENTOS ---------------------
	
	private Scene previousScene;
	private int sizeX, sizeY;
	private String description;
	private static Stage window;
		
	//--------------------- CONSTRUTORES --------------------
	
	public Tela(Stage window){
		this(window,750,750);
	}
	
	public Tela(Stage window,int sizeX, int sizeY){
		this.window = window;
		setDescription("BATALHA NAVAL");
		setSizeX(sizeX);
		setSizeY(sizeY);
	}
		
	//----------------------- SETTERS -----------------------
	
	static Stage getWindow(){
		return window;
	}
	
	private void setDescription(String description){
		this.description = description;
	}
	
	private void setSizeX(int sizeX){
		this.sizeX = sizeX;
	}
	
	private void setSizeY(int sizeY){
		this.sizeY = sizeY;
	}
	//----------------------- GETTERS -----------------------
	
	private String getDescription(){
		return this.description;
	}
	
	protected int getSizeX(){
		return this.sizeX;
	}
	
	protected int getSizeY(){
		return this.sizeY;
	}
	
	//----------------------- MÉTODOS -----------------------
	
	protected StackPane addSceneResources() {
		/*
		// Método utilizado para setar a configuração inicial de todas as telas do jogo.
		*/
		
		// Caixa de texto
		Text titulo = new Text(getDescription());
        titulo.setFont(Font.loadFont("file:resources/fonts/fipps/Fipps-Regular.otf", 50));
		titulo.setFill(Color.WHITE);
		titulo.setTextAlignment(TextAlignment.CENTER);
		
		//Vertical box para setar a posição do texto
		VBox title = new VBox();
		title.getChildren().add(titulo);
		title.setAlignment(Pos.TOP_CENTER);
		
		// Código para setar imagem de bacground
		Canvas canvas = new Canvas(getSizeX(),getSizeY());
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Image water = new Image("file:resources/img/water3.jpg");
		gc.drawImage(water,0,0,getSizeX(),getSizeY());
		
		//Juntando todos os objetos criados em uma única cena
		StackPane mixSceneObjects = new StackPane();
		mixSceneObjects.getChildren().add(canvas);
		mixSceneObjects.getChildren().add(title);
		
		return mixSceneObjects;
	}
	
	public abstract Scene createScene();
		/*
		// Método que define objetos específicos de cada cena.
		*/

}