package com.example.oop_application.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.List;

@Data
public class Head {

    private Integer id;

    private String firstName;

    private String lastName;

    private String patronymic;

    private List<Student> studentList;

}
