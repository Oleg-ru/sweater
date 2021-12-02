package com.example.sweater.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Конфигурация веб слоя, не создавать свои контролеры, это итпо шаблоны не имеющие никакой логики
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        /*
         login - это шаблон на который указывает "/login".
         Смысл security тут, создать преграду при переходе по ссылке
         на главную страницу '<a href="/main">Main page</a>' ---> "/main".
        */
        registry.addViewController("/login").setViewName("login");
    }
}
