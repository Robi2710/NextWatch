package models;

import java.util.List;
import java.util.LinkedList;

public class Watchlist {
    private int userId;
    private List<Integer> contentIds;
    public Watchlist(int userId) {
        this.userId = userId;
        this.contentIds = new LinkedList<>();
    }
    public void addContent(int contentId) {
        if (!contentIds.contains(contentId)) {
            contentIds.add(contentId);
        }
    }
    public boolean removeContent(int contentId) {
        return contentIds.remove(Integer.valueOf(contentId));
    }
    public boolean contains(int contentId) {
        return contentIds.contains(contentId);
    }

    public int size() {
        return contentIds.size();
    }

    public int getUserId() { return userId; }

    public List<Integer> getContentIds() { return contentIds; }
    @Override
    public String toString() {
        return "Watchlist{" +
                "userId=" + userId +
                ", contentIds=" + contentIds +
                '}';
    }
}
