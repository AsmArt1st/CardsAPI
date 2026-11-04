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

        public Builder(String pileName) {

            if (pileName == null) {

                pileName = "Community Pile";
            }

            this.pileName = pileName;
        }

        public Builder maxSize(int maxSize) {

            if (maxSize == 0) {

                throw new IllegalStateException("Community Pile maximum size cannot be zero");
            }

            this.maxSize = maxSize;
            return this;
        }

        public CommunityPile build() {

            ArrayList<Card> communityPileCards = new ArrayList<Card>(maxSize);

            CommunityPile communityPile = new CommunityPile(communityPileCards, this.pileName, this.maxSize);

            return communityPile;
        }
    }

    private CommunityPile(Collection<Card> cards, String name, int maxSize) {

        super(cards, name);
        this.maxSize = maxSize;
    }

    /**
     * Helper method to cast CommunityPile backing collection
     * 
     * Provides access to a CommunityPile's backing collection for modification
     * 
     * @return returns CommunityPile backing collection as a modifiable List
     */

    private List<Card> getCommunityPileCollection() {

        return (List<Card>) getBackingCollection();
    }

    @Override
    protected Card addCard(Card card) {

        if (getCommunityPileCollection().size() >= maxSize) {
            
            throw new UnsupportedOperationException("The addCard() method is not supported for CommunityPile");
        }

        getCommunityPileCollection().add(card);

        return card;
    }

}
