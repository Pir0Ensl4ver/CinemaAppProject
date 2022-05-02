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
public interface PersonEditable {
    
    boolean editPerson(Person oldPerson, Person newPerson);
    
}
