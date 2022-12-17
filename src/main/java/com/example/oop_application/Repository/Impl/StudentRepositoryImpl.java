package com.example.oop_application.Repository.Impl;

import com.example.oop_application.Controller.StudentController;
import com.example.oop_application.Model.Student;
import com.example.oop_application.Repository.StudentRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class StudentRepositoryImpl implements StudentRepository {


    @Override
    public List<Student> surnameSearch(String str) {
        List<Student> students = new ArrayList<>(getAllStudent());
        List<Student> response = new ArrayList<>();

        for (Student element : students)
            if(element.getLastName().toLowerCase().startsWith(str.toLowerCase()))
                response.add(element);

        return response;
    }

    @Override
    public List<Student> instructionSearch(String str){
        List<Student> students = new ArrayList<>(getAllStudent());
        List<Student> response = new ArrayList<>();

        for (Student element : students)
            if(element.getIdInstruction().toString().toLowerCase().startsWith(str.toLowerCase()))
                response.add(element);

        return response;
    }

    @Override
    public void updateStudentById(int id, Student student) {
        Student copy = getStudentById(id);
        copy.setId(student.getId());
        copy.setFirstName(student.getFirstName());
        copy.setLastName(student.getLastName());
        copy.setIdInstruction(student.getIdInstruction());
        try {
            List<Student> students = new ArrayList<>(getAllStudent());

            students.set(students.indexOf(getStudentById(id)), copy);

            Gson gson = new Gson();
            PrintWriter out = new PrintWriter(new FileWriter(StudentController.filepath));
            out.write(gson.toJson(students));
            out.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    @Override
    public List<Student> nameSearch(String str) {
        List<Student> students = new ArrayList<>(getAllStudent());
        List<Student> response = new ArrayList<>();

        for (Student element : students)
            if(element.getFirstName().toLowerCase().startsWith(str.toLowerCase()))
                response.add(element);

        return response;
    }

    @Override
    public List<Student> getAllStudent()  {
        try {
            System.out.println(StudentController.filepath);
            BufferedReader reader = new BufferedReader(new FileReader("student.json"));
            Gson gson = new Gson();

            Type studentListType = new TypeToken<List<Student>>(){}.getType();
            List<Student> studentList = gson.fromJson(reader, studentListType);
            System.out.println(studentList);
            return studentList;
        }
       catch (Exception e){
           System.out.println("Error: " + e);
       }
      return null;
    }

    @Override
    public void deleteStudentById(int id) {
        Gson gson = new Gson();
        try {
            List<Student> students = new ArrayList<>(getAllStudent());

            for(int i = 0; i < students.size(); i++)
                if(students.get(i).getId() == id)
                    students.remove(i);

            PrintWriter out = new PrintWriter(new FileWriter(StudentController.filepath));
            out.write(gson.toJson(students));
            out.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public void saveStudent(Student student) {
        try {
            Gson gson = new Gson();
            List<Student> students = new ArrayList<>(getAllStudent());
            students.add(student);
            PrintWriter out = new PrintWriter(new FileWriter(StudentController.filepath));
            out.write(gson.toJson(students));
            out.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public Student getStudentById(int id) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(StudentController.filepath)) {
            List<Student> students = gson.fromJson(reader, new TypeToken<List<Student>>() {}.getType());
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
