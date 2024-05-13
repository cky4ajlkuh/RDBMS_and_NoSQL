package connection;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class ApplicationDataSource {
    private static Session session;
    private static Cluster cluster;

    public static void connect() {
        cluster = Cluster.builder()
                .withClusterName("cloudinfra")
                .withPort(9042)
                .addContactPoint("25.44.48.172")
                .build();
        session = cluster.connect();
    }

    public static Session getSession() {
        return session;
    }

    public static void close() {
        session.close();
        cluster.close();
    }
}
