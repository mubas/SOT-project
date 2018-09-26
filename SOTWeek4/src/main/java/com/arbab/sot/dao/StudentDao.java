package com.arbab.sot.dao;

import com.arbab.sot.models.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentDao {
    private List<Student> studentList;

    public StudentDao() {
        studentList = new ArrayList<Student>() {{
            add(new Student(1, "Mubasher", "36660237", 0));
            add(new Student(2, "Ahmed", "3666230237", 0));
            add(new Student(3, "Ali", "366560237", 0));
            add(new Student(4, "Qasim", "366660237", 0));
            add(new Student(5, "Arbab", "364678960+237", 0));
        }};
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void createStudent(Student student) {
        studentList.add(student);
    }

    public Student getStudentById(int id) {
        for (Student student : studentList) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public boolean deleteStudentById(int id) {
        for (Student student : studentList) {
            if (student.getId() == id) {
                studentList.remove(id);
                return true;
            }
        }
        return false;
    }
}
