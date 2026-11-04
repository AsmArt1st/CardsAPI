package cardsapi;

//imports yay
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Collection;

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

    public Card returnTopCard(Deck deck) {

        if(isEmpty()) {

            throw new IllegalArgumentException("Discard Pile is empty");
        }

        Card card = getDiscardPileCollection().pollLast();

        return transferCard(deck, card);
    }

    @Override
    protected Card removeCard(Card card) {

        throw new UnsupportedOperationException("The removeCard() method is not supported for DiscardPile");
    }

    @Override
    protected Card transferCard(Pile pile, Card card) {

        throw new UnsupportedOperationException("The transferCard() method is not supported for DiscardPile");
    }
    
}
