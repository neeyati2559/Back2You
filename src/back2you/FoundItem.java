package back2you;

import java.time.LocalDate;

public class FoundItem extends Item {
    private String finderName;
    private String finderContact;

    public FoundItem(int id, String name, String description, Category category,
                     String location, LocalDate date,
                     String finderName, String finderContact) {
        super(id, name, description, category, location, date);
        this.finderName = finderName;
        this.finderContact = finderContact;
    }

    public String getFinderName() { return finderName; }
    public String getFinderContact() { return finderContact; }

    @Override
    public String getType() {
        return "FOUND";
    }

    @Override
    public void displayDetails() {
        System.out.println("===== FOUND ITEM =====");
        super.displayDetails();
        System.out.println("Found By: " + finderName);
        System.out.println("Finder Contact: " + finderContact);
    }
}
