package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Review {
    private int id;
    private int userId;
    private int contentId;
    private String text;
    private LocalDateTime createdAt;
    private boolean containsSpoiler;

    public Review(int id, int userId, int contentId, String text, boolean containsSpoiler) {
        this.id = id;
        this.userId = userId;
        this.contentId = contentId;
        this.text = text;
        this.createdAt = LocalDateTime.now();
        this.containsSpoiler = containsSpoiler;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isContainsSpoiler() {
        return containsSpoiler;
    }

    public void setContainsSpoiler(boolean containsSpoiler) {
        this.containsSpoiler = containsSpoiler;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", userId=" + userId +
                ", contentId=" + contentId +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", containsSpoiler=" + containsSpoiler +
                '}';
    }
}
