package com.example.oop_application.Repository.Impl;

import com.example.oop_application.Model.Context;
import com.example.oop_application.Model.Head;
import com.example.oop_application.Model.LocalDateAdapter;
import com.example.oop_application.Repository.HeadRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HeadRepositoryImpl implements HeadRepository {

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

    @Override
    public List<Head> patronymicSearch(String str) {
        List<Head> heads = new ArrayList<>(getAllHead());
        heads.removeIf(element -> !element.getPatronymic().toLowerCase().startsWith(str.toLowerCase()));
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
            BufferedReader reader = new BufferedReader(new FileReader(Context.filepath));

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

            PrintWriter out = new PrintWriter(new FileWriter(Context.filepath));
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
        copy.setStudentList(head.getStudentList());
        List<Head> heads = new ArrayList<>(getAllHead());
        try {
            for (int i = 0 ; i < heads.size(); i++){
                if (heads.get(i).getId() == id){
                    heads.remove(i);
                    heads.add(i, copy);
                }
            }
            PrintWriter out = new PrintWriter(new FileWriter(Context.filepath));
            out.write(gson.toJson(heads));
            out.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    @Override
    public void saveHead(Head head) {
        try {
            if(Context.filepath == null){
                File file = new File( "local.json");
                PrintWriter writer = new PrintWriter(file);
                writer.write("[]");
                writer.close();
                Context.filepath = file.getPath();
            }

            List<Head> heads = new ArrayList<>(getAllHead());
            if(getHeadById(head.getId()) == null){
                heads.add(head);
                PrintWriter out = new PrintWriter(new FileWriter(Context.filepath));
                out.write(gson.toJson(heads));
                out.close();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public Head getHeadById(int id) {
        try {
            List<Head> heads = new ArrayList<>(getAllHead());
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
        List<Head> heads = new ArrayList<>(getAllHead());

        try {  PrintWriter out = new PrintWriter(new FileWriter(saveFile.getPath()));
            out.write(gson.toJson(heads));
            out.close();

        }catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
