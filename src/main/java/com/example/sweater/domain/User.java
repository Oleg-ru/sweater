package com.example.sweater.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private boolean active;     //активность пользователя

    /*  @ElementCollection - помогает избавляться от формирования таблицы для хранения enum
            fetch - параметр который опр. как данные знач. будут подгружаться относит. основ. сущности
            когда загр. пользователя роли его храняться в отдельной табл.
            2 режима жадный (EAGER) и ленивый (LAZY):
                1Й При загрузке пользователя буддет подгружать все его роли. (хорош когда мало данных, ускоряет работу, но
                    потребляет больше памяти).
                2Й Подгрузит роли, когда тольуо пользователь реально обратится к этому полю. (хорош когда много записей
                    в Set ниже).
        @CollectionTable - описывает что данное поле будет храниться в отдельной таблице для которой мы не описывали мэпинг
            user_role позволяет создать табличку ролей Set<Role> которая будет соединяться с текущей таблицей через user_id
        @Enumerated - что будем хранить Enum в виде строки
     */
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    // т.к не реализуем эти методы то возвращают true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive(); // потому что это как синоним isActive()
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
}
