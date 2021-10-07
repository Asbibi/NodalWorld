package gamelogic;

/**
* Exception raised by an input or an output when it is asked to read or write an invalid data, for instance null.
* 
* @see Input
* @see Output
*/ 
public class NetworkIOException extends Exception {

	public NetworkIOException(String errorMessage) {
		super(errorMessage);
	}

}
