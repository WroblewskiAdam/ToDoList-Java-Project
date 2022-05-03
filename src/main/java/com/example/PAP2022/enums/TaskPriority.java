package com.example.PAP2022.enums;

import com.example.PAP2022.exceptions.InvalidTaskPriorityException;

public enum TaskPriority {
    GREEN,
    YELLOW,
    RED;

    public static TaskPriority parse(String priorityString) throws InvalidTaskPriorityException {
        if (priorityString.equals("RED")) {
            return TaskPriority.RED;

        } else if (priorityString.equals("YELLOW")) {
            return TaskPriority.YELLOW;

        } else if (priorityString.equals("GREEN")) {
            return TaskPriority.GREEN;

        } else {
            throw new InvalidTaskPriorityException("Task's priority can be only RED, YELLOW or GREEN");
        }

    }
}



