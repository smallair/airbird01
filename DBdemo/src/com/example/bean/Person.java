package com.example.bean;

public class Person {
	
	private int id;

	private String name;
	private String sex;
	
	
	
	public Person() {
		super();
	}
	
	public Person(String name, String sex) {
		super();
		this.name = name;
		this.sex = sex;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

}
