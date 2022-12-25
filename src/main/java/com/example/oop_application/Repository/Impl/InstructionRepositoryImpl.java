package com.example.oop_application.Repository.Impl;

import com.example.oop_application.Controller.InstructionController;
import com.example.oop_application.Model.*;
import com.example.oop_application.Repository.HeadRepository;
import com.example.oop_application.Repository.InstructionRepository;
import com.example.oop_application.Repository.StudentRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InstructionRepositoryImpl implements InstructionRepository {

    StudentRepository studentRepository = new StudentRepositoryImpl();
    ;

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
            List<Student> studentList = studentRepository.getAllStudent();
            List<Instruction> instructionList = new ArrayList<>();
            for (Student student : studentList) instructionList.addAll(student.getInstructionList());
            return instructionList;
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
        return null;
    }

    @Override
    public void deleteInstructionById(int id) {

        try {
            Instruction instruction = getInstructionById(id);
            Student student = studentRepository.getStudentById(instruction.getStudent_id());
            List<Instruction> instructions = new ArrayList<>(getAllInstruction());

            for(int i = 0; i < instructions.size(); i++)
                if(instructions.get(i).getId() == id)
                    instructions.remove(i);

            student.setInstructionList(instructions);
            studentRepository.updateStudentById(student.getId(), student);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public void updateInstructionById(int id, Instruction instruction) {
        Student student = studentRepository.getStudentById(instruction.getStudent_id());
        Instruction copy = getInstructionById(id);
        copy.setId(instruction.getId());
        copy.setContent(instruction.getContent());
        copy.setDateOfIndication(instruction.getDateOfIndication());
        copy.setDaysToComplete(instruction.getDaysToComplete());
        copy.setStudent_id(instruction.getStudent_id());
        try {
            List<Instruction> instructions = new ArrayList<>(getAllInstruction());

            for (int i = 0 ; i < instructions.size(); i++){
                if (instructions.get(i).getId() == id){
                    instructions.remove(i);
                    instructions.add(i, copy);
                }
            }
            if(studentRepository.getStudentById(instruction.getStudent_id()) != null) {
                student.setInstructionList(instructions);
                studentRepository.updateStudentById(student.getId(), student);
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    @Override
    public void saveInstruction(Instruction instruction) {
        try {
            Student student = studentRepository.getStudentById(instruction.getStudent_id());
            List<Instruction> instructions = new ArrayList<>();
            if (student.getInstructionList() != null)
                instructions = new ArrayList<>(student.getInstructionList());
            if(getInstructionById(instruction.getId()) == null &&
                    studentRepository.getStudentById(instruction.getStudent_id()) != null){
                instructions.add(instruction);
                student.setInstructionList(instructions);
                studentRepository.updateStudentById(student.getId(), student);
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
