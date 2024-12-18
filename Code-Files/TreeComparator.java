import java.util.Comparator;

// Author: Jacob Bacus
public class TreeComparator implements Comparator<BinaryTree<CodeTreeElement>> {
    public int compare(BinaryTree<CodeTreeElement> o1, BinaryTree<CodeTreeElement> o2) {
        if ((o1.data.myFrequency > o2.data.myFrequency)) {
            return 1;
        } else if (o1.data.myFrequency < o2.data.myFrequency) {
            return -1;
        } else {
            return 0;
        }
    }
}
