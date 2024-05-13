package connection;

import org.neo4j.driver.*;
import org.neo4j.driver.Driver;

public class ApplicationDataSource {
    private static final String dbUri = "neo4j+s://d5f0690e.databases.neo4j.io:7687";
    private static final String dbUser = "";
    private static final String dbPassword = "";

    public static Driver getDriver() {
        return GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword));
    }
}
