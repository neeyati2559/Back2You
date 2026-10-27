package back2you;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static ItemManager manager = new ItemManager();
    private static MatchingEngine engine = new MatchingEngine();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== Back2You: Campus Lost & Found =====");
            System.out.println("1. Report a LOST item");
            System.out.println("2. Report a FOUND item");
            System.out.println("3. View all items");
            System.out.println("4. Search items");
            System.out.println("5. Find matches for my lost item");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }


            switch (choice) {
                case 1 -> reportLostItem();
                case 2 -> reportFoundItem();
                case 3 -> manager.viewAllItems();
                case 4 -> searchItems();
                case 5 -> findMatches();
                case 6 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice, try again.");
            }
        } while (choice != 6);
    }

    private static void reportLostItem() {
        System.out.println("\n--- Report a Lost Item ---");
        System.out.print("Item name: ");
        String name = sc.nextLine();
        System.out.print("Description: ");
        String description = sc.nextLine();
        Category category = readCategory();
        System.out.print("Where did you lose it? ");
        String location = sc.nextLine();
        LocalDate date = readDate();
        System.out.print("Your name: ");
        String ownerName = sc.nextLine();
        System.out.print("Your contact (phone/email): ");
        String contact = sc.nextLine();

        LostItem item = new LostItem(manager.generateId(), name, description,
                category, location, date, ownerName, contact);
        manager.addItem(item);
    }

    private static void reportFoundItem() {
        System.out.println("\n--- Report a Found Item ---");
        System.out.print("Item name: ");
        String name = sc.nextLine();
        System.out.print("Description: ");
        String description = sc.nextLine();
        Category category = readCategory();
        System.out.print("Where did you find it? ");
        String location = sc.nextLine();
        LocalDate date = readDate();
        System.out.print("Your name: ");
        String finderName = sc.nextLine();
        System.out.print("Your contact (phone/email): ");
        String contact = sc.nextLine();

        FoundItem item = new FoundItem(manager.generateId(), name, description,
                category, location, date, finderName, contact);
        manager.addItem(item);
    }

    private static Category readCategory() {
        Category[] categories = Category.values();
        System.out.println("Choose a category:");
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        while (true) {
            System.out.print("Category number: ");
            try {
                int num = Integer.parseInt(sc.nextLine());
                if (num >= 1 && num <= categories.length) {
                    return categories[num - 1];
                }
            } catch (NumberFormatException e) {
                // fall through to the message below
            }
            System.out.println("Invalid number, try again.");
        }
    }

    private static LocalDate readDate() {
        while (true) {
            System.out.print("Date (yyyy-mm-dd, e.g. 2026-10-25): ");
            try {
                return LocalDate.parse(sc.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format, try again.");
            }
        }
    }
    private static void searchItems() {
        System.out.println("\n--- Search Items ---");
        System.out.println("1. By category");
        System.out.println("2. By location");
        System.out.println("3. By keyword (name or description)");
        System.out.print("Enter choice: ");

        String input = sc.nextLine();
        switch (input) {
            case "1" -> {
                Category category = readCategory();
                manager.displayResults(manager.searchByCategory(category));
            }
            case "2" -> {
                System.out.print("Enter location: ");
                manager.displayResults(manager.searchByLocation(sc.nextLine()));
            }
            case "3" -> {
                System.out.print("Enter keyword: ");
                manager.displayResults(manager.searchByKeyword(sc.nextLine()));
            }
            default -> System.out.println("Invalid choice.");
        }
    }
        private static void findMatches() {
            System.out.print("\nEnter the ID of your lost item: ");
            int id;
            try {
                id = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID.");
                return;
            }

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
}