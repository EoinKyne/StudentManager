package org.studentmanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudentGradeLabel {

    private static final Logger log = LogManager.getLogger(StudentGradeLabel.class);

    public StudentGradeLabel(){

    }

    public Student getLabelStrings(Student student){
        log.debug("Getting gpa grade label from gpa for student {} ", student);
        double gpa = student.getGradePointAverage();
        String gpaString = calculateGradePointAverageLabel(gpa);
        student.setGradePointAverageLabel(gpaString);
        return student;
    }

    public String calculateGradePointAverageLabel(double gradePointAverage){
        log.debug("Returning gpa label from gpa {} ", gradePointAverage);
        if(gradePointAverage == 4.0){
            return StudentGradePointAverageScale.GRADE_POINT_AVERAGE_LETTER_4_0.getGradeLabel();
        } else if (gradePointAverage < 4.0 && gradePointAverage >= 3.7) {
            return StudentGradePointAverageScale.GRADE_POINT_AVERAGE_LETTER_3_7.getGradeLabel();
        } else if (gradePointAverage < 3.7 && gradePointAverage >= 3.3) {
            return StudentGradePointAverageScale.GRADE_POINT_AVERAGE_LETTER_3_3.getGradeLabel();
        } else if (gradePointAverage < 3.3 && gradePointAverage >= 3.0) {
            return StudentGradePointAverageScale.GRADE_POINT_AVERAGE_LETTER_3_0.getGradeLabel();
        } else if (gradePointAverage < 3.0 && gradePointAverage >= 2.7) {
            return StudentGradePointAverageScale.GRADE_POINT_AVERAGE_LETTER_2_7.getGradeLabel();
        } else if (gradePointAverage < 2.7 && gradePointAverage >= 2.3) {
            return StudentGradePointAverageScale.GRADE_POINT_AVERAGE_LETTER_2_3.getGradeLabel();
        } else if (gradePointAverage < 2.3 && gradePointAverage >= 2.0 ) {
            return StudentGradePointAverageScale.GRADE_POINT_AVERAGE_LETTER_2_0.getGradeLabel();
        } else if (gradePointAverage < 2.0 && gradePointAverage >= 1.7) {
            return StudentGradePointAverageScale.GRADE_POINT_AVERAGE_LETTER_1_7.getGradeLabel();
        } else if (gradePointAverage < 1.7 && gradePointAverage >= 1.3) {
            return StudentGradePointAverageScale.GRADE_POINT_AVERAGE_LETTER_1_3.getGradeLabel();
        } else if (gradePointAverage < 1.3 && gradePointAverage >= 1.0) {
            return StudentGradePointAverageScale.GRADE_POINT_AVERAGE_LETTER_1_0.getGradeLabel();
        } else if (gradePointAverage < 1.0 && gradePointAverage > 0.0){
            return StudentGradePointAverageScale.GRADE_POINT_AVERAGE_LETTER_0_0.getGradeLabel();
        } else {
            return StudentGradePointAverageScale.GRADE_POINT_AVERAGE_LETTER_0_0.getGradeLabel();
        }
    }
}
