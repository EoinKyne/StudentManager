package org.studentmanager;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class StudentNotFoundExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String StudentNotFoundExceptionHandler(StudentNotFoundException exception){
        return exception.getMessage();
    }
}
