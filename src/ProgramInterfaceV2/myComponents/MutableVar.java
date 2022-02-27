package ProgramInterfaceV2.myComponents;


public class MutableVar <T>{
	private T value;
	
	public MutableVar(T value) {
		this.value = value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
}
