package com.messenger.chat;

import javax.persistence.*;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }
}
