package repository;

import models.Review;

import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ReviewRepository extends JdbcRepository<Review> {
    private static ReviewRepository instance;

    private ReviewRepository() {
    }

    public static synchronized ReviewRepository getInstance() {
        if (instance == null) {
            instance = new ReviewRepository();
        }
        return instance;
    }

    @Override
    public Review create(Review review) throws SQLException {
        String sql = """
                INSERT INTO reviews (user_id, content_id, text, contains_spoiler, created_at)
                VALUES (?, ?, ?, ?, ?)
                """;
        int id = executeInsert(sql, statement -> {
            statement.setInt(1, review.getUserId());
            statement.setInt(2, review.getContentId());
            statement.setString(3, review.getText());
            statement.setBoolean(4, review.isContainsSpoiler());
            statement.setTimestamp(5, Timestamp.valueOf(review.getCreatedAt()));
        });
        review.setId(id);
        return review;
    }

    @Override
    public Optional<Review> findById(int id) throws SQLException {
        return queryOne("""
                        SELECT id, user_id, content_id, text, contains_spoiler, created_at
                        FROM reviews
                        WHERE id = ?
                        """,
                statement -> statement.setInt(1, id),
                this::mapReview);
    }

    @Override
    public List<Review> findAll() throws SQLException {
        return queryMany("""
                        SELECT id, user_id, content_id, text, contains_spoiler, created_at
                        FROM reviews
                        ORDER BY id
                        """,
                null,
                this::mapReview);
    }

    @Override
    public boolean update(Review review) throws SQLException {
        String sql = """
                UPDATE reviews
                SET user_id = ?, content_id = ?, text = ?, contains_spoiler = ?, created_at = ?
                WHERE id = ?
                """;
        return executeUpdate(sql, statement -> {
            statement.setInt(1, review.getUserId());
            statement.setInt(2, review.getContentId());
            statement.setString(3, review.getText());
            statement.setBoolean(4, review.isContainsSpoiler());
            statement.setTimestamp(5, Timestamp.valueOf(review.getCreatedAt()));
            statement.setInt(6, review.getId());
        }) > 0;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return executeUpdate("DELETE FROM reviews WHERE id = ?",
                statement -> statement.setInt(1, id)) > 0;
    }

    private Review mapReview(java.sql.ResultSet resultSet) throws SQLException {
        Review review = new Review(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getInt("content_id"),
                resultSet.getString("text"),
                resultSet.getBoolean("contains_spoiler")
        );
        review.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        return review;
    }
}
