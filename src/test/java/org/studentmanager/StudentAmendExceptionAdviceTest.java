package org.studentmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StudentAmendExceptionAdviceTest {

    @Test
    void studentAmendExceptionAdvice() {
        StudentAmendExceptionAdvice studentAmendExceptionAdvice = new StudentAmendExceptionAdvice();
        StudentAmendException studentAmendException = new StudentAmendException(1L);
        String message = studentAmendExceptionAdvice.StudentAmendExceptionAdvice(studentAmendException);
        Assertions.assertFalse(message.isEmpty());
    }
}