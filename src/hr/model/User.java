/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.model;

/**
 *
 * @author User
 */
public class User {
    
    private int id;
    private String name;
    private String password;
    private UserType type;
    

    public User() {
    }
        public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, UserType type) {
        this(name, password);
        this.type = type;
    }

    public User(int id, String name, String password, UserType type) {
        this(name, password, type);
        this.id = id;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }
    
}
