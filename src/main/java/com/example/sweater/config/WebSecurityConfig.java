package com.example.sweater.config;

/*
Следующая конфигурация безопасности  гарантирует,
что только аутентифицированные пользователи смогут увидеть секретное приветствие (страницу):
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


/*
При старте приложения конфигурирует security.
Далее переходит в "configure", передает на вход объект http
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /*
    Метод configure (HttpSecurity) определяет, какие URL-пути должны быть защищены, а какие нет.
    В частности, пути / и /home настроены так, чтобы не требовалось никакой аутентификации.
    Все остальные пути должны быть аутентифицированы.

    Когда пользователь успешно входит в систему, он перенаправляется на ранее запрошенную страницу,
    для которой требовалась аутентификация.
        Есть пользовательская страница "/login" для входа
    (которая задается с помощью LoginPage()), и всем разрешено ее просматривать.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()// включаем авторизацию
                    .antMatchers("/").permitAll()// <-Разрешаем полный доступ"permitAll()" на <- эти странички
                    .anyRequest().authenticated()// для всех остальных запросов "anyRequest()" - требуем авторизацию "authenticated()"
                    .and()
                .formLogin()// включаем форму логин
                    .loginPage("/login")// указываем что login находится на таком "/login" мапинге
                    .permitAll()// разрешаем этим пользоваться всем
                    .and()
                .logout() // включаем ?выйти? "logout()"
                    .permitAll(); // разрешаем им пользоваться всем

    }

    /*
    Метод UserDetailsService() настраивает хранилище пользователей в памяти с одним пользователем.
    Этому пользователю присваивается имя пользователя user, пароль password и роль USER.
        Будет выдаваться системе по требованию этот метод
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()//нужен для тестирования, он ничего не шифрует, ничего не хранит, и при каждом запске создает этого пользователя
                        .username("u")
                        .password("p")
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(user); // создает в памяти менеджер который обслуживает ученые записи
    }
}
