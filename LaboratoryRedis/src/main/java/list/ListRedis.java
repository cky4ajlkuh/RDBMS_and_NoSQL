package list;

import connection.ApplicationDataSource;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.List;

public class ListRedis {
    public void pushList(String name, List<String> list) {
        if (!name.isEmpty() && list != null) {
            RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
            for (String s : list) {
                commands.lpush(name, s);
            }
            System.out.println("Лист = " + name + " успешно создан!");
        } else {
            throw new RuntimeException("Название листа или лист пуст!");
        }
    }

    public void add(String name, String value) {
        if (!name.isEmpty() && !value.isEmpty()) {
            RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
            if (commands.lpush(name, value) != 0) {
                System.out.println("В лист = " + name + " успешно добавлено значение = " + value);
            } else {
                throw new RuntimeException("В листе = " + name + " значение = " + value + " уже существует");
            }
        } else {
            throw new RuntimeException("Лист или входное значение пустое!");
        }
    }

    public void get(String name) {
        RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
        List<String> list = commands.lrange(name, 0, -1);
        if (list != null) {
            if (!list.isEmpty()) {
                System.out.println(name + ": " + list);
            } else {
                System.out.println("Лист с именем = " + name + " пуст!");
            }
        } else {
            throw new RuntimeException("Лист с именем = " + name + " отсутствует!");
        }
    }

    public void deleteList(String name) {
        RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
        List<String> list = commands.lrange(name, 0, -1);
        if (list != null) {
            commands.del(name);
            System.out.println("Лист с именем = " + name + " удален");
        } else {
            throw new RuntimeException("Лист с именем = " + name + " отсутствует!");
        }
    }

    public void remove(String name, String value) {
        RedisCommands<String, String> commands = ApplicationDataSource.prepareRedisClient().sync();
        if (commands.lrem(name, 0, value) != 0) {
            System.out.println("Элемент = " + value + " успешно удален");
        } else {
            throw new RuntimeException("Элемент со значением = " + value + " не найден в листе с именем = " + name);
        }
    }
}
