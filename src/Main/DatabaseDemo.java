package Main;

import database.DatabaseConnection;
import models.Movie;
import models.Rating;
import models.Review;
import models.Series;
import models.User;
import repository.MovieRepository;
import repository.RatingRepository;
import repository.ReviewRepository;
import repository.SeriesRepository;
import repository.UserRepository;
import service.AuditService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseDemo {
    public static void main(String[] args) {
        try {
            AuditService auditService = AuditService.getInstance();
            UserRepository userRepository = UserRepository.getInstance();
            MovieRepository movieRepository = MovieRepository.getInstance();
            SeriesRepository seriesRepository = SeriesRepository.getInstance();
            ReviewRepository reviewRepository = ReviewRepository.getInstance();
            RatingRepository ratingRepository = RatingRepository.getInstance();

            String runId = String.valueOf(System.currentTimeMillis());

            User alice = userRepository.create(new User(0, "db_alice_" + runId,
                    "db_alice_" + runId + "@mail.com", "pass123"));
            User bob = userRepository.create(new User(0, "db_bob_" + runId,
                    "db_bob_" + runId + "@mail.com", "pass456"));
            User carol = userRepository.create(new User(0, "db_carol_" + runId,
                    "db_carol_" + runId + "@mail.com", "pass789"));
            auditService.logAction("db_create_users");

            Movie arrival = movieRepository.create(new Movie(0, "Arrival", 2016, "Denis Villeneuve", 116));
            Movie matrix = movieRepository.create(new Movie(0, "The Matrix", 1999, "The Wachowskis", 136));
            Movie spiritedAway = movieRepository.create(new Movie(0, "Spirited Away", 2001, "Hayao Miyazaki", 125));
            auditService.logAction("db_create_movie");

            Series dark = seriesRepository.create(new Series(0, "Dark", 2017, "Baran bo Odar", 3, false));
            Series severance = seriesRepository.create(new Series(0, "Severance", 2022, "Dan Erickson", 2, true));
            auditService.logAction("db_create_series");

            addGenre(arrival.getId(), "SCIENCE_FICTION");
            addGenre(arrival.getId(), "DRAMA");
            addGenre(matrix.getId(), "SCIENCE_FICTION");
            addGenre(matrix.getId(), "ACTION");
            addGenre(spiritedAway.getId(), "ANIMATION");
            addGenre(spiritedAway.getId(), "FANTASY");
            addGenre(dark.getId(), "MYSTERY");
            addGenre(dark.getId(), "SCIENCE_FICTION");
            addGenre(severance.getId(), "SCIENCE_FICTION");
            addGenre(severance.getId(), "DRAMA");
            auditService.logAction("db_create_content_genres");

            addEpisode(dark.getId(), "Secrets", 1, 1, 51, "A missing child sets four families on a search for answers.");
            addEpisode(dark.getId(), "Lies", 1, 2, 45, "Jonas explores the caves and the town's hidden connections.");
            addEpisode(severance.getId(), "Good News About Hell", 1, 1, 60, "Mark starts a new chapter at Lumon.");
            addEpisode(severance.getId(), "Half Loop", 1, 2, 55, "Helly tries to quit.");
            auditService.logAction("db_create_episodes");

            addWatchlistItem(alice.getId(), dark.getId());
            addWatchlistItem(alice.getId(), severance.getId());
            addWatchlistItem(bob.getId(), arrival.getId());
            addWatchlistItem(carol.getId(), spiritedAway.getId());
            auditService.logAction("db_create_watchlist_items");

            addWatchedContent(alice.getId(), arrival.getId());
            addWatchedContent(bob.getId(), matrix.getId());
            addWatchedContent(carol.getId(), dark.getId());
            auditService.logAction("db_create_watched_content");

            Review review = reviewRepository.create(new Review(0, alice.getId(), arrival.getId(),
                    "Smart science fiction with a strong emotional core.", false));
            reviewRepository.create(new Review(0, bob.getId(), matrix.getId(),
                    "Still one of the most influential action sci-fi movies.", false));
            reviewRepository.create(new Review(0, carol.getId(), dark.getId(),
                    "Complex story, but the atmosphere is excellent.", true));
            auditService.logAction("db_create_review");

            Rating rating = ratingRepository.create(new Rating(0, alice.getId(), arrival.getId(), 9.2));
            ratingRepository.create(new Rating(0, bob.getId(), matrix.getId(), 9.7));
            ratingRepository.create(new Rating(0, carol.getId(), spiritedAway.getId(), 9.5));
            ratingRepository.create(new Rating(0, alice.getId(), severance.getId(), 8.8));
            auditService.logAction("db_create_rating");

            arrival.setDurationMinutes(117);
            movieRepository.update(arrival);
            auditService.logAction("db_update_movie");

            System.out.println("Users in database: " + userRepository.findAll().size());
            System.out.println("Movies in database: " + movieRepository.findAll().size());
            System.out.println("Series in database: " + seriesRepository.findAll().size());
            System.out.println("Genres in database: " + countRows("content_genres"));
            System.out.println("Episodes in database: " + countRows("episodes"));
            System.out.println("Watchlist items in database: " + countRows("watchlist_items"));
            System.out.println("Watched content rows in database: " + countRows("watched_content"));
            System.out.println("Reviews in database: " + reviewRepository.findAll().size());
            System.out.println("Ratings in database: " + ratingRepository.findAll().size());
            System.out.println("Review loaded: " + reviewRepository.findById(review.getId()).orElseThrow());
            System.out.println("Rating loaded: " + ratingRepository.findById(rating.getId()).orElseThrow());
        } catch (SQLException e) {
            System.err.println("Database demo failed: " + e.getMessage());
        }
    }

    private static void addGenre(int contentId, String genre) throws SQLException {
        executeInsert("""
                INSERT INTO content_genres (content_id, genre)
                VALUES (?, ?)
                ON CONFLICT DO NOTHING
                """, statement -> {
            statement.setInt(1, contentId);
            statement.setString(2, genre);
        });
    }

    private static void addEpisode(int seriesId, String title, int seasonNumber,
                                   int episodeNumber, int durationMinutes, String description) throws SQLException {
        executeInsert("""
                INSERT INTO episodes (series_id, title, season_number, episode_number, duration_minutes, description)
                VALUES (?, ?, ?, ?, ?, ?)
                ON CONFLICT DO NOTHING
                """, statement -> {
            statement.setInt(1, seriesId);
            statement.setString(2, title);
            statement.setInt(3, seasonNumber);
            statement.setInt(4, episodeNumber);
            statement.setInt(5, durationMinutes);
            statement.setString(6, description);
        });
    }

    private static void addWatchlistItem(int userId, int contentId) throws SQLException {
        executeInsert("""
                INSERT INTO watchlist_items (user_id, content_id)
                VALUES (?, ?)
                ON CONFLICT DO NOTHING
                """, statement -> {
            statement.setInt(1, userId);
            statement.setInt(2, contentId);
        });
    }

    private static void addWatchedContent(int userId, int contentId) throws SQLException {
        executeInsert("""
                INSERT INTO watched_content (user_id, content_id)
                VALUES (?, ?)
                ON CONFLICT DO NOTHING
                """, statement -> {
            statement.setInt(1, userId);
            statement.setInt(2, contentId);
        });
    }

    private static void executeInsert(String sql, SqlBinder binder) throws SQLException {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            binder.bind(statement);
            statement.executeUpdate();
        }
    }

    private static int countRows(String tableName) throws SQLException {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM " + tableName);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    @FunctionalInterface
    private interface SqlBinder {
        void bind(PreparedStatement statement) throws SQLException;
    }
}
