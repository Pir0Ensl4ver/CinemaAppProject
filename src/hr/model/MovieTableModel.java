/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author User
 */
public class MovieTableModel extends AbstractTableModel{
    
    private List<Movie> movies;

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        fireTableDataChanged();
    }

    public MovieTableModel(List<Movie> movies) {
        this.movies = movies;
    }
    
    

    private static final String[] COLUMN_NAMES = {"IDMovie", "Title", "Original Title", "Link", "Description", "Picture path", "Published date", "Duration", "Year", "Genre", "Directors", "Actors"};

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex);
    }
    
    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return Movie.class.getDeclaredFields().length -1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return movies.get(rowIndex).getId();
            case 1:
                return movies.get(rowIndex).getTitle();
            case 2:
                return movies.get(rowIndex).getOriginalTitle();
            case 3:
                return movies.get(rowIndex).getLink();
            case 4:
                return movies.get(rowIndex).getDescription();
            case 5:
                return movies.get(rowIndex).getPicturePath();
            case 6:
                return movies.get(rowIndex).getPublisedDate().format(Movie.DATE_FORMATTER);
            case 7:
                return movies.get(rowIndex).getDuration();
            case 8:
                return movies.get(rowIndex).getYear();
            case 9:
                return movies.get(rowIndex).getGenre();
            case 10:
                return movies.get(rowIndex).getDirectors();
            case 11:
                return movies.get(rowIndex).getActors();
            
        }
        throw new RuntimeException("No such column!");
    }
    
}
