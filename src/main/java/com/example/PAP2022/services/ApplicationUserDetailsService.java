package com.example.PAP2022.services;

import com.example.PAP2022.exceptions.EmailTakenException;
import com.example.PAP2022.exceptions.UserNotFoundException;
import com.example.PAP2022.models.*;
import com.example.PAP2022.repository.ApplicationUserRepository;
import com.example.PAP2022.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {
    private final ApplicationUserRepository applicationUserRepository;
    private final EmailTokenService emailTokenService;
    private final ImageService imageService;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ApplicationUser user = applicationUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + email));

        return ApplicationUserDetails.build(user);
    }

    public Optional<ApplicationUser> loadApplicationUserByEmail(String email) {
        return applicationUserRepository.findByEmail(email);
    }

    public Optional<ApplicationUser> loadApplicationUserById(Long id) {
        return applicationUserRepository.findById(id);
    }

    public List<ApplicationUser> getAllUsers(){
        return applicationUserRepository.findAll();
    }

    public List<Team> getAllTeams(Long id){
        return applicationUserRepository.getById(id).getTeams();
    }

    public List<Task> getAllTasks(Long id){
        return applicationUserRepository.getById(id).getTasks();
    }

    public Image getImage(Long id){
        return imageService.getImage(applicationUserRepository.getById(id));
    }

    public ApplicationUser editApplicationUser(ApplicationUser applicationUser) {
        return applicationUserRepository.save(applicationUser);
    }

    public Long deleteUser(Long id){
        emailTokenService.deleteEmailToken(applicationUserRepository.getById(id));
        applicationUserRepository.deleteById(id);
        return id;
    }

    public List<ApplicationUser> getUsersByIds(List<Long> usersIds) throws UserNotFoundException {
        List<ApplicationUser> users = new ArrayList<>();

        for (Long id: usersIds) {
            if (!loadApplicationUserById(id).isPresent()) {
                throw new UserNotFoundException("Could not find user with ID " + id);
            } else {
                users.add(loadApplicationUserById(id).get());
            }
        }

        return users;
    }

    public String signUpUser(ApplicationUser applicationUser) throws EmailTakenException {
        if (loadApplicationUserByEmail(applicationUser.getEmail()).isPresent()) {
            ApplicationUser existingUser = loadApplicationUserByEmail(applicationUser.getEmail()).get();
            if (!existingUser.getEnabled()) {
                emailTokenService.deleteEmailToken(existingUser);
                applicationUserRepository.deleteByEmail(existingUser.getEmail());
            } else {
                throw new EmailTakenException("Email is already taken");
            }
        }

        String encodedPassword = encoder.bCryptPasswordEncoder().encode(applicationUser.getPassword());

        applicationUser.setPassword(encodedPassword);
        applicationUserRepository.save(applicationUser);

        String token = UUID.randomUUID().toString();

        EmailToken emailToken = new EmailToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                applicationUser
        );

        emailTokenService.saveEmailToken(emailToken);
        return token;
    }

    public int enableApplicationUser(String email) {
        return applicationUserRepository.enableApplicationUser(email);
    }

    public void resetPassword(String password, Long id) throws UserNotFoundException {
        if (loadApplicationUserById(id).isPresent()) {
            ApplicationUser applicationUser = loadApplicationUserById(id).get();
            applicationUser.setPassword(encoder.bCryptPasswordEncoder().encode(password));
            applicationUserRepository.save(applicationUser);
        } else {
            throw new UserNotFoundException("Could not find user with ID " + id);
        }
    }
}




