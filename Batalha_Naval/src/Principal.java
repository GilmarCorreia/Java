import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

import scenes.*;
 
public class Principal extends Application {
	
    public static void main(String[] args) {
        launch(args);
    }
    
	@Override
    public void start(Stage primaryStage) throws Exception{
		
		String musicFile = "resources/sounds/8 Bit Universe.mp3";     // For example

		Media sound = new Media(new File(musicFile).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
			
		primaryStage.setTitle("Batalha Naval");
		primaryStage.setScene(new TelaMain(primaryStage).createScene());
		primaryStage.show();
		
		
    }

}