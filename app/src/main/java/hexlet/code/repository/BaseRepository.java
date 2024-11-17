package hexlet.code.repository;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;

public class BaseRepository {
    @Getter
    @Setter
    public static HikariDataSource dataSource;
}
