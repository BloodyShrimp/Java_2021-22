import java.util.*;

public class Compression implements CompressionInterface {

    private int bits_in_word;
    private int unique_words = 0;
    private int exit_counter = 0;
    private List<String> input = new LinkedList<>();
    private List<String> output = new LinkedList<>();
    public Map<String, Integer> word_count = new HashMap<>();
    private Map<String, String> header = new HashMap<>();
    private Map<String, String> reversed_header = new HashMap<>();

    public void addWord(String word) {
        bits_in_word = word.length();
        input.add(word);
        if (!word_count.containsKey(word)) {
            unique_words++;
        }
        word_count.merge(word, 1, (a, b) -> a + b);
    }

    public void compress() {
        Map<String, Integer> descending_word_count = sortMap(word_count);
        Integer[] sorted_words_amount = descending_word_count.values()
                .toArray(new Integer[descending_word_count.size()]);
        String[] sorted_words_keys = descending_word_count.keySet().toArray(new String[descending_word_count.size()]);

        int gain = 0;
        int loss = 0;
        int balance = 0;
        int up_to_which_word = -1;

        // i + 1 = ile slow kompresuje
        for (int i = 0; i < unique_words && howManyBitsToCompress(i + 1) < bits_in_word; i++) {
            gain = 0;
            loss = 0;

            for (int j = 0; j <= i; j++) {
                gain += sorted_words_amount[j] * bits_in_word - sorted_words_amount[j] * howManyBitsToCompress(i + 1);
                loss += howManyBitsToCompress(i + 1) + bits_in_word;
            }

            for (int k = i + 1; k < sorted_words_amount.length; k++) {
                loss += sorted_words_amount[k];
            }

            int new_balance = gain - loss;

            if (new_balance > balance) {
                up_to_which_word = i;
                balance = new_balance;
            }

        }

        if (up_to_which_word != -1) {
            for (int i = 0; i < up_to_which_word + 1; i++) {
                String temp;
                if (up_to_which_word == 0) {
                    temp = Integer.toBinaryString(i);
                } else {
                    temp = String.join("", Collections.nCopies(
                            Integer.toBinaryString(up_to_which_word).length() - Integer.toBinaryString(i).length() + 1,
                            "0")) + Integer.toBinaryString(i);
                }
                header.put(temp, sorted_words_keys[i].toString());
                reversed_header.put(sorted_words_keys[i].toString(), temp);
            }
        }

        for (var i : input) {
            if (!reversed_header.containsKey(i)) {
                output.add("1" + i);
            } else {
                output.add(reversed_header.get(i));
            }
        }

    }

    public Map<String, String> getHeader() {
        return header;
    }

    public String getWord() {
        return output.get(exit_counter++);

    }

    private Integer howManyBitsToCompress(int x) {
        int result = (int) Math.ceil((Math.log(x) / Math.log(2)));
        return result + 1;
    }

    private HashMap<String, Integer> sortMap(Map<String, Integer> mapa) {
        List<Map.Entry<String, Integer>> lista = new LinkedList<Map.Entry<String, Integer>>(mapa.entrySet());

        Collections.sort(lista, (first, second) -> second.getValue().compareTo(first.getValue()));

        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> i : lista) {
            temp.put(i.getKey(), i.getValue());
        }

        return temp;
    }

}
