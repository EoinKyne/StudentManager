package org.studentmanager;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

/*
Using hashmap to cache students
 */

@Repository
public class StudentRepository {

    private static final Logger log = LogManager.getLogger(StudentRepository.class);

    private Map<Long, Student> repo = new HashMap<>();
    private long nextStudentId;
    private Student student;

    public StudentRepository(){

    }

    public StudentRepository(Map<Long, Student> repo){
        this.repo = repo;
    }

    public boolean saveStudent(Student student){
        if(!repo.containsKey(student.getStudentId()) && student.checkStudentName(student)){
            repo.put(student.getStudentId(), student);
            log.info("Saving student studentId {} to the repo ", student.getStudentId());
            return true;
        }
        log.warn("Student Id {} exists, failed to add student {} to repository ", student.getStudentFullName(), student.getStudentId());
        return false;
    }

    public Student addStudentGradePointAverageLabel(Student student){
        log.debug("Generating GPA label for grade point average provided for student {} ", student);
        StudentGradeLabel studentGradeLabel1 = new StudentGradeLabel();
        student = studentGradeLabel1.getLabelStrings(student);
        return student;
    }

    public long  generateStudentId(){
        log.info("Generating a new id for next student  ");
        if(repo.isEmpty()){
            return 1L;
        }else{
            nextStudentId = Collections.max(repo.keySet()) + 1;
        }
        return nextStudentId;
    }

    public Student retrieveStudentFromRepo(long studentId){
        log.info("Retrieve student studentId {} from repo ", studentId);
        student = repo.get(studentId);
        return student;
    }

    public List<Student> getAllStudents(){
        log.info("Get all students");
        List<Student> allStudents = new ArrayList<>();
        for(Student student : repo.values()){
            allStudents.add(student);
        }
        allStudents.sort(Comparator.comparingDouble(Student::getGradePointAverage).reversed());
        return allStudents;
    }

    public Student amendGradePointAverage(long studentId, double gradePointAverage){
        log.info("Amend student studentId {} for GPA {} ", studentId, gradePointAverage);
        student = repo.get(studentId);
        if(!student.isGradePointAverageChecked()){
            student.setGradePointAverage(gradePointAverage);
            return student;
        }
        return student;
    }

    public Student finalizeGradePointAverage(long studentId) {
        log.info("Finalize student studentId {} ", studentId);
        student = repo.get(studentId);
        if(!student.isGradePointAverageChecked()){
            student.setGradePointAverageChecked(true);
            return student;
        }
        return student;
    }
}
