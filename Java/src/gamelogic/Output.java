package gamelogic;

import java.lang.Object;
import java.lang.Class;

public class Output {

	private String name;
	protected Class<?> dataClass;
	private Object data;

	public Output(String name, Class<?> dataClass) {
		this.name = name;
		this.dataClass = dataClass;
	}

	public String getName() {
		return name;
	}

	public Class<?> getDataClass() {
		return dataClass;
	}

	public void setData(Object data) {
		if(this.dataClass.isInstance(data)) {
			this.data = data;
		}
	}

	public <T> T getData(Class<T> requestClass) {
		if(this.dataClass.equals(requestClass)) {
			return requestClass.cast(data);
		}
		return null;
	}

}
