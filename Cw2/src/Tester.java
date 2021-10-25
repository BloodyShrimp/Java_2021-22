public class Tester {
    public static void doTest(BazaKoduDoTestowania kod) {
        long start = System.currentTimeMillis();

        kod.run();

        long end = System.currentTimeMillis();

        long delta = end - start;

        System.out.println("Czas pracy " + (delta/1000) + " s");
    }
}
