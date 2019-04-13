package scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.*;

public class TelaMain extends Tela{
	
	//--------------------- CONSTRUTORES --------------------
	
	public TelaMain(Stage window){
		super(window);
	}

    //----------------------- MÉTODOS -----------------------
		
	@Override
	public Scene createScene(){
		
		// Botao Jogar
		ButtonClass jogar = new ButtonClass(new Escolher(Tela.getWindow()).createScene(),"JOGAR");
		
		// Botao Regras
		ButtonClass regras = new ButtonClass(new Regras(Tela.getWindow()).createScene(),"REGRAS");
		
		// VBox que seta a posição dos botões com espaçamento entre eles de 10 px. 
		VBox vbox = new VBox(10); 
		vbox.getChildren().addAll(jogar.getButton(), regras.getButton());
		vbox.setAlignment(Pos.CENTER);
		
		// Cena construida para ser apresentada
        StackPane mix = addSceneResources();
		mix.getChildren().add(vbox);
		
		return new Scene(mix, getSizeX(), getSizeY());
	}
    
}