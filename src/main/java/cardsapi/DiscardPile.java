package cardsapi;

//imports yay
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Collection;

public class DiscardPile extends Pile {

    public static class Builder {

        //fields
        private String pileName;

        /**
         * Constructs a Builder for a DiscardPile.
         *
         * @param pileName the name for the new DiscardPile
         */

        public Builder(String pileName) {

            if (pileName == null) {

                pileName = "Discard Pile";
            }

            this.pileName = pileName;
        }

        /**
         * Builds the DiscardPile object based on the configured parameters.
         *
         * @return a new DiscardPile instance
         */
        
        public DiscardPile build() {

            ArrayDeque<Card> discardPileCards = new ArrayDeque<Card>();

            DiscardPile discardPile = new DiscardPile(discardPileCards, this.pileName);

            return discardPile;
        }
    }

    /**
     * Constructs a DiscardPile object.
     *
     * @param cards the initial collection of Cards
     * @param name the name assigned to the DiscardPile
     */

    private DiscardPile(Collection<Card> cards, String name) {

        super(cards, name);
    }

    /**
     * Helper method to cast DiscardPile backing collection.
     * 
     * Provides access to a DiscardPile's backing collection for modification.
     * 
     * @return returns DiscardPile backing collection as a modifiable List
     */

    private Deque<Card> getDiscardPileCollection() {

        return (Deque<Card>) getBackingCollection();
    }

    /**
     * Returns the top Card of the DiscardPile without removing it.
     *
     * @return the top Card, or null if the DiscardPile is empty
     */

    public Card peekTopCard() {

        return getDiscardPileCollection().peekLast();
    }

    /**
     * Removes the top Card from the DiscardPile and adds it to the specified Deck.
     *
     * @param deck the Deck to receive the Card
     * @return the Card that was returned
     * @throws IllegalArgumentException if the DiscardPile is empty
     */

    public Card returnTopCard(Deck deck) {

        if(isEmpty()) {

            throw new IllegalArgumentException("Discard Pile is empty");
        }

        Card card = this.peekTopCard();

        return transferCard(deck, card);
    }

    /**
     * Prevents removal of specific Cards from the DiscardPile.
     *
     * @param card the Card to remove
     * @return never returns
     * @throws UnsupportedOperationException always
     */

    @Override
    protected Card removeCard(Card card) {

        throw new UnsupportedOperationException("The removeCard() method is not supported for DiscardPile");
    }
    
}
