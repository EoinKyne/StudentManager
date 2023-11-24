package org.studentmanager;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentAmendExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(StudentAmendException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String StudentAmendExceptionAdvice(StudentAmendException exception){
        return exception.getMessage();
    }
}
