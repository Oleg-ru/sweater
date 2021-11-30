package com.example.sweater.repository;

import com.example.sweater.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//Репозиторий для хранения сообщений
// This will be AUTO IMPLEMENTED by Spring into a Bean called messageRepository
// CRUD refers Create, Read, Update, Delete

public interface MessageRepository extends CrudRepository<Message, Integer> {

    //Поиск по тегу в БД
    List<Message> findByTag(String tag);
}
