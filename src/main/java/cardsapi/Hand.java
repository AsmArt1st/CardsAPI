package cardsapi;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.List;

/**
 * Represents Hands as an ArrayList of Card objects.
 * 
 * Inherits Pile functionality while adding some Hand-specific methods.
 */

public class Hand extends Pile {

    public static class Builder {

        //fields
        private String handName;

        /**
         * Constructs a Builder for a Hand.
         *
         * @param handName the name for the new Hand
         */

        public Builder(String handName) {

            this.handName = handName;
        }

        /**
         * Builds the Hand object based on the configured parameters.
         *
         * @return a new Hand instance
         */

        public Hand build() {

            return new Hand(handName);
        }
    }

    /**
     * Constructs a Hand object.
     *
     * @param name the name assigned to this Hand
     */

    private Hand(String name) {

        super(name);
    }

    /**
     * Sorts the Cards in this Hand using the specified Comparator.
     *
     * @param comparator the Comparator to use for sorting
     */

    public void sort(Comparator<Card> comparator) {

        ((List<Card>) getBackingCollection()).sort(comparator);
    }

    /**
     * Sorts the Cards in this Hand by Rank (descending), then Suit (descending), then Joker (descending).
     */

    public void rankSort() {

        sort(Card.sortByRank);
    }

    /**
     * Sorts the Cards in this Hand by Suit (descending), then Rank (descending), then Joker (descending).
     */

    public void suitSort() {

        sort(Card.sortBySuit);
    }

    /**
     * Moves a Card from this Hand to a CommunityPile.
     *
     * @param playArea the CommunityPile to move the Card to
     * @param card the Card to move
     * @return the Card that was played
     */

    public Card playCard(CommunityPile playArea, Card card) {

        return this.transferCard(playArea, card);
    }

    /**
     * Moves a Card from a CommunityPile to this Hand.
     *
     * @param playArea the CommunityPile to take the Card from
     * @param card the Card to take
     * @return the Card that was taken
     */

    public Card takeCard(CommunityPile playArea, Card card) {

        return playArea.transferCard(this, card);
    }

    /**
     * Draws the top Card from a DrawPile into this Hand.
     *
     * @param drawPile the DrawPile to draw from
     * @return the Card that was drawn
     */

    public Card drawCard(DrawPile drawPile) {

        return drawPile.transferCard(this, ((ArrayDeque <Card>) drawPile.getBackingCollection()).peekLast());
    }

    /**
     * Moves a Card from this Hand to a DiscardPile.
     *
     * @param discardPile the DiscardPile to move the Card to
     * @param card the Card to discard
     * @return the Card that was discarded
     */
    
    public Card discardCard(DiscardPile discardPile, Card card) {

        return this.transferCard(discardPile, card);
    }

}
