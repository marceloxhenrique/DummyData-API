package com.api.dummydata.infra;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.api.dummydata.exceptions.UsersAmmountNotSupportedException;
import com.api.dummydata.exceptions.LanguageNotSupportedException;
import com.api.dummydata.exceptions.UserNotFoundException;
import com.api.dummydata.exceptions.CommentAmmountNotSupportedException;
import com.api.dummydata.exceptions.CommentNotFoundException;
import com.api.dummydata.exceptions.ImageAmountNotSupportedException;
import com.api.dummydata.exceptions.ImageNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {
	@Autowired
	ObjectMapper objectMapper;

	@ExceptionHandler(LanguageNotSupportedException.class)
	public ResponseEntity<Object> handleIsLanguageSupported(LanguageNotSupportedException e){
		List<String> response = new ArrayList<>();
		response.add(0, e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UsersAmmountNotSupportedException.class)
	public ResponseEntity<Object> handleIsUserQuantitySupported(UsersAmmountNotSupportedException e){
		List<String> response = new ArrayList<>();
		response.add(0, e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e){
		List<String> response = new ArrayList<>();
		response.add(0, e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CommentNotFoundException.class)
	public ResponseEntity<Object> handleCommentNotFoundException(CommentNotFoundException e){
		List<String> response = new ArrayList<>();
		response.add(0, e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CommentAmmountNotSupportedException.class)
	public ResponseEntity<Object> handleIsCommentQuantitySupported(CommentAmmountNotSupportedException e){
		List<String> response = new ArrayList<>();
		response.add(0, e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ImageNotFoundException.class)
	public ResponseEntity<Object> handleIsImageNotFoundException(ImageNotFoundException e){
		List<String> response = new ArrayList<>();
		response.add(0, e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ImageAmountNotSupportedException.class)
	public ResponseEntity<Object> handleIsImageAmmountquantitySupported(ImageAmountNotSupportedException e){
		List<String> response = new ArrayList<>();
		response.add(0, e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
