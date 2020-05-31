package fr.plb.springsecuritydemo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.Objects;

@Document(collection = "vinyl")
public class Vinyl {

    @Id
    private String id;

    @Field("song_name")
    private String songName;

    @Field("release_date")
    private Instant releaseDate;

    @DBRef
    @Field("author")
    @JsonIgnoreProperties("vinyls")
    private Author author;

    public Vinyl() {
    }

    public Vinyl(String id, String songName, Instant releaseDate) {
        this.id = id;
        this.songName = songName;
        this.releaseDate = releaseDate;
    }

    public Vinyl(String songName, Instant releaseDate, Author author) {
        this.songName = songName;
        this.releaseDate = releaseDate;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Instant getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vinyl vinyl = (Vinyl) o;
        return Objects.equals(id, vinyl.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Vinyl{" +
                "id='" + id + '\'' +
                ", songName='" + songName + '\'' +
                ", releaseDate=" + releaseDate +
                ", author=" + author +
                '}';
    }
}
