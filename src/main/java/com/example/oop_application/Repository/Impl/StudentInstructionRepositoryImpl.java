package com.example.oop_application.Repository.Impl;

import com.example.oop_application.Model.Context;
import com.example.oop_application.Model.Head_Student;
import com.example.oop_application.Model.LocalDateAdapter;
import com.example.oop_application.Model.Student_Instruction;
import com.example.oop_application.Repository.InstructionRepository;
import com.example.oop_application.Repository.StudentInstructionRepository;
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

public class StudentInstructionRepositoryImpl implements StudentInstructionRepository {

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

    @Override
    public List<Student_Instruction> getAll() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Context.studentInstructionFilePath));

            Type student_instruction = new TypeToken<List<Student_Instruction>>(){}.getType();
            List<Student_Instruction> student_instructions = gson.fromJson(reader, student_instruction);
            if (student_instructions == null)
                return new ArrayList<>();
            return student_instructions;
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
        return null;
    }

    @Override
    public void deleteByInstructionId(int id) {
        try {
            StudentRepositoryImpl studentRepository = new StudentRepositoryImpl();

            int tempId;
            List<Student_Instruction> student_instructions = new ArrayList<>(getAll());
            for(int i = 0; i < student_instructions.size(); i++ ){
                if(student_instructions.get(i).getInstruction_id() == id){
                    tempId = student_instructions.get(i).getStudent_id();
                    student_instructions.remove(i);
                    i--;

                    PrintWriter out = new PrintWriter(new FileWriter(Context.studentInstructionFilePath));
                    out.write(gson.toJson(student_instructions));
                    out.close();

                    if(!isPresentStudent(tempId, student_instructions))
                        studentRepository.deleteStudentById(tempId);
                }
            }

        }catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public void deleteByStudentId(int id) {
        try {
            InstructionRepository instructionRepository = new InstructionRepositoryImpl();
            int tempId;
            List<Student_Instruction> student_instructions = new ArrayList<>(getAll());

            for(int i = 0; i < student_instructions.size(); i++ ){
                if(student_instructions.get(i).getStudent_id() == id){
                    tempId = student_instructions.get(i).getInstruction_id();
                    student_instructions.remove(i);
                    i--;

                    PrintWriter out = new PrintWriter(new FileWriter(Context.studentInstructionFilePath));
                    out.write(gson.toJson(student_instructions));
                    out.close();

                    if(!isPresentInstruction(tempId, student_instructions))
                        instructionRepository.deleteInstructionById(tempId);
                }
            }

        }catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public boolean isPresentStudent(int id, List<Student_Instruction> list) {
        for (Student_Instruction student_instruction : list) {
            if (student_instruction.getStudent_id() == id)
                return true;
        }
        return false;
    }

    @Override
    public boolean isPresentInstruction(int id, List<Student_Instruction> list) {
        for (Student_Instruction student_instruction : list) {
            if (student_instruction.getInstruction_id() == id)
                return true;
        }
        return false;
    }

    @Override
    public void save(Student_Instruction student_instruction) {
        try {
            List<Student_Instruction> student_instructions = new ArrayList<>(getAll());
            student_instructions.add(student_instruction);
            PrintWriter out = new PrintWriter(new FileWriter(Context.studentInstructionFilePath));
            out.write(gson.toJson(student_instructions));
            out.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
