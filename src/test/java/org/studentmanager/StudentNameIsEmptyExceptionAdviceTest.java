package org.studentmanager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentNameIsEmptyExceptionAdviceTest {

    @Test
    void studentNameIsEmptyExceptionHandler() {
        Student student = new Student(1L, " ");

        StudentNameIsEmptyException studentNameIsEmptyException = new StudentNameIsEmptyException(student);
        StudentNameIsEmptyExceptionAdvice studentNameIsEmptyExceptionAdvice = new StudentNameIsEmptyExceptionAdvice();

        String message = studentNameIsEmptyExceptionAdvice.studentNameIsEmptyExceptionHandler(studentNameIsEmptyException);
        assertFalse(message.isEmpty());
    }

}