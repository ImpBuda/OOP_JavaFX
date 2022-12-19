package com.example.oop_application.Service.Impl;

import com.example.oop_application.Model.Head;
import com.example.oop_application.Repository.HeadRepository;
import com.example.oop_application.Repository.Impl.HeadRepositoryImpl;
import com.example.oop_application.Service.HeadService;

import java.io.File;
import java.util.List;

public class HeadServiceImpl implements HeadService {

    private final HeadRepository headRepository = new HeadRepositoryImpl();

    @Override
    public List<Head> patronymicSearch(String str) {return headRepository.patronymicSearch(str);}

    @Override
    public List<Head> instructionSearch(String str) {
       return headRepository.instructionSearch(str);
    }

    @Override
    public List<Head> nameSearch(String str) {
        return headRepository.nameSearch(str);
    }

    @Override
    public List<Head> surnameSearch(String str) {
        return headRepository.surnameSearch(str);
    }

    @Override
    public List<Head> getAllHead() {
        return headRepository.getAllHead();
    }

    @Override
    public void deleteHeadById(int id) {
        headRepository.deleteHeadById(id);
    }

    @Override
    public void updateHeadById(int id, Head head) {
        headRepository.updateHeadById(id, head);
    }

    @Override
    public void saveHead(Head student) {
        headRepository.saveHead(student);
    }

    @Override
    public Head getHeadById(int id) {
        return headRepository.getHeadById(id);
    }

    @Override
    public void saveFileSystem(File saveFile) {
        headRepository.saveFileSystem(saveFile);
    }
}
