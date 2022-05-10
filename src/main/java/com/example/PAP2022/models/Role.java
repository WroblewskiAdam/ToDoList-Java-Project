package com.example.PAP2022.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;


  public Role(String name){
    this.name = name;
  }

//  @ManyToMany
//  @JoinTable(
//          name = "roles_users",
//          joinColumns = @JoinColumn(name = "role_id"),
//          inverseJoinColumns = @JoinColumn(name = "application_user_id")
//  )
//  private List<ApplicationUser> roleMembers;
}