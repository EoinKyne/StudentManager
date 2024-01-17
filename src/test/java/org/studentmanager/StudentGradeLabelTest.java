package org.studentmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StudentGradeLabelTest {

    private StudentGradeLabel studentGradeLabel = new StudentGradeLabel();

    @Test
    void getlabelStrings() {
        Student student = new Student(1L, "John Doe", 2.5);
        Student studentWithGpaLabel = studentGradeLabel.getLabelStrings(student);
        Assertions.assertEquals("C+", studentWithGpaLabel.getGradePointAverageLabel());
    }

    @Test
    void calculateGradePointAverageLabel() {
        String gpaLabel = "C";
        double gradePointAverage = 2.0;
        String returnedGpaLabel = studentGradeLabel.calculateGradePointAverageLabel(gradePointAverage);
        Assertions.assertEquals(gpaLabel, returnedGpaLabel);
    }

    @Test
    void calculateGradePointAverageLabelIfGreaterThan4_0() {
        String gpaLabel = "F";
        double gradePointAverage = 5.0;
        String returnedGpaLabel = studentGradeLabel.calculateGradePointAverageLabel(gradePointAverage);
        Assertions.assertEquals(gpaLabel, returnedGpaLabel);
    }

    @Test
    void calculateGradePointAverageLabelIfLessThan0_0() {
        String gpaLabel = "F";
        double gradePointAverage = -2.0;
        String returnedGpaLabel = studentGradeLabel.calculateGradePointAverageLabel(gradePointAverage);
        Assertions.assertEquals(gpaLabel, returnedGpaLabel);
    }
}