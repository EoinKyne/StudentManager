package org.studentmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StudentGradePointAverageOutOfBoundsExceptionAdviceTest {

    @Test
    void studentGradePointAverageOutOfBoundsExceptionHandler() {
        StudentGradePointAverageOutOfBoundsException studentGradePointAverageOutOfBoundsException = new StudentGradePointAverageOutOfBoundsException(5.0);
        StudentGradePointAverageOutOfBoundsExceptionAdvice studentGradePointAverageOutOfBoundsExceptionAdvice = new StudentGradePointAverageOutOfBoundsExceptionAdvice();
        String message = studentGradePointAverageOutOfBoundsExceptionAdvice.StudentGradePointAverageOutOfBoundsExceptionHandler(studentGradePointAverageOutOfBoundsException);
        Assertions.assertFalse(message.isEmpty());
    }
}