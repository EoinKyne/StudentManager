package org.studentmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


public class StudentIntegrationTests {

    private StudentController studentController = new StudentController();
    private Map<Long, Student> repository = new HashMap<>();
    private StudentRepository studentRepository = new StudentRepository(repository);
    //private StudentGradeLabel studentGradeLabel;
    private StudentService studentService = new StudentService();
    private Student student;

    @BeforeEach
    public void setUp(){
        studentController = new StudentController(studentRepository, studentService);
    }

    @Test
    public void addStudentTest(){
        student = new Student("Jane Doe", 1.5);
        student = studentController.addStudent(student);
        Assertions.assertEquals(1L, student.getStudentId());
    }

    @Test
    public void amendStudentTest(){
        student = new Student( "Jane Doe", 2.5 );
        student = studentController.addStudent(student);
        Student amendedStudent = studentController.amendGradePointAverageByStudent(student.getStudentId(), 3.3);
        Assertions.assertEquals(3.3, amendedStudent.getGradePointAverage());
        Assertions.assertEquals("B+", amendedStudent.getGradePointAverageLabel());
    }

    @Test
    public void getStudentByIdTest(){
        student = new Student( 1L,"Jane Doe", 2.5);
        student = studentController.addStudent(student);
        Student returnedStudent = studentController.getStudentById(1L);
        Assertions.assertEquals(1, returnedStudent.getStudentId());
    }

    @Test
    public void getAllStudentsSortedByGPA(){
        Student studentLow = new Student("Jane Doe",2.5);
        Student studentHigh = new Student("John Doe", 2.5);

        studentLow = studentController.addStudent(studentLow);
        studentHigh = studentController.addStudent(studentHigh);

        List<Student> studentList = studentService.getAllStudents();
        Assertions.assertEquals(2, studentList.size());
        Assertions.assertEquals(2, studentList.get(1).getStudentId());
    }

    @Test
    public void finalizeStudentGradePointAverageTest(){
        student = new Student( "Jane Doe", 2.5);
        student = studentController.addStudent(student);

        Student finalizedStudent = studentController.finalizeGradePointAverageByStudent(1L);
        Assertions.assertEquals(true, finalizedStudent.isGradePointAverageChecked());
    }

    @Test
    public void addNewStudentWithoutName(){
        student = new Student( "", 2.5);
        StudentNameIsEmptyException studentNameIsEmptyException = Assertions.assertThrows(StudentNameIsEmptyException.class, () -> {
            studentController.addStudent(student);
        }, "StudentNameIsEmptyException exception was expected");
        Assertions.assertEquals("Cannot add new student without a full name", studentNameIsEmptyException.getMessage());
    }

    @Test
    public void addNewStudentWithGPAOutOfRange(){
        student = new Student( "Jane Doe", 4.5);
        StudentGradePointAverageOutOfBoundsException studentGradePointAverageOutOfBoundsException = Assertions.assertThrows(StudentGradePointAverageOutOfBoundsException.class, () -> {
            studentController.addStudent(student);
        }, "StudentGradePointAverageOutOfBoundsException exception was expected" );
        Assertions.assertEquals("GPA should be between 0.0 to 4.0. Provided GPA 4.5 is out of bounds.", studentGradePointAverageOutOfBoundsException.getMessage());
    }

    @Test
    public void updateStudentWithGPAOutOfRange(){
        student = new Student( "Jane Doe", 2.5);
        student = studentController.addStudent(student);
        StudentGradePointAverageOutOfBoundsException studentGradePointAverageOutOfBoundsException = Assertions.assertThrows(StudentGradePointAverageOutOfBoundsException.class, () -> {
            studentController.amendGradePointAverageByStudent(1L, 4.5);
        }, "StudentGradePointAverageOutOfBoundsException exception was expected" );
        Assertions.assertEquals("GPA should be between 0.0 to 4.0. Provided GPA 4.5 is out of bounds.", studentGradePointAverageOutOfBoundsException.getMessage());
    }

    @Test
    public void studentNotFoundWhenAmendingGPA(){
        StudentNotFoundException studentNotFoundException = Assertions.assertThrows(StudentNotFoundException.class, () -> {
            studentController.amendGradePointAverageByStudent(1L, 2.7);
        }, "StudentNotFoundException exception was expected");
        Assertions.assertEquals("StudentId is not found 1", studentNotFoundException.getMessage());
    }

    @Test
    public void studentNotFoundWhenFinalizingGPA(){
        StudentNotFoundException studentNotFoundException = Assertions.assertThrows(StudentNotFoundException.class, () -> {
            studentController.finalizeGradePointAverageByStudent(1L);
        }, "StudentNotFoundException exception was expected");
        Assertions.assertEquals("StudentId is not found 1", studentNotFoundException.getMessage());
    }

    @Test
    public void studentNotFoundWhenGettingStudentById(){
        StudentNotFoundException studentNotFoundException = Assertions.assertThrows(StudentNotFoundException.class, () -> {
            studentController.getStudentById(1L);
        }, "StudentNotFoundException exception was expected");
        Assertions.assertEquals("StudentId is not found 1", studentNotFoundException.getMessage());
    }

    @Test
    public void studentAmendExceptionWhenStudentIsMarkedFinalized(){
        student = new Student("Jane Doe", 3.9, "A-", true );
        student = studentController.addStudent(student);
        StudentAmendException studentAmendException = Assertions.assertThrows(StudentAmendException.class, () -> {
            studentController.amendGradePointAverageByStudent(1L, 3.8);
        }, "StudentAmendException exception was expected");
        Assertions.assertEquals("Student GPA is finalized and cannot be amended for student studentId 1", studentAmendException.getMessage());
    }
}
