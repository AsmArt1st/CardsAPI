package cardsapi;

/**
 * Enum list of both Jokers in a standard deck of playing cards
 */

public enum Joker {

    RED("Red Joker"),
    BLACK("Black Joker");

    private final String jokerName;

    Joker(String jokerName) {
        this.jokerName = jokerName;
    }

    @Override
    public String toString() {
        return jokerName;
    }
    
}
