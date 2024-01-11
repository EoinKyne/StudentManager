package org.studentmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;


public class StudentControllerTest {

    private StudentController studentController;
    private List<Student> students = new ArrayList<>();
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentService studentService;
    @Mock
    private Student student = new Student("John Doe", 3.5);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        studentController = new StudentController(studentRepository, studentService);
    }

    @Test
    public void addStudent() {
        Student s = new Student(1L, "John Doe", 3.3);
        given(studentService.addNewStudent(s)).willReturn(s);
        Student newStudent = studentController.addStudent(s);
        Assertions.assertEquals(s.toString(), newStudent.toString());
        Assertions.assertEquals(3.3, newStudent.getGradePointAverage());
    }

    @Test
    public void testGetAllStudents(){
        given(studentRepository.getAllStudents()).willReturn(students);
        List<Student> allStudents = studentController.getAllStudents();
        Assertions.assertEquals(allStudents, students);
    }

    @Test
    public void testGetStudentById(){
        //given(studentRepository.retrieveStudentFromRepo(anyLong())).willReturn(student);
        given(studentService.getStudentById(anyLong())).willReturn(student);
        //System.out.println(student.hashCode());
        Student returnedStudent = studentController.getStudentById(1L);
        //System.out.println(returnedStudent.hashCode());
        Assertions.assertEquals(returnedStudent, student);
    }

    @Test
    public void testAmendGradePointAverageByStudent(){

        Student studentGPA = new Student(1L, "Jane Doe", 4.0);
        given(studentService.amendGPAForStudentId(anyLong(), anyDouble())).willReturn(studentGPA);
        //Student amendedStudentGPA = studentController.amendGradePointAverageByStudent(1L, 3.5);

        //Assertions.assertEquals(amendedStudentGPA, s);
        Assertions.assertEquals(4.0, studentGPA.getGradePointAverage());
    }

    @Test
    public void testFinalizeStudentGradePointAverage(){
        Student finalStudent = new Student(1L, "Jane Dow", 2.0, "C", true);

        given(studentService.finalizeStudentGPA(anyLong())).willReturn(finalStudent);
        //given(studentRepository.retrieveStudentFromRepo(anyLong())).willReturn(student);
        //given(studentRepository.finalizeGradePointAverage(anyLong())).willReturn(finalStudent);

        Student finalizedStudent = studentController.finalizeGradePointAverageByStudent(1L);

        Assertions.assertEquals(true, finalizedStudent.isGradePointAverageChecked());
        Assertions.assertEquals("C", finalizedStudent.getGradePointAverageLabel());
    }
}