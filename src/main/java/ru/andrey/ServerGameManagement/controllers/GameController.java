package ru.andrey.ServerGameManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andrey.ServerGameManagement.databases.CardService;
import ru.andrey.ServerGameManagement.databases.UserService;
import ru.andrey.ServerGameManagement.model.Card;
import ru.andrey.ServerGameManagement.model.User;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {
    private final CardService cardService;
    private final UserService userService;

    @Autowired
    public GameController(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @GetMapping("/start")
    public ResponseEntity<List<Card>> start(@RequestParam("userId") int userId) {
        User user = userService.findByIdWithCards(userId);
        List<Card> cards = user.getCards();

        return new ResponseEntity<>(cards, HttpStatus.OK);
    }
}
