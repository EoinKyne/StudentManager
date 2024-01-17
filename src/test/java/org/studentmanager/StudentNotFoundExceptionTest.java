package org.studentmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StudentNotFoundExceptionTest {

    @Test
    public void StudentNotFoundException(){
        Student student = new Student(1L, "John Doe");

        StudentNotFoundException studentNotFoundException = new StudentNotFoundException(1L);

        Assertions.assertNotNull(studentNotFoundException);
    }

}