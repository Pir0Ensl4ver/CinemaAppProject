/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@XmlRootElement(name = "moviearchive")
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieArchive {
    
    public MovieArchive() {
    }

    public MovieArchive(List<Movie> movies) {
        this.movies = movies;
    }
    
    @XmlElementWrapper
    @XmlElement(name = "movie")
    private List<Movie> movies;

    public List<Movie> getPapers() {
        return movies;
    }

    
}
