package cardsapi;

/**
 * Enum list of both Jokers in a standard deck of playing cards
 */

public enum Joker {

    RED("Red Joker", 1),
    BLACK("Black Joker", 2);

    private final String jokerName;
    private final int jokerValue;

    Joker(String jokerName, int jokerValue) {

        this.jokerName = jokerName;
        this.jokerValue = jokerValue;
    }

    @Override
    public String toString() {
        
        return jokerName;
    }

    public int getJokerValue() {

        return jokerValue;
    }
    
}
