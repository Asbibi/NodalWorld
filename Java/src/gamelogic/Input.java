package gamelogic;

import java.io.Serializable;
import java.lang.Class;

/**
* This class provides the abstract model for the input(s) of the nodes in the nodal system.
* An input is used to retrieve some data from an output, so it can be used for further calculations.
* It is parameterized at construction time by the type of data it contains using class objects.
* 
* @see Output
* @see Node
*/ 
public class Input implements Serializable {

	private static int idCounter = 0;

	private int id;
	private String name;
	private Class<?> dataClass;
	private Output source;

	private boolean manual;
	private Object value;

	/**
	* @param name
	* @param dataClass the class object representing the type of data the input can retrieve
	*/ 
	public Input(String name, Class<?> dataClass) {
		id = idCounter;
		idCounter++;
		this.name = name;
		this.dataClass = dataClass;
		source = null;

		if(dataClass.equals(Boolean.class)) {
			manual = true;
			value = false;

		} else if(dataClass.equals(Integer.class)) {
			manual = true;
			value = 0;

		} else if(dataClass.equals(Double.class)) {
			manual = true;
			value = 0.;

		} else if(dataClass.equals(Surface.class)) {
			manual = true;
			value = Surface.getEmpty();

		} else if(dataClass.equals(Species.class)) {
			manual = true;
			value = Species.getEmpty();

		} else if(dataClass.equals(String.class)) {
			manual = true;
			value = "";

		} else {
			manual = false;
			value = null;

		}
	}

	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(!(o instanceof Input)) return false;

		Input input = (Input) o;
		return (this.id == input.id);
	}

	/**
	* @return the input's name
	*/ 
	@Override
	public String toString() { return name; }

	/**
	* @return the class object representing the type of data the input can retrieve
	*/ 
	public Class<?> getDataClass() { return dataClass; }


	// ========== SOURCE OUTPUT ==========

	/**
	* @param source
	*/ 
	public void setSource(Output source) { this.source = source; }

	/**
	*
	*/ 
	public void removeSource() { source = null; }

	/**
	* @return true if input is connected to an output, otherwise false
	*/ 
	public boolean hasSource() { return (source != null); }

	/**
	* @return the output used a source for the data
	*/ 
	public Output getSource() { return source; }


	// ========== MANIPULATE DATA ==========

	/**
	* @return true if the input can be set manually, otherwise false
	*/ 
	public boolean isManual() { return manual; }

	/**
	* @param val the value to set manually in the input
	*/ 
	public void setManualValue(Object val) {
		if(dataClass.isInstance(val) && manual) {
			value = val;
		}
	}

	/**
	* @param requestClass
	* @return the value that was set manually
	* @throws NetworkIOException if the value is null or if the request class differs from the input data class
	*/ 
	public <T> T getManualValue(Class<T> requestClass) throws NetworkIOException {
		if(value == null) {
			throw new NetworkIOException("No manual value defined on input " + name);
		}
		if(dataClass.equals(requestClass)) {
			return requestClass.cast(value);
		}
		throw new NetworkIOException("Request class do not match data class of input " + name);
	}

	/**
	* @param requestClass a class object representing the type expected by the object calling this method
	* @return the data retrieved by the input, null if the request type doesn't match the data type or if the input isn't connected to an output
	* @throws NetworkIOException if the request class differs from the input data class or if the data and the manual value are null
	*/ 
	public <T> T getData(Class<T> requestClass) throws NetworkIOException {
		if(source==null) {
			if(manual) return getManualValue(requestClass);
			else throw new NetworkIOException("Cannot read data from input " + name);
		}
		if(dataClass.equals(requestClass)) {
			return source.getData(requestClass);
		}
		throw new NetworkIOException("Request class do not match data class of input " + name);
	}

}
