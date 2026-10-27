package back2you;

import java.util.ArrayList;

public class ItemManager {
    private ArrayList<Item> items = new ArrayList<>();
    private int nextId = 1;

    // Gives every new item a unique ID
    public int generateId() {
        return nextId++;
    }

    public void addItem(Item item) {
        items.add(item);
        System.out.println(item.getType() + " item added with ID " + item.getId());
    }

    public ArrayList<Item> getAllItems() {
        return items;
    }

    public ArrayList<LostItem> getLostItems() {
        ArrayList<LostItem> lostItems = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof LostItem) {
                lostItems.add((LostItem) item);
            }
        }
        return lostItems;
    }

    public ArrayList<FoundItem> getFoundItems() {
        ArrayList<FoundItem> foundItems = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof FoundItem) {
                foundItems.add((FoundItem) item);
            }
        }
        return foundItems;
    }

    public Item findById(int id) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public boolean deleteItem(int id) {
        Item item = findById(id);
        if (item != null) {
            items.remove(item);
            return true;
        }
        return false;
    }

    public void viewAllItems() {
        if (items.isEmpty()) {
            System.out.println("No items recorded yet.");
            return;
        }
        for (Item item : items) {
            item.displayDetails();
            System.out.println("-------------------------");
        }
    }

    public void viewLostItems() {
        ArrayList<LostItem> lostItems = getLostItems();
        if (lostItems.isEmpty()) {
            System.out.println("No lost items recorded.");
            return;
        }
        for (LostItem item : lostItems) {
            item.displayDetails();
            System.out.println("-------------------------");
        }
    }

    public void viewFoundItems() {
        ArrayList<FoundItem> foundItems = getFoundItems();
        if (foundItems.isEmpty()) {
            System.out.println("No found items recorded.");
            return;
        }
        for (FoundItem item : foundItems) {
            item.displayDetails();
            System.out.println("-------------------------");
        }
    }
    public ArrayList<Item> searchByCategory(Category category) {
        ArrayList<Item> results = new ArrayList<>();
        for (Item item : items) {
            if (item.getCategory() == category) {
                results.add(item);
            }
        }
        return results;
    }

    public ArrayList<Item> searchByLocation(String location) {
        ArrayList<Item> results = new ArrayList<>();
        String query = location.trim().toLowerCase();
        for (Item item : items) {
            if (item.getLocation().toLowerCase().contains(query)) {
                results.add(item);
            }
        }
        return results;
    }

    public ArrayList<Item> searchByKeyword(String keyword) {
        ArrayList<Item> results = new ArrayList<>();
        String query = keyword.trim().toLowerCase();
        for (Item item : items) {
            if (item.getName().toLowerCase().contains(query)
                    || item.getDescription().toLowerCase().contains(query)) {
                results.add(item);
            }
        }
        return results;
    }

    public void displayResults(ArrayList<Item> results) {
        if (results.isEmpty()) {
            System.out.println("No items found.");
            return;
        }
        System.out.println(results.size() + " item(s) found:\n");
        for (Item item : results) {
            item.displayDetails();
            System.out.println("-------------------------");
        }
    }
}
