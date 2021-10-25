import java.util.*;

public class Testowanie {
    public static void main(String[] args){
        Loops petle = new Loops();
        List<Integer> lower = new ArrayList<Integer>();
        List<Integer> upper = new ArrayList<Integer>();
        upper.add(1);
        upper.add(1);
        upper.add(2);
        petle.setUpperLimits(upper);
        System.out.println(lower);
        System.out.println(upper);
        
        List<List<Integer>> wyniki = new ArrayList<List<Integer>>();
        
        wyniki = petle.getResult();
        System.out.println(wyniki);
        upper.add(0);
        System.out.println(lower);
        System.out.println(upper);
        wyniki = petle.getResult();
        System.out.println(wyniki);
    }
}
