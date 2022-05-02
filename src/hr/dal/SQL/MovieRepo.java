/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.dal.SQL;

import hr.dal.Repo;
import hr.dal.RepoFactory;
import hr.model.Genre;
import hr.model.Movie;
import hr.model.MoviePersonel;
import hr.model.Person;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author User
 */
public class MovieRepo implements Repo<Movie>{
    
    private static final String ID_MOVIE = "IDMovie";
    private static final String TITLE = "Title";
    private static final String ORIGINAL_TITLE = "OriginalTitle";
    private static final String LINK = "Link";
    private static final String DESCRIPTION = "Description";
    private static final String PICTURE_PATH = "PicturePath";
    private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String DURATION = "Duration";
    private static final String YEAR = "Year";
    private static final String GENRE_ID = "GenreID";

    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie (?,?,?,?,?,?,?,?,?,?) }";
    private static final String DELETE_MOVIE = "{ CALL deleteMovie (?) }";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?) }";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";
    private static final String DELETE_MOVIES = "{ CALL deleteMovies }";
    @Override
    public int create(Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
       
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            int genreId = RepoFactory.getGenreRepo().create(movie.getGenre());
            return createMovie(stmt, movie, genreId);
        }
    }

    @Override
    public void create(List<Movie> movies) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            
            for (Movie movie : movies) {
                int genreId = RepoFactory.getGenreRepo().create(movie.getGenre());          
                createMovie(stmt, movie, genreId);
            } 
        }
    }

    @Override
    public void update(int id, Movie data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {
            
            int genreId = RepoFactory.getGenreRepo().create(data.getGenre());
            stmt.setString(1, data.getTitle());
            stmt.setString(2, data.getOriginalTitle());
            stmt.setString(3, data.getLink());
            stmt.setString(4, data.getDescription());
            stmt.setString(5, data.getPicturePath());
            stmt.setString(6, data.getPublisedDate().format(Movie.DATE_FORMATTER));
            stmt.setInt(7, data.getDuration());
            stmt.setInt(8, data.getYear());
            stmt.setInt(9, genreId);
            
            stmt.setInt(10, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Movie> select(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIE)) {

            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                        
                    List<Person> directors = DirectorRepo.selectByMovie(rs.getInt(ID_MOVIE));
                    List<Person> actors = ActorRepo.selectByMovie(rs.getInt(ID_MOVIE));
                    Optional<Genre> genre = RepoFactory.getGenreRepo().select(rs.getInt(GENRE_ID));
                    
                    if(!genre.isPresent()) {
                        throw new RuntimeException("Movie data is corrupted! Update movie data to fix this problem!");
                    }
                    return Optional.of(new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE), 
                            rs.getString(ORIGINAL_TITLE), 
                            rs.getString(LINK), 
                            directors,
                            rs.getString(DESCRIPTION),
                            rs.getString(PICTURE_PATH), 
                            LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_FORMATTER),
                            rs.getInt(DURATION),
                            rs.getInt(YEAR), 
                            genre.get(),
                            actors));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> select() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIES);
                ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                List<Person> directors = DirectorRepo.selectByMovie(rs.getInt(ID_MOVIE));
                List<Person> actors = ActorRepo.selectByMovie(rs.getInt(ID_MOVIE));
                Optional<Genre> genre = RepoFactory.getGenreRepo().select(rs.getInt(GENRE_ID));

                if(!genre.isPresent()) {
                    throw new RuntimeException("Movie data is corrupted! Update movie data to fix this problem!");
                }
                movies.add(new Movie(
                        rs.getInt(ID_MOVIE),
                        rs.getString(TITLE), 
                        rs.getString(ORIGINAL_TITLE), 
                        rs.getString(LINK), 
                        directors,
                        rs.getString(DESCRIPTION),
                        rs.getString(PICTURE_PATH), 
                        LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_FORMATTER),
                        rs.getInt(DURATION),
                        rs.getInt(YEAR), 
                        genre.get(),
                        actors));
            }
        }
        return movies;
    }
    
    public static void deleteMovies() throws SQLException {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIES)) {

            stmt.executeUpdate();
        }
    }
    
    private int createMovie(final CallableStatement stmt, Movie movie, int genreId) throws SQLException {
        stmt.setString(1, movie.getTitle());
        stmt.setString(2, movie.getOriginalTitle());
        stmt.setString(3, movie.getLink());
        stmt.setString(4, movie.getDescription());
        stmt.setString(5, movie.getPicturePath());
        //stmt.setString(6, movie.getPublisedDate().format(Movie.DATE_FORMATTER));
        stmt.setString(6, LocalDateTime.now().format(Movie.DATE_FORMATTER));
        stmt.setInt(7, movie.getDuration());
        stmt.setInt(8, movie.getYear());
        stmt.setInt(9, genreId);
        
        stmt.registerOutParameter(10, Types.INTEGER);
        stmt.executeUpdate();
        
        if(movie.getDirectors() != null){
            movie.getDirectors().forEach(d -> {
                try {
                    int personId = RepoFactory.getPersonRepo().create(d);
                    int movieID = stmt.getInt(10);
                    RepoFactory.getDirectorRepo().create(new MoviePersonel(personId, movieID));
                } catch (Exception ex) {
                    Logger.getLogger(MovieRepo.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        
        if(movie.getActors() != null){
            movie.getActors().forEach(d -> {
                try {
                    int personId = RepoFactory.getPersonRepo().create(d);
                    int movieID = stmt.getInt(10);
                    RepoFactory.getActorRepo().create(new MoviePersonel(personId, movieID));
                } catch (Exception ex) {
                    Logger.getLogger(MovieRepo.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        return stmt.getInt(10);
    }
    
}
