package com.example.oop_application.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class Head {

    private Integer id;

    private String firstName;

    private String lastName;

    private String patronymic;
}
