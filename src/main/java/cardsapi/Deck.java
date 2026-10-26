package cardsapi;

//imports yay
import java.util.Collections;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;


/**
 * Creates "decks" of 52 cards with the standard 13 ranks and 4 suits
 * 
 * Allows you to create new decks that store cards to be manipulated
 * 
 * Stores card objects
 */

public class Deck extends Pile{
    
    //fields
    private final List<Card> originalCards;

    public static class Builder {
        
        //fields
        private String deckName;
        private boolean hasJokers;
        private int numDecks;
        private Deque<Card> tempCards = new ArrayDeque<>();

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

    private Deck(Deque<Card> cards, String name) {
        
        super(cards, name);
        this.originalCards = new ArrayList<>(cards);
    }


    //Deck commands

    /**
     * Helper method to cast deck backing collection
     * 
     * Provides access to a deck's backing collection for modification
     * 
     * @return returns deck backing collection as a modifiable List
     */

    private Deque<Card> getDeckCollection() {

        return (Deque<Card>) getBackingCollection();
    }


    /**
     * Shuffles the given deck
     * 
     * randomizes the position of cards in a given deck
     */

    public void shuffle(long seed) {

        Random random = new Random(seed);
        List<Card> currentCards = new ArrayList<>(getDeckCollection());
        Collections.shuffle(currentCards, random);
        getDeckCollection().clear();
        getDeckCollection().addAll(currentCards);
    }

    public void shuffle() {

        shuffle(System.currentTimeMillis());
    }

    public Card peekTopCard(){

        return getDeckCollection().peekLast();
    }

    public Card drawTopCard(Hand hand){
        
        Card card = getDeckCollection().pollLast();
        hand.addCard(card);
        return card;
    }

    public void reset() {

        getDeckCollection().clear();
        getDeckCollection().addAll(originalCards);
    }

    public void cut() {

        List<Card> list = new ArrayList<>(getDeckCollection());
        int midpoint = list.size()/2;
        List<Card> topHalf = list.subList(0, midpoint);
        List<Card> bottomHalf = list.subList(midpoint, list.size()); 

        getDeckCollection().clear();
        getDeckCollection().addAll(bottomHalf);
        getDeckCollection().addAll(topHalf);
    }

    public void merge(Deck deck) {

        if (deck == null) {

            throw new NullPointerException("Cannot merge into a null Deck");
        }
        
        List<Card> deckList = new ArrayList<>(deck.getDeckCollection());
        getDeckCollection().addAll(deckList);
    }

    public Deck copy(String name) {

        if (name == null || name.isEmpty()) {
            name = "Copy Deck";
        }

        Deque<Card> copyDeck = new ArrayDeque<>(getDeckCollection());
        return new Deck(copyDeck, name);
    }

    public List<Deck> split(int splitIndex, String name1, String name2) {

        if (splitIndex > size()-1 || splitIndex < 1) {

            throw new IllegalArgumentException("Split index must fall between 1 and " + (size()-1));
        }

        if (name1 == null || name1.isEmpty()) {
            name1 = "First Deck";
        }

        if (name2 == null || name2.isEmpty()) {
            name2 = "Second Deck";
        }

        List<Deck> deckList = new ArrayList<>();
        List<Card> list = new ArrayList<>(getDeckCollection());
        List<Card> topPart = list.subList(0, splitIndex);
        List<Card> bottomPart = list.subList(splitIndex, list.size());

        Deque<Card> firstDeck = new ArrayDeque<Card>(topPart);
        Deque<Card> secondDeck = new ArrayDeque<Card>(bottomPart);

        getDeckCollection().clear();

        deckList.add(new Deck(firstDeck, name1));
        deckList.add(new Deck(secondDeck, name2));
        return deckList;
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
            Card topCard = getDeckCollection().peekLast();
            transferCard(hand, topCard);
        }
    }
}