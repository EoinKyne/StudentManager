package org.studentmanager;

public class StudentAmendException extends RuntimeException {

    public StudentAmendException(long studentId){
        super("Student GPA is finalized and cannot be amended for student studentId " + studentId);
    }
}
