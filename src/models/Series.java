package models;

import java.util.List;
import java.util.ArrayList;
public class Series extends Content{
    private int numberOfSeasons;
    private List<Episode> episodes;
    private boolean isOngoing;

    public Series(int id, String title, int releaseYear, String director, List<Genre> genres, double averageRating, int ratingCount, int numberOfSeasons, boolean isOngoing) {
        super(id, title, releaseYear, director, genres, averageRating, ratingCount);
        this.numberOfSeasons = numberOfSeasons;
        this.episodes = new ArrayList<>();
        this.isOngoing = isOngoing;
    }
    public void addEpisode(Episode episode) {
        episodes.add(episode);
    }
    public List<Episode> getEpisodesBySeason(int seasonNumber) {
        List<Episode> result = new ArrayList<>();
        for (Episode episode : episodes) {
            if (episode.getSeasonNumber() == seasonNumber) {
                result.add(episode);
            }
        }
        return result;
    }
    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }
    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }
    public List<Episode> getEpisodes() {
        return episodes;
    }

    public boolean isOngoing() {
        return isOngoing;
    }
    public void setOngoing(boolean ongoing) { isOngoing = ongoing; }
    @Override
    public String getContentType() {
        return "Series";
    }

    @Override
    public String toString() {
        return super.toString() + " Number of Seasons: " + numberOfSeasons + " Episodes: " + episodes.size();
    }
}
