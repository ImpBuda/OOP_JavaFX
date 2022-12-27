package com.example.oop_application.Repository;

import com.example.oop_application.Model.Head_Student;
import com.example.oop_application.Model.Student;
import com.example.oop_application.Model.Student_Instruction;

import java.io.File;
import java.util.List;

public interface StudentRepository {

    List<Student> nameSearch(String str);

    List<Student> surnameSearch(String str);

    List<Student> getAllStudent();

    void deleteStudentById(int id);

    void updateStudentById(int id, Student student);

    void saveStudent(Student student, Head_Student head_student, Student_Instruction student_instruction);

    Student getStudentById(int id);

}
