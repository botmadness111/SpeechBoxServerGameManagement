package ru.andrey.ServerGameManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andrey.ServerGameManagement.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByTelegramId(String tg_id);

    Optional<User> findById(int id);

}
