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

        public Builder(String pileName) {

            if (pileName == null) {

                pileName = "Draw Pile";
            }

            this.pileName = pileName;
        }

        public Builder homeDeck(Deck homeDeck) {

            if (homeDeck == null) {

                throw new IllegalStateException("Draw Pile requires a Home Deck");
            }

            this.homeDeck = homeDeck;
            return this;
        }

        public Builder isShuffled(boolean isShuffled) {

            this.isShuffled = isShuffled;
            return this;
        }

        public DrawPile build() {

            ArrayDeque<Card> drawPileCards = new ArrayDeque<Card>(homeDeck.getCards());

            DrawPile drawPile = new DrawPile(drawPileCards, this.pileName);

            if (isShuffled) {

                drawPile.shuffle();
            }

            return drawPile;
        }
    }

    private DrawPile(Collection<Card> cards, String name) {

        super(cards, name);
    }

    /**
     * Helper method to cast DrawPile backing collection
     * 
     * Provides access to a DrawPile's backing collection for modification
     * 
     * @return returns DrawPile backing collection as a modifiable List
     */

    private Deque<Card> getDrawPileCollection() {

        return (Deque<Card>) getBackingCollection();
    }

    public Card peekTopCard() {

        return getDrawPileCollection().peekLast();
    }

    public Card drawTopCard(Hand hand) {

        if (hand == null) {
            throw new IllegalArgumentException("Hand cannot be null");
        }
        
        Card card = getDrawPileCollection().pollLast();
        return transferCard(hand, card);
    }

    @Override
    protected Card addCard(Card card) {

        throw new UnsupportedOperationException("The addCard() method is not supported for DrawPile");
    }
    
    @Override
    protected Card removeCard(Card card) {

        throw new UnsupportedOperationException("The removeCard() method is not supported for DrawPile");
    }

    @Override
    protected Card transferCard(Pile pile, Card card) {

        throw new UnsupportedOperationException("The transferCard() method is not supported for DrawPile");
    }
}
