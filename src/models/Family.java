package models;

import java.io.Serializable;
import java.util.List;

public class Family implements Serializable {
	
	public static final long serialVersionUID = 1;
	
	private String surName;
	private List<Person> parents;
	private List<Person> children;
	
	
	// ------ ------ Constructors ------ ------
	public Family() {
		super();
	}
	public Family(String surName, List<Person> parents, List<Person> children) {
		super();
		this.surName = surName;
		this.parents = parents;
		this.children = children;
	}

	// ------ Standard Getters and Setters -------
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public List<Person> getParents() {
		return parents;
	}
	public void setParents(List<Person> parents) {
		this.parents = parents;
	}
	public List<Person> getChildren() {
		return children;
	}
	public void setChildren(List<Person> children) {
		this.children = children;
	}

} // end class Family
