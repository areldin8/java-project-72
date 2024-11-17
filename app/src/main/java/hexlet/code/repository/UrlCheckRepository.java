package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UrlCheckRepository extends BaseRepository {
    public static void save(UrlCheck urlCheck) throws SQLException {
        var sql = """
                INSERT INTO url_checks (status_code, title, h1, description, url_id, created_at)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (var conn = getDataSource().getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, urlCheck.getStatusCode());
            preparedStatement.setString(2, urlCheck.getTitle());
            preparedStatement.setString(3, urlCheck.getH1());
            preparedStatement.setString(4, urlCheck.getDescription());
            preparedStatement.setLong(5, urlCheck.getUrlId());
            var createdAt = LocalDateTime.now();
            preparedStatement.setTimestamp(6, Timestamp.valueOf(createdAt));

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Saving UrlCheck failed, no rows affected.");
            }

            try (var generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    urlCheck.setId(generatedKeys.getLong(1));
                    urlCheck.setCreatedAt(Timestamp.valueOf(createdAt)); // Устанавливаем созданное время
                } else {
                    throw new SQLException("DB did not return an id after saving an entity");
                }
            }
        }
    }

    public static List<UrlCheck> getEntitiesByParentId(long urlId) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY id DESC";

        try (var conn = getDataSource().getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            var resultSet = stmt.executeQuery();
            var result = new ArrayList<UrlCheck>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

                var urlCheck = new UrlCheck(
                        resultSet.getInt("status_code"),
                        resultSet.getString("title"),
                        resultSet.getString("h1"),
                        resultSet.getString("description"),
                        urlId // Устанавливаем urlId
                );
                urlCheck.setId(id);
                urlCheck.setCreatedAt(Timestamp.valueOf(createdAt)); // Устанавливаем созданное время
                result.add(urlCheck);
            }
            return result;
        }
    }
}






