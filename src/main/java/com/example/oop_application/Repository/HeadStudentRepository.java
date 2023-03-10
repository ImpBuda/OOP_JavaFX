package com.example.oop_application.Repository;

import com.example.oop_application.Model.Head_Student;

import java.util.List;

public interface HeadStudentRepository {

    List<Head_Student> searchStud(String str);

    List<Head_Student> searchHead(String str);

    List<Head_Student> getAll();

    void deleteByStudentId(int id);

    void deleteByHeadId(int id);

    boolean isPresentStudent(int id, List<Head_Student> list);

    boolean isPresentHead(int id, List<Head_Student> list);

    void save(Head_Student head_student);

    void delete(int studId, int headId);

    void update(int studId, int headId);
}
