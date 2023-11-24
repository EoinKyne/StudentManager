package org.studentmanager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentNameIsEmptyExceptionTest {

    @Test
    public void testStudentNameIsEmptyException(){
        Student student = new Student(1L, " ");
        StudentNameIsEmptyException studentNameIsEmptyException = new StudentNameIsEmptyException(student);
        assertNotNull(studentNameIsEmptyException);
    }

}