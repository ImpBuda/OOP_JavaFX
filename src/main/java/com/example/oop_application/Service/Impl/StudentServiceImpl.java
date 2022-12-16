package com.example.oop_application.Service.Impl;

import com.example.oop_application.Model.Student;
import com.example.oop_application.Repository.Impl.StudentRepositoryImpl;
import com.example.oop_application.Repository.StudentRepository;
import com.example.oop_application.Service.StudentService;

import java.util.List;


public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository = new StudentRepositoryImpl();

    @Override
    public Student getStudentById(int id) {
       return studentRepository.getStudentById(id);
    }

    @Override
    public List<Student> instructionSearch(String str) {
        return studentRepository.instructionSearch(str);
    }

    @Override
    public List<Student> surnameSearch(String str) {
        return studentRepository.surnameSearch(str);
    }

    @Override
    public List<Student> nameSearch(String str) {
       return studentRepository.nameSearch(str);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.getAllStudent();
    }

    @Override
    public void createStudent(Student student) {
        studentRepository.saveStudent(student);
    }

    @Override
    public void updateStudentById(int id, Student student) {
        studentRepository.updateStudentById(id, student);
    }

    @Override
    public void deleteStudentById(int id) {
        studentRepository.deleteStudentById(id);
    }
}
