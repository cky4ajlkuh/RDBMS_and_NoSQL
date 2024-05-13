package set;

import connection.ApplicationDataSource;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.List;
import java.util.Set;

public class SetRedis {

    public void pushSet(String name, Set<String> set) {
        RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
        if (!name.isEmpty() && set != null) {
            for (String s : set) {
                commands.sadd(name, s);
            }
            System.out.println("Сет = " + name + " успешно создан!");
        } else {
            throw new RuntimeException("Название сета или сет пуст!");
        }
    }

    public void add(String name, String value) {
        if (!name.isEmpty() && !value.isEmpty()) {
            RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
            if (commands.sadd(name, value) != 0) {
                System.out.println("В сет = " + name + " успешно добавлено значение = " + value);
            } else {
                throw new RuntimeException("В сете = " + name + " значение = " + value + " уже существует");
            }
        } else {
            throw new RuntimeException("Сет или входное значение пустое!");
        }
    }

    public void get(String name) {
        RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
        Set<String> set = commands.smembers(name);
        if (set != null) {
            if (!set.isEmpty()) {
                System.out.println(name + ": " + set);
            } else {
                System.out.println("Сет с именем = " + name + " пуст!");
            }
        } else {
            throw new RuntimeException("Сет с именем = " + name + " отсутствует!");
        }
    }

    public void deleteSet(String name) {
        RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
        Set<String> set = commands.smembers(name);
        if (set != null) {
            commands.del(name);
            System.out.println("Сет с именем = " + name + " удален");
        } else {
            throw new RuntimeException("Сет с именем = " + name + " отсутствует!");
        }
    }

    public void remove(String name, String value) {
        RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
        if (commands.srem(name, value) != 0) {
            System.out.println("Элемент = " + value + " успешно удален");
        } else {
            throw new RuntimeException("Элемент со значением = " + value + " не найден в сете с именем = " + name);
        }
    }
}
