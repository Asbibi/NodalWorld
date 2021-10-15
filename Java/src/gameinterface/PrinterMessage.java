package gameinterface;

/**
* Simple class to hold a message to print. It consists on the frame the message was emitted on and a string.
* 
* @see PrinterComponent
*/
public class PrinterMessage {
	private String message;
	private int frame;
	
	
	/**
	* @param frame the frame the message is emitted on
	* @param message the actual message
	*/
	public PrinterMessage(int frame, String message) {
		this.frame = frame;
		this.message = message;
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
}
