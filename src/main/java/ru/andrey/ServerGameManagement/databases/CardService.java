package ru.andrey.ServerGameManagement.databases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrey.ServerGameManagement.model.Card;
import ru.andrey.ServerGameManagement.model.User;
import ru.andrey.ServerGameManagement.repositories.CardRepository;
import ru.andrey.ServerGameManagement.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardService(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Card save(Card card) {
        User user = userRepository.findById(card.getUser().getId()).get();

        user.addCard(card);
        card.addUser(user);

        return cardRepository.save(card);
    }

    @Transactional
    public void delete(String original, String translation, String category, User user) {
        cardRepository.deleteCardByOriginalAndTranslationAndCategoryAndUser(original, translation, category, user);
    }

    @Transactional
    public void setCategory(Card card, String category) {
        card = findById(card.getId()).get();
        card.setCategory(category);
        save(card);

        User user = userRepository.findById(card.getUser().getId()).get();
        user.setSelectedCardId(null);
    }

    public Optional<Card> findByOriginalAndTranslationAndCategoryAndUser(String original, String translation, String category, User user) {
        List<Card> cards = cardRepository.findByOriginalAndTranslationAndCategoryAndUser(original, translation, category, user);
        if (cards.isEmpty()) return Optional.empty();
        else return Optional.of(cards.get(0));
    }

    public Optional<Card> findByOriginalAndTranslation(String original, String translation, User user) {
        List<Card> cards = cardRepository.findByOriginalAndTranslationAndUser(original, translation, user);
        if (cards.isEmpty()) return Optional.empty();
        else return Optional.of(cards.get(0));
    }

    public List<Card> findAll(User user) {
        return cardRepository.findAllByUser(user);
    }

    public List<Card> findByIdGreaterThan(Integer value, int userId) {
        return cardRepository.findByIdGreaterThanAndUser(value, userId);
    }

    public Optional<Card> findById(int id) {
        return cardRepository.findById(id);
    }

    public Integer findCardWithMaxId(int userId) {
        return cardRepository.findCardWithMaxId(userId);
    }

    public Integer countCardByIdLessThan(int id, User user){
        return cardRepository.countCardByIdLessThanAndUser(id, user);
    }
}
