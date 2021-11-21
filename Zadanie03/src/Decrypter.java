import java.util.*;

//final version final
public class Decrypter implements DecrypterInterface {
    public List<String> words;
    public List<Integer> lengths;
    private List<String> wzor = Arrays
            .asList(new String[] { "Wydział", "Fizyki,", "Astronomii", "i", "Informatyki", "Stosowanej" });
    private Map<Character, Character> Code;
    private Map<Character, Character> Decode;

    public Decrypter() {
        words = new ArrayList<String>();
        lengths = new ArrayList<Integer>();
        Code = new HashMap<Character, Character>();
        Decode = new HashMap<Character, Character>();
    }

    public void setInputText(String encryptedDocument) {
        if (encryptedDocument == null) {
            Code.clear();
            Decode.clear();
            return;
        }
        words = new ArrayList<String>();
        lengths = new ArrayList<Integer>();
        words = Arrays.asList(encryptedDocument.split("\\s+"));
        for (String temp : words) {
            lengths.add(temp.length());
        }
        findCode();
    }

    public Map<Character, Character> getCode() {
        return Code;
    }

    public Map<Character, Character> getDecode() {
        return Decode;
    }

    public List<Integer> findIndexesOfChar(char znak, String slowo) {
        List<Integer> indeksy = new ArrayList<Integer>();
        for (int index = slowo.indexOf(znak); index >= 0; index = slowo.indexOf(znak, index + 1)) {
            indeksy.add(index);
        }

        return indeksy;
    }

    public int findIndexOfWFAIS(int search_start) {
        for (int i = search_start; i < lengths.size(); i++) {
            if (lengths.get(i) == 7 && lengths.get(i + 1) == 7 && lengths.get(i + 2) == 10 && lengths.get(i + 3) == 1
                    && lengths.get(i + 4) == 11 && lengths.get(i + 5) == 10) {
                return i;
            }
        }
        return -1;
    }

    public void findCode() {
        int indeks_wydzial = findIndexOfWFAIS(0);
        boolean isFinished = true;
        while (isFinished) {
            boolean isWordOK = true;
            if (indeks_wydzial == -1) {
                return;
            }
            for (int i = 0; i < 6; i++) {
                int j = 0;
                for (Character temp : words.get(i + indeks_wydzial).toCharArray()) {
                    if (findIndexesOfChar(temp, words.get(i + indeks_wydzial))
                            .equals(findIndexesOfChar(wzor.get(i).charAt(j), wzor.get(i)))) {
                        if ((temp == ',' && wzor.get(i).charAt(j) != ',')
                                || (temp != ',' && wzor.get(i).charAt(j) == ',')) {
                            isWordOK = false;
                            break;
                        } else {
                            if (!Decode.containsKey(temp)) {
                                Code.put((Character) wzor.get(i).charAt(j), temp);
                                Decode.put(temp, (Character) wzor.get(i).charAt(j));
                            }
                        }
                    } else {
                        isWordOK = false;
                        break;
                    }
                    j++;
                }
                if (!isWordOK) {
                    break;
                }
            }
            if (Code.size() != Decode.size()) {
                isWordOK = false;
            }
            if (isWordOK) {
                isFinished = false;
            } else {
                Code.clear();
                Decode.clear();
                indeks_wydzial = findIndexOfWFAIS(indeks_wydzial + 1);
            }
        }
        Code.remove(',');
        Decode.remove(',');
    }
}
