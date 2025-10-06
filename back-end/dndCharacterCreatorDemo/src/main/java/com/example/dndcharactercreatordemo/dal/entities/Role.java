package com.example.dndcharactercreatordemo.dal.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Roles")
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 20)
    @NotBlank(message = "Title must not be empty")
    private String title;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Role_privileges",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "privilege_id") }
    )
    private Set<Privilege> privileges;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) || Objects.equals(title, role.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, privileges);
    }
}
