package com.api.dummydata.exceptions;

public class UserNotFoundException extends RuntimeException{
	public UserNotFoundException(){
		super("User ID not found!");
	}
	public UserNotFoundException(String message){
		super(message);
	}
}
