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
import javafx.scene.text.*;
import javafx.geometry.*;

public class Regras extends Tela{
	
	//--------------------- CONSTRUTORES --------------------
	
	public Regras(Stage window){
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
	
	private Text rulesSentences(String text){
		Text sentence = new Text(text);
		sentence.setFont(Font.loadFont("file:resources/fonts/minecraft/Minecraft.ttf", 25));
		sentence.setFill(Color.WHITE);
		sentence.setTextAlignment(TextAlignment.JUSTIFY);
		
		return sentence;
	}
	
	private VBox fill(int space, int width){
		VBox fill = new VBox(space);
		fill.setAlignment(Pos.CENTER);
		fill.setPrefWidth(width);
		
		return fill;
	}
	
	@Override
	public Scene createScene(){
		// Botao voltar
		ButtonClass voltar = new ButtonClass("voltar",20,150,75);
		VBox buttonPosition = posButton(voltar,voltar.getMaxSizeX());
		
		//Texto das regras
		Text text1 = rulesSentences("1 - Clique com o botao esquerdo do mouse para\n posicionar a peca e com o direito\n para rotaciona-la");
		Text text2 = rulesSentences("2 - Apos posicionado todos os barcos, \n o jogador 1 comeca atirando no \n tabuleiro da direita");
		Text text3 = rulesSentences("3 - O Jogo Acaba quando a vida de \n um dos jogadores se anular");
		
		// Espaço entre o botão e o texto
		VBox fill = fill(25,getSizeX()-voltar.getMaxSizeX());
		fill.getChildren().addAll(text1,text2, text3);
		
		// Juntando os elementos num único HBox
		HBox allSceneElements = new HBox();
		allSceneElements.setAlignment(Pos.CENTER);
		allSceneElements.getChildren().addAll(fill,buttonPosition);
		
        StackPane mix = addSceneResources();
		mix.getChildren().add(allSceneElements);
		
		return new Scene(mix, getSizeX(), getSizeY());
	}
    
}