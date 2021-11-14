import java.util.*;

public class Main {
    public static void main(String[] args){
        // String test = "dupa Wydział Przyrodniczy Wydzaał Fizyki, Astronomii i Informatyki Stosowanej xDD aaa Wydział Fizyki, Astronomii i Informatyki Stosowanej oadjsod aj alsd jals jasdl jasl djas l lsajd ;lajslaskdjalds ";
        // String test = " z8avk#s )kv8nk, z8avk#s   )kv8nk, \t qŚC2ż9ż5kk k &9(ż25#C8nk 7CżŚż>#9[Y 7CżŚż>#9[Y W2#nA>";
        String test = " EżMłpĆź śpłżfp, lPgdFcFDpp p scvFdDĆgżfp 8gFPFJĆcT, EżMłpĆź   śpłżfp, \t lPgdFcFDpp p scvFdDĆgżfp 8gFPFJĆcTć 8gFPFJĆcTć RdĆfwJ";
        // String test = "dupa cipa Wydział Fizyki, Astronomii i Informatyki Stosowanej tokyo";
        Decrypter dekrypter = new Decrypter();
        dekrypter.setInputText(test);
        Map<Character, Character> code = new HashMap<>();
        Map<Character, Character> decode = new HashMap<>();
        code = dekrypter.getCode();
        decode = dekrypter.getDecode();
        System.out.println(dekrypter.lengths);
        System.out.println(dekrypter.words);
        System.out.println(code);
        System.out.println(decode);
    }
}
