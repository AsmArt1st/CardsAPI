package cardsapi;

//imports
import java.util.Objects;
import java.util.Comparator;


/**
 * Formats Card objects with Suits and Ranks.
 * 
 * Uses the Suits and Ranks from a standard deck of 52 Cards and stores them in objects.
 */

public class Card {

    //fields
    private final Rank rank;
    private final Suit suit;
    private final Joker joker;


    /**
     * Constructs a Card object.
     * 
     * @param rank takes in a Rank enum as a parameter to store the rank of the Card
     * @param suit takes in a Suit enum as a parameter to store the suit of the Card
     * @throws IllegalArgumentException if Rank parameter is null
     * @throws IllegalArgumentException if Suit parameter is null
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
     * Constructs a Joker Card object.
     * 
     * @param joker takes in a Joker enum as a parameter to store the value of the Card
     * @throws IllegalArgumentException if Joker parameter is null
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
     * Comparator that sorts Cards by Rank (descending), then Suit (descending), then Joker (descending).
     */

    public static final Comparator<Card> sortByRank = Comparator.comparingInt((Card c) -> c.getRank().getRankValue())
                                                                .thenComparingInt((Card c) -> c.getSuit().getSuitValue())
                                                                .thenComparingInt((Card c) -> c.getJoker() != null ? c.getJoker().getJokerValue() : 0).reversed();
                                    
    /**
     * Comparator that sorts Cards by Suit (descending), then Rank (descending), then Joker (descending).
     */

    public static final Comparator<Card> sortBySuit = Comparator.comparingInt((Card c) -> c.getSuit().getSuitValue())
                                                                .thenComparingInt((Card c) -> c.getRank().getRankValue())
                                                                .thenComparingInt((Card c) -> c.getJoker() != null ? c.getJoker().getJokerValue() : 0).reversed();

    /**
     * Allows caller to get the Rank of given Card.
     * 
     * @return returns Rank of given Card
    */

    public Rank getRank() {

        return rank;
    }

    /**
     * Gets the numerical value of the Card's Suit.
     *
     * @return the Suit value as an integer
     */
    public int getSuitValue() {

        return suit.getSuitValue();
    }


    /**
     * Allows caller to get the Suit of given Card.
     * 
     * @return returns Suit of given Card
     */

    public Suit getSuit() {
        
        return suit;
    }

    /**
     * Allows caller to get the Joker type of given Card.
     * 
     * @return returns Joker type of given Card
     */

    public Joker getJoker() {

        return joker;
    }

    /**
     * Checks if the Card is a Joker.
     *
     * @return true if the Card is a Joker, false otherwise
     */

    public boolean isJoker() {

        if (rank == null && suit == null) {

            return true;
        } else {

            return false;
        }
    }

    /**
     * Checks if the Card is a standard (non-Joker) Card.
     *
     * @return true if the Card is standard, false otherwise
     */
    
    public boolean isStandard() {

        if (joker == null) {

            return true;
        } else {

            return false;
        }
    }


    /**
     * Returns a String representation of the Card.
     *
     * @return a formatted String of the Card's rank and suit, or its Joker type
     */

    @Override
    public String toString() {
        
        if (isStandard()) {

        return rank + " of " + suit;
        } else if (isJoker()) {

            return joker.toString();
        } else {

            throw new IllegalStateException("Card is in an invalid state: neither standard nor joker");
        }
    }

    /**
     * Compares this Card to another object for equality.
     *
     * @param inCard the object to compare to
     * @return true if the objects are equal, false otherwise
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

    /**
     * Returns the hash code for this Card.
     *
     * @return the hash code value
     */
    
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