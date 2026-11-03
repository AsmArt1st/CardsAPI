package cardsapi;

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

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


    /**
     * Shuffles the given DrawPile
     * 
     * randomizes the position of cards in a given DrawPile
     */

    public void shuffle(long seed) {

        Random random = new Random(seed);
        List<Card> currentCards = new ArrayList<>(getDrawPileCollection());
        Collections.shuffle(currentCards, random);
        getDrawPileCollection().clear();
        getDrawPileCollection().addAll(currentCards);
    }

    public void shuffle() {

        shuffle(System.currentTimeMillis());
    }

    public Card peekTopCard() {

        return getDrawPileCollection().peekLast();
    }

    public Card drawTopCard(Hand hand) {

        if (hand == null) {
            throw new IllegalArgumentException("Hand cannot be null");
        }
        
        Card card = getDrawPileCollection().pollLast();
        hand.addCard(card);
        return card;
    }

    @Override
    protected void addCard(Card card) {

        throw new UnsupportedOperationException("The addCard() method is not supported for DrawPile");
    }
    
    @Override
    protected void removeCard(Card card) {

        throw new UnsupportedOperationException("The removeCard() method is not supported for DrawPile");
    }

    @Override
    protected void transferCard(Pile pile, Card card) {

        throw new UnsupportedOperationException("The transferCard() method is not supported for DrawPile");
    }
}
