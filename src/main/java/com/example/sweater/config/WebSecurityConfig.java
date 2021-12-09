package com.example.sweater.config;

/*
Следующая конфигурация безопасности  гарантирует,
что только аутентифицированные пользователи смогут увидеть секретное приветствие (страницу):
 */

import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;


/*
При старте приложения конфигурирует security.
Далее переходит в "configure", передает на вход объект http
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    //@Autowired
    //private DataSource dataSource; //генерируется спринг и можем легко получить
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
                    .antMatchers("/","/registration").permitAll()// <-Разрешаем полный доступ"permitAll()" на <- эти странички
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
    Будем брать пользователя из БД
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance()); // шифрование пароля, что бы не хранился в явнов виде.. NoOpPasswordEncoder - используется только для тестирования

    }
}
