package cardsapi;

/**
 * Represents hands as an ArrayList of card objects
 * 
 * Inherits Pile functionality while adding some hand-specific methods
 */

public class Hand extends Pile {

    /**
     * Constructs a Hand object
     * 
     * @param cardCollection collection used to store cards in this hand
     * @param name name assigned to this hand
     * @throws IllegalArgumentException if name is a string with less than 1 character
     * @throws IllegalArgumentException if name is null
     */

    public Hand(String name) {
        super(name);
    }

     public void playCard(Pile playArea, Card card) {
        transferCard(playArea, card);
    }
}
