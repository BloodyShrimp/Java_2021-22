import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

public class Compression implements CompressionInterface {

    private int bits_in_word;
    // private int length_of_message = 0;
    private int unique_words = 0;
    private int exit = 0;
    private List<String> input = new LinkedList<>();
    public Map<String, Integer> word_count = new HashMap<>();
    private Map<String, String> naglowek = new HashMap<>();
    private Map<String, String> reverse_naglowek = new HashMap<>();
    // private List<String> output = new LinkedList<>();

    public void addWord(String word) {
        bits_in_word = word.length();
        // length_of_message += word.length();
        input.add(word);
        if (word_count.get(word) != null) {
            word_count.put(word, word_count.get(word) + 1);
        } else {
            word_count.put(word, 1);
            unique_words++;
        }
    }

    public void compress() {
        Map<String, Integer> descending_word_count = word_count.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        // String[] sorted_words = descending_word_count.keySet().toArray(new String[descending_word_count.size()]);
        Integer[] sorted_words_amount = descending_word_count.values()
                .toArray(new Integer[descending_word_count.size()]);

        int gain = 0;
        int loss = 0;
        int balance = 0;
        int up_to_which_word = -1;
        int optimal_key_length = -1;

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
                optimal_key_length = howManyBitsToCompress(i + 1);
                balance = new_balance;
            }

        }


        if (up_to_which_word != -1) {
            for (int i = 0; i < up_to_which_word + 1; i++) {
                naglowek.put(
                        String.format("%" + optimal_key_length + "s", Integer.toBinaryString(i)).replaceAll(" ", "0"),
                        descending_word_count.keySet().toArray()[i].toString());
            }

            reverse_naglowek = naglowek.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        }

    }

    public Map<String, String> getHeader() {
        return naglowek;
    }

    public String getWord() {
        if (reverse_naglowek.get(input.get(exit)) == null) {
            return "1" + input.get(exit++);
        } else {
            return reverse_naglowek.get(input.get(exit++));
        }

    }

    private Integer howManyBitsToCompress(int x) {
        int result = (int) Math.ceil((Math.log(x) / Math.log(2)));
        return result + 1;
    }

}
