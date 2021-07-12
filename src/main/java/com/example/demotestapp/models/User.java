package com.example.demotestapp.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity @Getter @Setter @ToString @EqualsAndHashCode
@Table(name = "tbl_user")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String address;
    @NotNull
    private String email;
}
