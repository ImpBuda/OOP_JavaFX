package com.example.oop_application.Repository.Impl;

import com.example.oop_application.Controller.InstructionController;
import com.example.oop_application.Model.*;
import com.example.oop_application.Repository.HeadRepository;
import com.example.oop_application.Repository.InstructionRepository;
import com.example.oop_application.Repository.StudentInstructionRepository;
import com.example.oop_application.Repository.StudentRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InstructionRepositoryImpl implements InstructionRepository {

    StudentInstructionRepository studentInstructionRepository = new StudentInstructionRepositoryImpl();


    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

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
            BufferedReader reader = new BufferedReader(new FileReader(Context.instructionFilePath));

            Type instructionListType = new TypeToken<List<Instruction>>(){}.getType();
            List<Instruction> instructions = gson.fromJson(reader, instructionListType);
            return instructions;
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
        return null;
    }

    @Override
    public void deleteInstructionById(int id) {

        try {

            List<Instruction> instructions = new ArrayList<>(getAllInstruction());

            for(int i = 0; i < instructions.size(); i++)
                if(instructions.get(i).getId() == id)
                {
                    studentInstructionRepository.deleteByInstructionId(instructions.get(i).getId());
                    instructions.remove(i);
                    break;
                }
            PrintWriter out = new PrintWriter(new FileWriter(Context.instructionFilePath));
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

            for (int i = 0 ; i < instructions.size(); i++){
                if (instructions.get(i).getId() == id){
                    instructions.remove(i);
                    instructions.add(i, copy);
                    break;
                }
            }
            PrintWriter out = new PrintWriter(new FileWriter(Context.instructionFilePath));
            out.write(gson.toJson(instructions));
            out.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    @Override
    public void saveInstruction(Instruction instruction, Student_Instruction student_instruction) {
        try {

            List<Instruction> instructions = new ArrayList<>(getAllInstruction());

            if(getInstructionById(instruction.getId()) == null){
                instructions.add(instruction);
                studentInstructionRepository.save(student_instruction);
                PrintWriter out = new PrintWriter(new FileWriter(Context.instructionFilePath));
                out.write(gson.toJson(instructions));
                out.close();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    @Override
    public Instruction getInstructionById(int id) {
        try {
            List<Instruction> instructions = new ArrayList<>(getAllInstruction());
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

}
