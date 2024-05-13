package jdbc.connection;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ApplicationDataSource {
    private static final PGSimpleDataSource dataSource;
    private static final Connection connection;

    static {
        dataSource = new PGSimpleDataSource();
        dataSource.setServerNames(new String[]{"localhost:5433"});
        dataSource.setUser("postgres");
        dataSource.setPassword("password");
        dataSource.setDatabaseName("MinistryOfFinanceManagement");
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
