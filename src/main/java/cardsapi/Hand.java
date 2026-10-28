package cardsapi;

import java.util.Comparator;
import java.util.List;

/**
 * Represents hands as an ArrayList of card objects
 * 
 * Inherits Pile functionality while adding some hand-specific methods
 */

public class Hand extends Pile {

    public static class Builder {

        //fields
        private String handName;

        public Builder(String handName) {

            this.handName = handName;
        }

        public Hand build() {

            return new Hand(handName);
        }
    }

    /**
     * Constructs a Hand object
     * 
     * @param cardCollection collection used to store cards in this hand
     * @param name name assigned to this hand
     * @throws IllegalArgumentException if name is a string with less than 1 character
     * @throws IllegalArgumentException if name is null
     */

    private Hand(String name) {

        super(name);
    }

    public void sort(Comparator<Card> comparator) {

        ((List<Card>) getBackingCollection()).sort(comparator);
    }

    public void rankSort() {

        sort(Card.sortByRank);
    }

    public void suitSort() {

        sort(Card.sortBySuit);
    }

     public void playCard(Pile playArea, Card card) {

        transferCard(playArea, card);
    }
}
