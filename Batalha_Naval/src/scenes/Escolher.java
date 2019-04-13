package scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.*;
import javafx.scene.text.*;

public class Escolher extends Tela{
	
	//--------------------- CONSTRUTORES --------------------
	
	public Escolher(Stage window){
		super(window);
	}
	
	//----------------------- MÉTODOS -----------------------
	
	private VBox posButton(ButtonClass button, int width){
		VBox buttonPosition = new VBox();
		buttonPosition.getChildren().addAll(button.getButton());
		buttonPosition.setAlignment(Pos.BOTTOM_RIGHT);
		buttonPosition.setPrefWidth(width);
		
		return buttonPosition;
	}
	
	@Override
	public Scene createScene(){
		
		// Botao voltar
		ButtonClass voltar = new ButtonClass("voltar",20,150,75);
		VBox buttonPosition = posButton(voltar,voltar.getMaxSizeX());
		
		// Botao VS Jogador
		ButtonClass vsP = new ButtonClass(new GameScene(Tela.getWindow()).createScene(),"P Vs P");
		
		// Botao VS Comp
		ButtonClass vsC = new ButtonClass(new GameScene(Tela.getWindow(),false).createScene(),"P Vs C");
			
			
		// Setando a caixa da esquerda	
		VBox left = new VBox();
		left.setPrefWidth(150);
		
		
		//Arrumando a caixa do centro
		VBox center = new VBox(10);
		center.getChildren().addAll(vsP.getButton(),vsC.getButton());
		center.setAlignment(Pos.CENTER);
		center.setPrefWidth(vsP.getMaxSizeX()+50);
		
		// Agrupando todos os elementos em uma única HBox
		HBox tudo = new HBox();
		tudo.getChildren().addAll(left,center,buttonPosition);
		tudo.setAlignment(Pos.CENTER);
        
		// Grupo
        StackPane mix = addSceneResources();
		mix.getChildren().addAll(tudo);
		
		return new Scene(mix, getSizeX(), getSizeY());
	}
    
}