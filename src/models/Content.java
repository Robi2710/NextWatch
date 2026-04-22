package models;
import java.util.List;

public abstract class Content {
    private int id;
    private String title;
    private int releaseYear;
    private String director;
    private List<Genre> genres;
    private double averageRating;
    private int ratingCount;

    public Content(int id, String title, int releaseYear, String director, List<Genre> genres, double averageRating, int ratingCount) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.genres = genres;
        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
