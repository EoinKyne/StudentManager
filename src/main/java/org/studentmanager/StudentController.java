package org.studentmanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
Rest Controller Layer
 */
@RestController
public class StudentController {

    private static final Logger log = LogManager.getLogger(StudentController.class);

    @Autowired
    private StudentRepository studentRepository;

    private StudentGradeLabel studentGradeLabel;

    public StudentController(){

    }
    public StudentController(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public StudentController(StudentRepository studentRepository, StudentGradeLabel studentGradeLabel){
        this.studentRepository = studentRepository;
        this.studentGradeLabel = studentGradeLabel;
    }

    @PostMapping("/student/add")
    public Student addStudent(@RequestBody Student student ){
        log.debug("Adding student {} to repository ", student);
        if(student.getGradePointAverage() < 0.0 || student.getGradePointAverage() > 4.0){
            log.error("Grade point average entered {} is out of range. " , student.getGradePointAverage());
            throw new StudentGradePointAverageOutOfBoundsException(student.getGradePointAverage());
        }

        student = addStudentGradePointAverageLabel(student);
        long studentId = getStudentId(student);
        student.setStudentId(studentId);
        studentRepository.saveStudent(student);
        return student;
    }

    private Student addStudentGradePointAverageLabel(Student student){
        log.debug("Generating GPA label for grade point average provided for student {} ", student);
        student = studentRepository.addStudentGradePointAverageLabel(student);
        return student;
    }

    private long getStudentId(Student student){
        log.debug("Generating the next student Id for student {}", student);
        long studentId = studentRepository.generateStudentId();
        return studentId;
    }

    @GetMapping("/students/getall")
    public List<Student> getAllStudents(){
        log.debug("Getting all students.....");
        return studentRepository.getAllStudents();
    }

    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable("id") long studentId){
        log.debug("Returning student studentId {} ", studentId);
        Student returnedStudent = studentRepository.retrieveStudentFromRepo(studentId);
        if(returnedStudent == null){
            log.error("Student studentId {} is not found. ", studentId);
            throw new StudentNotFoundException(studentId);
        }else{
            log.info("Return student by student id {} ", studentId);
            return returnedStudent;
        }
    }

    @PutMapping("/student/updategpa/{id}")
    public Student amendGradePointAverageByStudent(@PathVariable("id") long studentId, @RequestParam double gradePointAverage){
        log.debug("Amending student studentId {} grade point average {} ", studentId, gradePointAverage);
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
        amendedStudent = addStudentGradePointAverageLabel(amendedStudent);

        log.info("Updated GPA {} for student {} ", gradePointAverage, amendedStudent.getStudentId());
        return amendedStudent;
    }

    @PutMapping("/student/finalizegpa/{id}")
    public Student finalizeGradePointAverageByStudent(@PathVariable("id") long studentId){
        log.debug("Setting student studentId {} to grade point average finalized ", studentId);
        Student finalizedStudent = studentRepository.retrieveStudentFromRepo(studentId);
        if(finalizedStudent == null){
            log.error("Student studentId {} is not found. ", studentId);
            throw new StudentNotFoundException(studentId);
        }
        if(finalizedStudent.isGradePointAverageChecked()){
            log.error("Student is marked finalized and grade point average cannot be amended. ");
            throw new StudentNotFoundException(studentId);
        }

        finalizedStudent = studentRepository.finalizeGradePointAverage(studentId);
        log.info("Finalized student from finalizeGPA {} ", finalizedStudent.getStudentId());
        return finalizedStudent;
    }
}
