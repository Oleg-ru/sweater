package com.example.sweater.domain;


import javax.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String text;
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER) // одному пользователю, соответствует множество сообщений
    //fetch - каждый раз получая сообщение хотим получать информацию об авторе вместе с этим сообщение сразу же
    @JoinColumn(name = "user_id") // что бы это поле в БД называлось так "user_id"
    private User author; // author message

    public Message() {
    }

    public Message(String text, String tag, User author) {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    // т.к не у всех сообщениях есть автор, если нет то выводится none
    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
