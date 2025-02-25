package com.emailer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    private String soeid;
    private String name;
    private String email;
    private String role;
} 