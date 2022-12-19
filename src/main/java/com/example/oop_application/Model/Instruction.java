package com.example.oop_application.Model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Instruction {

    private Integer id;

    private String content;

    private LocalDate dateOfIndication;

    private Integer DaysToComplete;
}
