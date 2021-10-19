package gameinterface;

/**
* Simple class to hold a message to print. It consists on the frame the message was emitted on and a string.
* 
* @see PrinterComponent
*/
public class PrinterMessage {
	private String message;
	private int frame;
	private boolean isError;
	
	
	/**
	* @param frame the frame the message is emitted on
	* @param message the actual message
	*/
	public PrinterMessage(int frame, String message) {
		this(frame, message, false);
	}
	
	/**
	* @param frame the frame the message is emitted on
	* @param message the actual message
	* @param isError indicates if the message is from anetwork error and thus if should be printed in red
	*/
	public PrinterMessage(int frame, String message, boolean isError) {
		this.frame = frame;
		this.message = message;
		this.isError = isError;
	}
	
	/**
	* @return the frame the message was emitted on
	*/
	public int getFrame() {
		return frame;
	}

	/**
	* @param withFrame indicates if the frame should be added on the message
	* @return the message, eventually with the frame included in it
	*/
	public String getMessage(boolean withFrame) {
		if(withFrame)
			return "F: " + frame + "     \t|     " + message;
		else
			return message;
	}
	
	/**
	* @return if the message has been emitted from a printer or a the node editor for a Network Exception
	*/
	public boolean getIsError() {
		return isError;
	}
}
