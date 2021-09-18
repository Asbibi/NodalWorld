package gamelogic;

import java.lang.Object;
import java.lang.Class;

/**
* This class provides the abstract model for the output(s) of the nodes in the nodal system.
* An output is used to store some data once it has been calculated by a node, and then pass it on.
* 
* @see Input
* @see Node
*/ 
public class Output {

	private String name;
	private Class<?> dataClass;
	private Object data;

	/**
	* @param name
	* @param dataClass the class object representing the type of data the output can hold
	*/ 
	public Output(String name, Class<?> dataClass) {
		this.name = name;
		this.dataClass = dataClass;
	}

	/**
	* @return the output's name
	*/ 
	@Override
	public String toString() {
		return name;
	}

	/**
	* @return the class object representing the type of data the output can hold
	*/ 
	public Class<?> getDataClass() {
		return dataClass;
	}

	/**
	* @param data
	*/ 
	public void setData(Object data) {
		if(this.dataClass.isInstance(data)) {
			this.data = data;
		}
	}

	/**
	* @param requestClass a class object representing the type expected by the object calling this method
	* @return the data held by the output, null if the request type doesn't match the data type
	*/ 
	public <T> T getData(Class<T> requestClass) {
		if(this.dataClass.equals(requestClass)) {
			return requestClass.cast(data);
		}
		return null;
	}

}
