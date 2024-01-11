package org.studentmanager;


import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Service
public class StudentService {

    private static final Logger log = LogManager.getLogger(StudentService.class);

    private StudentRepository studentRepository = new StudentRepository();

    public StudentService() {
    }

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        log.debug("Called student service class to call student repo");
        return studentRepository.getAllStudents();
    }

    public Student addNewStudent(Student student) {
        log.info("Add new student to repo");

        if(student.getGradePointAverage() < 0.0 || student.getGradePointAverage() > 4.0){
            log.error("Grade point average entered {} is out of range. " , student.getGradePointAverage());
            throw new StudentGradePointAverageOutOfBoundsException(student.getGradePointAverage());
        }

        log.debug("Get new id for student {} ", student.getStudentFullName());
        if(student.getStudentId() == 0){
            student = getGeneratedStudentId(student);
        }

        log.debug("Add GPA string value {} for grade {} ", student.getGradePointAverageLabel(), student.getGradePointAverage());
        if (student.getGradePointAverageLabel() == null){
            student = addStudentGradePointAverageLabel(student);
        }
        studentRepository.saveStudent(student);
        return student;
    }


    private Student addStudentGradePointAverageLabel(Student student){
        log.debug("Generating GPA label for grade point average provided for student {} ", student);
        student = studentRepository.addStudentGradePointAverageLabel(student);
        return student;
    }


    private Student getGeneratedStudentId(Student student){
        log.debug("Generating the next student Id for student {}", student);
        long studentId = studentRepository.generateStudentId();
        student.setStudentId(studentId);
        return student;
    }

    public Student getStudentById(long studentId) {
        log.debug("Get student by student Id {} ", studentId);
        Student returnedStudent = studentRepository.retrieveStudentFromRepo(studentId);
        if(returnedStudent == null){
            log.error("Student studentId {} is not found. ", studentId);
            throw new StudentNotFoundException(studentId);
        }else{
            log.info("Return student by student id {} ", studentId);
            return returnedStudent;
        }
    }

    public Student amendGPAForStudentId(long studentId, double gradePointAverage) {
        log.debug("Amend  to {} for student Id {} ", gradePointAverage, studentId);
        Student amendedStudent = studentRepository.retrieveStudentFromRepo(studentId);

        if(amendedStudent == null){
            log.error("Student studentId {} is not found. ", studentId);
            throw new StudentNotFoundException(studentId);
        }
        if(amendedStudent.isGradePointAverageChecked()){
            log.error("Student is marked finalized and grade point average cannot be amended. ");
            throw new StudentAmendException(studentId);
        }
        if(gradePointAverage < 0.0 || gradePointAverage > 4.0){
            log.error("Grade point average entered {} is out of range. " , gradePointAverage);
            throw new StudentGradePointAverageOutOfBoundsException(gradePointAverage);
        }
        amendedStudent = studentRepository.amendGradePointAverage(studentId, gradePointAverage);
        amendedStudent = studentRepository.addStudentGradePointAverageLabel(amendedStudent);

        log.info("Updated GPA {} for student {} ", gradePointAverage, amendedStudent.getStudentId());
        return amendedStudent;
    }


    public Student finalizeStudentGPA(long studentId) {
        log.debug("Finalize GPA and marking student immutablt for student id {}");
        Student finalizedStudent = studentRepository.retrieveStudentFromRepo(studentId);
        if(finalizedStudent == null){
            log.error("Student studentId {} is not found. ", studentId);
            throw new StudentNotFoundException(studentId);
        }
        if(finalizedStudent.isGradePointAverageChecked()){
            log.error("Student is marked finalized and grade point average cannot be amended. ");
            throw new StudentAmendException(studentId);
        }

        finalizedStudent = studentRepository.finalizeGradePointAverage(studentId);
        log.info("Finalized student from finalizeGPA {} ", finalizedStudent.getStudentId());
        return finalizedStudent;
    }
}
