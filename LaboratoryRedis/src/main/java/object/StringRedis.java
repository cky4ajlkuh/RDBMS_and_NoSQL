package object;

import connection.ApplicationDataSource;
import io.lettuce.core.api.sync.RedisCommands;

public class StringRedis {
    public void pushToRedis(String key, String value) {
        RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
        if (!key.isEmpty() && !value.isEmpty()) {
            String set = commands.set(key, value);
            if (set != null) {
                System.out.println("Данные успешно загружены!");
            }
        } else {
            throw new RuntimeException("Загрузить данные с key = '" + key + "' и value = '" + value + "' не удалось!");
        }
    }

    public void getFromRedis(String key) {
        RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
        String value = commands.get(key);
        if (value != null) {
            System.out.println("key = '" + key + "'\nvalue = '" + value + "'");
        } else {
            throw new RuntimeException("Данных с ключом = '" + key + "' не обнаружено!");
        }
    }

    public void deleteFromRedis(String key) {
        RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
        if (commands.unlink(key) != 0) {
            System.out.println("Значение по ключу key = '" + key + "' успешно удалено!");
        } else {
            throw new RuntimeException("Данные не удалены!");
        }
    }
}
