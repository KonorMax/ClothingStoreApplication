package ru.clothingstore.clothingstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clothingstore.clothingstore.model.User;


import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    List<User> findAll();

    User findUserById(Long id);

    User findUserByUsername(String username);

    User findByUsername(String s);

    User findByEmail(String email);

    List<User> findAllByUsernameContainingOrEmailContaining(String  username, String email);
}

