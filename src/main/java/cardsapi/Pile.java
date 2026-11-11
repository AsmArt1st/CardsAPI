package cardsapi;

//imports yay
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;


/**
 * Base abstract class for collections that store Card objects.
 * 
 * Provides common methods for various pile types.
 */

public abstract class Pile {

    private final Collection<Card> cards;
    private final String name;

    
    /**
     * Constructs a Pile object.
     * 
     * The provided collection becomes the backing collection for this Pile.
     * 
     * @param name name assigned to this pile
     * @param cardCollection collection used to store Cards in given Pile
     * @throws IllegalArgumentException if name is empty
     * @throws IllegalArgumentException if name is null
     * @throws IllegalArgumentException if CardCollection is null
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
     * Constructs a Pile object.
     * 
     * Default backing collection is an ArrayList.
     * 
     * @param name name assigned to this Pile
     * @throws IllegalArgumentException if name is empty
     * @throws IllegalArgumentException if name is null
     */
    
    protected Pile(String name) {

        this(new ArrayList<Card>(), name);
    }

    //getter methods

    /**
     * Returns an unmodifiable collection of Cards.
     * 
     * @return an unmodifiable collection of the Cards in this Pile
     */

    public Collection<Card> getCards() {

        return Collections.unmodifiableCollection(cards);
    }


    /**
     * Returns a modifiable collection of Cards.
     * 
     * @return the backing collection of the Cards in this Pile
     */

    protected Collection<Card> getBackingCollection() {

        return cards;
    }


    /**
     * Gets name of given Pile.
     * 
     * @return the name assigned to this Pile
     */

    public String getName() {

        return name;
    }


    /**
     * Gets size of given Pile.
     * 
     * @return the number of Cards currently in this Pile
     */

    public int size() {

        return cards.size();
    }

    /**
     * Checks if the Pile is empty.
     *
     * @return true if the Pile contains no Cards, false otherwise
     */

    public boolean isEmpty() {

        return cards.size() == 0;
    }

    /**
     * Checks if the Pile contains a specific Card.
     *
     * @param card the Card to search for
     * @return true if the Card is in the Pile, false otherwise
     */
    
    public boolean containsCard(Card card) {

        return ((List<Card>) getBackingCollection()).contains(card);
    }


    //Manipulation methods

    /**
     * Shuffles the given Pile.
     * 
     * Randomizes the position of Cards in a given Pile.
     * 
     * @param seed the seed for the shuffle
     */

    public void shuffle(long seed) {

        Random random = new Random(seed);
        List<Card> currentCards = new ArrayList<>(cards);
        Collections.shuffle(currentCards, random);
        cards.clear();
        cards.addAll(currentCards);
    }

    /**
     * Shuffles the given Pile.
     * 
     * Randomizes the position of Cards in a given Pile.
     * 
     * Default seed is the current time in milliseconds.
     */

    public void shuffle() {

        shuffle(System.currentTimeMillis());
    }

    /**
     * Adds a Card to a Pile.
     * 
     * @param card Card to add to this Pile
     * @throws IllegalArgumentException if Card is null
     */

    protected Card addCard(Card card) {

        //validation
        if (card == null) {

            throw new IllegalArgumentException("Cannot add \"null\" to pile");
        }

        //Method body
        cards.add(card);
        return card;
    }


    /** 
     * Removes a Card from a Pile.
     * 
     * @param card Card object to remove from Pile.
     * 
     * If the specified Card isnt in the pile, nothing will happen.
    */

    protected Card removeCard(Card card) {
        
        cards.remove(card);
        return card;
    }


    /**
     * Transfers a Card from the given Pile to a specified Pile.
     * 
     * @param pile Pile that receives transferred Cards
     * @param card which Card is selected
     * @throws IllegalArgumentException if pile is null
     * @throws IllegalArgumentException if transferCard is null
     * @throws IllegalArgumentException if Card is not in source Pile
     */

    protected Card transferCard(Pile pile, Card card) {

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
        } else {

            throw new IllegalArgumentException("Card does not exist");
        }

        return card;
    }
        


    //Overrides

    /**
     * Constructs a String to represent Cards in a given Pile.
     * 
     * @return formatted String of Pile name and Cards contained
     */

    @Override
    public String toString() {

        StringBuilder pile = new StringBuilder();

        pile.append("---" + name + "---\n");

        for (Card card : cards) {
            
            pile.append(card + "\n");
        }

        return pile.toString();
    }
}
