/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.dal;

import hr.dal.SQL.ActorRepo;
import hr.dal.SQL.DirectorRepo;
import hr.dal.SQL.GenerRepo;
import hr.dal.SQL.MovieRepo;
import hr.dal.SQL.PersonRepo;
import hr.dal.SQL.UserRepo;

/**
 *
 * @author User
 */
public class RepoFactory {
    
    private RepoFactory() {
    }
    
    public static Repo getMovieRepo() throws Exception {
        return new MovieRepo();
    }
    public static Repo getPersonRepo() throws Exception {
        return new PersonRepo();
    }
    public static Repo getGenreRepo() throws Exception {
        return new GenerRepo();
    }
    public static Repo getActorRepo() throws Exception {
        return new ActorRepo();
    }
    public static Repo getDirectorRepo() throws Exception {
        return new DirectorRepo();
    }
    public static Repo getUserRepo() throws Exception {
        return new UserRepo();
    }
    
}
