package org.studentmanager;

public class StudentGradePointAverageOutOfBoundsException extends RuntimeException {

    public StudentGradePointAverageOutOfBoundsException(double gpa){
        super ("GPA should be between 0.0 to 4.0. Provided GPA " + gpa + " is out of bounds.");
    }
}
