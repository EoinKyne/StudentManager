package org.studentmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentIntegrationTests {

    private StudentController studentController;
    private Map<Long, Student> repository = new HashMap<>();
    private StudentRepository studentRepository = new StudentRepository(repository);
    private Student student;

    @BeforeEach
    public void setUp(){
        studentController = new StudentController(studentRepository);
    }

    @Test
    public void addStudentTest(){
        student = new Student("Jane Doe", 2.5);
        student = studentController.addStudent(student);
        Assertions.assertEquals(1L, student.getStudentId());
    }

    @Test
    public void amendStudentTest(){
        student = new Student( "Jane Doe", 2.3);
        student = studentController.addStudent(student);
        Student amendedStudent = studentController.amendGradePointAverageByStudent(student.getStudentId(), 3.3);
        Assertions.assertEquals(3.3, amendedStudent.getGradePointAverage());
    }

    @Test
    public void getStudentByIdTest(){
        student = new Student( "Jane Doe", 2.3);
        student = studentController.addStudent(student);
        Student returnedStudent = studentController.getStudentById(1L);
        Assertions.assertEquals(1, returnedStudent.getStudentId());
    }

    @Test
    public void getAllStudentsSortedByGPA(){
        Student studentLow = new Student("Tom Doe", 1.2);
        Student studentHigh = new Student("Tina Doe", 1.3);

        studentLow = studentController.addStudent(studentLow);
        studentHigh = studentController.addStudent(studentHigh);

        List<Student> studentList = studentController.getAllStudents();
        Assertions.assertEquals(2, studentList.size());
        Assertions.assertEquals(2, studentList.get(0).getStudentId());
    }

    @Test
    public void finalizeStudentGradePointAverageTest(){
        student = new Student( "Jane Doe", 2.3);
        student = studentController.addStudent(student);

        Student finalizedStudent = studentController.finalizeGradePointAverageByStudent(1L);
        Assertions.assertEquals(true, finalizedStudent.isGradePointAverageChecked());
    }
}
