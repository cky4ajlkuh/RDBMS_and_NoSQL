import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class CassandraTest {

    @Container
    public static final CassandraContainer<?> cassandra
            = new CassandraContainer<>("cassandra:4.0.7").withExposedPorts(9042, 9042);

    @Test
    void givenCassandraContainer() {
        Assertions.assertTrue(cassandra.isRunning());
    }
}
