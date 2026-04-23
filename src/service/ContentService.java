package service;

import models.*;

import java.util.*;
import java.util.stream.Collectors;

public class ContentService {

    private final TreeMap<Integer, Content> contentMap;

    private final List<Review> reviews;

    private final Map<Integer, Map<Integer, Rating>> ratingsMap;

    private int nextContentId = 1;
    private int nextReviewId = 1;
    private int nextRatingId = 1;

    public ContentService() {
        this.contentMap = new TreeMap<>();
        this.reviews = new ArrayList<>();
        this.ratingsMap = new HashMap<>();
    }

    public Movie addMovie(String title, int releaseYear, String director, int durationMinutes) {
        Movie movie = new Movie(nextContentId++, title, releaseYear, director, durationMinutes);
        contentMap.put(movie.getId(), movie);
        System.out.println("[ContentService] Added movie: " + movie.getTitle());
        return movie;
    }
    
    public Series addSeries(String title, int releaseYear, String director, int seasons, boolean isOngoing) {
        Series series = new Series(nextContentId++, title, releaseYear, director, seasons, isOngoing);
        contentMap.put(series.getId(), series);
        System.out.println("[ContentService] Added series: " + series.getTitle());
        return series;
    }

    public void addGenreToContent(int contentId, Genre genre) {
        Content content = contentMap.get(contentId);
        if (content == null) throw new NoSuchElementException("Content not found: " + contentId);
        content.addGenre(genre);
    }

    public Episode addEpisodeToSeries(int seriesId, String title, int season,
                                      int epNumber, int duration, String description) {
        Content c = contentMap.get(seriesId);
        if (!(c instanceof Series)) throw new IllegalArgumentException("Content is not a series: " + seriesId);
        Series series = (Series) c;
        Episode ep = new Episode(epNumber, title, season, epNumber, duration, description);
        series.addEpisode(ep);
        return ep;
    }

    public Review addReview(int userId, int contentId, String text, boolean spoilers) {
        if (!contentMap.containsKey(contentId))
            throw new NoSuchElementException("Content not found: " + contentId);
        Review review = new Review(nextReviewId++, userId, contentId, text, spoilers);
        reviews.add(review);
        System.out.println("[ContentService] Review added by user " + userId + " for content " + contentId);
        return review;
    }

    public Rating rateContent(int userId, int contentId, double score) {
        Content content = contentMap.get(contentId);
        if (content == null) throw new NoSuchElementException("Content not found: " + contentId);

        ratingsMap.putIfAbsent(userId, new HashMap<>());
        Map<Integer, Rating> userRatings = ratingsMap.get(userId);

        if (userRatings.containsKey(contentId)) {
            throw new IllegalStateException("User " + userId + " has already rated content " + contentId);
        }

        Rating rating = new Rating(nextRatingId++, userId, contentId, score);
        userRatings.put(contentId, rating);
        content.updateRating(score);
        System.out.println("[ContentService] User " + userId + " rated \"" + content.getTitle() + "\" -> " + score);
        return rating;
    }

    public List<Content> findByGenre(Genre genre) {
        List<Content> result = new ArrayList<>();
        for (Content c : contentMap.values()) {
            if (c.getGenres().contains(genre)) {
                result.add(c);
            }
        }
        return result;
    }

    public List<Content> getTopRated(int limit) {
        return contentMap.values().stream()
                .filter(c -> c.getRatingCount() > 0)
                .sorted((a, b) -> Double.compare(b.getAverageRating(), a.getAverageRating()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Content> searchByTitle(String keyword) {
        String kw = keyword.toLowerCase();
        List<Content> result = new ArrayList<>();
        for (Content c : contentMap.values()) {
            if (c.getTitle().toLowerCase().contains(kw)) {
                result.add(c);
            }
        }
        return result;
    }

    public List<Review> getReviewsForContent(int contentId) {
        List<Review> result = new ArrayList<>();
        for (Review r : reviews) {
            if (r.getContentId() == contentId) {
                result.add(r);
            }
        }
        return result;
    }

    public Content getContentById(int id) {
        return contentMap.get(id);
    }

    public Collection<Content> getAllContent() {
        return contentMap.values();
    }

    public List<Review> getAllReviews() {
        return reviews;
    }
}