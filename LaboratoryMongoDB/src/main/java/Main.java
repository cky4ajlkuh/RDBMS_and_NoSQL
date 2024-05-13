import collection.Cat;

public class Main {
    public static void main(String[] args) {
        Cat cat = new Cat("admin", "cat");
        cat.cats();
        cat.createCat("Queen", 33, "Queen", "UK");
        cat.cats();
        cat.updateCatOwnerName("Queen", "Masha");
        cat.cats();
        cat.updateCatName("Queen", "Муся");
        cat.cats();
        cat.catByOwnerName("Alex");
        cat.catByWeight(4.7);
        cat.deleteCatByName("Муся");
        cat.cats();
        cat.closeConnection();
    }
}
