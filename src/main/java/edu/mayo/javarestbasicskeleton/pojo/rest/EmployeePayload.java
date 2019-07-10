package edu.mayo.javarestbasicskeleton.pojo.rest;

public class EmployeePayload {

	String firstName;
	String lastName;
	String email;
	
	public EmployeePayload(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "AssetPayload [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
	
}