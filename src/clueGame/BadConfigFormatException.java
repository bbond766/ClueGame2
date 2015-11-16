package clueGame;

public class BadConfigFormatException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public BadConfigFormatException(String s) {
		message = s;
	}
	
	public String getMessage() {
		return message;
	}
}
