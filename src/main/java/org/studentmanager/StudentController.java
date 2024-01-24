package org.studentmanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
Rest Controller Layer
 */

@RestController
public class StudentController {

    private static final Logger log = LogManager.getLogger(StudentController.class);

    //@Autowired
    //private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    public StudentController(){

    }

    public StudentController(StudentService studentService){
        //this.studentRepository = studentRepository;
        this.studentService = studentService;
    }

    @PostMapping("/student/add")
    public Student addStudent(@RequestBody Student student ){
        log.debug("Adding student {} to repository ", student);
        Student newStudent = studentService.addNewStudent(student);
        return newStudent;
    }

     @GetMapping("/students/getall")
    public List<Student> getAllStudents(){
        log.debug("Getting all students.....");
        return studentService.returnAllStudents();
    }

    @GetMapping("/student/{id}")
    public Student getStudentById(@NonNull @PathVariable("id") long studentId){
        log.debug("Returning student studentId {} ", studentId);
        Student returnedStudent = studentService.getStudentById(studentId);
        return returnedStudent;
    }

    @PutMapping("/student/updategpa/{id}")
    public Student amendGradePointAverageByStudent(@PathVariable("id") long studentId, @RequestParam double gradePointAverage) {
        log.debug("Amending student studentId {} grade point average {} ", studentId, gradePointAverage);
        Student amendedStudent = studentService.amendGPAForStudentId(studentId, gradePointAverage);
        return amendedStudent;
    }

    @PutMapping("/student/finalizegpa/{id}")
    public Student finalizeGradePointAverageByStudent(@PathVariable("id") long studentId){
        log.debug("Setting student studentId {} to grade point average finalized ", studentId);
        Student finalizedStudent = studentService.finalizeStudentGPA(studentId);
        return finalizedStudent;
    }
}
