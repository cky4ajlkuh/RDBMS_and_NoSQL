import table.PedometerColapsingMergeTree;
import table.PersonMergeTree;
import table.ProductReplacingMergeTree;

public class Main {
    public static void main(String[] args) {

        /* Merge Tree
        PersonMergeTree mergeTree = new PersonMergeTree();
        mergeTree.select();
        mergeTree.delete(1);
        mergeTree.insert(1, "Pasha", 2, "M");
        mergeTree.update(1, "Karl", 4, "M");
        */
        /* Replacing Merge Tree
        ProductReplacingMergeTree replacingMergeTree = new ProductReplacingMergeTree();
        replacingMergeTree.select();
        replacingMergeTree.selectLimitBy(1);
        replacingMergeTree.insert(4, "rice", 3);
        replacingMergeTree.insert(5, "cheesecake", 5);
        */
        /* Colapsing Merge Tree

        PedometerColapsingMergeTree colapsingMergeTree = new PedometerColapsingMergeTree();
        colapsingMergeTree.select();
        colapsingMergeTree.selectSum();*/
    }
}
