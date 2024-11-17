package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {

    public static void save(Url url) throws SQLException {
        var sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (var conn = getDataSource().getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, url.getName());
            var createdAt = LocalDateTime.now();
            preparedStatement.setTimestamp(2, Timestamp.valueOf(createdAt));

            // Выполняем обновление и получаем сгенерированные ключи
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Saving Url failed, no rows affected.");
            }

            try (var generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    url.setId(generatedKeys.getLong(1));
                    url.setCreatedAt(Timestamp.valueOf(createdAt)); // Устанавливаем созданное время
                } else {
                    throw new SQLException("DB did not return an id after saving an entity");
                }
            }
        }
    }

    public static List<Url> getEntities() throws SQLException {
        var sql = "SELECT * FROM urls";
        try (var conn = getDataSource().getConnection();
             var stmt = conn.prepareStatement(sql);
             var resultSet = stmt.executeQuery()) {

            var result = new ArrayList<Url>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var urlName = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

                var url = new Url(urlName);
                url.setId(id);
                url.setCreatedAt(Timestamp.valueOf(createdAt));

                // Получаем связанные UrlCheck для текущего Url
                var urlChecks = UrlCheckRepository.getEntitiesByParentId(id);
                url.setUrlChecks(urlChecks);
                result.add(url);
            }
            return result;
        }
    }

    public static Optional<Url> find(Long id) throws SQLException {
        var sql = "SELECT * FROM urls WHERE id = ?";
        try (var conn = getDataSource().getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return mapUrl(resultSet);
                }
                return Optional.empty();
            }
        }
    }


    public static Optional<Url> findByName(String name) throws SQLException {
        var sql = "SELECT * FROM urls WHERE name = ?";
        try (var conn = getDataSource().getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (var resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return mapUrl(resultSet);
                }
                return Optional.empty();
            }
        }
    }

    private static Optional<Url> mapUrl(ResultSet resultSet) throws SQLException {
        var id = resultSet.getLong("id");
        var urlName = resultSet.getString("name");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        var url = new Url(urlName);
        url.setId(id);
        url.setCreatedAt(Timestamp.valueOf(createdAt));

        var urlChecks = UrlCheckRepository.getEntitiesByParentId(id);
        url.setUrlChecks(urlChecks);

        return Optional.of(url);

    }

    public static boolean existsByName(String name) throws SQLException {
        var sql = "SELECT 1 FROM urls WHERE name = ?"; // Используем SELECT 1 для повышения производительности
        try (var conn = getDataSource().getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (var resultSet = stmt.executeQuery()) {
                return resultSet.next(); // Возвращаем true, если запись найдена
            }
        }
    }
}
