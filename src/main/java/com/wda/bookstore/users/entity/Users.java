package com.wda.bookstore.users.entity;

import com.wda.bookstore.entity.Auditable;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Users extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

}

