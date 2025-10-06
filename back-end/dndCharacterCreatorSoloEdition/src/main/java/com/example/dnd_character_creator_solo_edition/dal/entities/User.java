package com.example.dnd_character_creator_solo_edition.dal.entities;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="users")
public class User extends BaseEntity implements Serializable {
    @Column(name="username", nullable = false, length = 50)
    private String username;
    @Column(name="password", nullable = false, length = 64)
    private String password;
    @Column(name="email", nullable = false,
            length = 320, unique = true)
    private String email;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Character> characters;

    @ManyToOne
    @JoinColumn(name="role_id", referencedColumnName = "id",
    nullable = false)
    private Role role;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass()!=this.getClass()) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy oProxy
                ? oProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy thisProxy
                ? thisProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy thisProxy
                ? thisProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
