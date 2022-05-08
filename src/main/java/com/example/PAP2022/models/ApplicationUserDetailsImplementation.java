package com.example.PAP2022.models;

        import java.util.Collection;
        import java.util.Collections;
        import org.springframework.security.core.GrantedAuthority;
        import org.springframework.security.core.authority.SimpleGrantedAuthority;
        import org.springframework.security.core.userdetails.UserDetails;

        import com.fasterxml.jackson.annotation.JsonIgnore;

public class ApplicationUserDetailsImplementation implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    private String img; // TODO trzeba ewentualnie zmienić typ

    private Collection<? extends GrantedAuthority> authorities;

    public ApplicationUserDetailsImplementation(Long id, String firstName, String lastName, String email, String password, String img,
                                                Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.img = img;
        this.authorities = authorities;
    }

    public static ApplicationUserDetailsImplementation build(ApplicationUser user) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getApplicationUserRole().name());

        return new ApplicationUserDetailsImplementation(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getImg(),
                Collections.singleton(authority));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
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
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
