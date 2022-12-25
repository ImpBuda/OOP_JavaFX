package com.example.oop_application.Model;

import com.example.oop_application.Controller.HeadController;
import com.example.oop_application.Controller.InstructionController;
import com.example.oop_application.Controller.StudentController;
import lombok.Data;

@Data
public class Context {

   public static String filepath;

   private StudentController studentController;

   private InstructionController instructionController;

   private HeadController headController;
}
