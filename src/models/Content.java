package models;
import java.util.List;
import java.util.ArrayList;
public abstract class Content {
    private int id;
    private String title;
    private int releaseYear;
    private String director;
    private List<Genre> genres;
    private double averageRating;
    private int ratingCount;

    public Content(int id, String title, int releaseYear, String director) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.genres = new ArrayList<>();
        this.averageRating = 0.0;
        this.ratingCount = 0;
    }
    public void addGenre(Genre genre) {
        if (!genres.contains(genre)) {
            genres.add(genre);
        }
    }

    public void updateRating(double newRating) {
        this.averageRating = (this.averageRating * this.ratingCount + newRating) / (this.ratingCount + 1);
        this.ratingCount++;
    }
    public abstract String getContentType();
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
    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", director='" + director + '\'' +
                ", genres=" + genres +
                ", averageRating=" + averageRating +
                ", ratingCount=" + ratingCount +
                '}';
    }
}
