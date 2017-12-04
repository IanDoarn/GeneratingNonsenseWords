import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class DataFileReader {

    private int wordCount = 0;
    private String filename;
    private BSTDictionary<Integer, Double> wordLengthsTree;
    private BSTDictionary<String, BSTDictionary<String, Double>> letterFrequenciesTree;

    public BSTDictionary<String, BSTDictionary<String, Double>> getLetterFrequenciesTree() {
        return letterFrequenciesTree;
    }

    public BSTDictionary<Integer, Double> getWordLengthsTree() {
        return wordLengthsTree;
    }

    public DataFileReader(String filename, int n) throws IOException {
        this.filename = filename;
        wordLengthsTree = createDictWordLengths(filename);
        letterFrequenciesTree = createDictLetterFrequencies(filename, n);
    }

    public BSTDictionary<Integer, Double> createDictWordLengths(String fileName) throws IOException {
        ArrayList<String> words = loadFile(fileName);

        BSTDictionary<Integer, Double> tree = new BSTDictionary<>();

        for(String word : words) {
            int length = word.length();
            double freq = 1.0;

            if(tree.contains(length))
                freq += tree.getValue(length);

            tree.add(length, freq);

        }

        return convertToFrequency(tree);
    }

    private ArrayList<String> loadFile(String fileName) throws IOException {
        File file = new File(fileName);
        Scanner input = new Scanner(file);
        ArrayList<String> data = new ArrayList<>();

        while(input.hasNextLine())
            data.add(input.nextLine());

        wordCount = data.size() - 1;

        return data;
    }

    private BSTDictionary<Integer, Double> convertToFrequency(BSTDictionary<Integer, Double> tree) {

        for(BSTDictionary.Node<Integer, Double> node : tree)
        {
            double value = node.getValue() / wordCount;
            value = Math.round(value * 10000.0) / 10000.0;
            node.setValue(value);
        }
        return tree;
    }

    public BSTDictionary<String, BSTDictionary<String, Double>> createDictLetterFrequencies(String fileName, int n) throws IOException {
        BSTDictionary<String, BSTDictionary<String, Double>> outer = new BSTDictionary<>();

        Scanner scanner = new Scanner(new File(fileName));

        while(scanner.hasNextLine()) {
            String s = scanner.nextLine();

            for (int i = 0; i < s.length() - n; i++) {
                String key = s.substring(i, i + n);
                outer.add(key, new BSTDictionary<>());
            }
        }

        scanner = new Scanner(new File(fileName));

        while(scanner.hasNextLine()) {
            String s = scanner.nextLine();

            for (int i = 0; i < s.length() - n; i++) {
                String oKey = s.substring(i ,i + n);
                String iKey = s.substring(i + n, i + n + 1);

                double occurrences;

                if(!outer.getValue(oKey).contains(iKey))
                    occurrences = 1;
                else
                    occurrences = outer.getValue(oKey).getValue(iKey) + 1;

                outer.getValue(oKey).add(iKey, occurrences);
            }
        }

        for(BSTDictionary.Node<String, BSTDictionary<String, Double>> outerNode : outer) {
            for(BSTDictionary.Node<String, Double> innerNode : outerNode.getValue()) {
                double value = innerNode.getValue() / wordCount;
                value = Math.round(value * 10000.0) / 10000.0;
                innerNode.setValue(value);
            }
        }

        return outer;
    }
}
