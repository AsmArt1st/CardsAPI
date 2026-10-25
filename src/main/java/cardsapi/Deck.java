package cardsapi;

//imports yay
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


/**
 * Creates "decks" of 52 cards with the standard 13 ranks and 4 suits
 * 
 * Allows you to create new decks that store cards to be manipulated
 * 
 * Stores card objects
 */

public class Deck extends Pile{

    public static class Builder {
        
        //fields
        private String deckName;
        private boolean hasJokers;
        private int numDecks;
        private List<Card> tempCards = new ArrayList<>();

        public Builder(String deckName) {
            this.deckName = deckName;
        }

        public Builder withJokers(boolean hasJokers) {
            this.hasJokers = hasJokers;
            return this;
        }

        public Builder deckCount(int numDecks) {
            this.numDecks = numDecks;
            return this;
        }

        public Deck build() {

            for (int i=0; i < numDecks; i++) {
                for (Suit suit : Suit.values()) {
                    for (Rank rank : Rank.values()) {
                        tempCards.add(new Card(rank, suit));
                    }
                }
                if (hasJokers) {
                    for (Joker joker : Joker.values()) {
                        tempCards.add(new Card(joker));
                    }
                }
            }

            return new Deck(this.tempCards, this.deckName);
        }
    }

    /**
     * Constructs a deck object
     * 
     * @param name name assigned to Deck object
     * @param builder 
     * @throws IllegalArgumentException if deck name is less than 1 character
     * @throws IllegalArgumentException if deck name is null
     */

    private Deck(List<Card> cards, String name) {
        
        super(name);
        getBackingCollection().addAll(cards);
    }


    //Deck commands

    /**
     * Helper method to cast deck backing collection
     * 
     * Provides access to a deck's backing collection for modification
     * 
     * @return returns deck backing collection as a modifiable List
     */

    private List<Card> getDeckList() {
        return (List<Card>) getBackingCollection();
    }


    /**
     * Shuffles the given deck
     * 
     * randomizes the position of cards in a given deck
     */

    public void shuffle() {
        Collections.shuffle(getDeckList());
    }


    /**
     * Deals x cards from the top of the given deck to a specified hand
     * 
     * @param hand needs a hand object as an input to deal cards to
     * @param dealSize needs an integer to decide how many cards to deal
     * @throws IllegalArgumentException if hand is null
     * @throws IllegalArgumentException if dealSize is less than 1
     * @throws IllegalArgumentException if there aren't enough cards left in specified deck
     */
    public void deal(Hand hand, int dealSize) {

        //validation
        if (hand == null) {
            throw new IllegalArgumentException("Hand cannot be null");
        }

        if (dealSize < 1) {
            throw new IllegalArgumentException("Must deal at least 1 card");
        }

        if (dealSize > size()) {
            throw new IllegalArgumentException("Not enough cards left in deck");
        }
        
        //method body
        for (int i = 0; i < dealSize; i++) {
            Card topCard = getDeckList().get(size()-1);
            transferCard(hand, topCard);
        }
    }
}