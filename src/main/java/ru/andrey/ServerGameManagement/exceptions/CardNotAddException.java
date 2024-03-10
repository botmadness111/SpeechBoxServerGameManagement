package ru.andrey.ServerGameManagement.exceptions;

public class CardNotAddException extends CardErrorException {

    public CardNotAddException(Long chatId, String message) {
        super(chatId, message);
    }
}
