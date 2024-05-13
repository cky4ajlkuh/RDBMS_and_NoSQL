package hash;

import connection.ApplicationDataSource;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashRedis {
    public void pushHash(String name, Map<String, String> map) {
        RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
        if (!name.isEmpty() && map != null) {
            Set<String> keys = map.keySet();
            for (String key : keys) {
                commands.hset(name, key, map.get(key));
            }
            System.out.println("Хэш = " + name + " успешно создан!");
        } else {
            throw new RuntimeException("Название хэша или хэш пуст!");
        }
    }

    public void add(String name, String key, String value) {
        if (!name.isEmpty() && !value.isEmpty() && !key.isEmpty()) {
            RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
            if (commands.hset(name, key, value)) {
                System.out.println("В хэш = " + name + " успешно добавлено значение = " + value);
            } else {
                throw new RuntimeException("В хэше = " + name + " ключ = " + key + " уже существует");
            }
        } else {
            throw new RuntimeException("Хэш или входное значение пустое!");
        }
    }

    public void get(String name) {
        RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
        Map<String, String> map = commands.hgetall(name);
        if (map != null) {
            if (!map.isEmpty()) {
                System.out.println(name + ": " + map);
            } else {
                System.out.println("Хэш с именем = " + name + " пуст!");
            }
        } else {
            throw new RuntimeException("Хэш с именем = " + name + " отсутствует!");
        }
    }

    public void deleteHash(String name) {
        RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
        Map<String, String > map = commands.hgetall(name);
        if (map != null) {
            commands.del(name);
            System.out.println("Хэш с именем = " + name + " удален");
        } else {
            throw new RuntimeException("Хэш с именем = " + name + " отсутствует!");
        }
    }

    public void remove(String name, String key) {
        RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
        if (commands.hdel(name, key) != 0) {
            System.out.println("Элемент c ключом = " + key + " успешно удален");
        } else {
            throw new RuntimeException("Элемент с ключом = " + key + " не найден в хэше!");
        }
    }

}
