package back2you;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Item item = new Item(1, "Black Wallet", "Leather wallet with college ID inside",
                "Library", LocalDate.of(2026, 10, 25));
        item.displayDetails();
    }
}