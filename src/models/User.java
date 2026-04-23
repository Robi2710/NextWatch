package models;

import java.util.HashSet;
import java.util.Set;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private Set<Integer> watchedIds;

    public User(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.watchedIds = new HashSet<>();
    }
    public void markAsWatched(int contentId) {
        watchedIds.add(contentId);
    }
    public boolean hasWatched(int contentId) {
        return watchedIds.contains(contentId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Integer> getWatchedIds() {
        return watchedIds;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
