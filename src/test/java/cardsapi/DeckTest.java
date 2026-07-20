package cardsapi;

//imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.HashSet;

public class DeckTest {

    @Test
    void validConstructor() {
        Deck deck = new Deck("Standard");

        assertEquals("Standard", deck.getName());
    }

    @Test
    void nullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Deck(null);
        });
    }

    @Test
    void emptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Deck("");
        });
    }

    @Test
    void deckStartsWith52Cards() {
        Deck deck = new Deck("Standard");

        assertEquals(52, deck.size());
    }

    @Test
    void deckContainsAllUniqueCards() {
        Deck deck = new Deck("Standard");

        HashSet<Card> cards = new HashSet<>(deck.getCards());

        assertEquals(52, cards.size());
    }

    @Test
    void getCardsIsUnmodifiable() {
        Deck deck = new Deck("Standard");

        assertThrows(UnsupportedOperationException.class, () -> {
            deck.getCards().clear();
        });
    }

    @Test
    void shuffleKeepsSameCards() {
        Deck deck = new Deck("Standard");

        HashSet<Card> beforeShuffle = new HashSet<>(deck.getCards());

        deck.shuffle();

        HashSet<Card> afterShuffle = new HashSet<>(deck.getCards());

        assertEquals(beforeShuffle, afterShuffle);
        assertEquals(52, deck.size());
    }

    @Test
    void dealRemovesCardsFromDeck() {
        Deck deck = new Deck("Standard");
        Hand hand = new Hand("Player");

        deck.deal(hand, 5);

        assertEquals(47, deck.size());
        assertEquals(5, hand.size());
    }

    @Test
    void dealMovesCardsToHand() {
        Deck deck = new Deck("Standard");
        Hand hand = new Hand("Player");

        deck.deal(hand, 10);

        assertEquals(42, deck.size());
        assertEquals(10, hand.size());
    }

    @Test
    void dealAllCardsWorks() {
        Deck deck = new Deck("Standard");
        Hand hand = new Hand("Player");

        deck.deal(hand, 52);

        assertEquals(0, deck.size());
        assertEquals(52, hand.size());
    }

    @Test
    void dealTooManyCardsThrowsException() {
        Deck deck = new Deck("Standard");
        Hand hand = new Hand("Player");

        assertThrows(IllegalArgumentException.class, () -> {
            deck.deal(hand, 53);
        });
    }

    @Test
    void dealNullHandThrowsException() {
        Deck deck = new Deck("Standard");

        assertThrows(IllegalArgumentException.class, () -> {
            deck.deal(null, 5);
        });
    }

    @Test
    void dealZeroCardsThrowsException() {
        Deck deck = new Deck("Standard");
        Hand hand = new Hand("Player");

        assertThrows(IllegalArgumentException.class, () -> {
            deck.deal(hand, 0);
        });
    }

    @Test
    void dealNegativeCardsThrowsException() {
        Deck deck = new Deck("Standard");
        Hand hand = new Hand("Player");

        assertThrows(IllegalArgumentException.class, () -> {
            deck.deal(hand, -1);
        });
    }
}