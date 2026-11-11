package cardsapi;

//imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class CardTest {

    // --- Constructor Tests ---

    @Test
    void validCardConstructor() {
        Card card = new Card(Rank.ACE, Suit.SPADES);

        assertEquals(Rank.ACE, card.getRank());
        assertEquals(Suit.SPADES, card.getSuit());
    }

    @Test
    void nullRankThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Card(null, Suit.SPADES);
        });
    }

    @Test
    void nullSuitThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Card(Rank.ACE, null);
        });
    }

    @Test
    void validJokerConstructor() {
        Card joker = new Card(Joker.RED);

        assertEquals(Joker.RED, joker.getJoker());
        assertNull(joker.getRank());
        assertNull(joker.getSuit());
    }

    @Test
    void nullJokerThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Card((Joker) null);
        });
    }

    // --- Getter Tests ---

    @Test
    void getSuitValueReturnsCorrectValue() {
        Card card = new Card(Rank.ACE, Suit.SPADES);
        assertEquals(4, card.getSuitValue());
    }

    @Test
    void getJokerValueReturnsCorrectValue() {
        Card joker = new Card(Joker.RED);
        assertEquals(1, joker.getJoker().getJokerValue());
    }

    // --- Type Check Tests ---

    @Test
    void isJokerReturnsTrueForJoker() {
        Card joker = new Card(Joker.BLACK);
        assertTrue(joker.isJoker());
    }

    @Test
    void isJokerReturnsFalseForStandardCard() {
        Card card = new Card(Rank.KING, Suit.HEARTS);
        assertFalse(card.isJoker());
    }

    @Test
    void isStandardReturnsTrueForStandardCard() {
        Card card = new Card(Rank.TWO, Suit.CLUBS);
        assertTrue(card.isStandard());
    }

    @Test
    void isStandardReturnsFalseForJoker() {
        Card joker = new Card(Joker.RED);
        assertFalse(joker.isStandard());
    }

    // --- toString Tests ---

    @Test
    void toStringFormatsCorrectly() {
        Card card = new Card(Rank.ACE, Suit.SPADES);
        assertEquals("Ace of Spades", card.toString());
    }

    @Test
    void toStringFormatsJokerCorrectly() {
        Card joker = new Card(Joker.BLACK);
        assertEquals("Black Joker", joker.toString());
    }

    // --- equals() Tests ---

    @Test
    void equalsSameCard() {
        Card card = new Card(Rank.ACE, Suit.SPADES);
        assertEquals(card, card);
    }

    @Test
    void equalsDifferentObjectsSameValues() {
        Card first = new Card(Rank.ACE, Suit.SPADES);
        Card second = new Card(Rank.ACE, Suit.SPADES);
        assertEquals(first, second);
    }

    @Test
    void equalsDifferentRank() {
        Card first = new Card(Rank.ACE, Suit.SPADES);
        Card second = new Card(Rank.KING, Suit.SPADES);
        assertNotEquals(first, second);
    }

    @Test
    void equalsDifferentSuit() {
        Card first = new Card(Rank.ACE, Suit.SPADES);
        Card second = new Card(Rank.ACE, Suit.HEARTS);
        assertNotEquals(first, second);
    }

    @Test
    void equalsNullReturnsFalse() {
        Card card = new Card(Rank.ACE, Suit.SPADES);
        assertNotEquals(card, null);
    }

    @Test
    void equalsDifferentTypeReturnsFalse() {
        Card card = new Card(Rank.ACE, Suit.SPADES);
        assertNotEquals(card, "Ace of Spades");
    }

    @Test
    void equalsJokersSameType() {
        Card joker1 = new Card(Joker.RED);
        Card joker2 = new Card(Joker.RED);
        assertEquals(joker1, joker2);
    }

    @Test
    void equalsJokersDifferentTypes() {
        Card joker1 = new Card(Joker.RED);
        Card joker2 = new Card(Joker.BLACK);
        assertNotEquals(joker1, joker2);
    }

    @Test
    void equalsJokerAndStandardCardReturnsFalse() {
        Card joker = new Card(Joker.RED);
        Card ace = new Card(Rank.ACE, Suit.SPADES);
        assertNotEquals(joker, ace);
        assertNotEquals(ace, joker);
    }

    // --- hashCode() Tests ---

    @Test
    void equalCardsHaveSameHashCode() {
        Card first = new Card(Rank.ACE, Suit.SPADES);
        Card second = new Card(Rank.ACE, Suit.SPADES);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void equalJokersHaveSameHashCode() {
        Card joker1 = new Card(Joker.BLACK);
        Card joker2 = new Card(Joker.BLACK);
        assertEquals(joker1.hashCode(), joker2.hashCode());
    }

    // --- Comparator Tests ---

    @Test
    void sortByRankOrdersDescending() {
        Card two = new Card(Rank.TWO, Suit.SPADES);
        Card ace = new Card(Rank.ACE, Suit.HEARTS);
        Card king = new Card(Rank.KING, Suit.CLUBS);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(two);
        cards.add(ace);
        cards.add(king);

        cards.sort(Card.sortByRank);

        assertEquals(ace, cards.get(0));
        assertEquals(king, cards.get(1));
        assertEquals(two, cards.get(2));
    }

    @Test
    void sortBySuitOrdersDescending() {
        Card club = new Card(Rank.TWO, Suit.CLUBS);
        Card heart = new Card(Rank.TWO, Suit.HEARTS);
        Card spade = new Card(Rank.TWO, Suit.SPADES);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(club);
        cards.add(heart);
        cards.add(spade);

        cards.sort(Card.sortBySuit);

        assertEquals(spade, cards.get(0));
        assertEquals(heart, cards.get(1));
        assertEquals(club, cards.get(2));
    }

    @Test
    void sortByRankTiebreaksWithSuit() {
        Card twoClubs = new Card(Rank.TWO, Suit.CLUBS);
        Card twoSpades = new Card(Rank.TWO, Suit.SPADES);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(twoClubs);
        cards.add(twoSpades);

        cards.sort(Card.sortByRank);

        assertEquals(twoSpades, cards.get(0));
        assertEquals(twoClubs, cards.get(1));
    }
}