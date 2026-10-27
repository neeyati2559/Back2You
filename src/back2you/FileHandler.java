package back2you;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class FileHandler {
    private static final String FILE_NAME = "items.txt";
    private static final String SEPARATOR = "|";

    public static void saveItems(ArrayList<Item> items) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Item item : items) {
                writer.write(toLine(item));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Warning: could not save data - " + e.getMessage());
        }
    }

    public static ArrayList<Item> loadItems() {
        ArrayList<Item> items = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return items;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                Item item = fromLine(line);
                if (item != null) {
                    items.add(item);
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: could not read saved data - " + e.getMessage());
        }
        return items;
    }

    private static String toLine(Item item) {
        String personName;
        String personContact;

        if (item instanceof LostItem) {
            LostItem lost = (LostItem) item;
            personName = lost.getOwnerName();
            personContact = lost.getOwnerContact();
        } else {
            FoundItem found = (FoundItem) item;
            personName = found.getFinderName();
            personContact = found.getFinderContact();
        }

        return item.getType() + SEPARATOR
                + item.getId() + SEPARATOR
                + clean(item.getName()) + SEPARATOR
                + clean(item.getDescription()) + SEPARATOR
                + item.getCategory() + SEPARATOR
                + clean(item.getLocation()) + SEPARATOR
                + item.getDate() + SEPARATOR
                + clean(personName) + SEPARATOR
                + clean(personContact);
    }

    private static Item fromLine(String line) {
        try {
            String[] p = line.split("\\|", -1);
            if (p.length != 9) {
                throw new IllegalArgumentException("wrong number of fields");
            }

            String type = p[0];
            int id = Integer.parseInt(p[1]);
            String name = p[2];
            String description = p[3];
            Category category = Category.valueOf(p[4]);
            String location = p[5];
            LocalDate date = LocalDate.parse(p[6]);
            String personName = p[7];
            String contact = p[8];

            if (type.equals("LOST")) {
                return new LostItem(id, name, description, category, location, date, personName, contact);
            } else if (type.equals("FOUND")) {
                return new FoundItem(id, name, description, category, location, date, personName, contact);
            }
            throw new IllegalArgumentException("unknown type " + type);
        } catch (Exception e) {
            System.out.println("Skipping a corrupted line in the data file.");
            return null;
        }
    }


    private static String clean(String text) {
        return text.replace("|", "/").replace("\n", " ").replace("\r", " ");
    }
}