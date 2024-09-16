package com.api.dummydata.exceptions;

public class UsersAmmountNotSupportedException extends RuntimeException {
  public UsersAmmountNotSupportedException (){
    super("Amount of users not supported");
  }
  public UsersAmmountNotSupportedException (String message){
    super(message);
  }
}
