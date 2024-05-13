package broker;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;

public class MessageBrokerRedis {

    private final RedisClient client = RedisClient.create(RedisURI.create("localhost", 6379));
    private final StatefulRedisPubSubConnection<String, String> connection = client.connectPubSub();
    StatefulRedisConnection<String, String> sender = client.connect();
    private final String channel;

    public MessageBrokerRedis(String channel) {
        this.channel = channel;
        connection.sync().subscribe(channel);
    }

    public void addListener() {
        RedisPubSubListener<String, String> listener = new RedisPubSubAdapter<>() {
            @Override
            public void message(String channel, String message) {
                System.out.printf("Channel: %s, Message: %s%n", channel, message);
            }
        };
        connection.addListener(listener);
    }

    public void publish(String message) {
        sender.sync().publish(channel, message);
    }
}
