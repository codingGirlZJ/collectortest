package out;

import java.io.Serializable;

public class SerializableDemo implements Serializable{

	private String name;
	private int age;
	public SerializableDemo(){
		
	}
	
	public SerializableDemo(String name, int age){
		this.name = name;
		this.age = age;
	}
	
	@Override
	public String toString(){
		return "姓名：" + name + " 年龄：" + age;
	}
}
