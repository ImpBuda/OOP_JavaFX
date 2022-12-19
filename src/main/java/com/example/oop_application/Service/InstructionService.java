package com.example.oop_application.Service;

import com.example.oop_application.Model.Head;
import com.example.oop_application.Model.Instruction;

import java.io.File;
import java.util.List;

public interface InstructionService {

    List<Instruction> contentSearch(String str);

    List<Instruction> dateOfIndicationSearch(String str);

    List<Instruction> getAllInstruction() ;

    void deleteInstructionById(int id);

    void updateInstructionById(int id, Instruction instruction);

    void saveInstruction(Instruction instruction);

    Instruction getInstructionById(int id);

    void saveFileSystem(File saveFile);

}
