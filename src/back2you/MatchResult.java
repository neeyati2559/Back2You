package back2you;

public class MatchResult {
    private LostItem lostItem;
    private FoundItem foundItem;
    private int score;

    public MatchResult(LostItem lostItem, FoundItem foundItem, int score) {
        this.lostItem = lostItem;
        this.foundItem = foundItem;
        this.score = score;
    }

    public LostItem getLostItem() { return lostItem; }
    public FoundItem getFoundItem() { return foundItem; }
    public int getScore() { return score; }

    public void display() {
        System.out.println(">>> Match Score: " + score + "/100");
        foundItem.displayDetails();
    }
}