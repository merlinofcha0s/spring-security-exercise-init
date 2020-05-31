package fr.plb.springsecuritydemo.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class VinylDTO implements Serializable {

    private String id;

    @NotNull
    private String songName;

    @NotNull
    private Instant releaseDate;

    private String authorId;

    public VinylDTO() {
    }

    public VinylDTO(String id, @NotNull String songName, @NotNull Instant releaseDate) {
        this.id = id;
        this.songName = songName;
        this.releaseDate = releaseDate;
    }

    public VinylDTO(String id, @NotNull String songName, @NotNull Instant releaseDate, String authorId) {
        this.id = id;
        this.songName = songName;
        this.releaseDate = releaseDate;
        this.authorId = authorId;
    }

    public VinylDTO(@NotNull String songName, @NotNull Instant releaseDate, String authorId) {
        this.songName = songName;
        this.releaseDate = releaseDate;
        this.authorId = authorId;
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

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VinylDTO vinylDTO = (VinylDTO) o;
        return Objects.equals(id, vinylDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VinylDTO{" +
                "id='" + id + '\'' +
                ", songName='" + songName + '\'' +
                ", releaseDate=" + releaseDate +
                ", authorId='" + authorId + '\'' +
                '}';
    }
}
