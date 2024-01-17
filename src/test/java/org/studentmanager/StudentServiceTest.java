package org.studentmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    private StudentService studentService;
    @BeforeEach
    public void setUp(){
        studentService = new StudentService(studentRepository);
    }

    @Test
    public void getAllStudents() {
        studentService.returnAllStudents();
        verify(studentRepository).getAllStudents();
    }

    @Test
    public void addNewStudent() {
        Student s = new Student( 1L, "John Doe", 3.3, "C", false);

        studentService.addNewStudent(s);

        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).saveStudent(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();
        Assertions.assertEquals(s, capturedStudent);
    }

    @Test
    public void addNewStudentGpaOutOfRange(){
        Student s = new Student( 1L, "John Doe", 4.3, "", false);

        StudentGradePointAverageOutOfBoundsException studentGradePointAverageOutOfBoundsException =
                Assertions.assertThrows(StudentGradePointAverageOutOfBoundsException.class, () -> {
                    studentService.addNewStudent(s);
                }, "StudentGradePointAverageOutOfBoundsException exception was expected");
        Assertions.assertEquals("GPA should be between 0.0 to 4.0. Provided GPA 4.3 is out of bounds.",
                studentGradePointAverageOutOfBoundsException.getMessage());
        verify(studentRepository,never()).saveStudent(any());
    }

    @Test
    public void getStudentById(){
        Student s = new Student(1L, "John Doe", 3.3,"C", false );
        given(studentRepository.retrieveStudentFromRepo(anyLong())).willReturn(s);

        studentService.addNewStudent(s);

        Student returnedStudent = studentService.getStudentById(1L);
        Assertions.assertEquals(returnedStudent, s);
    }

    @Test
    public void amendGPAForStudentId(){
        Student amendedStudent = new Student(1L, "Jane Doe", 3.3, "C", false);
        given(studentRepository.amendGradePointAverage(anyLong(), anyDouble())).willReturn(amendedStudent);
        given(studentRepository.addStudentGradePointAverageLabel(amendedStudent)).willReturn(amendedStudent);
        given(studentRepository.retrieveStudentFromRepo(anyLong())).willReturn(amendedStudent);

        studentService.amendGPAForStudentId(1L, 3.3);

        verify(studentRepository).retrieveStudentFromRepo(1L);
    }

    @Test
    public void amendGPAForStudentWhereStudentIsNotInMap(){
        Student amendedStudent = new Student(1L, "Jane Doe", 3.3, "C", false);

        StudentNotFoundException studentNotFoundException = Assertions.assertThrows(StudentNotFoundException.class, () -> {
            studentService.amendGPAForStudentId(1L, 3.3);
        }, "StudentNotFoundException exception was expected");
        Assertions.assertEquals("StudentId is not found id=1", studentNotFoundException.getMessage());
        verify(studentRepository, never()).amendGradePointAverage(anyLong(), anyDouble());
    }

    @Test
    public void testStudentNotFoundException(){
        StudentNotFoundException studentNotFoundException = Assertions.assertThrows(StudentNotFoundException.class, () -> {
            studentService.getStudentById(1L);
        }, "StudentNotFoundException exception was expected");
        Assertions.assertEquals("StudentId is not found id=1", studentNotFoundException.getMessage());
        verify(studentRepository, never()).amendGradePointAverage(1L, 3.3);
    }

    @Test
    public void testStudentAmendException(){
        Student finalStudent = new Student(1L, "Jane Doe", 2.0, "C", true);
        given(studentRepository.retrieveStudentFromRepo(anyLong())).willReturn(finalStudent);

        StudentAmendException studentAmendException = Assertions.assertThrows(StudentAmendException.class, () -> {
            studentService.amendGPAForStudentId(1L, 2.0);
        }, "StudentAmendException exception was expected");
        Assertions.assertEquals("Student GPA is finalized and cannot be amended for student studentId 1", studentAmendException.getMessage());
        verify(studentRepository, never()).amendGradePointAverage(1L, 2.0);
    }

    @Test
    public void testStudentGpaIsOutOfBoundsException(){
        Student finalStudent = new Student(1L, "Jane Doe", 2.0, "C", false);
        given(studentRepository.retrieveStudentFromRepo(anyLong())).willReturn(finalStudent);

        StudentGradePointAverageOutOfBoundsException studentGradePointAverageOutOfBoundsException =
                Assertions.assertThrows(StudentGradePointAverageOutOfBoundsException.class, () -> {
                    studentService.amendGPAForStudentId(1L, 5.5);
                }, "StudentGradePointAverageOutOfBoundsException exception was expected");
        Assertions.assertEquals("GPA should be between 0.0 to 4.0. Provided GPA 5.5 is out of bounds.",
                studentGradePointAverageOutOfBoundsException.getMessage());
        verify(studentRepository, never()).saveStudent(any());
    }

    @Test
    public void testFinalizeGPA(){
        Student s = new Student( 1L, "John Doe", 3.3, "C", false);
        given(studentRepository.retrieveStudentFromRepo(anyLong())).willReturn(s);
        given(studentRepository.finalizeGradePointAverage(anyLong())).willReturn(s);

        studentService.finalizeStudentGPA(s.getStudentId());

        verify(studentRepository).retrieveStudentFromRepo(1L);
        verify(studentRepository).finalizeGradePointAverage(1L);
    }

    @Test
    public void testFinalizeGpaWhenStudentIsNotInMap(){
        Student finalizedStudent = new Student(1L, "Jane Doe", 3.3, "C", false);

        StudentNotFoundException studentNotFoundException = Assertions.assertThrows(StudentNotFoundException.class, () -> {
            studentService.finalizeStudentGPA(1L);
        }, "StudentNotFoundException exception was expected");
        Assertions.assertEquals("StudentId is not found id=1", studentNotFoundException.getMessage());
        verify(studentRepository, never()).finalizeGradePointAverage(anyLong());
    }

    @Test
    public void testFinalizedGpaWhenStudentFinalizedIsTrueAmendException(){
        Student finalizedStudent = new Student(1L, "Jane Doe", 3.3, "C", true);
        given(studentRepository.retrieveStudentFromRepo(anyLong())).willReturn(finalizedStudent);

        StudentAmendException studentAmendException = Assertions.assertThrows(StudentAmendException.class, () -> {
            studentService.finalizeStudentGPA(1L);
        }, "StudentAmendException exception was expected");
        Assertions.assertEquals("Student GPA is finalized and cannot be amended for student studentId 1", studentAmendException.getMessage());
        verify(studentRepository, never()).finalizeGradePointAverage(1L);
    }
}