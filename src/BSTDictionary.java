import java.util.*;

public class BSTDictionary<K extends Comparable<K>, V> implements Iterable<BSTDictionary.Node<K, V>> {

    private class InOrderIterator implements Iterator<Node<K, V>> {
        private Node<K, V> current = root;
        private Stack<Node<K, V>> pile = new Stack<>();

        @Override
        public boolean hasNext() {
            return !(current == null && pile.empty());
        }

        @Override
        public Node<K, V> next() {
            while (current != null) {
                pile.push(current);
                current = current.left;
            }

            Node<K, V> popped = pile.pop();
            current = popped.right;
            return popped;
        }
    }

    protected static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> left, right;

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        private Node(K key, V value, Node<K, V> left, Node<K, V> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return String.format("<%s, %s>", key, value);
        }
    }

    private Node<K, V> root;

    public BSTDictionary() {
    }

    public void add(K key, V value) {
        if (root == null)
            root = new Node<>(key, value, null, null);
        else
            add(key, value, root);
    }

    private void add(K key, V value, Node<K, V> where) {
        int compare = key.compareTo(where.key);

        if (compare == 0)
            where.value = value;
        else if (compare < 0 && where.left == null)
            where.left = new Node<>(key, value, null, null);
        else if (compare > 0 && where.right == null)
            where.right = new Node<>(key, value, null, null);
        else if (compare < 0)
            add(key, value, where.left);
        else if (compare > 0)
            add(key, value, where.right);
    }

    public V getValue(K key) {
        return getValue(key, root);
    }

    private V getValue(K key, Node<K, V> where) {
        if (where == null)
            return null;
        else {
            int compare = key.compareTo(where.key);

            if (compare == 0)
                return where.value;
            else if (compare < 0)
                return getValue(key, where.left);
            else
                return getValue(key, where.right);
        }
    }

    public boolean contains(K key) {
        if (getValue(key) == null)
            return false;
        else
            return true;
    }

    @Override
    public Iterator<Node<K, V>> iterator() {
        return new InOrderIterator();
    }

    @Override
    public String toString() {
        return toString(root, " ");
    }

    private String toString(Node<K, V> where, String indent) {
        if (where == null)
            return indent + "null";
        else {
            String s = indent + where.toString() + "\n";
            s += toString(where.left, indent + " ") + "\n";
            s += toString(where.right, indent + " ");
            return s;
        }
    }

    public void printLevelWise() {
        List<List<Node>> levels = traverseLevels(root);

        for (List<Node> level : levels) {
            for (Node node : level) {
                System.out.print(String.format("<%s> ", node.getKey()));
            }
            System.out.println();
        }
    }

    private List<List<Node>> traverseLevels(Node root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Node>> levels = new LinkedList<>();

        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {
            List<Node> level = new ArrayList<>(nodes.size());
            levels.add(level);

            for (Node node : new ArrayList<>(nodes)) {
                level.add(node);
                if (node.left != null) {
                    nodes.add(node.left);
                }
                if (node.right != null) {
                    nodes.add(node.right);
                }
                nodes.poll();
            }
        }
        return levels;
    }
}