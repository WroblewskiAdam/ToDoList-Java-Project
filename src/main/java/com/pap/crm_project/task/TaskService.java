package com.pap.crm_project.task;

import org.springframework.beans.factory.annotation.Autowired;
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

        public void deleteTask(Long id) {
                taskRepository.deleteById(id);
        }

        public void saveTask(TaskRequest request){
                String date = request.getDeadline();
                date = date + ":00.0";
                LocalDateTime deadline = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);

                Task task = new Task(
                        request.getTitle(),
                        request.getDescription(),
                        deadline,
                        request.getPriority(),
                        request.getGiver(),
                        request.getReceivers(),
                        request.getTeam()
                );

                taskRepository.save(task);
        }
}
