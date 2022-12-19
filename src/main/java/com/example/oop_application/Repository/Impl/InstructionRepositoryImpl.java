package com.example.oop_application.Repository.Impl;

import com.example.oop_application.Controller.InstructionController;
import com.example.oop_application.Model.Instruction;
import com.example.oop_application.Model.LocalDateAdapter;
import com.example.oop_application.Repository.InstructionRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InstructionRepositoryImpl implements InstructionRepository {



    @Override
    public List<Instruction> contentSearch(String str) {
        List<Instruction> instruction = new ArrayList<>(getAllInstruction());
        instruction.removeIf(element -> !element.getContent().toLowerCase().startsWith(str.toLowerCase()));
        return instruction;
    }

    @Override
    public List<Instruction> dateOfIndicationSearch(String str) {
        List<Instruction> instruction = new ArrayList<>(getAllInstruction());
        instruction.removeIf(element -> !element.getDateOfIndication().toString().startsWith(str));
        return instruction;
    }

    @Override
    public List<Instruction> getAllInstruction() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(InstructionController.filepath));
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

            Type instructionListType = new TypeToken<List<Instruction>>(){}.getType();
            List<Instruction> instructionList = gson.fromJson(reader, instructionListType);
            return instructionList;
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
        return null;
    }

    @Override
    public void deleteInstructionById(int id) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        try {
            List<Instruction> instructions = new ArrayList<>(getAllInstruction());

            for(int i = 0; i < instructions.size(); i++)
                if(instructions.get(i).getId() == id)
                    instructions.remove(i);

            PrintWriter out = new PrintWriter(new FileWriter(InstructionController.filepath));
            out.write(gson.toJson(instructions));
            out.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public void updateInstructionById(int id, Instruction instruction) {
        Instruction copy = getInstructionById(id);
        copy.setId(instruction.getId());
        copy.setContent(instruction.getContent());
        copy.setDateOfIndication(instruction.getDateOfIndication());
        copy.setDaysToComplete(instruction.getDaysToComplete());
        try {
            List<Instruction> instructions = new ArrayList<>(getAllInstruction());

            instructions .set(instructions .indexOf(getInstructionById(id)), copy);

            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
            PrintWriter out = new PrintWriter(new FileWriter(InstructionController.filepath));
            out.write(gson.toJson(instructions ));
            out.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    @Override
    public void saveInstruction(Instruction instruction) {
        try {
            if(InstructionController.filepath == null){
                File file = new File( "local.json");

                PrintWriter writer = new PrintWriter(file);
                writer.write("[]");
                writer.close();
                InstructionController.filepath = file.getPath();
            }
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
            List<Instruction> instructions = new ArrayList<>(getAllInstruction());
            if(getInstructionById(instruction.getId()) == null){
                instructions.add(instruction);
                PrintWriter out = new PrintWriter(new FileWriter(InstructionController.filepath));
                out.write(gson.toJson(instructions));
                out.close();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    @Override
    public Instruction getInstructionById(int id) {

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        try (FileReader reader = new FileReader(InstructionController.filepath)) {
            List<Instruction> instructions = gson.fromJson(reader, new TypeToken<List<Instruction>>() {}.getType());
            for (Instruction instruction : instructions) {
                if (instruction.getId() == id) {
                    return instruction;
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    @Override
    public void saveFileSystem(File saveFile) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        List<Instruction> instructions = new ArrayList<>(getAllInstruction());

        try {  PrintWriter out = new PrintWriter(new FileWriter(saveFile.getPath()));
            out.write(gson.toJson(instructions));
            out.close();

        }catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
