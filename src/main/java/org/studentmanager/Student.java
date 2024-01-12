package org.studentmanager;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Student Model

public class Student {

    private static final Logger log = LogManager.getLogger(Student.class);
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long studentId;
    private String studentFullName;
    private double gradePointAverage;
    private boolean gradePointAverageChecked;
    private String gradePointAverageLabel;


    public Student(){

    }

    public Student(long studentId, String studentFullName){
        this.studentId = studentId;
        this.studentFullName = studentFullName;
    }

    public Student(String studentFullName, double gradePointAverage){
        this.studentFullName = studentFullName;
        this.gradePointAverage = gradePointAverage;
    }

    public Student(long studentId, String studentFullName, double gradePointAverage) {
        super();
        this.studentId = studentId;
        this.studentFullName = studentFullName;
        this.gradePointAverage = gradePointAverage;
    }

    public Student(String studentFullName, double gradePointAverage, String gradePointAverageLabel, boolean gradePointAverageChecked) {
        super();
        this.studentFullName = studentFullName;
        this.gradePointAverage = gradePointAverage;
        this.gradePointAverageLabel = gradePointAverageLabel;
        this.gradePointAverageChecked = gradePointAverageChecked;
    }

    public Student(long studentId, String studentFullName, double gradePointAverage, String gradePointAverageLabel, boolean gradePointAverageChecked) {
        super();
        this.studentId = studentId;
        this.studentFullName = studentFullName;
        this.gradePointAverage = gradePointAverage;
        this.gradePointAverageLabel = gradePointAverageLabel;
        this.gradePointAverageChecked = gradePointAverageChecked;
    }


    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
    }

    public double getGradePointAverage() {
        return gradePointAverage;
    }

    public void setGradePointAverage(double gradePointAverage) {
        this.gradePointAverage = gradePointAverage;
    }

    public String getGradePointAverageLabel() {
        return gradePointAverageLabel;
    }

    public void setGradePointAverageLabel(String gradePointAverageLabel) {
        this.gradePointAverageLabel = gradePointAverageLabel;
    }

    public boolean isGradePointAverageChecked() {
        return gradePointAverageChecked;
    }

    public void setGradePointAverageChecked(boolean gradePointAverageChecked) {
        this.gradePointAverageChecked = gradePointAverageChecked;
    }

    public boolean checkStudentName(Student student){
        log.info("Check student full name is provided. ");
        if(student.getStudentFullName().isEmpty()){
            throw new StudentNameIsEmptyException(student);
        }else {
            return true;
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentFullName='" + studentFullName + '\'' +
                ", gradePointAverage=" + gradePointAverage +
                ", gradePointAverageLabel=" + gradePointAverageLabel +
                ", gradePointAverageChecked=" + gradePointAverageChecked +
                '}';
    }
}
