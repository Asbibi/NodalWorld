package gamelogic;

import java.lang.Class;

/**
* This class provides the abstract model for the input(s) of the nodes in the nodal system.
* An input is used to retrieve some data from an output, so it can be used for further calculations.
* 
* @see Output
* @see Node
*/ 
public class Input {

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

		manual = false;
		value = null;
	}

	public Input(String name, Boolean val) {
		this(name, Boolean.class);
		manual = true;
		value = val;
	}

	public Input(String name, Integer val) {
		this(name, Integer.class);
		manual = true;
		value = val;
	}

	public Input(String name, Double val) {
		this(name, Double.class);
		manual = true;
		value = val;
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

	public Output getSource() { return source; }

	public boolean isManual() { return manual; }

	public void setManualValue(Object val) {
		if(dataClass.isInstance(val) && manual) {
			value = val;
		}
	}

	public <T> T getManualValue(Class<T> requestClass) {
		if(manual && dataClass.equals(requestClass)) {
			return requestClass.cast(value);
		}
		return null;
	}

	/**
	* @param requestClass a class object representing the type expected by the object calling this method
	* @return the data retrieved by the input, null if the request type doesn't match the data type or if the input isn't connected to an output
	*/ 
	public <T> T getData(Class<T> requestClass) {
		if(source==null) {
			return getManualValue(requestClass);
		}
		if(dataClass.equals(requestClass)) {
			return source.getData(requestClass);
		}
		return null;
	}

}
