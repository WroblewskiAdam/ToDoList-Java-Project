package com.example.PAP2022.services;

import com.example.PAP2022.exceptions.EmailTakenException;
import com.example.PAP2022.exceptions.ImageNotFoundException;
import com.example.PAP2022.exceptions.UserNotFoundException;
import com.example.PAP2022.models.*;
import com.example.PAP2022.repository.ApplicationUserRepository;
import com.example.PAP2022.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public ApplicationUser getApplicationUser(Long id) throws UserNotFoundException {
        if (loadApplicationUserById(id).isPresent()) {
            return loadApplicationUserById(id).get();
        } else {
            throw new UserNotFoundException("Could not find user with ID " + id);
        }
    }

    public ApplicationUser getApplicationUserByEmail(String email) throws UserNotFoundException {
        if (loadApplicationUserByEmail(email).isPresent()) {
            return loadApplicationUserByEmail(email).get();
        } else {
            throw new UserNotFoundException("Could not find user with email " + email);
        }
    }

    public List<ApplicationUser> getAllUsers(){
        return applicationUserRepository.findAll();
    }

    public List<Team> getAllTeams(Long id) throws UserNotFoundException {
        return getApplicationUser(id).getTeams();
    }

    public List<Task> getAllTasks(Long id) throws UserNotFoundException {
        return getApplicationUser(id).getTasks();
    }

    public Image getImage(Long id) throws ImageNotFoundException, UserNotFoundException {
        return imageService.getImage(getApplicationUser(id).getImage().getId());
    }

    public ApplicationUser editApplicationUser(Long id,
                                               String firstName,
                                               String lastName,
                                               String password,
                                               MultipartFile file) throws IOException, UserNotFoundException {

        Image image = imageService.convertToImage(file);
        ApplicationUser applicationUser = getApplicationUser(id);

        applicationUser.setFirstName(firstName);
        applicationUser.setLastName(lastName);
        applicationUser.setImage(image);
        applicationUser.setPassword(encoder.bCryptPasswordEncoder().encode(password));

        return applicationUserRepository.save(applicationUser);
    }

    public Long deleteUser(Long id) throws UserNotFoundException {
        if (loadApplicationUserById(id).isPresent()) {
            emailTokenService.deleteEmailToken(getApplicationUser(id));
            applicationUserRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("Could not find user with ID " + id);
        }
        return id;
    }

    public List<ApplicationUser> getUsersByIds(List<Long> usersIds) throws UserNotFoundException {
        List<ApplicationUser> users = new ArrayList<>();

        for (Long id: usersIds) {
                users.add(getApplicationUser(id));
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
        ApplicationUser applicationUser = getApplicationUser(id);
        applicationUser.setPassword(encoder.bCryptPasswordEncoder().encode(password));
        applicationUserRepository.save(applicationUser);
    }
}




