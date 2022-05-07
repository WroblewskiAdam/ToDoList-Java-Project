package com.example.PAP2022.services;

import com.example.PAP2022.exceptions.UserNotFoundException;
import com.example.PAP2022.models.Task;
import com.example.PAP2022.models.Team;
import com.example.PAP2022.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.PAP2022.models.ApplicationUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {
  private final ApplicationUserRepository applicationUserRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    ApplicationUser user = applicationUserRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + email));
    return user;
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

  public ApplicationUser editApplicationUser(ApplicationUser applicationUser) {
      return applicationUserRepository.save(applicationUser);
  }

  public Long deleteUser(Long id){
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

//  !!!!!!!!!!!!!!!!!!!!!!!
//  public String signUpUser(ApplicationUser applicationUser) {
//    boolean exists = applicationUserRepository
//            .findByEmail(applicationUser.getEmail())
//            .isPresent();
//
//    if (exists) {
//      ApplicationUser existingUser = applicationUserRepository.findByEmail(applicationUser.getEmail()).get();
//      if (!existingUser.getEnabled()) {
//        registrationTokenService.deleteRegistrationToken(existingUser);
//        applicationUserRepository.deleteByEmail(existingUser.getEmail());
//      }
//      else {
//        return "emailTaken";
//      }
//    }
//
//    String encodedPassword = bCryptPasswordEncoder
//            .encode(applicationUser.getPassword());
//
//    applicationUser.setPassword(encodedPassword);
//    applicationUserRepository.save(applicationUser);
//
//    String token = UUID.randomUUID().toString();
//
//    Token registrationToken = new Token(
//            token,
//            LocalDateTime.now(),
//            LocalDateTime.now().plusMinutes(15),
//            applicationUser
//    );
//
//    registrationTokenService.saveRegistrationToken(registrationToken);
//    return token;
//  }
//
//  public int enableApplicationUser(String email) {
//    return applicationUserRepository.enableApplicationUser(email);
//  }

}
