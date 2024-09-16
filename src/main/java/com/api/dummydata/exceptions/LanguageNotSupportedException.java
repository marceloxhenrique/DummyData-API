package com.api.dummydata.exceptions;

public class LanguageNotSupportedException  extends RuntimeException {
  public LanguageNotSupportedException (){
    super("Language no supported");
  }
  public LanguageNotSupportedException (String message){
    super(message);
  }

}
