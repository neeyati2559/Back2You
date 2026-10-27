package back2you;

import java.util.ArrayList;

public class MatchingEngine {
    private static final int MIN_SCORE = 40;

    public ArrayList<MatchResult> findMatches(LostItem lost, ArrayList<FoundItem> foundItems) {
        ArrayList<MatchResult> results = new ArrayList<>();

        for (FoundItem found : foundItems) {
            int score = lost.calculateMatchScore(found);
            if (score >= MIN_SCORE) {
                results.add(new MatchResult(lost, found, score));
            }
        }

        // Highest score first
        results.sort((a, b) -> b.getScore() - a.getScore());
        return results;
    }
}
