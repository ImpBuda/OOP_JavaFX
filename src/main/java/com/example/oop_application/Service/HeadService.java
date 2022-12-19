package com.example.oop_application.Service;

import com.example.oop_application.Model.Head;

import java.io.File;
import java.util.List;

public interface HeadService {

    List<Head> patronymicSearch(String str);

    List<Head> instructionSearch(String str);

    List<Head> nameSearch(String str);

    List<Head> surnameSearch(String str);

    List<Head> getAllHead() ;

    void deleteHeadById(int id);

    void updateHeadById(int id, Head head);

    void saveHead(Head student);

    Head getHeadById(int id);

    void saveFileSystem(File saveFile);
}
