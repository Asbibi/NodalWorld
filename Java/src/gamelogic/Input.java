package gamelogic;

import java.lang.Class;

public class Input {

	private String name;
	private Class<?> dataClass;
	private Output source;

	public Input(String name, Class<?> dataClass) {
		this.name = name;
		this.dataClass = dataClass;
		source = null;
	}

	public String getName() {
		return name;
	}

	public Class<?> getDataClass() {
		return dataClass;
	}

	public boolean isCompatibleWith(Output output) {
		return dataClass.equals(output.getDataClass());
	}

	public void setSource(Output source) {
		this.source = source;
	}

	public <T> T getData(Class<T> requestClass) {
		if(source==null) return null;
		if(this.dataClass.equals(requestClass)) {
			return source.getData(requestClass);
		}
		return null;
	}

}
