package clueGame;

import java.io.FileNotFoundException;

public class BadConfigFormatException extends Exception{
	private String message;
	
	public BadConfigFormatException(String s) {
		message = s;
	}
	
	public String getMessage() {
		return message;
	}
}
