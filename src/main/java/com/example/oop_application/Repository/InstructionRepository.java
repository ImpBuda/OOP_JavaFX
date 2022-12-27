package com.example.oop_application.Repository;

import com.example.oop_application.Model.Head;
import com.example.oop_application.Model.Instruction;
import com.example.oop_application.Model.Student_Instruction;

import java.io.File;
import java.util.List;

public interface InstructionRepository {

    List<Instruction> contentSearch(String str);

    List<Instruction> dateOfIndicationSearch(String str);

    List<Instruction> getAllInstruction() ;

    void deleteInstructionById(int id);

    void updateInstructionById(int id, Instruction instruction);

    void saveInstruction(Instruction instruction, Student_Instruction student_instruction);

    Instruction getInstructionById(int id);

}
