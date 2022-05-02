/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.parser;


import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.model.Genre;
import hr.model.Movie;
import hr.model.Person;
import hr.algebra.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author User
 */
public class MovieParser {
    
    private static final String RSS_URL = "https://www.blitz-cinestar.hr/rss.aspx?najava=2";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";

    private MovieParser() {
    }
    
    public static List<Movie> parse() throws IOException, XMLStreamException {
        List<Movie> movies = new ArrayList<>();
        
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL);
        try(InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);
            
            StartElement startElement = null;
            Movie movie = null;
            Optional<TagType> tagType = Optional.empty();
            
            
            while(reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch(event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        startElement = event.asStartElement();
                        String gName = startElement.getName().getLocalPart();
                        tagType = TagType.from(gName);
                        if(tagType.isPresent() && tagType.get() == TagType.ITEM){
                            movie = new Movie();
                            movies.add(movie);
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        String data = characters.getData().trim();
                        if(tagType.isPresent()) {
                            switch(tagType.get()) {
                                case TITLE:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setTitle(data);
                                    }
                                    break;
                                case PUB_DATE:
                                    if (movie != null && !data.isEmpty()) {
                                        LocalDateTime publishedDate = LocalDateTime.parse(data, DateTimeFormatter.RFC_1123_DATE_TIME);
                                        movie.setPublisedDate(publishedDate);
                                    }
                                    break;
                                case DESCRIPTION:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setDescription(data);
                                    }
                                    break;
                                case ORIG_TITLE:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setOriginalTitle(data);
                                    }
                                    break;
                                case DIRECTOR:
                                    if (movie != null && !data.isEmpty()) {
                                        List<Person> directors = new ArrayList<>();
                                        String[] stringdata = data.split(", ");
                                        Stream.of(stringdata).forEach(d -> directors.add(new Person(
                                                d.substring(0, d.indexOf(" ")), 
                                                d.substring(d.indexOf(" ")+1))
                                        ));
                                        movie.setDirectors(directors);
                                    }
                                    break;
                                case ACTOR:
                                    if (movie != null && !data.isEmpty()) {
                                        List<Person> actors = new ArrayList<>();
                                        String[] stringdata = data.split(", ");
                                        Stream.of(stringdata).forEach(d -> actors.add(new Person(
                                                d.substring(0, d.indexOf("")), 
                                                d.substring(d.indexOf(" ")+1))
                                        )); 
                                        movie.setActors(actors);
                                    }
                                    break;
                                case DURATION:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setDuration(Integer.parseInt(data));
                                    }
                                    break;
                                case YEAR:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setYear(Integer.parseInt(data));
                                    }
                                    break;
                                case GENRE:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setGenre(new Genre(data));
                                    }
                                    break;
                                case LINK:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setLink(data);
                                    }
                                    break;
                                case POSTER:
                                    if(movie != null && !data.isEmpty()) {
                                        handlePicture(movie, data);
                                    }
                                    break;
                            }
                        }
                        break;
                }
            }
        }
        return movies;
    }
    
    private static void handlePicture(Movie movie, String pictureUrl) throws IOException {
        String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));
        if(ext.length() > 4) {
            ext = EXT;
        }
        String pictureName = UUID.randomUUID() + ext;
        String picturePath = DIR + File.separator + pictureName;
        FileUtils.copyFromUrl(pictureUrl, picturePath);
        movie.setPicturePath(picturePath);
    }
    
    private enum TagType {
        ITEM("item"),
        TITLE("title"),
        PUB_DATE("pubDate"),
        DESCRIPTION("description"),
        ORIG_TITLE("orignaziv"),
        DIRECTOR("redatelj"),
        ACTOR("glumci"),
        DURATION("trajanje"),
        YEAR("godina"),
        GENRE("zanr"),
        POSTER("plakat"),
        LINK("link");
        
        private final String name;

        private TagType(String name) {
            this.name = name;
        }
        
        public static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if(value.name.equals(name)) {
                    return Optional.of(value);
                }   
            }
            return Optional.empty();
        }
        
    }
}
