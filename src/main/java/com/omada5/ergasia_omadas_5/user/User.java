package com.omada5.ergasia_omadas_5.user;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User{

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private LocalDate dayOfBirth;
    @Transient
    private int age;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(){}

    public User(Long id,
                String name,
                String username,
                String password,
                String email,
                LocalDate dayOfBirth
                ){
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dayOfBirth = dayOfBirth;
    }

    public User(String name,
                String username,
                String password,
                String email,
                LocalDate dayOfBirth
                ){
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dayOfBirth = dayOfBirth;
    }

    public User(String name,
                String username,
                String password,
                String email,
                LocalDate dayOfBirth,
                Set<Role> roles) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dayOfBirth = dayOfBirth;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDate getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(LocalDate dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public int getAge() {
        return Period.between(this.dayOfBirth, LocalDate.now()).getYears();
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString(){
        return "Student{"+
                "name='" + name + '\'' +
                "username='" + username + '\'' +
                "password='" + password + '\'' +
                "dayOfBirth=" + dayOfBirth +
                "age=" + age +
                "}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role){
        this.roles.add(role);
    }
}
