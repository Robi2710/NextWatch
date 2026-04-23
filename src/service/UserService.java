package service;

import models.*;

import java.util.*;

public class UserService {
    private final Map<Integer, User> users;
    private final Map<Integer, Watchlist> watchlists;

    private int nextUserId = 1;

    public UserService() {
        this.users = new HashMap<>();
        this.watchlists = new HashMap<>();
    }

    public User registerUser(String username, String email, String password) {
        for (User u : users.values()) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                throw new IllegalArgumentException("Username already taken: " + username);
            }
            if (u.getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("Email already in use: " + email);
            }
        }
        User user = new User(nextUserId++, username, email, password);
        users.put(user.getId(), user);
        watchlists.put(user.getId(), new Watchlist(user.getId()));
        System.out.println("[UserService] Registered user: " + username);
        return user;
    }

    public void addToWatchlist(int userId, int contentId) {
        Watchlist wl = getWatchlistOrThrow(userId);
        wl.addContent(contentId);
        System.out.println("[UserService] Added content " + contentId + " to watchlist of user " + userId);
    }

    public void removeFromWatchlist(int userId, int contentId) {
        Watchlist wl = getWatchlistOrThrow(userId);
        boolean removed = wl.removeContent(contentId);
        if (removed) {
            System.out.println("[UserService] Removed content " + contentId + " from watchlist of user " + userId);
        } else {
            System.out.println("[UserService] Content " + contentId + " was not in watchlist of user " + userId);
        }
    }

    public void markAsWatched(int userId, int contentId) {
        User user = getUserOrThrow(userId);
        user.markAsWatched(contentId);
        Watchlist wl = watchlists.get(userId);
        if (wl != null) {
            wl.removeContent(contentId);
        }
        System.out.println("[UserService] User " + userId + " marked content " + contentId + " as watched.");
    }

    public Watchlist getWatchlist(int userId) {
        return getWatchlistOrThrow(userId);
    }

    public User getUserById(int userId) {
        return getUserOrThrow(userId);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    private User getUserOrThrow(int userId) {
        User user = users.get(userId);
        if (user == null) throw new NoSuchElementException("User not found: " + userId);
        return user;
    }

    private Watchlist getWatchlistOrThrow(int userId) {
        getUserOrThrow(userId); // valideaza ca userul exista
        return watchlists.get(userId);
    }
}