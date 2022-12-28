package com.example.oop_application.Repository.Impl;

import com.example.oop_application.Controller.StudentController;
import com.example.oop_application.Model.*;
import com.example.oop_application.Repository.HeadRepository;
import com.example.oop_application.Repository.HeadStudentRepository;
import com.example.oop_application.Repository.StudentInstructionRepository;
import com.example.oop_application.Repository.StudentRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class StudentRepositoryImpl implements StudentRepository {


    HeadStudentRepository headStudentRepository = new HeadStudentRepositoryImpl();

    StudentInstructionRepository studentInstructionRepository = new StudentInstructionRepositoryImpl();

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

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
        Student copy = getStudentById(id);
        copy.setId(student.getId());
        copy.setFirstName(student.getFirstName());
        copy.setLastName(student.getLastName());
        try {
            List<Student> students = new ArrayList<>(getAllStudent());
            for (int i = 0 ; i < students.size(); i++){
                if (students.get(i).getId() == id){
                    students.remove(i);
                    students.add(i, copy);
                    break;
                }
            }
            PrintWriter out = new PrintWriter(new FileWriter(Context.studentFilePath));
            out.write(gson.toJson(students));
            out.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    @Override
    public List<Student> getAllStudent()  {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Context.studentFilePath));

            Type studentListType = new TypeToken<List<Student>>(){}.getType();
            List<Student> studentList = gson.fromJson(reader, studentListType);
            if (studentList == null)
                return new ArrayList<>();
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
                List<Student> students = new ArrayList<>(getAllStudent());

                for(int i = 0; i < students.size(); i++)
                    if(students.get(i).getId() == id) {
                        headStudentRepository.deleteByStudentId(students.get(i).getId());
                        studentInstructionRepository.deleteByStudentId(students.get(i).getId());
                        students.remove(i);
                        break;
                    }

                PrintWriter out = new PrintWriter(new FileWriter(Context.studentFilePath));
                out.write(gson.toJson(students));
                out.close();
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
    }

    @Override
    public void saveStudent(Student student, Head_Student head_student, Student_Instruction student_instruction) {
        try {
            List<Student> students = new ArrayList<>(getAllStudent());
            if(getStudentById(student.getId()) == null) {
                students.add(student);
                headStudentRepository.save(head_student);
                studentInstructionRepository.save(student_instruction);
                PrintWriter out = new PrintWriter(new FileWriter(Context.studentFilePath));
                out.write(gson.toJson(students));
                out.close();
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
