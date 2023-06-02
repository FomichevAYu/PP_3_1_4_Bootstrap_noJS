package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kata.spring.boot_security.demo.models.User;

public interface UserRepository extends JpaRepository <User, Long> {
    //Такое решение позволяет не ставить транзакции на читающих методах и работают с "ленивой" загрузкой,
    //что хорошо влияет на оптимизацию приложения
    @Query("Select u from User u left join fetch u.roles where u.username=:username")
    User findByUsername(String username);
    @Query("Select u from User u left join fetch u.roles where u.id=:id")
    User findById (long id);
}
