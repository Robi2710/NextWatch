package repository;

import models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserRepository extends JdbcRepository<User> {
    private static UserRepository instance;

    private UserRepository() {
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    @Override
    public User create(User user) throws SQLException {
        String sql = "INSERT INTO app_users (username, email, password) VALUES (?, ?, ?)";
        int id = executeInsert(sql, statement -> {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
        });
        user.setId(id);
        return user;
    }

    @Override
    public Optional<User> findById(int id) throws SQLException {
        return queryOne("SELECT id, username, email, password FROM app_users WHERE id = ?",
                statement -> statement.setInt(1, id),
                resultSet -> new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ));
    }

    @Override
    public List<User> findAll() throws SQLException {
        return queryMany("SELECT id, username, email, password FROM app_users ORDER BY id",
                null,
                resultSet -> new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ));
    }

    @Override
    public boolean update(User user) throws SQLException {
        String sql = "UPDATE app_users SET username = ?, email = ?, password = ? WHERE id = ?";
        return executeUpdate(sql, statement -> {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getId());
        }) > 0;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return executeUpdate("DELETE FROM app_users WHERE id = ?",
                statement -> statement.setInt(1, id)) > 0;
    }
}
