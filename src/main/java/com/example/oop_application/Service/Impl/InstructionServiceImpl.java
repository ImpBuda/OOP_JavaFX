package com.example.oop_application.Service.Impl;

import com.example.oop_application.Model.Head;
import com.example.oop_application.Model.Instruction;
import com.example.oop_application.Repository.Impl.InstructionRepositoryImpl;
import com.example.oop_application.Repository.InstructionRepository;
import com.example.oop_application.Service.InstructionService;

import java.io.File;
import java.util.List;

public class InstructionServiceImpl implements InstructionService {

    InstructionRepository instructionRepository = new InstructionRepositoryImpl();

    @Override
    public List<Instruction> contentSearch(String str) {
       return instructionRepository.contentSearch(str);
    }

    @Override
    public List<Instruction> dateOfIndicationSearch(String str) {
        return instructionRepository.dateOfIndicationSearch(str);
    }

    @Override
    public List<Instruction> getAllInstruction() {
        return instructionRepository.getAllInstruction();
    }

    @Override
    public void deleteInstructionById(int id) {
        instructionRepository.deleteInstructionById(id);
    }

    @Override
    public void updateInstructionById(int id, Instruction instruction) {
        instructionRepository.updateInstructionById(id, instruction);
    }

    @Override
    public void saveInstruction(Instruction instruction) {
        instructionRepository.saveInstruction(instruction);
    }

    @Override
    public Instruction getInstructionById(int id) {
        return instructionRepository.getInstructionById(id);
    }

}
