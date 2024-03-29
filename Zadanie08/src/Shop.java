import java.util.HashMap;
import java.util.Map;

public class Shop implements ShopInterface {
    Map<String, Integer> current_stock = new HashMap<>();
    Map<String, Object> locks = new HashMap<>();
    Object maps_lock = new Object();

    public void delivery(Map<String, Integer> goods) {
        for (Map.Entry<String, Integer> entry : goods.entrySet()) {
            synchronized (maps_lock) {
                locks.putIfAbsent(entry.getKey(), new Object());
            }
            synchronized (locks.get(entry.getKey())) {
                synchronized (maps_lock) {
                    current_stock.merge(entry.getKey(), entry.getValue(), (a, b) -> a + b);
                    locks.get(entry.getKey()).notifyAll();
                }
            }
        }
    }

    public boolean purchase(String productName, int quantity) {
        synchronized (maps_lock) {
            locks.putIfAbsent(productName, new Object());
        }
        synchronized (locks.get(productName)) {
            if (!current_stock.containsKey(productName) || !(current_stock.get(productName) >= quantity)) {
                try {
                    locks.get(productName).wait();
                } catch (Exception e) {
                }
            }

            if (current_stock.containsKey(productName) && current_stock.get(productName) >= quantity) {
                synchronized (maps_lock) {
                    current_stock.put(productName, current_stock.get(productName) - quantity);
                }
                return true;
            } else {
                return false;
            }
        }
    }

    public Map<String, Integer> stock() {
        return current_stock;
    }
}
