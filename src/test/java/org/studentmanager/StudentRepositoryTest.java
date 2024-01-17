package org.studentmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class StudentRepositoryTest {

    private Map<Long, Student> repository;
    private StudentRepository studentRepository;

    private Student student = new Student(1L, "Jane Doe", 3.5);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        repository = new HashMap<>();
        studentRepository = new StudentRepository(repository);
    }

    @Test
    public void testRepoIsEmpty(){
        Assertions.assertTrue(repository.isEmpty());
    }

    @Test
    public void testSaveStudent() {
        studentRepository.saveStudent(student);
        Assertions.assertEquals(1, repository.size());
    }

    @Test
    public void testFailedToSaveStudent(){
        Student failedSave = new Student(1L, "Jane Doe", 3.5);

        studentRepository.saveStudent(student);
        studentRepository.saveStudent(failedSave);

        Assertions.assertFalse(studentRepository.saveStudent(failedSave));
        Assertions.assertEquals(1, repository.size());
    }

    @Test
    public void testGenerateStudentId() {
        long studentId = studentRepository.generateStudentId();

        Assertions.assertEquals(1, studentId);
    }

    @Test
    public void testRetrieveStudentFromRepo() {
        studentRepository.retrieveStudentFromRepo(1L);

        Assertions.assertEquals(1, student.getStudentId());
    }

    @Test
    public void testGetAllStudents() {
        Student studentOne = new Student(2L, "John Doe");
        Student studentTwo = new Student(3L, "Jane Doe");

        studentRepository.saveStudent(studentOne);
        studentRepository.saveStudent(studentTwo);
        studentRepository.getAllStudents();

        Assertions.assertEquals(2, repository.size());
    }

    @Test
    public void testAmendStudentGradePointAverage(){
        studentRepository.saveStudent(student);

        Student amendedStudent = studentRepository.amendGradePointAverage(1L, 4.0);

        Assertions.assertEquals(4.0, amendedStudent.getGradePointAverage());
        Assertions.assertEquals(student, amendedStudent);
    }

    @Test
    public void testFinalizeStudentGradePointAverage(){
        studentRepository.saveStudent(student);

        Student finalizedStudent = studentRepository.finalizeGradePointAverage(1L);

        Assertions.assertEquals(true, finalizedStudent.isGradePointAverageChecked());
    }

    @Test
    public void testCannotAmmendStudentGPAOfFinalizedStudent(){
        studentRepository.saveStudent(student);
        student.setGradePointAverageChecked(true);

        Student amendedStudent = studentRepository.amendGradePointAverage(1L, 3.8);

        Assertions.assertEquals(3.5, amendedStudent.getGradePointAverage());
    }

    @Test
    public void testCannotAmmendStudentGPAOfFinalizedStudentAndReturnFinalizedStudent(){
        studentRepository.saveStudent(student);
        student.setGradePointAverageChecked(true);

        Student amendedStudent = studentRepository.finalizeGradePointAverage(1L);

        Assertions.assertEquals(3.5, amendedStudent.getGradePointAverage());
    }
}