package com.example.PAP2022.exceptions;


public class UserIsNotAssignedToTaskException extends Exception {
    public UserIsNotAssignedToTaskException(String message) {
        super(message);
    }
}
