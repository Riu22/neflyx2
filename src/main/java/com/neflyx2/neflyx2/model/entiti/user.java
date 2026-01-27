package com.neflyx2.neflyx2.model.entiti;

import jakarta.persistence.*;

@Entity
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int user_id;
    @Column(length = 100)
    String username;
    @Column(length = 100)
    String password;
    @Column(length = 100)
    String email;

    public user(){}

    public user(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
