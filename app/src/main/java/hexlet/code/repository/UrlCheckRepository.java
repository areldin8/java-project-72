package hexlet.code.repository;

import hexlet.code.model.UrlCheck;


import java.sql.SQLException;
import java.sql.Statement;
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

        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, check.getStatusCode());
            preparedStatement.setString(2, check.getTitle());
            preparedStatement.setString(3, check.getH1());
            preparedStatement.setString(4, check.getDescription());

            var createdAt = LocalDateTime.now(); // Получаем текущее время
            check.setCreatedAt(createdAt); // Устанавливаем время создания в объекте UrlCheck
            preparedStatement.setTimestamp(5, Timestamp.valueOf(createdAt)); // Устанавливаем время в PreparedStatement

            preparedStatement.setLong(6, check.getUrlId());
            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                check.setId(generatedKeys.getLong(1)); // Устанавливаем сгенерированный id в объекте UrlCheck
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static List<UrlCheck> getEntitiesByUrlId(Long urlId) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY created_at";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, urlId);
            var resultSet = preparedStatement.executeQuery();
            var result = new ArrayList<UrlCheck>();

            while (resultSet.next()) {
                var checkId = resultSet.getLong("id");
                var statusCode = resultSet.getInt("status_code");
                var title = resultSet.getString("title");
                var h1 = resultSet.getString("h1");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                var checkUrlId = resultSet.getLong("url_id");

                var urlCheck = new UrlCheck(checkId, statusCode, title, h1, createdAt, checkUrlId);
                urlCheck.setId(checkId);
                result.add(urlCheck);
            }
            return result;
        }
    }

    public static Map<Long, LocalDateTime> getDateTimeLastChecks() throws SQLException {
        String sql = "SELECT url_id, created_at FROM url_checks ORDER BY created_at";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            var resultSet = preparedStatement.executeQuery();
            var result = new HashMap<Long, LocalDateTime>();

            while (resultSet.next()) {
                var urlId = resultSet.getLong("url_id");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                result.put(urlId, createdAt);
            }

            return result;
        }
    }

    public static Map<Long, Integer> getStatusLastChecks() throws SQLException {
        String sql = "SELECT url_id, status_code FROM url_checks ORDER BY created_at";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            var resultSet = preparedStatement.executeQuery();
            var result = new HashMap<Long, Integer>();

            while (resultSet.next()) {
                var urlId = resultSet.getLong("url_id");
                var statusCode = resultSet.getInt("status_code");
                result.put(urlId, statusCode);
            }

            return result;
        }
    }
}





