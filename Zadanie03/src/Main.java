import java.util.*;

public class Main {
    public static void main(String[] args){
        // String test = "dupa Wydział Przyrodniczy Wydzaał Fizyki, Astronomii i Informatyki Stosowanej xDD aaa Wydział Fizyki, Astronomii i Informatyki Stosowanej oadjsod aj alsd jals jasdl jasl djas l lsajd ;lajslaskdjalds ";
        String test = "dupa WyWział Fizyki, Astronomii i Informatyki Stosowanej oadjsod aj Wygział Fizyki, Astronomii i Informatyki Stosowanej d";
        // String test = "dupa cipa Wydział Fizyki, Astronomii i Informatyki Stosowanej tokyo";
        Decrypter dekrypter = new Decrypter();
        dekrypter.setInputText(test);
        Map<Character, Character> code = new HashMap<>();
        // Map<Character, Character> decode = new HashMap<>();
        code = dekrypter.getCode();
        // decode = dekrypter.getDecode();
        System.out.println(dekrypter.lengths);
        System.out.println(dekrypter.words);
        System.out.println(code);
        // System.out.println(decode);
    }
}
