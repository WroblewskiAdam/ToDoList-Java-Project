package com.pap.crm_project.task;

import com.pap.crm_project.applicationuser.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TaskService {

        private TaskRepository taskRepository;

        @Autowired
        public TaskService(TaskRepository taskRepository) {
                this.taskRepository = taskRepository;
        }

        public List<Task> getAllTasks(){
                return taskRepository.findAll();
        }

        public void saveTask(TaskRequest request){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime deadline = LocalDateTime.parse(request.getDeadline().toString(), formatter);

                Task task = new Task(
                        request.getTitle(),
                        request.getDescription(),
                        deadline,
                        request.getPriority(),
                        request.getGiver(),
                        request.getReceivers()
                );

                taskRepository.save(task);
        }

        public List<Task> getTaskById(Long id){
               return taskRepository.findTaskByGiverId(id);
        }
}
