package com.example.PAP2022.services;

import com.example.PAP2022.enums.ApplicationUserRole;
import com.example.PAP2022.exceptions.EmailTakenException;
import com.example.PAP2022.exceptions.ImageNotFoundException;
import com.example.PAP2022.exceptions.InvalidApplicationUserRoleException;
import com.example.PAP2022.exceptions.UserNotFoundException;
import com.example.PAP2022.models.*;
import com.example.PAP2022.payload.AppUserEditRequest;
import com.example.PAP2022.repository.ApplicationUserRepository;
import com.example.PAP2022.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        return applicationUserRepository.findAll().stream()
                .sorted(Comparator.comparing(ApplicationUser::getLastName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public List<Team> getAllTeams(Long id) throws UserNotFoundException {
        return getApplicationUser(id).getTeams().stream()
                .sorted(Comparator.comparing(Team::getName))
                .collect(Collectors.toList());
    }

    public List<Task> getAllTasks(Long id) throws UserNotFoundException {
        return getApplicationUser(id).getTasks().stream()
                .sorted(Comparator.comparing(Task::getDeadline).reversed())
                .collect(Collectors.toList());
    }

    public Image getImage(Long id) throws ImageNotFoundException, UserNotFoundException {
        return imageService.getImage(getApplicationUser(id).getImage().getId());
    }

    public ApplicationUser editApplicationUser(Long id, AppUserEditRequest userEditRequest) throws IOException, UserNotFoundException {
        ApplicationUser applicationUser = getApplicationUser(id);
        applicationUser.setFirstName(userEditRequest.getFirstName());
        applicationUser.setLastName(userEditRequest.getLastName());

        return applicationUserRepository.save(applicationUser);
    }

    public ApplicationUser editWithImageApplicationUser(Long id, AppUserEditRequest userEditRequest) throws IOException, UserNotFoundException, ImageNotFoundException {
        ApplicationUser applicationUser = getApplicationUser(id);
        applicationUser.setFirstName(userEditRequest.getFirstName());
        applicationUser.setLastName(userEditRequest.getLastName());
        Long imgId = applicationUser.getImage().getId();
        applicationUser.setImage(imageService.editImage(imgId, userEditRequest.getImage()));

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

        return users.stream()
                .sorted(Comparator.comparing(ApplicationUser::getLastName))
                .collect(Collectors.toList());

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

    public ApplicationUser changeRole(Long id, String role) throws
            IOException,
            UserNotFoundException,
            InvalidApplicationUserRoleException {

        ApplicationUser applicationUser = getApplicationUser(id);
        ApplicationUserRole roleEnum = ApplicationUserRole.parse(role);
        applicationUser.setApplicationUserRole(roleEnum);

        return applicationUserRepository.save(applicationUser);
    }

}




