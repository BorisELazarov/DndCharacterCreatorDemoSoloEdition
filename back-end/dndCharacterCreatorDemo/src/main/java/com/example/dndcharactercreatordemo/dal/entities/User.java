package com.example.dndcharactercreatordemo.dal.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="users")
public class User extends BaseEntity {
    @Column(name="username", nullable = false, length = 50)
    @NotBlank(message = "Username must not be empty")
    private String username;
    @Column(name="password", nullable = false, length = 50)
    @NotBlank(message = "Password must not be empty")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Character> characters;

    @ManyToOne
    @JoinColumn(name="role_id", referencedColumnName = "id")
    @NotNull(message = "Role must not be null")
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

    @Override
    public boolean equals(Object o) {
        if (super.equals(o))
            return true;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && isDeleted
                && user.getIsDeleted();
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password,
                characters, role, isDeleted);
    }
}
