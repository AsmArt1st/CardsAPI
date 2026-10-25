package cardsapi;

//imports
import java.util.Objects;


/**
 * Formats Card objects with suits and ranks
 * 
 * Uses the suits and ranks from a standard deck of 52 cards and stores them in objects
 */

public class Card {

    //fields
    private final Rank rank;
    private final Suit suit;
    private final Joker joker;


    /**
     * Constructs a card object
     * 
     * @param rank takes in a rank enum as a parameter to store the rank of the card
     * @param suit takes in a suit enum as a parameter to store the suit of the card
     * @throws IllegalArgumentException if rank or suit parameter is null
     */

    public Card(Rank rank, Suit suit) {

        //validation
        if (rank == null) {
            throw new IllegalArgumentException("Rank cannot be null");
        }

        if (suit == null) {
            throw new IllegalArgumentException("Suit cannot be null");
        }

        this.rank = rank;
        this.suit = suit;
        this.joker = null;
    }

    /**
     * Constructs a joker card object
     * 
     * @param joker takes in a joker enum as a parameter to store the value of the card
     * @throws IllegalArgumentException if joker parameter is null
     */

    public Card(Joker joker) {

        //validation
        if (joker == null) {
            throw new IllegalArgumentException("Joker cannot be null");
        }

        this.joker = joker;
        this.rank = null;
        this.suit = null;
    }

    /**
     * Allows caller to get the rank of given card
     * 
     * @return returns rank of given card
    */

    public Rank getRank() {
        return rank;
    }


    /**
     * Allows caller to get the suit of given card
     * 
     * @return returns suit of given card
     */

    public Suit getSuit() {
        return suit;
    }

    /**
     * Allows caller to get the joker value of given card
     * 
     * @return returns joker value of given card
     */

    public Joker getJoker() {
        return joker;
    }

    public boolean isJoker() {
        if (rank == null && suit == null) {
            return true;
        } 
        else {
            return false;
        }
    }

    public boolean isStandard() {
        if (joker == null) {
            return true;
        } 
        else {
            return false;
        }
    }


    //card string formatting
    @Override
    public String toString() {
        if (isStandard()) {
        return rank + " of " + suit;
        }
        else if (isJoker()) {
            return joker.toString();
        }
        else {
            throw new IllegalStateException("Card is in an invalid state: neither standard nor joker");
        }
    }

    /**
     * equals() override in order  to compare a cards rank and suit enums
     */
    
    @Override
    public boolean equals(Object inCard) {

        if (!(inCard instanceof Card otherCard)) {
            return false;
        }

        if (otherCard.isStandard()) {
            return rank == otherCard.rank && suit == otherCard.suit;
        }
        else if (otherCard.isJoker()) {
            return joker == otherCard.joker;
        }
        else if (this.isJoker() != otherCard.isJoker()) {
            return false;
        }
        else {
            throw new IllegalStateException("Card is in an invalid state: neither standard nor joker");
        }
    }

    //hashCode() method override
    @Override
    public int hashCode() {

        if (isStandard()) {
            return Objects.hash(rank, suit);
        }
        else if (isJoker()) {
            return Objects.hash(joker);
        }
        else {
            throw new IllegalStateException("Card is in an invalid state: neither standard nor joker");
        }
    }
}