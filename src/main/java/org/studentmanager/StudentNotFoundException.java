package org.studentmanager;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(long studentId){
        super("StudentId is not found " + studentId);
    }
}
