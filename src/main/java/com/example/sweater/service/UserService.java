package com.example.sweater.service;

import com.example.sweater.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service //даем понять что это сервис, больше берем то, что это @Component
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    /*
    нужно использовать вот такой способ:
        private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    Не требуется аннотация, из-за того что спринг видит в конструкторе UserRepo и сам инжектит зависимости
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // возвращаем пользователя User
        return userRepo.findByUsername(username);
    }
}
