package com.example.oop_application.Service;

import com.example.oop_application.Model.Student;

import java.io.File;
import java.util.List;

public interface StudentService {

 List<Student> nameSearch(String str);

 List<Student> surnameSearch(String str);

 Student getStudentById(int id);

 List<Student> getAllStudents();

 void createStudent(Student student);

 void updateStudentById(int id, Student student);

 void deleteStudentById(int id);

 void saveFileSystem(File saveFile);
}
