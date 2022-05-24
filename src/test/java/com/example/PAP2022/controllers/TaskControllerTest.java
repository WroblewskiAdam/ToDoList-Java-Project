package com.example.PAP2022.controllers;

import com.example.PAP2022.models.ApplicationUser;
import com.example.PAP2022.models.Image;
import com.example.PAP2022.models.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.PAP2022.enums.ApplicationUserRole.USER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void beforeAll() {

//        ApplicationUser user1 = new ApplicationUser("Jurek", "Ogórek", "ogorek@gmail.com", "password", new Image(), USER);
//        ApplicationUser user2 = new ApplicationUser("Tomasz", "Złomiarz", "zlomiarz@gmail.com", "password", new Image(), USER);
//        ApplicationUser user3 = new ApplicationUser("Karolina", "Malina", "malina@gmail.com", "password", new Image(), USER);
//
//        Task task = new Task("Task 1", "A", "2022-05-25T10:55", "GREEN", 1, [1], )
    }

    @Test
    void shouldGetAllTasks() {


    }

    @Test
    void getReceivers() {
    }

    @Test
    void getTaskReceiversWhoDone() {
    }

    @Test
    void getTodayTasks() {
    }

    @Test
    void getTodayTasksGiven() {
    }

    @Test
    void getSevenDaysTasks() {
    }

    @Test
    void getSevenDaysTasksGiven() {
    }

    @Test
    void getPrivateTasks() {
    }

    @Test
    void getPrivateTasksGiven() {
    }

    @Test
    void getReceivedTasks() {
    }

    @Test
    void getGivenTasks() {
    }

    @Test
    void getExpiredTasks() {
    }

    @Test
    void getExpiredTasksGiven() {
    }

    @Test
    void getDoneGivenTasks() {
    }

    @Test
    void getDoneReceivedTasks() {
    }

    @Test
    void checkIfTaskIsDoneByUser() {
    }

    @Test
    void getAllExpiredTasksTeam() {
    }

    @Test
    void getAllDoneTasksTeam() {
    }

    @Test
    void getReceivedTasksTeam() {
    }

    @Test
    void getGivenTasksTeam() {
    }

    @Test
    void getExpiredReceivedTasksTeam() {
    }

    @Test
    void getDoneTasksTeam() {
    }

    @Test
    void getTodayTasksTeam() {
    }

    @Test
    void getTodayTasksGivenTeam() {
    }

    @Test
    void getTodayTasksReceivedTeam() {
    }

    @Test
    void getSevenDaysTasksTeam() {
    }

    @Test
    void getSevenDaysTasksGivenTeam() {
    }

    @Test
    void getSevenDaysReceivedTasksTeam() {
    }

    @Test
    void editTask() {
    }

    @Test
    void saveTask() {
    }

    @Test
    void tickTask() {
    }

    @Test
    void deleteTask() {
    }
}