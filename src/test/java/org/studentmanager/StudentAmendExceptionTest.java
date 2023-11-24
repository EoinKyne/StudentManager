package org.studentmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StudentAmendExceptionTest {

    @Test
    public void StudentAmendException(){
        StudentAmendException studentAmendException = new StudentAmendException(1L);
        Assertions.assertNotNull(studentAmendException);
    }

}