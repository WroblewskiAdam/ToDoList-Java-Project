package com.example.PAP2022.enums;

import com.example.PAP2022.exceptions.InvalidTaskPriorityException;

public enum TaskPriority {
    GREEN,
    YELLOW,
    RED;

    public static TaskPriority parse(String priorityString) throws InvalidTaskPriorityException {
        return switch (priorityString) {
            case "RED" -> TaskPriority.RED;
            case "YELLOW" -> TaskPriority.YELLOW;
            case "GREEN" -> TaskPriority.GREEN;
            default -> throw new InvalidTaskPriorityException("Task's priority can be only RED, YELLOW or GREEN");
        };
    }
}



