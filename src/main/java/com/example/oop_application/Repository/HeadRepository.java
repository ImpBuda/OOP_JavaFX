package com.example.oop_application.Repository;

import com.example.oop_application.Model.Head;
import com.example.oop_application.Model.Head_Student;

import java.io.File;
import java.util.List;

public interface HeadRepository {

    List<Head> patronymicSearch(String str);

    List<Head> nameSearch(String str);

    List<Head> surnameSearch(String str);

    List<Head> getAllHead();

    void deleteHeadById(int id);

    void updateHeadById(int id, Head head);

    void saveHead(Head head, Head_Student head_student);

    Head getHeadById(int id);

    void saveFileSystem(File saveFile);
}
