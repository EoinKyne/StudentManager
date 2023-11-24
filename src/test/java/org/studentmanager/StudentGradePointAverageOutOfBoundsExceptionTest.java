package org.studentmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StudentGradePointAverageOutOfBoundsExceptionTest {

    @Test
    public void StudentGradePointAverageOutOfBoundsExceptionTest(){
        StudentGradePointAverageOutOfBoundsException studentGradePointAverageOutOfBoundsException = new StudentGradePointAverageOutOfBoundsException(5.0);
        Assertions.assertNotNull(studentGradePointAverageOutOfBoundsException);
    }
}