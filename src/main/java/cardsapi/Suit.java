package cardsapi;

/**
 * Enum list of all 4 suits in a standard 52 card deck
 */

public enum Suit {
    
    SPADES("Spades", 4),
    HEARTS("Hearts", 3),
    DIAMONDS("Diamonds", 2),
    CLUBS("Clubs", 1);

    private final String suitName;
    private final int suitValue;

    Suit(String suitName, int suitValue) {

        this.suitName = suitName;
        this.suitValue = suitValue;
    }

    @Override
    public String toString() {

        return suitName;
    }

    public int getSuitValue() {

        return suitValue;
    }
}
