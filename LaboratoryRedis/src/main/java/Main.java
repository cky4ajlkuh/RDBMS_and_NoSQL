import broker.MessageBrokerRedis;
import hash.HashRedis;
import list.ListRedis;
import object.StringRedis;
import set.SetRedis;

import java.util.*;

public class Main {
    public static void main(String[] args) {
/*
        StringRedis str = new StringRedis();
        str.pushToRedis("name", "Albert");
        str.getFromRedis("name");
        str.pushToRedis("name", "Egor");
        str.getFromRedis("name");
        str.deleteFromRedis("name");
        str.getFromRedis("name");*/
/*
        ListRedis list = new ListRedis();
        List<String> autos = new ArrayList<>();
        autos.add("2101");
        autos.add("2102");
        list.pushList("autos", autos);
        list.get("autos");
        list.add("autos","2103");
        list.get("autos");
        list.remove("autos", "2101");
        list.get("autos");
        list.deleteList("autos");
        list.get("autos");
         *//*
        SetRedis set = new SetRedis();
        Set<String> letters = new HashSet<>();
        letters.add("a");
        letters.add("b");
        letters.add("c");
        set.pushSet("сэт", letters);
        set.get("сэт");
        set.add("сэт", "d");
        set.get("сэт");
        set.remove("сэт", "a");
        set.get("сэт");
        *//*
        HashRedis hash = new HashRedis();
        hash.pushHash("name", new HashMap<>());
        hash.add("name", "1", "a");
        hash.add("name", "2", "aa");
        hash.add("name", "3", "aaa");
        hash.get("name");
        hash.remove("name", "1");
        hash.get("name");
        hash.deleteHash("name");
        hash.get("name");


        MessageBrokerRedis messageBrokerRedis = new MessageBrokerRedis("канал");
        messageBrokerRedis.addListener();
        messageBrokerRedis.addListener();
        messageBrokerRedis.addListener();
        messageBrokerRedis.publish("hello"); */
    }
}
