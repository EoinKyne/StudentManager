package org.studentmanager;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class StudentNameIsEmptyExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(StudentNameIsEmptyException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String studentNameIsEmptyExceptionHandler(StudentNameIsEmptyException exception){
        return exception.getMessage();
    }

}
