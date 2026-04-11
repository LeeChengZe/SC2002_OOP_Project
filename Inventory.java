import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventory {
    private final List<Item> items = new ArrayList<>();

    // add new item to inventory
    public void addItem(Item item) {
        items.add(item);
    }

    // remove an instance of the item from inventory
    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    // Returns an unmodifiable view of the items in this inventory
    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    // Returns true only if inventory is empty
    public boolean isEmpty() {
        return items.isEmpty();
    }
}
