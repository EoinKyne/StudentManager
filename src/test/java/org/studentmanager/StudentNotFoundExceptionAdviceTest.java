package org.studentmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StudentNotFoundExceptionAdviceTest {

    @Test
    void studentNotFoundExceptionHandler() {
        StudentNotFoundException studentNotFoundException = new StudentNotFoundException(1L);
        StudentNotFoundExceptionAdvice studentNotFoundExceptionAdvice = new StudentNotFoundExceptionAdvice();
        String message = studentNotFoundExceptionAdvice.StudentNotFoundExceptionHandler(studentNotFoundException);
        Assertions.assertFalse(message.isEmpty());
    }
}