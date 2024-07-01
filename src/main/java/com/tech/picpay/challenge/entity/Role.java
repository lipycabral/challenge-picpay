package com.tech.picpay.challenge.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;

    public Role() {
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum Enum {

        USER(1L, "user"),
        MERCHANT(2L, "merchant");

        private Long id;
        private String name;
        Enum(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Role get() {
            return new Role(id, name);
        }
    }
}
