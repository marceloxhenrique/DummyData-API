package com.api.dummydata.exceptions;

public class CommentAmmountNotSupportedException extends RuntimeException {
  public CommentAmmountNotSupportedException (){
    super("Amount of comments not supported");
  }
  public CommentAmmountNotSupportedException (String message){
    super(message);
  }
}
