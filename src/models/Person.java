package models;

import java.io.Serializable;

public class Person implements Serializable {
	
	public final static long serialVersionUID = 1;
	
	private long id;
	private String firstName;
	private String lastName;
	private int age;
	
	// ------ ------ Constructors ------ ------
	public Person() {
		super();
	}

	public Person(long id, String firstName, String lastName, int age) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}
	
	// ------ ------ Standard getters and setters ------ ------
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	public String getFirstName() {return firstName;}
	public void setFirstName(String firstName) {this.firstName = firstName;}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
} // end Class Person
