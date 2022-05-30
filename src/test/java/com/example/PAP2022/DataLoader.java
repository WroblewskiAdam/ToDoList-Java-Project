package com.example.PAP2022;

import com.example.PAP2022.enums.TaskPriority;
import com.example.PAP2022.models.*;
import com.example.PAP2022.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.PAP2022.enums.ApplicationUserRole.USER;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private ApplicationUserRepository userRepository;
    private EmailTokenRepository emailTokenRepository;
    private ImageRepository imageRepository;
    private TaskRepository taskRepository;
    private TeamRepository teamRepository;

    @Override
    public void run(String... args) {

        Image image1 = new Image("name", "xxx");
        Image image2 = new Image("name", "xxx");
        Image image3 = new Image("name", "xxx");
        Image image4 = new Image("name", "xxx");

        imageRepository.save(image1);
        imageRepository.save(image2);
        imageRepository.save(image3);
        imageRepository.save(image4);


        ApplicationUser user1 = new ApplicationUser("Jurek", "Ogórek", "ogorek@gmail.com", "password", image1, USER);
        ApplicationUser user2 = new ApplicationUser("Tomasz", "Złomiarz", "zlomiarz@gmail.com", "password", image2, USER);
        ApplicationUser user3 = new ApplicationUser("Karolina", "Malina", "malina@gmail.com", "password", image3, USER);
        ApplicationUser user4 = new ApplicationUser("Zenek", "Błazenek", "blazenek@gmail.com", "password", image4, USER);

        user1.setEnabled(true);
        user2.setEnabled(true);
        user3.setEnabled(true);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);


        EmailToken emailToken1 = new EmailToken("token1", LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(1).plusMinutes(15), user1);
        EmailToken emailToken2 = new EmailToken("token2", LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(2).plusMinutes(15), user2);
        EmailToken emailToken3 = new EmailToken("token3", LocalDateTime.now().minusDays(3), LocalDateTime.now().minusDays(3).plusMinutes(15), user3);
        EmailToken emailToken4 = new EmailToken("token4", LocalDateTime.now().minusDays(4), LocalDateTime.now().minusDays(4).plusMinutes(15), user4);

        emailToken1.setConfirmationTime(LocalDateTime.now().minusDays(1).plusMinutes(5));
        emailToken2.setConfirmationTime(LocalDateTime.now().minusDays(2).plusMinutes(5));
        emailToken3.setConfirmationTime(LocalDateTime.now().minusDays(3).plusMinutes(5));

        emailTokenRepository.save(emailToken1);
        emailTokenRepository.save(emailToken2);
        emailTokenRepository.save(emailToken3);
        emailTokenRepository.save(emailToken4);


        Team team1 = new Team("TEAM A", user1, List.of(user1));
        Team team2 = new Team("TEAM B", user2, List.of(user1, user2));
        Team team3 = new Team("TEAM C", user3, List.of(user1, user2, user3));

        teamRepository.save(team1);
        teamRepository.save(team2);
        teamRepository.save(team3);


        Task task1 = new Task("Task 1", "Description to task 1", LocalDateTime.now().plusDays(1), TaskPriority.GREEN, user1, List.of(user1), team1);
        Task task2 = new Task("Task 2", "Description to task 2", LocalDateTime.now().plusDays(5), TaskPriority.YELLOW, user2, List.of(user2), team2);
        Task task3 = new Task("Task 3", "Description to task 3", LocalDateTime.now().plusDays(8), TaskPriority.RED, user3, List.of(user2, user3), team3);

        Task pvTask1 = new Task("Private Task 1", "Description to private task 1", LocalDateTime.now().plusDays(5), TaskPriority.GREEN, user2, List.of(user1, user2), null);
        Task pvTask2 = new Task("Private Task 2", "Description to private task 2", LocalDateTime.now().plusDays(8), TaskPriority.YELLOW, user3, List.of(user2), null);
        Task pvTask3 = new Task("Private Task 3", "Description to private task 3", LocalDateTime.now().plusDays(8), TaskPriority.RED, user2, List.of(user3), null);

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        taskRepository.save(pvTask1);
        taskRepository.save(pvTask2);
        taskRepository.save(pvTask3);
    }
}