package back2you;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final ItemManager manager = new ItemManager();
    private static final MatchingEngine engine = new MatchingEngine();

    public static void main(String[] args) {
        manager.loadItems(FileHandler.loadItems());
        System.out.println("Welcome to Back2You! Loaded " + manager.getAllItems().size() + " saved item(s).");

        int choice;
        do {
            printMenu();
            choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> reportLostItem();
                case 2 -> reportFoundItem();
                case 3 -> manager.viewAllItems();
                case 4 -> searchItems();
                case 5 -> findMatches();
                case 6 -> deleteItem();
                case 7 -> System.out.println("Thank you for using Back2You. Goodbye!");
                default -> System.out.println("Invalid choice, try again.");
            }
        } while (choice != 7);
    }

    private static void printMenu() {
        System.out.println("\n===== Back2You: Campus Lost & Found =====");
        System.out.println("1. Report a LOST item");
        System.out.println("2. Report a FOUND item");
        System.out.println("3. View all items");
        System.out.println("4. Search items");
        System.out.println("5. Find matches for my lost item");
        System.out.println("6. Remove an item (returned / reported by mistake)");
        System.out.println("7. Exit");
    }

    private static void reportLostItem() {
        System.out.println("\n--- Report a Lost Item ---");
        String name = readText("Item name: ", "Item name");
        String description = readText("Description: ", "Description");
        Category category = readCategory();
        String location = readText("Where did you lose it? ", "Location");
        LocalDate date = readDate();
        String ownerName = readText("Your name: ", "Name");
        String contact = readContact();

        LostItem item = new LostItem(manager.generateId(), name, description,
                category, location, date, ownerName, contact);
        manager.addItem(item);
    }

    private static void reportFoundItem() {
        System.out.println("\n--- Report a Found Item ---");
        String name = readText("Item name: ", "Item name");
        String description = readText("Description: ", "Description");
        Category category = readCategory();
        String location = readText("Where did you find it? ", "Location");
        LocalDate date = readDate();
        String finderName = readText("Your name: ", "Name");
        String contact = readContact();

        FoundItem item = new FoundItem(manager.generateId(), name, description,
                category, location, date, finderName, contact);
        manager.addItem(item);
    }

    private static void searchItems() {
        System.out.println("\n--- Search Items ---");
        System.out.println("1. By category");
        System.out.println("2. By location");
        System.out.println("3. By keyword (name or description)");
        int choice = readInt("Enter choice: ");

        switch (choice) {
            case 1 -> manager.displayResults(manager.searchByCategory(readCategory()));
            case 2 -> manager.displayResults(
                    manager.searchByLocation(readText("Enter location: ", "Location")));
            case 3 -> manager.displayResults(
                    manager.searchByKeyword(readText("Enter keyword: ", "Keyword")));
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void findMatches() {
        int id = readInt("\nEnter the ID of your lost item: ");

        Item item = manager.findById(id);
        if (!(item instanceof LostItem)) {
            System.out.println("No lost item found with that ID.");
            return;
        }

        LostItem lost = (LostItem) item;
        ArrayList<MatchResult> matches = engine.findMatches(lost, manager.getFoundItems());

        if (matches.isEmpty()) {
            System.out.println("No possible matches yet. Check back later!");
            return;
        }

        System.out.println("\nPossible matches for your " + lost.getName() + ":\n");
        for (MatchResult match : matches) {
            match.display();
            System.out.println("-------------------------");
        }
        System.out.println("Contact the finder using the details above to claim your item.");
    }

    private static void deleteItem() {
        int id = readInt("\nEnter the ID of the item to remove: ");
        Item item = manager.findById(id);
        if (item == null) {
            System.out.println("No item found with that ID.");
            return;
        }

        item.displayDetails();
        System.out.print("Remove this item? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            manager.deleteItem(id);
            System.out.println("Item removed.");
        } else {
            System.out.println("Cancelled.");
        }
    }

    // ---------- Input helpers (all validation happens here) ----------

    private static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String readText(String prompt, String fieldName) {
        while (true) {
            System.out.print(prompt);
            try {
                return Validator.requireNonEmpty(sc.nextLine(), fieldName);
            } catch (InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static String readContact() {
        while (true) {
            System.out.print("Your contact (10-digit phone or email): ");
            try {
                return Validator.requireValidContact(sc.nextLine());
            } catch (InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static LocalDate readDate() {
        while (true) {
            System.out.print("Date (yyyy-mm-dd, e.g. 2026-10-25): ");
            try {
                LocalDate date = LocalDate.parse(sc.nextLine().trim());
                return Validator.requireNotFuture(date);
            } catch (DateTimeParseException e) {
                System.out.println("Error: invalid date format.");
            } catch (InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static Category readCategory() {
        Category[] categories = Category.values();
        System.out.println("Choose a category:");
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        while (true) {
            int num = readInt("Category number: ");
            if (num >= 1 && num <= categories.length) {
                return categories[num - 1];
            }
            System.out.println("Invalid number, try again.");
        }
    }
}