import java.io.IOException;

public class Client {


    public static void main(String[] args) throws IOException {
        Client client = new Client("english.txt", 3, 5);
    }

    private Client(String file, int n, int w) throws IOException {
        String[] words;
        words = generateRandomWord(file, new DataFileReader(file, n), n, w);

        for (int i = 0; i < words.length; i++) {
            System.out.println(String.format("%s. %s", i + 1, words[i]));
        }
    }

    private String[] generateRandomWord(String file, DataFileReader dfr, int n, int w) throws IOException {
        BSTDictionary<Integer, Double> lengthsTree = dfr.getWordLengthsTree();
        BSTDictionary<String, BSTDictionary<String, Double>> letterFrequenciesTree;
        KeyGen<Integer> keyGen = new KeyGen<>();
        KeyGen<String> keyGen2 = new KeyGen<>();
        String[] words = new String[w];

        letterFrequenciesTree = dfr.createDictLetterFrequencies(file, n + 1);


        for (int j = 0; j < w; j++) {
            int length = (int) keyGen.generateKey(lengthsTree);

            StringBuilder randomWord = new StringBuilder(generateRandomLetter());

            for (int i = 0; i < length - 1; i++) {
                String substring;

                if (i < n)
                    substring = randomWord.substring(0, i + 1);
                else
                    substring = randomWord.substring(i - n + 1, i + 1);

                String next = (String) keyGen2.generateKey(letterFrequenciesTree.getValue(substring));

                randomWord.append(next);
            }
            words[j] = randomWord.toString();
        }

        return words;
    }

    private String generateRandomLetter() {
        char c = 'a';
        int rand = (int) (Math.random() * 26);

        c += (char) rand;

        return String.valueOf(c);
    }
}
