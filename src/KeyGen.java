import java.util.Iterator;

public class KeyGen<K extends Comparable<K>> {

    public KeyGen() { }

    public Object generateKey(BSTDictionary<K, Double> d) {
        if(d == null) {
            return randomLetter();
        }

        Iterator<BSTDictionary.Node<K, Double>> iterator = d.iterator();

        double rand = Math.random();

        while (iterator.hasNext()) {
            BSTDictionary.Node entry = iterator.next();

            if(rand < (double)entry.getValue()) {
                return entry.getKey();
            } else {
                rand -= (double)entry.getValue();
            }
        }

        return null;
    }

    private String randomLetter() {
        char a = 'a';
        int rand = (int)(Math.random() * 26);
        a += (char)rand;
        return String.valueOf(a);
    }
}
