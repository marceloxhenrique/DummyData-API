package com.api.dummydata.exceptions;

public class ImageAmountNotSupportedException extends RuntimeException{
  public ImageAmountNotSupportedException(){
    super("Ammount of Images not suported");
  }
  public ImageAmountNotSupportedException(String message){
    super(message);
  }
}
