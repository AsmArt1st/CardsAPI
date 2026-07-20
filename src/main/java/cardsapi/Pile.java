package cardsapi;

//imports yay
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;


/**
 * Base class for collections that store card objects
 * 
 * Provides common methods for various pile types
 */

public abstract class Pile {

    private final Collection<Card> cards;
    private final String name;

    
    /**
     * Constructs a Pile object
     * 
     * The provided collection becomes the backing collection for this pile
     * 
     * @param name name assigned to this pile
     * @param cardCollection collection used to store cards in given pile
     * @throws IllegalArgumentException if name is empty
     * @throws IllegalArgumentException if name is null
     * @throws IllegalArgumentException if cardCollection is null
     */
    
    protected Pile(Collection<Card> cardCollection, String name) {
        
        //validation
        if (name == null) {
            throw new IllegalArgumentException("Pile name cannot be null");
        }

        if (name.length() < 1) {
            throw new IllegalArgumentException("Pile name must contain at least 1 character");
        }

        if (cardCollection == null) {
            throw new IllegalArgumentException("Card collection cannot be null");
        }

        //constructor body
        this.cards = cardCollection;
        this.name = name;
    }


    /**
     * Constructs a Pile object
     * 
     * Default backing collection is an arraylist
     * 
     * @param name name assigned to this pile
     * @throws IllegalArgumentException if name is empty
     * @throws IllegalArgumentException if name is null
     */
    
    protected Pile(String name) {
        this(new ArrayList<Card>(), name);
    }

    //getter methods

    /**
     * Returns an unmodifiable collection of cards to
     * 
     * @return an unmodifiable collection of the cards in this pile
     */

    public Collection<Card> getCards() {
        return Collections.unmodifiableCollection(cards);
    }


    /**
     * Returns anmodifiable collection of cards
     * 
     * @return the backing collection of the cards in this pile
     */

    protected Collection<Card> getBackingCollection() {
        return cards;
    }


    /**
     * Gets name of given pile
     * 
     * @return the name assigned to this pile
     */

    public String getName() {
        return name;
    }


    /**
     * Gets size of given pile
     * 
     * @return the number of cards currently in this pile
     */

    public int size() {
        return cards.size();
    }


    //Manipulation methods

    /**
     * Adds a card to a pile
     * 
     * @param card card to add to this pile
     * @throws IllegalArgumentException if card is null
     */

    protected void addCard(Card card) {

        //validation
        if (card == null) {
            throw new IllegalArgumentException("Cannot add \"null\" to pile");
        }

        //Method body
        cards.add(card);
    }


    /** 
     * Removes a card from a pile
     * 
     * @param card card object to remove from pile
     * 
     * If the specified card isnt in the pile, nothing will happen
    */

    protected void removeCard(Card card) {
        cards.remove(card);
    }


    /**
     * Transfers a card from the given pile to a specified pile
     * 
     * @param pile pile that receives transferred cards
     * @param card which card is selected
     * @throws IllegalArgumentException if pile is null
     * @throws IllegalArgumentException if transferCard is null
     * @throws IllegalArgumentException if card is not in source pile
     */

    protected void transferCard(Pile pile, Card card) {

        //validation
        if (pile == null) {
            throw new IllegalArgumentException("Pile cannot be null");
        }

        if (card == null) {
            throw new IllegalArgumentException("Cannot transfer a null card");
        }

        //method body
        if (cards.contains(card)) {
            cards.remove(card);
            pile.addCard(card);
        }
        else {
            throw new IllegalArgumentException("Card does not exist");
        }
    }
        


    //Overrides

    /**
     * Constructs a string to represent cards in a given pile
     * 
     * @return formatted string of pile name and cards contained
     */

    @Override
    public String toString() {

        StringBuilder pile = new StringBuilder();

        pile.append(name + ":\n");

        for (Card card : cards) {
            pile.append(card + "\n");
        }

        return pile.toString();
    }
}
