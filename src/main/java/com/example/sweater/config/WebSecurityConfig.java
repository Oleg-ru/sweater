package com.example.sweater.config;

/*
Следующая конфигурация безопасности  гарантирует,
что только аутентифицированные пользователи смогут увидеть секретное приветствие (страницу):
 */

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
    private DataSource dataSource; //генерируется спринг и можем легко получить
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
        auth.jdbcAuthentication()
                .dataSource(dataSource) // нужен для того, что бы менеджер мог ходить в БД и искать там пользователей и их роли
                .passwordEncoder(NoOpPasswordEncoder.getInstance()) // шифрование пароля, что бы не хранился в явнов виде.. NoOpPasswordEncoder - используется только для тестирования
                .usersByUsernameQuery("select username,password,active from usr where username = ?")  // добавление запроса, что бы система смогла найти пользователя по его имени,
                                                                                                        // порядок важен, он опрежелен системой
                .authoritiesByUsernameQuery("select u.username, ur.roles from usr u inner join user_role ur on u.id = ur.user_id where u.username=?"); // Позволяет получить спрингу список пользователей с из ролями
                // из таблицы user с присоединненной к ней таблицей user_role соеденной через поля u.id = ur.user_id выбираем выбираем поля u.username, ur.roles
    }
}
