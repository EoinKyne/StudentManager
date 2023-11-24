package org.studentmanager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentGradePointAverageScaleTest {

    private StudentGradePointAverageScale studentGradePointAverageScale;

    @Test
    void getGradeLabel() {
        assertEquals("A-", studentGradePointAverageScale.GRADE_POINT_AVERAGE_LETTER_3_7.getGradeLabel());
    }
}