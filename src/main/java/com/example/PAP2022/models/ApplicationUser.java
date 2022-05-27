package com.example.PAP2022.models;

import com.example.PAP2022.enums.ApplicationUserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "image_id")
    private Image image;

    @JsonIgnore
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "teamMembers")
    private List<Team> teams;

    @JsonIgnore
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "receivers")
    private List<Task> tasks;

    @Enumerated(EnumType.STRING)
    private ApplicationUserRole applicationUserRole;
    private Boolean enabled = false;

    public ApplicationUser(String firstName,
                           String lastName,
                           String email,
                           String password,
                           Image img,
                           ApplicationUserRole applicationUserRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.image = img;
        this.applicationUserRole = applicationUserRole;
    }


}
