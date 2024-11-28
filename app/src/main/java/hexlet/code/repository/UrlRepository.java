package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {
    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";

        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, url.getName());

            var createdAt = LocalDateTime.now(); // Получаем текущее время
            url.setCreatedAt(createdAt); // Устанавливаем время создания в объекте Url
            preparedStatement.setTimestamp(2, Timestamp.valueOf(createdAt)); // Устанавливаем время в PreparedStatement

            preparedStatement.executeUpdate(); // Выполняем обновление
            var generatedKeys = preparedStatement.getGeneratedKeys(); // Получаем сгенерированные ключи
            if (generatedKeys.next()) {
                url.setId(generatedKeys.getLong(1)); // Устанавливаем сгенерированный id в объекте Url
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }

    }

    public static List<Url> getEntities() throws SQLException {
        String sql = "SELECT * FROM urls";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Url> resultList = new ArrayList<>();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

                Url url = new Url(name);
                url.setId(id);
                url.setCreatedAt(createdAt);
                resultList.add(url);
            }
            return resultList;
        }
    }

    public static Optional<Url> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM urls WHERE id = ?";

        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                var urlId = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

                var url = new Url(name); // Предполагается, что у Url есть конструктор, принимающий name
                url.setId(urlId);
                url.setCreatedAt(createdAt);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }

    public static Optional<Url> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM urls WHERE name = ?";

        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                var urlId = resultSet.getLong("id");
                var urlName = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

                var url = new Url(urlName);
                url.setId(urlId);
                url.setCreatedAt(createdAt);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }

    public static void update(Url url) throws SQLException {
        String sql = "UPDATE urls SET name = ?, created_at = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, url.getName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(url.getCreatedAt()));
            preparedStatement.setLong(3, url.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No URL found with the given ID to update.");
            }
        }
    }

    public static void delete(Long id) throws SQLException {
        String sql = "DELETE FROM urls WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No URL found with the given ID to delete.");
            }
        }
    }

    public static long count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM urls";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            return 0;
        }
    }

    public static List<Url> findAllByName(String name) throws SQLException {
        List<Url> resultList = new ArrayList<>();
        String sql = "SELECT * FROM urls WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultList.add(Url.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                        .build());
            }
        }
        return resultList;
    }

    public static boolean existsById(Long id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM urls WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1) > 0;
                }
                return false;
            }
        }
    }
}


