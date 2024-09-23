package com.api.dummydata.exceptions;

public class CommentNotFoundException extends RuntimeException{
	public CommentNotFoundException(){
		super("Comment ID not found");
	}
	public CommentNotFoundException(String message){
		super(message);
	}
}
