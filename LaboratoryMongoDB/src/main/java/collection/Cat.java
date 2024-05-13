package collection;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class Cat {

    private final MongoClient mongoClient;
    private MongoCollection<Document> collection;
    private final String databaseName;
    private final String collectionName;

    public Cat(String databaseName, String collectionName) {
        mongoClient = MongoClients.create();
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    public void updateCatName(String oldName, String newName) {
        collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
        collection.updateOne(eq("name", oldName), set("name", newName));
        System.out.println("Кличка кота измена с " + oldName + " на " + newName + "!");
    }

    public void updateCatWeight(double oldWeight, double newWeight) {
        collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
        collection.updateOne(eq("weight", oldWeight), set("weight", newWeight));
        System.out.println("Вес кота измена с " + oldWeight + " на " + newWeight + "!");
    }

    public void updateCatOwnerName(String oldOwnerName, String newOwnerName) {
        collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
        collection.updateOne(eq("owner.name", oldOwnerName), set("owner.name", newOwnerName));
        System.out.println("Имя хозяина изменено с " + oldOwnerName + " на " + newOwnerName + "!");
    }

    public void updateCatOwnerAddress(String oldOwnerAddress, String newOwnerAddress) {
        collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
        collection.updateOne(eq("owner.address", oldOwnerAddress), set("owner.address", newOwnerAddress));
        System.out.println("Адрес хозяина изменен с " + oldOwnerAddress + " на " + newOwnerAddress + "!");
    }

    public void deleteCatByName(String name) {
        collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
        Document document = collection.find(eq("name", name)).first();
        if (document != null) {
            collection.deleteOne(document);
            System.out.println("Кот с именем " + name + " удален из системы!");
        } else {
            closeConnection();
            System.out.println("Кот с именем " + name + " не удален из системы!");
        }
    }

    public void createCat(String name, double weight, String ownerName, String ownerAddress) {
        collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
        collection.insertOne(new Document()
                .append("_id", new ObjectId())
                .append("name", name)
                .append("weight", weight)
                .append("owner", new Document()
                        .append("name", ownerName)
                        .append("address", ownerAddress)));
        System.out.println("Кот с веденными параметрами успешно создан!");
    }

    public void catByName(String name) {
        collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
        Document document = collection.find(eq("name", name)).first();
        if (document != null) {
            System.out.println("Кот по кличке " + name + " найден:");
            System.out.println(document.toJson());
        } else {
            closeConnection();
            System.out.println("Кот по кличке " + name + " не найден!");
        }
    }

    public void catByWeight(double weight) {
        collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
        Document document = collection.find(eq("weight", weight)).first();
        if (document != null) {
            System.out.println("Кот с весом " + weight + " найден:");
            System.out.println(document.toJson());
        } else {
            closeConnection();
            System.out.println("Кот с весом " + weight + " не найден!");
        }
    }

    public void catByOwnerName(String ownerName) {
        collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
        Document document = collection.find(eq("owner.name", ownerName)).first();
        if (document != null) {
            System.out.println("Кот с хозяином " + ownerName + " найден:");
            System.out.println(document.toJson());
        } else {
            closeConnection();
            System.out.println("Кот с хозяином " + ownerName + " не найден!");
        }
    }

    public void catByOwnerAddress(String ownerAddress) {
        collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
        Document document = collection.find(eq("owner.address", ownerAddress)).first();
        if (document != null) {
            System.out.println("Кот, проживающий по адресу " + ownerAddress + ", найден:");
            System.out.println(document.toJson());
        } else {
            closeConnection();
            System.out.println("Кот, проживающий по адресу " + ownerAddress + ", не найден!");
        }
    }

    public void cats() {
        collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            System.out.println("Все коты:");
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }
    }

    public void closeConnection() {
        mongoClient.close();
    }
}
