package repository;

import models.Rating;

import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RatingRepository extends JdbcRepository<Rating> {
    private static RatingRepository instance;

    private RatingRepository() {
    }

    public static synchronized RatingRepository getInstance() {
        if (instance == null) {
            instance = new RatingRepository();
        }
        return instance;
    }

    @Override
    public Rating create(Rating rating) throws SQLException {
        String sql = """
                INSERT INTO ratings (user_id, content_id, score, created_at)
                VALUES (?, ?, ?, ?)
                """;
        int id = executeInsert(sql, statement -> {
            statement.setInt(1, rating.getUserId());
            statement.setInt(2, rating.getContentId());
            statement.setDouble(3, rating.getScore());
            statement.setTimestamp(4, Timestamp.valueOf(rating.getCreatedAt()));
        });
        rating.setId(id);
        return rating;
    }

    @Override
    public Optional<Rating> findById(int id) throws SQLException {
        return queryOne("""
                        SELECT id, user_id, content_id, score, created_at
                        FROM ratings
                        WHERE id = ?
                        """,
                statement -> statement.setInt(1, id),
                this::mapRating);
    }

    @Override
    public List<Rating> findAll() throws SQLException {
        return queryMany("""
                        SELECT id, user_id, content_id, score, created_at
                        FROM ratings
                        ORDER BY id
                        """,
                null,
                this::mapRating);
    }

    @Override
    public boolean update(Rating rating) throws SQLException {
        String sql = """
                UPDATE ratings
                SET user_id = ?, content_id = ?, score = ?, created_at = ?
                WHERE id = ?
                """;
        return executeUpdate(sql, statement -> {
            statement.setInt(1, rating.getUserId());
            statement.setInt(2, rating.getContentId());
            statement.setDouble(3, rating.getScore());
            statement.setTimestamp(4, Timestamp.valueOf(rating.getCreatedAt()));
            statement.setInt(5, rating.getId());
        }) > 0;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return executeUpdate("DELETE FROM ratings WHERE id = ?",
                statement -> statement.setInt(1, id)) > 0;
    }

    private Rating mapRating(java.sql.ResultSet resultSet) throws SQLException {
        Rating rating = new Rating(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getInt("content_id"),
                resultSet.getDouble("score")
        );
        rating.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        return rating;
    }
}
