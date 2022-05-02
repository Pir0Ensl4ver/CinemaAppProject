/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.dal.SQL;

import hr.dal.Repo;
import hr.model.MoviePersonel;
import hr.model.Person;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author User
 */
public class DirectorRepo implements Repo<MoviePersonel>{
    
    private static final String ID_DIRECTOR = "IDMovieDirector";
    private static final String PERSON_ID = "PersonID";
    private static final String MOVIE_ID = "MovieID";

    private static final String CREATE_DIRECTOR = "{ CALL createDirector (?,?,?) }";
    private static final String UPDATE_DIRECTOR = "{ CALL updateDirector (?,?,?) }";
    private static final String DELETE_DIRECTOR = "{ CALL deleteDirector (?) }";
    private static final String SELECT_DIRECTOR = "{ CALL selectDirector (?) }";
    private static final String SELECT_DIRECTORS = "{ CALL selectDirectors }";
    
    
    private static final String ID_PERSON = "IDPerson";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String SELECT_PERSON_MOVIE = "{ CALL selectDirectorByMovie (?) }";
    private static final String DELETE_DIRECTORS_BY_MOVIE = "{ CALL deleteDirectorsByMovie (?) }";
    @Override
    public int create(MoviePersonel person) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_DIRECTOR)) {
            stmt.setInt(1, person.getPersonId());
            stmt.setInt(2, person.getMovieId());
            
            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(3);
        }
    }

    @Override
    public void create(List<MoviePersonel> persons) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_DIRECTOR)) {
            
            for (MoviePersonel person : persons) {
                stmt.setInt(1, person.getPersonId());
                stmt.setInt(2, person.getMovieId());

                stmt.registerOutParameter(3, Types.INTEGER);
                stmt.executeUpdate();
            } 
        }
    }

    @Override
    public void update(int id, MoviePersonel data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_DIRECTOR)) {
            
            stmt.setInt(1, data.getPersonId());
            stmt.setInt(2, data.getMovieId());
            
            stmt.setInt(3, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_DIRECTOR)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<MoviePersonel> select(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_DIRECTOR)) {

            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(new MoviePersonel(
                            rs.getInt(ID_DIRECTOR),
                            rs.getInt(PERSON_ID),
                            rs.getInt(MOVIE_ID)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<MoviePersonel> select() throws Exception {
        List<MoviePersonel> persons = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_DIRECTORS);
                ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                persons.add(new MoviePersonel(
                        rs.getInt(ID_DIRECTOR), 
                        rs.getInt(PERSON_ID),
                        rs.getInt(MOVIE_ID)));
            }
        }
        return persons;
    }
    
    public static List<Person> selectByMovie(int movieId) throws Exception {
        List<Person> persons = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_PERSON_MOVIE)) {

            stmt.setInt(1, movieId);
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                persons.add(new Person(
                        rs.getInt(ID_PERSON), 
                        rs.getString(FIRST_NAME),
                        rs.getString(LAST_NAME)));
                }
            }
        }
        return persons;
    }
    
    public static void deleteByMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_DIRECTORS_BY_MOVIE)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
}
