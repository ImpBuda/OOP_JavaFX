package com.example.oop_application.Repository.Impl;

import com.example.oop_application.Model.Context;
import com.example.oop_application.Model.Head;
import com.example.oop_application.Model.Head_Student;
import com.example.oop_application.Model.LocalDateAdapter;
import com.example.oop_application.Repository.HeadStudentRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HeadStudentRepositoryImpl implements HeadStudentRepository {


    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

    @Override
    public List<Head_Student> searchStud(String str) {
        List<Head_Student> head_students = new ArrayList<>(getAll());
        head_students.removeIf(element -> !element.getStudent_id().toString().startsWith(str));
        return head_students;
    }

    @Override
    public List<Head_Student> searchHead(String str) {
        List<Head_Student> head_students = new ArrayList<>(getAll());
        head_students.removeIf(element -> !element.getHead_id().toString().startsWith(str));
        return head_students;
    }

    @Override
    public List<Head_Student> getAll() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Context.headStudentFilepath));

            Type headStudentListType = new TypeToken<List<Head_Student>>(){}.getType();
            List<Head_Student> headStudentList = gson.fromJson(reader, headStudentListType);
            return headStudentList;
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
        return null;
    }

    @Override
    public void save(Head_Student head_student) {
        try {
            List<Head_Student> head_students = new ArrayList<>(getAll());
            head_students.add(head_student);
            PrintWriter out = new PrintWriter(new FileWriter(Context.headStudentFilepath));
            out.write(gson.toJson(head_students));
            out.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }


    }

    @Override
    public boolean isPresentHead(int id, List<Head_Student> list) {

        for (Head_Student head_student : list) {
            if (head_student.getHead_id() == id)
                return true;
        }
        return false;
    }

    @Override
    public boolean isPresentStudent(int id, List<Head_Student> list) {

        for (Head_Student head_student : list) {
            if (head_student.getStudent_id() == id)
                return true;
        }
        return false;
    }

    @Override
    public void deleteByHeadId(int id) {
        try {
            StudentRepositoryImpl studentRepository = new StudentRepositoryImpl();

            int tempId;
            List<Head_Student> head_students = new ArrayList<>(getAll());
            for(int i = 0; i < head_students.size(); i++ ){
                if(head_students.get(i).getHead_id() == id){
                    tempId = head_students.get(i).getStudent_id();
                    head_students.remove(i);
                    i--;

                    PrintWriter out = new PrintWriter(new FileWriter(Context.headStudentFilepath));
                    out.write(gson.toJson(head_students));
                    out.close();

                    if(!isPresentStudent(tempId, head_students))
                        studentRepository.deleteStudentById(tempId);
                }
            }

        }catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public void delete(int studId, int headId) {
        try {
            List<Head_Student> head_students = new ArrayList<>(getAll());
            head_students.removeIf(head_student -> head_student.getStudent_id() == studId && head_student.getHead_id() == headId);

            PrintWriter out = new PrintWriter(new FileWriter(Context.headStudentFilepath));
            out.write(gson.toJson(head_students));
            out.close();

        }catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }

    @Override
    public void update(int studId, int headId) {
        Head_Student copy = new Head_Student();
        copy.setHead_id(headId);
        copy.setStudent_id(studId);
        List<Head_Student> head_students = new ArrayList<>(getAll());
        try {
            for (int i = 0 ; i < head_students.size(); i++){
                if (head_students.get(i).getHead_id() == headId && head_students.get(i).getStudent_id() == studId){
                    head_students.remove(i);
                    head_students.add(i, copy);
                    break;
                }
            }

            PrintWriter out = new PrintWriter(new FileWriter(Context.headStudentFilepath));
            out.write(gson.toJson(head_students));
            out.close();
        }catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }

    @Override
    public void deleteByStudentId(int id) {
        try {
            HeadRepositoryImpl headRepository = new HeadRepositoryImpl();
            int tempId;
            List<Head_Student> head_students = new ArrayList<>(getAll());

            for(int i = 0; i < head_students.size(); i++ ){
                if(head_students.get(i).getStudent_id() == id){
                    tempId = head_students.get(i).getHead_id();
                    head_students.remove(i);
                    i--;

                    PrintWriter out = new PrintWriter(new FileWriter(Context.headStudentFilepath));
                    out.write(gson.toJson(head_students));
                    out.close();

                    if(!isPresentHead(tempId, head_students))
                        headRepository.deleteHeadById(tempId);
                }
            }

        }catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
