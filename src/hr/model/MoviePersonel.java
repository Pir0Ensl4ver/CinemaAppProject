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
public class MoviePersonel {
    
    private int id;
    private int personId;
    private int movieId;

    public MoviePersonel(int personId, int movieId) {
        this.personId = personId;
        this.movieId = movieId;
    }

    public MoviePersonel(int id, int personId, int movieId) {
        this(personId, movieId);
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getId() {
        return id;
    }
    
}
