package repository;

import models.Movie;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MovieRepository extends JdbcRepository<Movie> {
    private static MovieRepository instance;

    private MovieRepository() {}

    public static synchronized MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    @Override
    public Movie create(Movie movie) throws SQLException {
        String sql = """
                INSERT INTO content (content_type, title, release_year, director, duration_minutes)
                VALUES ('MOVIE', ?, ?, ?, ?)
                """;
        int id = executeInsert(sql, statement -> {
            statement.setString(1, movie.getTitle());
            statement.setInt(2, movie.getReleaseYear());
            statement.setString(3, movie.getDirector());
            statement.setInt(4, movie.getDurationMinutes());
        });
        movie.setId(id);
        return movie;
    }

    @Override
    public Optional<Movie> findById(int id) throws SQLException {
        return queryOne("""
                        SELECT id, title, release_year, director, duration_minutes
                        FROM content
                        WHERE id = ? AND content_type = 'MOVIE'
                        """,
                statement -> statement.setInt(1, id),
                resultSet -> new Movie(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("release_year"),
                        resultSet.getString("director"),
                        resultSet.getInt("duration_minutes")
                ));
    }

    @Override
    public List<Movie> findAll() throws SQLException {
        return queryMany("""
                        SELECT id, title, release_year, director, duration_minutes
                        FROM content
                        WHERE content_type = 'MOVIE'
                        ORDER BY id
                        """,
                null,
                resultSet -> new Movie(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("release_year"),
                        resultSet.getString("director"),
                        resultSet.getInt("duration_minutes")
                ));
    }

    @Override
    public boolean update(Movie movie) throws SQLException {
        String sql = """
                UPDATE content
                SET title = ?, release_year = ?, director = ?, duration_minutes = ?
                WHERE id = ? AND content_type = 'MOVIE'
                """;
        return executeUpdate(sql, statement -> {
            statement.setString(1, movie.getTitle());
            statement.setInt(2, movie.getReleaseYear());
            statement.setString(3, movie.getDirector());
            statement.setInt(4, movie.getDurationMinutes());
            statement.setInt(5, movie.getId());
        }) > 0;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return executeUpdate("DELETE FROM content WHERE id = ? AND content_type = 'MOVIE'",
                statement -> statement.setInt(1, id)) > 0;
    }
}
