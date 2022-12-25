package com.example.oop_application.Repository.Impl;

import com.example.oop_application.Controller.StudentController;
import com.example.oop_application.Model.Context;
import com.example.oop_application.Model.Head;
import com.example.oop_application.Model.Student;
import com.example.oop_application.Repository.HeadRepository;
import com.example.oop_application.Repository.StudentRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;


public class StudentRepositoryImpl implements StudentRepository {

    HeadRepository headRepository = new HeadRepositoryImpl();

    @Override
    public List<Student> surnameSearch(String str) {
        List<Student> students = new ArrayList<>(getAllStudent());
        students.removeIf(element -> !element.getLastName().toLowerCase().startsWith(str.toLowerCase()));
        return students;
    }


    @Override
    public List<Student> nameSearch(String str) {
        List<Student> students = new ArrayList<>(getAllStudent());
        students.removeIf(element -> !element.getFirstName().toLowerCase().startsWith(str.toLowerCase()));

        return students;
    }


    @Override
    public void updateStudentById(int id, Student student) {
        Head head = headRepository.getHeadById(student.getHead_id());
        Student copy = getStudentById(id);
        copy.setId(student.getId());
        copy.setFirstName(student.getFirstName());
        copy.setLastName(student.getLastName());
        copy.setHead_id(student.getHead_id());
        copy.setInstructionList(student.getInstructionList());
        try {
            List<Student> students = new ArrayList<>(head.getStudentList());
            for (int i = 0 ; i < students.size(); i++){
                if (students.get(i).getId() == id){
                    students.remove(i);
                    students.add(i, copy);
                }
            }
            if(headRepository.getHeadById(head.getId()) != null ) {
                head.setStudentList(students);
                headRepository.updateHeadById(head.getId(), head);
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    @Override
    public List<Student> getAllStudent()  {
        try {
            List<Head> headList = headRepository.getAllHead();
            List<Student> studentList = new ArrayList<>();
            for (Head head : headList) studentList.addAll(head.getStudentList());
            return studentList;
        }
       catch (Exception e){
           System.out.println("Error: " + e);
       }
      return null;
    }

    @Override
    public void deleteStudentById(int id) {
        try {
            Student student = getStudentById(id);
            Head head = headRepository.getHeadById(student.getHead_id());
            List<Student> students = new ArrayList<>(head.getStudentList());

            for(int i = 0; i < students.size(); i++)
                if(students.get(i).getId() == id)
                    students.remove(i);

            head.setStudentList(students);
            headRepository.updateHeadById(head.getId(), head);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public void saveStudent(Student student) {
        try {
            Head head = headRepository.getHeadById(student.getHead_id());
            List<Student> students = new ArrayList<>();
            if(head.getStudentList() != null)
                students = new ArrayList<>(head.getStudentList());
            if(getStudentById(student.getId()) == null &&
                    headRepository.getHeadById(head.getId()) != null ) {
                student.setInstructionList(new ArrayList<>());
                students.add(student);
                head.setStudentList(students);
                headRepository.updateHeadById(head.getId(), head);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public Student getStudentById(int id) {
        try {
            List<Student> students = new ArrayList<>(getAllStudent());
            for (Student student : students) {
                if (student.getId() == id) {
                    return student;
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
}
