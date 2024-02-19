package fr._42.chat.exceptions;

public class NotSavedSubEntityException extends RuntimeException{
    public NotSavedSubEntityException(String errorMessage) {
        super(errorMessage);
    }
}
