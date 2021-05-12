package com.example;

import com.example.controller.StudentController;
import com.example.model.Student;
import com.example.view.StudentView;

public class MVCPattern {
    public static void main(String[] args) {
        Student model = retrieveStudentFromDatabase();
        StudentView view = new StudentView();
        StudentController controller = new StudentController(model, view);

        controller.updateView();

        controller.setStudentName("Jon Snow");

        controller.updateView();
    }

    private static Student retrieveStudentFromDatabase() {
        Student student = new Student();
        student.setName("Karl Påhlsson");
        student.setRollNo("15UCS157");
        return student;
    }
}
