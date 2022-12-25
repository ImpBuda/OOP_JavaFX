package com.example.oop_application.Model;

import lombok.Data;

import java.util.List;

@Data
public class Student {

    private Integer id;

    private String firstName;

    private String lastName;

    private Integer head_id;

    private List<Instruction> instructionList;

}
