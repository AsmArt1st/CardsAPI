package cardsapi;

/**
 * Enum list of both Jokers in a standard deck of playing cards.
 */

public enum Joker {

    RED("Red Joker", 1),
    BLACK("Black Joker", 2);

    private final String jokerName;
    private final int jokerValue;

    /**
     * Constructs a Joker enum constant.
     *
     * @param jokerName the name of the Joker
     * @param jokerValue the numerical value of the Joker
     */

    Joker(String jokerName, int jokerValue) {

        this.jokerName = jokerName;
        this.jokerValue = jokerValue;
    }

    /**
     * Returns the name of the Joker.
     *
     * @return the name of the Joker
     */

    @Override
    public String toString() {
        
        return jokerName;
    }

    /**
     * Gets the numerical value of the Joker.
     *
     * @return the Joker value as an integer
     */

    public int getJokerValue() {

        return jokerValue;
    }
    
}
