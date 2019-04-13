package exceptions;


import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.geometry.*;

public class TextException extends Exception{

	private String text;
	
	public TextException(String text){
		setText(text);
	}
	
	private void setText(String text){
		this.text = text;
	}
	
	public String getText(){
		return this.text;
	}
	
}