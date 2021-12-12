import java.util.ArrayList;
import java.util.List;

public class ParallelSearcher implements ParallelSearcherInterface {
    private static double contained_value = 0;

    public void set(HidingPlaceSupplierSupplier supplier) {
        HidingPlaceSupplier skrytka = supplier.get(0);
        List<Thread> myThreads = new ArrayList<>();
        
        while (skrytka != null) {
            contained_value = 0;

            for (int i = 0; i < skrytka.threads(); i++) {
                myThreads.add(new Thread(new Check(skrytka)));
                myThreads.get(i).start();
            }

            for (int i = 0; i < skrytka.threads(); i++) {
                try {
                    myThreads.get(i).join();
                } catch(Exception e) {}
            }

            skrytka = supplier.get(contained_value);
            myThreads.clear();
        }
    }

    private static class Check implements Runnable {
        private HidingPlaceSupplier checking_skrytka;

        public Check(HidingPlaceSupplier given_skrytka) {
            checking_skrytka = given_skrytka;
        }

        private static synchronized void getContents(HidingPlaceSupplier.HidingPlace komora) {
            contained_value += komora.openAndGetValue();
        }

        public void run() {
            HidingPlaceSupplier.HidingPlace checking_komora;
            while ((checking_komora = checking_skrytka.get()) != null) {
                if (checking_komora.isPresent()) {
                    getContents(checking_komora);
                }
            }
        }
    }
}
