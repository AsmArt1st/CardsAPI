package cardsapi;

//imports yay
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Collection;

public class DrawPile extends Pile {

    public static class Builder {

        //fields
        private String pileName;
        private Deck homeDeck;
        private boolean isShuffled;

        /**
         * Constructs a Builder for a DrawPile.
         *
         * @param pileName the name for the new DrawPile
         */

        public Builder(String pileName) {

            if (pileName == null) {

                pileName = "Draw Pile";
            }

            this.pileName = pileName;
        }

        /**
         * Configures the source Deck for the DrawPile.
         *
         * @param homeDeck the Deck to copy Cards from
         * @return this Builder instance for method chaining
         * @throws IllegalStateException if homeDeck is null
         */

        public Builder homeDeck(Deck homeDeck) {

            if (homeDeck == null) {

                throw new IllegalStateException("Draw Pile requires a Home Deck");
            }

            this.homeDeck = homeDeck;
            return this;
        }

        /**
         * Configures whether the DrawPile should be shuffled upon creation.
         *
         * @param isShuffled true to shuffle the pile after creation, false otherwise
         * @return this Builder instance for method chaining
         */

        public Builder isShuffled(boolean isShuffled) {

            this.isShuffled = isShuffled;
            return this;
        }

        /**
         * Builds the DrawPile object based on the configured parameters.
         *
         * @return a new DrawPile instance
         */
        
        public DrawPile build() {

            ArrayDeque<Card> drawPileCards = new ArrayDeque<Card>(homeDeck.getCards());

            DrawPile drawPile = new DrawPile(drawPileCards, this.pileName);

            if (isShuffled) {

                drawPile.shuffle();
            }

            return drawPile;
        }
    }

    /**
     * Constructs a DrawPile object.
     *
     * @param cards the initial collection of Cards
     * @param name the name assigned to the DrawPile
     */

    private DrawPile(Collection<Card> cards, String name) {

        super(cards, name);
    }

    /**
     * Helper method to cast DrawPile backing collection.
     * 
     * Provides access to a DrawPile's backing collection for modification.
     * 
     * @return returns DrawPile backing collection as a modifiable List
     */

    private Deque<Card> getDrawPileCollection() {

        return (Deque<Card>) getBackingCollection();
    }

    /**
     * Returns the top Card of the DrawPile without removing it.
     *
     * @return the top Card, or null if the DrawPile is empty
     */

    public Card peekTopCard() {

        return getDrawPileCollection().peekLast();
    }

    /**
     * Prevents adding Cards to the DrawPile.
     *
     * @param card the Card to add
     * @return never returns
     * @throws UnsupportedOperationException always
     */

    @Override
    protected Card addCard(Card card) {

        throw new UnsupportedOperationException("The addCard() method is not supported for DrawPile");
    }
    
    /**
     * Prevents removal of specific Cards from the DrawPile.
     *
     * @param card the Card to remove
     * @return never returns
     * @throws UnsupportedOperationException always
     */

    @Override
    protected Card removeCard(Card card) {

        throw new UnsupportedOperationException("The removeCard() method is not supported for DrawPile");
    }

}
