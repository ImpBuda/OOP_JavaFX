package com.example.oop_application.Model;

import com.example.oop_application.Controller.HeadController;
import com.example.oop_application.Controller.InstructionController;
import com.example.oop_application.Controller.StudentController;
import lombok.Data;

@Data
public class Context {

   public static String headFilePath = "head.json";

   public static String studentFilePath = "student.json";

   public static String instructionFilePath = "instruction.json";

   public static String headStudentFilepath = "studentHead.json";

   public static String studentInstructionFilePath = "studentInstruction.json";

}
