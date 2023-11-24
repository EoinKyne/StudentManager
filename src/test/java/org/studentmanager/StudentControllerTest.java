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
    private StudentGradeLabel studentGradeLabel;
    @Mock
    private Student student = new Student("John Doe", 3.5);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        studentController = new StudentController(studentRepository, studentGradeLabel);
    }

    @Test
    public void addStudent() {
        Student s = new Student(1L, "John Doe", 3.3);
        given(studentGradeLabel.calculateGradePointAverageLabel(anyDouble())).willReturn("C");
        given(studentRepository.addStudentGradePointAverageLabel(s)).willReturn(s);
        given(studentRepository.generateStudentId()).willReturn(1L);
        given(studentRepository.saveStudent(s)).willReturn(true);
        Student newStudent = studentController.addStudent(s);
        Assertions.assertEquals(s.toString(), newStudent.toString());
    }

    @Test
    public void testGetAllStudents(){
        given(studentRepository.getAllStudents()).willReturn(students);
        List<Student> allStudents = studentController.getAllStudents();
        Assertions.assertEquals(allStudents, students);
    }

    @Test
    public void testGetStudentById(){
        given(studentRepository.retrieveStudentFromRepo(anyLong())).willReturn(student);
        System.out.println(student.hashCode());
        Student returnedStudent = studentController.getStudentById(1L);
        System.out.println(returnedStudent.hashCode());
        Assertions.assertEquals(returnedStudent, student);
    }

    @Test
    public void testAmendGradePointAverageByStudent(){
        Student amendedStudent = new Student(1L, "Jane Doe", 4.0);

        given(studentRepository.addStudentGradePointAverageLabel(amendedStudent)).willReturn(amendedStudent);
        given(studentRepository.amendGradePointAverage(anyLong(), anyDouble())).willReturn(amendedStudent);
        given(studentRepository.retrieveStudentFromRepo(anyLong())).willReturn(student);

        Student amendedStudentGPA = studentController.amendGradePointAverageByStudent(1L, 4.0);

        Assertions.assertEquals(amendedStudentGPA, amendedStudent);
        Assertions.assertEquals(4.0, amendedStudentGPA.getGradePointAverage());
    }

    @Test
    public void testStudentNotFoundException(){
        StudentNotFoundException studentNotFoundException = Assertions.assertThrows(StudentNotFoundException.class, () -> {
            studentController.getStudentById(1L);
        }, "StudentNotFoundException exception was expected");
        Assertions.assertEquals("StudentId is not found 1", studentNotFoundException.getMessage());
    }

    @Test
    public void testFinalizeStudentGradePointAverage(){
        Student finalStudent = new Student(1L, "Jane Dow", 2.0, "C", true);

        given(studentRepository.retrieveStudentFromRepo(anyLong())).willReturn(student);
        given(studentRepository.finalizeGradePointAverage(anyLong())).willReturn(finalStudent);

        Student finalizedStudent = studentController.finalizeGradePointAverageByStudent(1L);

        Assertions.assertEquals(true, finalizedStudent.isGradePointAverageChecked());
        Assertions.assertEquals("C", finalizedStudent.getGradePointAverageLabel());
    }

    @Test
    public void testStudentGpaIsOutOfBoundsException(){
        given(studentRepository.retrieveStudentFromRepo(anyLong())).willReturn(student);
        StudentGradePointAverageOutOfBoundsException studentGradePointAverageOutOfBoundsException =
                Assertions.assertThrows(StudentGradePointAverageOutOfBoundsException.class, () -> {
            studentController.amendGradePointAverageByStudent(1L, 5.5);
        }, "StudentGradePointAverageOutOfBoundsException exception was expected");
        Assertions.assertEquals("GPA should be between 0.0 to 4.0. Provided GPA 5.5 is out of bounds.",
                studentGradePointAverageOutOfBoundsException.getMessage());
    }

    @Test
    public void testStudentAmendException(){
        Student finalStudent = new Student(1L, "Jane Dow", 2.0, "C", true);
        given(studentRepository.retrieveStudentFromRepo(anyLong())).willReturn(finalStudent);
        StudentAmendException studentAmendException = Assertions.assertThrows(StudentAmendException.class, () -> {
            studentController.amendGradePointAverageByStudent(1L, 5.5);
        }, "StudentAmendException exception was expected");
        Assertions.assertEquals("Student GPA is finalized and cannot be amended for student studentId 1", studentAmendException.getMessage());
    }

}