package db;

public class InvalidEntryException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public InvalidEntryException(String msg) {
		super(msg);
		
	}

}
