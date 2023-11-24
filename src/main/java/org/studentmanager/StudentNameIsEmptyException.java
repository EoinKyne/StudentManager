package org.studentmanager;

public class StudentNameIsEmptyException extends RuntimeException{

    public StudentNameIsEmptyException(Student student){
        super ("Cannot add new student without a full name");
    }
}
