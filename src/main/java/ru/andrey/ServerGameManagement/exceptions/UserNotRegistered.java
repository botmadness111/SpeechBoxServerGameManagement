package ru.andrey.ServerGameManagement.exceptions;

public class UserNotRegistered extends UserErrorException{
    public UserNotRegistered(Long chatId, String message) {
        super(chatId, message);
    }
}
