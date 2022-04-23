package com.example.PAP2022.models;

import com.example.PAP2022.enums.ApplicationUserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ApplicationUser {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String img; // TODO trzeba ewentualnie zmienić typ

  @ManyToMany(
          fetch = FetchType.LAZY,
          cascade = CascadeType.ALL,
          mappedBy = "teamMembers")
  private List<Team> teams;

  @ManyToMany(
          fetch = FetchType.LAZY,
          cascade = CascadeType.ALL,
          mappedBy = "receivers")
  private List<Task> tasks;

  @Enumerated(EnumType.STRING)
  private ApplicationUserRole applicationUserRole;

  public ApplicationUser(String firstName,
                         String lastName,
                         String email,
                         String password,
                         String img,
                         ApplicationUserRole applicationUserRole) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.img = img;
    this.applicationUserRole = applicationUserRole;
  }
}
