package scenes;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ButtonClass{
	//---------------------- ARGUMENTOS ---------------------
	private String text;
	private Scene nextScene;
	private int textSize, maxSizeX, maxSizeY;
	
	//--------------------- CONSTRUTORES --------------------
	
	public ButtonClass(Scene nextScene, String text){
		this(nextScene,text,50);
	}
	
	public ButtonClass(Scene nextScene, String text, int textSize){
		this(nextScene,text,textSize,400,100);
	}
	
	public ButtonClass(String text, int textSize, int maxSizeX, int maxSizeY){
		setNextScene(null);
		setButtonText(text);
		setTextSize(textSize);
		setMaxSizeX(maxSizeX);
		setMaxSizeY(maxSizeY);
	}
	
	public ButtonClass(Scene nextScene, String text, int textSize, int maxSizeX, int maxSizeY){
		setNextScene(nextScene);
		setButtonText(text);
		setTextSize(textSize);
		setMaxSizeX(maxSizeX);
		setMaxSizeY(maxSizeY);
	}
	
	//---------------------- GET E SET ----------------------
	
	private void setNextScene(Scene nextScene){
		this.nextScene = nextScene;
	}
	
	private Scene getNextScene(){
		return this.nextScene;
	}
	
	private void setButtonText(String text){
		this.text = text;
	}
	
	private String getButtonText(){
		return this.text;
	}
	
	private void setTextSize(int textSize){
		this.textSize = textSize;
	}
	
	private int getTextSize(){
		return this.textSize;
	}
	
	private void setMaxSizeX(int maxSizeX){
		this.maxSizeX = maxSizeX;
	}
	
	int getMaxSizeX(){
		return this.maxSizeX;
	}
	
	private void setMaxSizeY(int maxSizeY){
		this.maxSizeY = maxSizeY;
	}
	
	int getMaxSizeY(){
		return this.maxSizeY;
	}
	
    //----------------------- MÉTODOS -----------------------
	
	public Button getButton(){
		
		Button newButton = new Button();
		newButton.setFont(Font.loadFont("file:resources/fonts/fipps/Fipps-Regular.otf", getTextSize()));
        newButton.setText(this.getButtonText());
		newButton.setTextFill(Color.WHITE);
		newButton.setStyle("-fx-background-color: null; ");
		
		if(getButtonText() == "voltar")
			newButton.setOnAction(e -> Tela.getWindow().setScene(new TelaMain(Tela.getWindow()).createScene()));
		else		
			newButton.setOnAction(e -> Tela.getWindow().setScene(getNextScene()));
		
		newButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent e) {
				newButton.setTextFill(Color.RED);
			}
		});
		
		newButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent e) {
				newButton.setTextFill(Color.WHITE);
			}
		});
		
		newButton.setMaxSize(getMaxSizeX(), getMaxSizeY());
		
		return newButton;
	}
	
}