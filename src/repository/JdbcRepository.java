package repository;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class JdbcRepository<T> implements CrudRepository<T> {
    protected final DatabaseConnection databaseConnection;

    protected JdbcRepository() {
        this.databaseConnection = DatabaseConnection.getInstance();
    }

    protected Optional<T> queryOne(String sql, StatementBinder binder, RowMapper<T> mapper) throws SQLException {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (binder != null) {
                binder.bind(statement);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapper.map(resultSet));
                }
                return Optional.empty();
            }
        }
    }

    protected List<T> queryMany(String sql, StatementBinder binder, RowMapper<T> mapper) throws SQLException {
        List<T> result = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (binder != null) {
                binder.bind(statement);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(mapper.map(resultSet));
                }
            }
        }
        return result;
    }

    protected int executeUpdate(String sql, StatementBinder binder) throws SQLException {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (binder != null) {
                binder.bind(statement);
            }
            return statement.executeUpdate();
        }
    }

    protected int executeInsert(String sql, StatementBinder binder) throws SQLException {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (binder != null) {
                binder.bind(statement);
            }
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        throw new SQLException("Insert did not return a generated id.");
    }
}
