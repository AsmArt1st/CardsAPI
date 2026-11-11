package cardsapi;

//imports yay
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class CommunityPile extends Pile{

    //fields
    private int maxSize;

    public static class Builder {

        //fields
        private String pileName;
        private int maxSize;

        /**
         * Constructs a Builder for a CommunityPile.
         *
         * @param pileName the name for the new CommunityPile
         */

        public Builder(String pileName) {

            if (pileName == null) {

                pileName = "Community Pile";
            }

            this.pileName = pileName;
        }

        /**
         * Configures the maximum size for the CommunityPile.
         *
         * @param maxSize the maximum number of Cards the pile can hold
         * @return this Builder instance for method chaining
         * @throws IllegalStateException if maxSize is zero
         */

        public Builder maxSize(int maxSize) {

            if (maxSize == 0) {

                throw new IllegalStateException("Community Pile maximum size cannot be zero");
            }

            this.maxSize = maxSize;
            return this;
        }

        /**
         * Builds the CommunityPile object based on the configured parameters.
         *
         * @return a new CommunityPile instance
         */

        public CommunityPile build() {

            ArrayList<Card> communityPileCards = new ArrayList<Card>(maxSize);

            CommunityPile communityPile = new CommunityPile(communityPileCards, this.pileName, this.maxSize);

            return communityPile;
        }
    }

    /**
     * Constructs a CommunityPile object.
     *
     * @param cards the initial collection of Cards
     * @param name the name assigned to the CommunityPile
     * @param maxSize the maximum size of the CommunityPile
     */

    private CommunityPile(Collection<Card> cards, String name, int maxSize) {

        super(cards, name);
        this.maxSize = maxSize;
    }

    /**
     * Helper method to cast CommunityPile backing collection.
     * 
     * Provides access to a CommunityPile's backing collection for modification.
     * 
     * @return returns CommunityPile backing collection as a modifiable List
     */

    private List<Card> getCommunityPileCollection() {

        return (List<Card>) getBackingCollection();
    }

    /**
     * Adds a Card to the CommunityPile, respecting the maxSize limit.
     *
     * @param card the Card to add
     * @return the Card that was added
     * @throws UnsupportedOperationException if the CommunityPile is at maximum capacity
     */

    @Override
    protected Card addCard(Card card) {

        if (getCommunityPileCollection().size() >= maxSize) {
            
            throw new UnsupportedOperationException("The addCard() method is not supported for CommunityPile");
        }

        getCommunityPileCollection().add(card);

        return card;
    }

}
