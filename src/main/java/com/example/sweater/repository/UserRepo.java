package com.example.sweater.repository;

import com.example.sweater.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    // Метод который будет возвращать пользователя
    User findByUsername(String username);
}
