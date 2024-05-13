package connection;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

public class ApplicationDataSource {

    private static RedisClient redisClient;

    private static StatefulRedisConnection<String, String> connection;

    public static StatefulRedisConnection<String, String> prepareRedisClient() {
        redisClient = RedisClient.create(RedisURI.create("localhost", 6379));
        connection = redisClient.connect();
        try {
            if (connection.isOpen())
                System.out.println("--------------------\nПодключение к Redis установлено!\n--------------------");
        } catch (Exception e) {
            closeClientAndConnection();
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeClientAndConnection() {
        redisClient.shutdown();
        connection.close();
    }
}
