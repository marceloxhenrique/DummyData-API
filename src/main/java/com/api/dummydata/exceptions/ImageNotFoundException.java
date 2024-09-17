package com.api.dummydata.exceptions;

public class ImageNotFoundException extends RuntimeException{
  public ImageNotFoundException(){
    super("Image ID not Found");
  }
  public ImageNotFoundException(String message){
    super(message);
  }
}
