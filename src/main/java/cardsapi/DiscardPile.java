package cardsapi;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public class DiscardPile extends Pile {

    public static class Builder {

        //fields
        private String pileName;

        public Builder(String pileName) {

            if (pileName == null) {

                pileName = "Discard Pile";
            }

            this.pileName = pileName;
        }

        public DiscardPile build() {

            ArrayDeque<Card> discardPileCards = new ArrayDeque<Card>();

            DiscardPile discardPile = new DiscardPile(discardPileCards, this.pileName);

            return discardPile;
        }
    }

    private DiscardPile(Collection<Card> cards, String name) {

        super(cards, name);
    }

    /**
     * Helper method to cast DiscardPile backing collection
     * 
     * Provides access to a DiscardPile's backing collection for modification
     * 
     * @return returns DiscardPile backing collection as a modifiable List
     */

    private Deque<Card> getDiscardPileCollection() {

        return (Deque<Card>) getBackingCollection();
    }

    public Card peekTopCard() {

        return getDiscardPileCollection().peekLast();
    }

    public void returnTopCard(Deck deck) {

        if(isEmpty()) {

            throw new IllegalArgumentException("Discard Pile is empty");
        }

        transferCard(deck, getDiscardPileCollection().pollLast());
    }

    @Override
    protected void removeCard(Card card) {

        throw new UnsupportedOperationException("The removeCard() method is not supported for DiscardPile");
    }

    @Override
    protected void transferCard(Pile pile, Card card) {

        throw new UnsupportedOperationException("The transferCard() method is not supported for DiscardPile");
    }
    
}
