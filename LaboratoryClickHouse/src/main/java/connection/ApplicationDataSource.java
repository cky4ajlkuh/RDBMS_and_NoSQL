package connection;

import com.clickhouse.jdbc.ClickHouseDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ApplicationDataSource {
    public static Connection getConnection() {
        try {
            return new ClickHouseDataSource("jdbc:clickhouse://localhost:8123/default", new Properties()).getConnection("default", "1234");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
