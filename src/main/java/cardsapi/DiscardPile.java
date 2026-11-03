package cardsapi;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class DiscardPile extends Pile {

    public static class Builder {

        //fields
        private String pileName;
        private boolean isShuffled;

        public Builder(String pileName) {

            if (pileName == null) {

                pileName = "Discard Pile";
            }

            this.pileName = pileName;
        }

        public Builder isShuffled(boolean isShuffled) {

            this.isShuffled = isShuffled;
            return this;
        }

        public DiscardPile build() {

            ArrayDeque<Card> discardPileCards = new ArrayDeque<Card>();

            DiscardPile discardPile = new DiscardPile(discardPileCards, this.pileName);

            if (isShuffled) {

                discardPile.shuffle();
            }

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


    /**
     * Shuffles the given Discard Pile
     * 
     * randomizes the position of cards in a given DiscardPile
     */

    public void shuffle(long seed) {

        Random random = new Random(seed);
        List<Card> currentCards = new ArrayList<>(getDiscardPileCollection());
        Collections.shuffle(currentCards, random);
        getDiscardPileCollection().clear();
        getDiscardPileCollection().addAll(currentCards);
    }

    public void shuffle() {

        shuffle(System.currentTimeMillis());
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
