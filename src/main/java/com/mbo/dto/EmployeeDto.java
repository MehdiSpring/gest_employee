package com.mbo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {

    @NotEmpty(message = "Le prénom ne doit pas être vide")
    private String firstName;

    @NotEmpty(message = "Le nom ne doit pas être vide")
    private String lastName;

    private double salary;

    @NotEmpty(message = "Le metier ne doit pas être vide")
    private String job;

    @NotEmpty(message = "Le secteur ne doit pas être vide")
    private String sector;
}
