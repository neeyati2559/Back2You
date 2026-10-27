package back2you;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Item lost = new LostItem(1, "Black Wallet", "Leather wallet with college ID inside",
                Category.ACCESSORIES, "Library", LocalDate.of(2026, 10, 25),
                "Neeyati", "9876543210");

        Item found = new FoundItem(2, "Wallet", "Black leather wallet found near shelves",
                Category.ACCESSORIES, "Library", LocalDate.of(2026, 10, 26),
                "Rahul", "9123456780");

        lost.displayDetails();
        System.out.println();
        found.displayDetails();
    }
}