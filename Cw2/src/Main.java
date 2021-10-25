public class Main {
    public static void main(String[] args) {
        BazaKoduDoTestowania test = new TestowanieKonkatenacjiCiagow();
        test.setLiczbaIteracji(250000);
        Tester.doTest(test);
    }
}

// String txt = "";
//         for (int i = 0; i < 500000; i++) {
//             txt += "*";
//         }