package com.example.PAP2022.models;

import com.example.PAP2022.enums.ApplicationUserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ApplicationUser implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String img; // TODO trzeba ewentualnie zmienić typ

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

  private Boolean locked = false;
  private Boolean enabled = false;


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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority =
            new SimpleGrantedAuthority(applicationUserRole.name());
    return Collections.singletonList(authority);
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() { return enabled; }

  @Override
  public String toString() {
    return firstName + " " + lastName;
  }
}

