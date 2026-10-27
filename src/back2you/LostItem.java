package back2you;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LostItem extends Item implements Matchable {
    private static final Set<String> STOP_WORDS = new HashSet<>(
            Arrays.asList("the", "and", "with", "has", "for", "from", "near", "inside", "was"));

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

    @Override
    public int calculateMatchScore(Item other) {
        if (!(other instanceof FoundItem)) {
            return 0;
        }
        int score = 0;

        // 1. Category match
        if (getCategory() == other.getCategory()) {
            score += 30;
        }

        // 2. Location match
        String loc1 = getLocation().trim().toLowerCase();
        String loc2 = other.getLocation().trim().toLowerCase();
        if (loc1.equals(loc2)) {
            score += 20;
        } else if (loc1.contains(loc2) || loc2.contains(loc1)) {
            score += 10;
        }

        // 3. Date match (item must be found on or after the day it was lost)
        long days = ChronoUnit.DAYS.between(getDate(), other.getDate());
        if (days >= 0) {
            if (days <= 3) {
                score += 20;
            } else if (days <= 7) {
                score += 10;
            }
        }

        // 4. Description match
        int common = countCommonWords(
                getName() + " " + getDescription(),
                other.getName() + " " + other.getDescription());
        score += Math.min(common * 10, 30);

        return score;
    }

    private int countCommonWords(String a, String b) {
        Set<String> wordsA = extractWords(a);
        Set<String> wordsB = extractWords(b);
        wordsA.retainAll(wordsB); // keeps only words present in both
        return wordsA.size();
    }

    private Set<String> extractWords(String text) {
        Set<String> words = new HashSet<>();
        for (String word : text.toLowerCase().split("[^a-z0-9]+")) {
            if (word.length() > 2 && !STOP_WORDS.contains(word)) {
                words.add(word);
            }
        }
        return words;
    }
}