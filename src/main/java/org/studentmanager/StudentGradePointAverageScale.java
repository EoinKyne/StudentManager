package org.studentmanager;

public enum StudentGradePointAverageScale {

    GRADE_POINT_AVERAGE_LETTER_4_0("A"),
    GRADE_POINT_AVERAGE_LETTER_3_7("A-"),
    GRADE_POINT_AVERAGE_LETTER_3_3("B+"),
    GRADE_POINT_AVERAGE_LETTER_3_0("B"),
    GRADE_POINT_AVERAGE_LETTER_2_7("B-"),
    GRADE_POINT_AVERAGE_LETTER_2_3("C+"),
    GRADE_POINT_AVERAGE_LETTER_2_0("C"),
    GRADE_POINT_AVERAGE_LETTER_1_7("C-"),
    GRADE_POINT_AVERAGE_LETTER_1_3("D+"),
    GRADE_POINT_AVERAGE_LETTER_1_0("D"),
    GRADE_POINT_AVERAGE_LETTER_0_0("F");

    public String gradeLabel;

    StudentGradePointAverageScale(String gradeLabel){
        this.gradeLabel = gradeLabel;
    }

    public String getGradeLabel(){
        return gradeLabel;
    }

}
