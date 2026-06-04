package repository;

import models.Series;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SeriesRepository extends JdbcRepository<Series> {
    private static SeriesRepository instance;

    private SeriesRepository() {
    }

    public static synchronized SeriesRepository getInstance() {
        if (instance == null) {
            instance = new SeriesRepository();
        }
        return instance;
    }

    @Override
    public Series create(Series series) throws SQLException {
        String sql = """
                INSERT INTO content (content_type, title, release_year, director, number_of_seasons, is_ongoing)
                VALUES ('SERIES', ?, ?, ?, ?, ?)
                """;
        int id = executeInsert(sql, statement -> {
            statement.setString(1, series.getTitle());
            statement.setInt(2, series.getReleaseYear());
            statement.setString(3, series.getDirector());
            statement.setInt(4, series.getNumberOfSeasons());
            statement.setBoolean(5, series.isOngoing());
        });
        series.setId(id);
        return series;
    }

    @Override
    public Optional<Series> findById(int id) throws SQLException {
        return queryOne("""
                        SELECT id, title, release_year, director, number_of_seasons, is_ongoing
                        FROM content
                        WHERE id = ? AND content_type = 'SERIES'
                        """,
                statement -> statement.setInt(1, id),
                resultSet -> new Series(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("release_year"),
                        resultSet.getString("director"),
                        resultSet.getInt("number_of_seasons"),
                        resultSet.getBoolean("is_ongoing")
                ));
    }

    @Override
    public List<Series> findAll() throws SQLException {
        return queryMany("""
                        SELECT id, title, release_year, director, number_of_seasons, is_ongoing
                        FROM content
                        WHERE content_type = 'SERIES'
                        ORDER BY id
                        """,
                null,
                resultSet -> new Series(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("release_year"),
                        resultSet.getString("director"),
                        resultSet.getInt("number_of_seasons"),
                        resultSet.getBoolean("is_ongoing")
                ));
    }

    @Override
    public boolean update(Series series) throws SQLException {
        String sql = """
                UPDATE content
                SET title = ?, release_year = ?, director = ?, number_of_seasons = ?, is_ongoing = ?
                WHERE id = ? AND content_type = 'SERIES'
                """;
        return executeUpdate(sql, statement -> {
            statement.setString(1, series.getTitle());
            statement.setInt(2, series.getReleaseYear());
            statement.setString(3, series.getDirector());
            statement.setInt(4, series.getNumberOfSeasons());
            statement.setBoolean(5, series.isOngoing());
            statement.setInt(6, series.getId());
        }) > 0;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return executeUpdate("DELETE FROM content WHERE id = ? AND content_type = 'SERIES'",
                statement -> statement.setInt(1, id)) > 0;
    }
}
