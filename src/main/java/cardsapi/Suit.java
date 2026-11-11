package cardsapi;

/**
 * Enum list of all 4 suits in a standard 52 card deck.
 */

public enum Suit {
    
    SPADES("Spades", 4),
    HEARTS("Hearts", 3),
    DIAMONDS("Diamonds", 2),
    CLUBS("Clubs", 1);

    private final String suitName;
    private final int suitValue;

    /**
     * Constructs a Suit enum constant.
     *
     * @param suitName the name of the Suit
     * @param suitValue the numerical value of the Suit
     */

    Suit(String suitName, int suitValue) {

        this.suitName = suitName;
        this.suitValue = suitValue;
    }

    /**
     * Returns the name of the Suit.
     *
     * @return the name of the Suit
     */

    @Override
    public String toString() {

        return suitName;
    }

    /**
     * Gets the numerical value of the Suit.
     *
     * @return the Suit value as an integer
     */

    public int getSuitValue() {

        return suitValue;
    }
}
