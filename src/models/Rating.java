package models;

import java.time.LocalDateTime;

public class Rating {
    private int id;
    private int userId;
    private int contentId;
    private double score;
    private LocalDateTime createdAt;

    public Rating(int id, int userId, int contentId, double score) {
        if (score < 1.0 || score > 10.0) {
            throw new IllegalArgumentException("Rating score must be between 1.0 and 10.0");
        }
        this.id = id;
        this.userId = userId;
        this.contentId = contentId;
        this.score = score;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public void setScore(double score) {
        if (score < 1.0 || score > 10.0) {
            throw new IllegalArgumentException("Rating score must be between 1.0 and 10.0");
        }
        this.score = score;
    }
    public double getScore() { return score; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    @Override
    public String toString() {
        return String.format("Rating{user=%d, content=%d, score=%.1f}", userId, contentId, score);
    }
}
