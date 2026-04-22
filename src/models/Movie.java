package models;

import java.util.List;

public class Movie extends Content{
    private int durationMinutes;

    public Movie(int id, String title, int releaseYear, String director, List<Genre> genres, double averageRating, int ratingCount, int durationMinutes) {
        super(id, title, releaseYear, director, genres, averageRating, ratingCount);
        this.durationMinutes = durationMinutes;
    }
    @Override
    public String getContentType() {
        return "Movie";
    }
    public int getDurationMinutes() {
        return durationMinutes;
    }
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
    @Override
    public String toString() {
        return super.toString() + " Duration: " + durationMinutes + " minutes";
    }
}
