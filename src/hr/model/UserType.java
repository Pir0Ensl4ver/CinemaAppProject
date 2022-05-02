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
public enum UserType {
    
    ADMIN(2), USER(1);
    
    private final int type;

    private UserType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
    
    public static UserType from(int type) {
        for (UserType value : values()) {
            if(value.type == type) {
                return value;
            }
        }
        throw new RuntimeException("No such enum");
    }
    
}
