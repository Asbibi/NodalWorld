package gameinterface;

public class PrinterMessage {
	private String message;
	private int frame;
	
	
	public PrinterMessage(int frame, String message) {
		this.frame = frame;
		this.message = message;
	}
	
	public int getFrame() {
		return frame;
	}

	public String getMessage(boolean withFrame) {
		if(withFrame)
			return "F: " + frame + "     \t|     " + message;
		else
			return message;
	}
}
