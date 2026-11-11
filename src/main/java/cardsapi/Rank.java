package cardsapi;

/**
 * Enum list of all Ranks in a standard deck of 52 cards.
 */

public enum Rank {
    
    TWO("Two", 2),
    THREE("Three", 3),
    FOUR("Four", 4),
    FIVE("Five", 5),
    SIX("Six", 6),
    SEVEN("Seven", 7),
    EIGHT("Eight", 8),
    NINE("Nine", 9),
    TEN("Ten", 10),
    JACK("Jack", 11),
    QUEEN("Queen", 12),
    KING("King", 13), 
    ACE("Ace", 14);

    private final String rankName;
    private final int rankValue;

    /**
     * Constructs a Rank enum constant.
     *
     * @param rankName the name of the Rank
     * @param rankValue the numerical value of the Rank
     */

    Rank(String rankName, int rankValue) {

        this.rankName = rankName;
        this.rankValue = rankValue;
    }

    /**
     * Returns the name of the Rank.
     *
     * @return the name of the Rank
     */

    @Override
    public String toString() {

        return rankName;
    }

    /**
     * Gets the numerical value of the Rank.
     *
     * @return the Rank value as an integer
     */

    public int getRankValue() {

        return rankValue;
    }
}  
