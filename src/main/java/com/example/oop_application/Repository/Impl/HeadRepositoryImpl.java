package com.example.oop_application.Repository.Impl;

import com.example.oop_application.Controller.HeadController;
import com.example.oop_application.Model.Head;
import com.example.oop_application.Repository.HeadRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HeadRepositoryImpl implements HeadRepository {

    @Override
    public List<Head> patronymicSearch(String str) {
        List<Head> heads = new ArrayList<>(getAllHead());
        heads.removeIf(element -> !element.getPatronymic().toLowerCase().startsWith(str.toLowerCase()));
        return heads;
    }

    @Override
    public List<Head> instructionSearch(String str) {
        List<Head> heads = new ArrayList<>(getAllHead());
        heads.removeIf(element -> !element.getPatronymic().toString().startsWith(str));
        return heads;
    }

    @Override
    public List<Head> nameSearch(String str) {
        List<Head> heads = new ArrayList<>(getAllHead());
        heads.removeIf(element -> !element.getFirstName().toLowerCase().startsWith(str.toLowerCase()));
        return heads;
    }

    @Override
    public List<Head> surnameSearch(String str) {
        List<Head> heads = new ArrayList<>(getAllHead());
        heads.removeIf(element -> !element.getLastName().toLowerCase().startsWith(str.toLowerCase()));
        return heads;
    }

    @Override
    public List<Head> getAllHead() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(HeadController.filepath));
            Gson gson = new Gson();

            Type headListType = new TypeToken<List<Head>>(){}.getType();
            List<Head> headList = gson.fromJson(reader, headListType);
            return headList;
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
        return null;
    }

    @Override
    public void deleteHeadById(int id) {
        Gson gson = new Gson();
        try {
            List<Head> heads = new ArrayList<>(getAllHead());

            for(int i = 0; i < heads.size(); i++)
                if(heads.get(i).getId() == id)
                    heads.remove(i);

            PrintWriter out = new PrintWriter(new FileWriter(HeadController.filepath));
            out.write(gson.toJson(heads));
            out.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public void updateHeadById(int id, Head head) {
        Head copy = getHeadById(id);
        copy.setId(head.getId());
        copy.setFirstName(head.getFirstName());
        copy.setLastName(head.getLastName());
        copy.setPatronymic(head.getPatronymic());
        copy.setIdInstruction(head.getIdInstruction());
        try {
            List<Head> heads = new ArrayList<>(getAllHead());

            heads.set(heads.indexOf(getHeadById(id)), copy);

            Gson gson = new Gson();
            PrintWriter out = new PrintWriter(new FileWriter(HeadController.filepath));
            out.write(gson.toJson(heads));
            out.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    @Override
    public void saveHead(Head head) {
        try {
            if(HeadController.filepath == null){
                File file = new File( "local.json");

                PrintWriter writer = new PrintWriter(file);
                writer.write("[]");
                writer.close();
                HeadController.filepath = file.getPath();
            }
            Gson gson = new Gson();
            List<Head> heads = new ArrayList<>(getAllHead());
            if(getHeadById(head.getId()) == null){
                heads.add(head);
                PrintWriter out = new PrintWriter(new FileWriter(HeadController.filepath));
                out.write(gson.toJson(heads));
                out.close();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public Head getHeadById(int id) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(HeadController.filepath)) {
            List<Head> heads = gson.fromJson(reader, new TypeToken<List<Head>>() {}.getType());
            for (Head head : heads) {
                if (head.getId() == id) {
                    return head;
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    @Override
    public void saveFileSystem(File saveFile) {
        Gson gson = new Gson();
        List<Head> heads = new ArrayList<>(getAllHead());

        try {  PrintWriter out = new PrintWriter(new FileWriter(saveFile.getPath()));
            out.write(gson.toJson(heads));
            out.close();

        }catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
