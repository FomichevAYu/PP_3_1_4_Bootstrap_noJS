package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserRepository extends JpaRepository <User, Long> {

    @Query("Select u from User u left join fetch u.roles where u.username=:username")
    User findByUsername(String username);
    @Query("Select u from User u left join fetch u.roles where u.id=:id")
    User findById (long id);
    @EntityGraph(attributePaths = "roles")
    List<User> findAll();
}
