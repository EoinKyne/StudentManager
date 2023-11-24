package org.studentmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class StudentTest {

    private Student student;
    private List<Student> studentList = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        student = new Student(1L, "", 3.3);
    }

    @Test
    public void testStudentNameIsEmptyException(){
        StudentNameIsEmptyException studentNameIsEmptyException = Assertions.assertThrows(StudentNameIsEmptyException.class, () -> {
            student.checkStudentName(student);
        }, "StudentNameIsEmptyException exception is expected");
        Assertions.assertEquals("Cannot add new student without a full name", studentNameIsEmptyException.getMessage());
    }

    @Test
    public void testAddGradePointAverage(){
        Student student2 = new Student(2L, "Jane Doe", 3.1);
        studentList.add(student);
        studentList.add(student2);

        Assertions.assertEquals(3.1, studentList.get(1).getGradePointAverage());
        Assertions.assertEquals(3.3, studentList.get(0).getGradePointAverage());

    }
}
