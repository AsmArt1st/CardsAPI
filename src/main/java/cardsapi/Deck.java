package cardsapi;

//imports yay
import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;


/**
 * Creates "decks" of 52 Cards with the standard 13 ranks and 4 suits.
 * 
 * Allows you to create new Decks that store Cards to be manipulated.
 * 
 * Stores Card objects.
 */

public class Deck extends Pile{
    
    //fields
    private final List<Card> originalCards;

    public static class Builder {
        
        //fields
        private String deckName;
        private boolean hasJokers;
        private int numDecks;
        private boolean isShuffled;

        public Builder(String deckName) {

            this.deckName = deckName;
        }

        /**
         * Configures the number of Decks to create.
         *
         * @param numDecks the number of Decks to include
         * @return this Builder instance for method chaining
         */

        public Builder deckCount(int numDecks) {

            this.numDecks = numDecks;
            return this;
        }

        /**
         * Configures whether the Deck should include Jokers.
         *
         * @param hasJokers true to include Jokers, false otherwise
         * @return this Builder instance for method chaining
         */
        
        public Builder withJokers(boolean hasJokers) {

            this.hasJokers = hasJokers;
            return this;
        }

        /**
         * Configures whether the Deck should be shuffled upon creation.
         *
         * @param isShuffled true to shuffle the Deck after creation, false otherwise
         * @return this Builder instance for method chaining
         */

        public Builder isShuffled(boolean isShuffled) {

            this.isShuffled = isShuffled;
            return this;
        }

        /**
         * Builds the Deck object based on the configured parameters.
         *
         * @return a new Deck instance
         */

        public Deck build() {

            Deque<Card> tempCards = new ArrayDeque<>();

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

            Deck newDeck =  new Deck(tempCards, this.deckName);

            if (isShuffled) {

                newDeck.shuffle();
            }

            return newDeck;

        }
    }

    /**
     * Constructs a Deck object.
     * 
     * @param name name assigned to Deck object
     * @param builder 
     * @throws IllegalArgumentException if Deck name is less than 1 character
     * @throws IllegalArgumentException if Deck name is null
     */

    private Deck(Deque<Card> cards, String name) {
        
        super(cards, name);
        this.originalCards = new ArrayList<>(cards);
    }


    //Deck commands

    /**
     * Helper method to cast Deck backing collection.
     * 
     * Provides access to a Deck's backing collection for modification.
     * 
     * @return returns Deck backing collection as a modifiable List
     */

    private Deque<Card> getDeckCollection() {

        return (Deque<Card>) getBackingCollection();
    }

    /**
     * Returns the top Card of the Deck without removing it.
     *
     * @return the top Card, or null if the Deck is empty
     */
    
    public Card peekTopCard() {

        return getDeckCollection().peekLast();
    }

    /**
     * Resets the Deck to its original state as it was when it was created.
     */

    public void reset() {

        getDeckCollection().clear();
        getDeckCollection().addAll(originalCards);
    }

    /**
     * Splits the Deck in half, swapping the top and bottom halves.
     */

    public void cut() {

        List<Card> list = new ArrayList<>(getDeckCollection());
        int midpoint = list.size()/2;
        List<Card> topHalf = list.subList(0, midpoint);
        List<Card> bottomHalf = list.subList(midpoint, list.size()); 

        getDeckCollection().clear();
        getDeckCollection().addAll(bottomHalf);
        getDeckCollection().addAll(topHalf);
    }

    /**
     * Adds all Cards from the specified Deck to this Deck.
     *
     * @param deck the Deck to merge into this one
     * @throws NullPointerException if Deck is null
     */

    public void merge(Deck deck) {

        if (deck == null) {

            throw new NullPointerException("Cannot merge into a null Deck");
        }
        
        List<Card> deckList = new ArrayList<>(deck.getDeckCollection());
        getDeckCollection().addAll(deckList);
    }

    /**
     * Creates a copy of this Deck.
     *
     * @param name the name for the new Deck
     * @return a new Deck with the same Cards
     */

    public Deck copy(String name) {

        if (name == null || name.isEmpty()) {
            name = "Copy Deck";
        }

        Deque<Card> copyDeck = new ArrayDeque<>(getDeckCollection());
        return new Deck(copyDeck, name);
    }

    /**
     * Splits this Deck into two new Decks at the specified index.
     *
     * @param splitIndex the index at which to split the Deck
     * @param name1 the name for the first new Deck
     * @param name2 the name for the second new Deck
     * @return a List containing the two new Decks
     * @throws IllegalArgumentException if splitIndex is out of bounds
     */

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
     * Deals x Cards from the top of the given Deck to a specified Hand
     * 
     * @param hand needs a Hand object as an input to deal Cards to
     * @param dealSize needs an integer to decide how many Cards to deal
     * @throws IllegalArgumentException if Hand is null
     * @throws IllegalArgumentException if dealSize is less than 1
     * @throws IllegalArgumentException if there aren't enough Cards left in specified Deck
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