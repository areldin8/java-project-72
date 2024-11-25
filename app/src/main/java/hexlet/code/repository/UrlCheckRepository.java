package hexlet.code.repository;

import hexlet.code.model.UrlCheck;


import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlCheckRepository extends BaseRepository {
    public static void save(UrlCheck check) throws SQLException {
        String sql = "INSERT INTO url_checks "
                + "(status_code, title, h1, description, created_at, url_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, check.getStatusCode());
            preparedStatement.setString(2, check.getTitle());
            preparedStatement.setString(3, check.getH1());
            preparedStatement.setString(4, check.getDescription());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(check.getCreatedAt()));
            preparedStatement.setLong(6, check.getUrlId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                check.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static List<UrlCheck> getEntitiesByUrlId(Long urlId) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY created_at";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, urlId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UrlCheck> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(UrlCheck.builder()
                        .id(resultSet.getLong("id"))
                        .statusCode(resultSet.getInt("status_code"))
                        .title(resultSet.getString("title"))
                        .h1(resultSet.getString("description"))
                        .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                        .urlId(resultSet.getLong("url_id"))
                        .build());
            }
            return result;
        }
    }

    public static Map<Long, LocalDateTime> getDateTimeLastChecks() throws SQLException {
        String sql = "SELECT * FROM url_checks ORDER BY created_at";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Long, LocalDateTime> result = new HashMap<>();
            while (resultSet.next()) {
                result.put(resultSet.getLong("url_id"),
                        resultSet.getTimestamp("created_at").toLocalDateTime());
            }
            return result;
        }
    }

    public static Map<Long, Integer> getStatusLastChecks() throws SQLException {
        String sql = "SELECT * FROM url_checks ORDER BY created_at";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Long, Integer> result = new HashMap<>();
            while (resultSet.next()) {
                result.put(resultSet.getLong("url_id"),
                        resultSet.getInt("status_code"));
            }
            return result;
        }
    }

}






