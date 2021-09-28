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

	private String name;
	private Class<?> dataClass;
	private Output source;

	/**
	* @param name
	* @param dataClass the class object representing the type of data the input can retrieve
	*/ 
	public Input(String name, Class<?> dataClass) {
		this.name = name;
		this.dataClass = dataClass;
		source = null;
	}

	/**
	* @return the input's name
	*/ 
	@Override
	public String toString() {
		return name;
	}

	/**
	* @return the class object representing the type of data the input can retrieve
	*/ 
	public Class<?> getDataClass() {
		return dataClass;
	}

	/**
	* @param source
	*/ 
	public void setSource(Output source) {
		this.source = source;
	}

	/**
	*
	*/ 
	public void removeSource() {
		source = null;
	}

	/**
	* @return true if input is connected to an output, otherwise false
	*/ 
	public boolean hasSource() {
		return (source != null);
	}

	/**
	* @param requestClass a class object representing the type expected by the object calling this method
	* @return the data retrieved by the input, null if the request type doesn't match the data type or if the input isn't connected to an output
	*/ 
	public <T> T getData(Class<T> requestClass) {
		if(source==null) return null;
		if(this.dataClass.equals(requestClass)) {
			return source.getData(requestClass);
		}
		return null;
	}

}
