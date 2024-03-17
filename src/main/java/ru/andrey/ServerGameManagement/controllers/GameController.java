package ru.andrey.ServerGameManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import ru.andrey.ServerGameManagement.databases.CardService;
import ru.andrey.ServerGameManagement.databases.UserService;
import ru.andrey.ServerGameManagement.exceptions.CardErrorException;
import ru.andrey.ServerGameManagement.model.Card;
import ru.andrey.ServerGameManagement.model.User;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/game")
public class GameController {
    private final CardService cardService;
    private final UserService userService;
    private final Random random;
    private final Jedis jedis;

    @Autowired
    public GameController(CardService cardService, UserService userService, Random random, Jedis jedis) {
        this.cardService = cardService;
        this.userService = userService;
        this.random = random;
        this.jedis = jedis;
    }

    @GetMapping("/start")
    public ResponseEntity<List<Card>> start(@RequestParam("userId") Integer userId) {
        User user = userService.findByIdWithCards(userId);
        List<Card> cards = user.getCards();


        jedis.del(String.valueOf(userId));

        for (int i = 0; i < cards.size(); i++) {
            int index = random.nextInt(cards.size());

            Card tmp = cards.get(i);
            cards.set(i, cards.get(index));
            cards.set(index, tmp);
        }

        for (Card card : cards) {
            jedis.lpush(String.valueOf(userId), String.valueOf(card.getId()));
        }

        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping("/result")
    private ResponseEntity<Boolean> result(@RequestParam("userId") int userId, @RequestParam("input") String input) {
        String cardIdStr = jedis.lpop(String.valueOf(userId));
        if (cardIdStr == null) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        int cardId = Integer.parseInt(cardIdStr);
        Card card = cardService.findById(cardId).orElseThrow(() -> new CardErrorException(123L, "Нет такой карты!!!"));

        Boolean response = card.getTranslation().equals(input);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get")
    private ResponseEntity<Card> get(@RequestParam("userId") int userId) {
        String cardIdStr = jedis.lindex(String.valueOf(userId), 0);
        if (cardIdStr == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        int cardId = Integer.parseInt(cardIdStr);
        Card card = cardService.findById(cardId).orElseThrow(() -> new CardErrorException(123L, "Нет такой карты!!!"));

        return new ResponseEntity<>(card, HttpStatus.OK);
    }

}
