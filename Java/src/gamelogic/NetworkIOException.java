package gamelogic;

/**
* Exception raised by an input or an output when it is asked to read or write an invalid data (usually a null value).
* 
* @see Input
* @see Output
*/ 
public class NetworkIOException extends Exception {

	/**
	* @param errorMessage
	*/ 
	public NetworkIOException(String errorMessage) {
		super(errorMessage);
	}

}
