package cardsapi;

//imports yay
import java.util.Collections;
import java.util.List;


/**
 * Creates "decks" of 52 cards with the standard 13 ranks and 4 suits
 * 
 * Allows you to create new decks that store cards to be manipulated
 * 
 * Stores card objects
 */

public class Deck extends Pile{
    
    /**
     * Constructs a deck object
     * 
     * @param name name assigned to Deck object
     * @throws IllegalArgumentException if deck name is less than 1 character
     * @throws IllegalArgumentException if deck name is null
     */

    public Deck(String name) {

        //method body
        super(name);
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                addCard(new Card(rank, suit));
            }
        }
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
            Card topCard = getDeckList().get(0);
            transferCard(hand, topCard);
        }
    }
}