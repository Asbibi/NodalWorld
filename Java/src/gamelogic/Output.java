package gamelogic;

import java.util.Set;
import java.util.HashSet;

import java.io.Serializable;

import java.lang.Object;
import java.lang.Class;

/**
* This class provides the abstract model for the output(s) of the nodes in the nodal system.
* An output is used to store some data once it has been calculated by a node, and then pass it on.
* 
* @see Input
* @see Node
*/ 
public class Output implements Serializable {

	private static int idCounter = 0;

	private int id;
	private String name;
	private Class<?> dataClass;
	private Object data;
	private Set<Input> targets;

	/**
	* @param name
	* @param dataClass the class object representing the type of data the output can hold
	*/ 
	public Output(String name, Class<?> dataClass) {
		id = idCounter;
		idCounter++;
		this.name = name;
		this.dataClass = dataClass;
		data = null;
		targets = new HashSet<Input>();
	}

	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(!(o instanceof Output)) return false;

		Output output = (Output) o;
		return (this.id == output.id);
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
	public void setData(Object data) throws NetworkIOException {
		if(data == null) {
			throw new NetworkIOException("Cannot write null to output " + name);
		}
		if(this.dataClass.isInstance(data)) {
			this.data = data;
		}
	}

	/**
	* @param requestClass a class object representing the type expected by the object calling this method
	* @return the data held by the output, null if the request type doesn't match the data type
	*/ 
	public <T> T getData(Class<T> requestClass) throws NetworkIOException {
		if(data == null) {
			throw new NetworkIOException("Cannot read data from output " + name);
		}
		if(this.dataClass.equals(requestClass)) {
			return requestClass.cast(data);
		}
		throw new NetworkIOException("Request class do not match data class of output " + name);
	}

	public void addTarget(Input target) {
		targets.add(target);
	}

	public void removeTarget(Input target) {
		targets.remove(target);
	}

	public void clearTargets() {
		targets.clear();
	}

	public boolean hasTarget() {
		return !targets.isEmpty();
	}

	public Set<Input> getTargets() {
		return targets;
	}

}
