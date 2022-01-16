import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.Comparator;

public class Kasjer implements KasjerInterface {
    List<Pieniadz> kasa = new LinkedList<>();
    RozmieniaczInterface rozmieniacz;

    @Override
    public List<Pieniadz> rozlicz(int cena, List<Pieniadz> pieniadze) {
        List<Pieniadz> reszta = new LinkedList<>();
        List<Pieniadz> given = new LinkedList<>(pieniadze);
        List<Pieniadz> toBeRemoved = new LinkedList<>();
        int ile_rozmienialnych = 0;
        int suma = 0;
        for (var coin : given) {
            if(coin.czyMozeBycRozmieniony()) {
                ile_rozmienialnych += coin.wartosc();
            }
            suma += coin.wartosc();
        }
        int to_be_given = suma - cena;

        kasa.addAll(given);
        allToOne(kasa);
        sortRosnaco(kasa);
        sortRosnaco(given);

        if(ile_rozmienialnych >= to_be_given) {
            for(int i = 0; i < to_be_given; i++) {
                reszta.add(kasa.get(0));
                kasa.remove(0);
            }
        } else {
            while(to_be_given > 0) {
                reszta.add(kasa.get(0));
                to_be_given -= kasa.get(0).wartosc();
                kasa.remove(0);
            }
            for(var coin : given) {
                if(!coin.czyMozeBycRozmieniony() && coin.wartosc() > to_be_given) {
                    reszta.add(coin);
                    toBeRemoved.add(coin);
                    break;
                }
            }
            kasa.removeAll(toBeRemoved);
            toBeRemoved.clear();
        }

        return reszta;
    }

    public void sortRosnaco(List<Pieniadz> list_of_money) {
        Collections.sort(list_of_money, new SortByValue());
        Collections.sort(list_of_money, new SortByRozmienialnosc());
    }

    class SortByRozmienialnosc implements Comparator<Pieniadz> {
        @Override
        public int compare(Pieniadz arg0, Pieniadz arg1) {
            int comparing = 0;
            if(arg0.czyMozeBycRozmieniony() && arg1.czyMozeBycRozmieniony()) {
                comparing = 0;
            } else if(!arg0.czyMozeBycRozmieniony() && arg1.czyMozeBycRozmieniony()) {
                comparing = 1;
            } else if(arg0.czyMozeBycRozmieniony() && !arg1.czyMozeBycRozmieniony()) {
                comparing = -1;
            }
            return comparing;
        }
    }

    class SortByValue implements Comparator<Pieniadz> {
        @Override
        public int compare(Pieniadz arg0, Pieniadz arg1) {
            return arg0.wartosc() - arg1.wartosc();
        }
    }

    public void allToOne(List<Pieniadz> input) {
        List<Pieniadz> toBeAdded = new LinkedList<>();
        List<Pieniadz> toBeDeleted = new LinkedList<>();
        boolean is_all_exchanged = false;
        while (true) {
            for (var coin : input) {
                if (coin.czyMozeBycRozmieniony() && coin.wartosc() > 1) {
                    var exchanged = rozmieniacz.rozmien(coin);
                    toBeAdded.addAll(exchanged);
                    toBeDeleted.add(coin);
                }
            }
            input.removeAll(toBeDeleted);
            input.addAll(toBeAdded);
            is_all_exchanged = true;
            for (var coin : input) {
                if (coin.czyMozeBycRozmieniony() && coin.wartosc() > 1) {
                    is_all_exchanged = false;
                }
            }
            if(is_all_exchanged) {
                break;
            }
            toBeAdded.clear();
            toBeDeleted.clear();
        }
    }

    @Override
    public List<Pieniadz> stanKasy() {
        return kasa;
    }

    @Override
    public void dostępDoRozmieniacza(RozmieniaczInterface rozmieniacz) {
        this.rozmieniacz = rozmieniacz;
    }

    @Override
    public void dostępDoPoczątkowegoStanuKasy(Supplier<Pieniadz> dostawca) {
        Pieniadz pieniadz = dostawca.get();
        while (pieniadz != null) {
            kasa.add(pieniadz);
            pieniadz = dostawca.get();
        }

    }

}
