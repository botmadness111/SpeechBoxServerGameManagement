package ru.andrey.ServerGameManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tg_user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tg_id")
    private String telegramId;

    @Column(name = "username")
    private String username;

    @Column(name = "stopid")
    private Integer stopId = 0;

    @Column(name = "selectedcardid")
    private Integer selectedCardId = 0;

    @OneToMany(mappedBy = "user")
    @JsonBackReference // Эта аннотация предотвратит сериализацию обратного поля для избежания рекурсии
    private List<Card> cards = new ArrayList<>();

    public User(String telegramId, String username) {
        this.telegramId = telegramId;
        this.username = username;
    }

    public void addCard(Card card) {
        cards.add(card);
    }
}
