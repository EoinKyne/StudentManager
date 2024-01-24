package org.studentmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


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
        studentController = new StudentController(studentService);
    }

    @Test
    public void testAddStudent() {
        Student s = new Student(1L, "John Doe", 3.3);
        given(studentService.addNewStudent(s)).willReturn(s);

        Student newStudent = studentController.addStudent(s);

        ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentService).addNewStudent(argumentCaptor.capture());
        Student capturedStudent = argumentCaptor.getValue();
        Assertions.assertEquals(newStudent, capturedStudent);
        Assertions.assertEquals(3.3, newStudent.getGradePointAverage());
    }

    @Test
    public void testGetAllStudents(){
        given(studentRepository.getAllStudents()).willReturn(students);

        List<Student> allStudents = studentController.getAllStudents();

        verify(studentService, times(1)).returnAllStudents();
        Assertions.assertEquals(allStudents, students);
    }

    @Test
    public void testGetStudentById(){
        given(studentService.getStudentById(anyLong())).willReturn(student);

        Student returnedStudent = studentController.getStudentById(1L);

        verify(studentService, times(1)).getStudentById(1L);
        Assertions.assertEquals(returnedStudent, student);
    }

    @Test
    public void testAmendGradePointAverageByStudent(){
        Student studentGPA = new Student(1L, "Jane Doe", 4.0, "A+", false);
        given(studentService.amendGPAForStudentId(anyLong(), anyDouble())).willReturn(studentGPA);
        Student amendedStudent = studentService.amendGPAForStudentId(1L, 4.0);
        verify(studentService, times(1)).amendGPAForStudentId(1L, 4.0);
        Assertions.assertEquals(4.0, amendedStudent.getGradePointAverage());
        Assertions.assertEquals(studentGPA, amendedStudent);
    }

    @Test
    public void testFinalizeStudentGradePointAverage(){
        Student finalStudent = new Student(1L, "Jane Dow", 2.0, "C", true);
        given(studentService.finalizeStudentGPA(anyLong())).willReturn(finalStudent);

        Student finalizedStudent = studentController.finalizeGradePointAverageByStudent(1L);

        verify(studentService, times(1)).finalizeStudentGPA(1L);
        Assertions.assertEquals(true, finalizedStudent.isGradePointAverageChecked());
        Assertions.assertEquals("C", finalizedStudent.getGradePointAverageLabel());
    }
}