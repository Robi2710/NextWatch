package models;

public class Episode {
    private int id;
    private String title;
    private int seasonNumber;
    private int episodeNumber;
    private int durationMinutes;
    private String description;

    public Episode(int id, String title, int seasonNumber, int episodeNumber, int durationMinutes, String description) {
        this.id = id;
        this.title = title;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.durationMinutes = durationMinutes;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Episode{" +
                ", title='" + title + '\'' +
                ", seasonNumber=" + seasonNumber +
                ", episodeNumber=" + episodeNumber +
                ", durationMinutes=" + durationMinutes +
                ", description='" + description + '\'' +
                '}';
    }
}
