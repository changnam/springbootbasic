package com.honsoft.web.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

import javax.persistence.*;

@Getter@Setter
@Entity
@Table(name="USERS")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;
    
    //@Transient
    private String email;
    
    @OneToMany(mappedBy="user")
    private Set<Address> addresses;

}