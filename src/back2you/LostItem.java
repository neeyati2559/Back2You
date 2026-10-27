package back2you;

import java.time.LocalDate;

public class LostItem extends Item {
    private String ownerName;
    private String ownerContact;

    public LostItem(int id, String name, String description, Category category,
                    String location, LocalDate date,
                    String ownerName, String ownerContact) {
        super(id, name, description, category, location, date);
        this.ownerName = ownerName;
        this.ownerContact = ownerContact;
    }

    public String getOwnerName() { return ownerName; }
    public String getOwnerContact() { return ownerContact; }

    @Override
    public String getType() {
        return "LOST";
    }

    @Override
    public void displayDetails() {
        System.out.println("===== LOST ITEM =====");
        super.displayDetails();
        System.out.println("Owner: " + ownerName);
        System.out.println("Owner Contact: " + ownerContact);
    }
}
