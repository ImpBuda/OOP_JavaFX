package com.example.oop_application.Repository;

import com.example.oop_application.Model.Head_Student;
import com.example.oop_application.Model.Student_Instruction;

import java.util.List;

public interface StudentInstructionRepository {

    List<Student_Instruction> getAll();

    void deleteByInstructionId(int id);

    void deleteByStudentId(int id);

    boolean isPresentStudent(int id, List<Student_Instruction> list);

    boolean isPresentInstruction(int id, List<Student_Instruction> list);

    void save(Student_Instruction student_instruction);

}
