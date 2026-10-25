package cardsapi;

//imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.HashSet;

public class DeckTest {

    // --- Builder Pattern Tests ---

    @Test
    void builderCreatesSingleDeck() {
        Deck deck = new Deck.Builder("Single Deck")
                .deckCount(1)
                .build();

        assertEquals("Single Deck", deck.getName());
        assertEquals(52, deck.size());
    }

    @Test
    void builderCreatesMultiDeckShoe() {
        Deck shoe = new Deck.Builder("Blackjack Shoe")
                .deckCount(6)
                .build();

        assertEquals("Blackjack Shoe", shoe.getName());
        assertEquals(312, shoe.size()); // 6 * 52
    }

    @Test
    void builderWithJokersAddsJokers() {
        Deck deck = new Deck.Builder("Joker Deck")
                .deckCount(1)
                .withJokers(true)
                .build();

        assertEquals(54, deck.size()); // 52 + 2 jokers
    }

    @Test
    void builderWithoutJokersNoJokers() {
        Deck deck = new Deck.Builder("Standard")
                .deckCount(1)
                .build();

        assertEquals(52, deck.size());
    }

    @Test
    void builderDefaultNameIsUsed() {
        Deck deck = new Deck.Builder("Custom Name").build();
        assertEquals("Custom Name", deck.getName());
    }

    // --- Card Identity & Joker Tests ---

    @Test
    void jokersAreDistinct() {
        Card joker1 = new Card(Joker.RED);
        Card joker2 = new Card(Joker.BLACK);

        assertFalse(joker1.equals(joker2));
        assertNotEquals(joker1.hashCode(), joker2.hashCode());
    }

    @Test
    void jokersAreNotEqualToStandardCards() {
        Card joker = new Card(Joker.RED);
        Card aceSpades = new Card(Rank.ACE, Suit.SPADES);

        assertFalse(joker.equals(aceSpades));
        assertFalse(aceSpades.equals(joker));
    }

    @Test
    void standardCardsAreEqualRegardlessOfInstance() {
        Card ace1 = new Card(Rank.ACE, Suit.SPADES);
        Card ace2 = new Card(Rank.ACE, Suit.SPADES);

        assertTrue(ace1.equals(ace2));
        assertEquals(ace1.hashCode(), ace2.hashCode());
    }

    @Test
    void jokerToStringReturnsJokerName() {
        Card joker = new Card(Joker.RED);
        assertEquals("Red Joker", joker.toString());
    }

    @Test
    void standardCardToStringReturnsRankAndSuit() {
        Card card = new Card(Rank.KING, Suit.HEARTS);
        assertEquals("King of Hearts", card.toString());
    }

    // --- Deck State & Manipulation Tests ---

    @Test
    void deckStartsWith52Cards() {
        Deck deck = new Deck.Builder("Standard").deckCount(1).build();
        assertEquals(52, deck.size());
    }

    @Test
    void deckContainsAllUniqueCards() {
        Deck deck = new Deck.Builder("Standard").deckCount(1).build();
        HashSet<Card> cards = new HashSet<>(deck.getCards());
        assertEquals(52, cards.size());
    }

    @Test
    void getCardsIsUnmodifiable() {
        Deck deck = new Deck.Builder("Standard").deckCount(1).build();
        assertThrows(UnsupportedOperationException.class, () -> {
            deck.getCards().clear();
        });
    }

    @Test
    void shuffleKeepsSameCards() {
        Deck deck = new Deck.Builder("Standard").deckCount(1).build();
        HashSet<Card> beforeShuffle = new HashSet<>(deck.getCards());

        deck.shuffle();

        HashSet<Card> afterShuffle = new HashSet<>(deck.getCards());

        assertEquals(beforeShuffle, afterShuffle);
        assertEquals(52, deck.size());
    }

    @Test
    void dealRemovesCardsFromDeck() {
        Deck deck = new Deck.Builder("Standard").deckCount(1).build();
        Hand hand = new Hand("Player");

        deck.deal(hand, 5);

        assertEquals(47, deck.size());
        assertEquals(5, hand.size());
    }

    @Test
    void dealMovesCardsToHand() {
        Deck deck = new Deck.Builder("Standard").deckCount(1).build();
        Hand hand = new Hand("Player");

        deck.deal(hand, 10);

        assertEquals(42, deck.size());
        assertEquals(10, hand.size());
    }

    @Test
    void dealAllCardsWorks() {
        Deck deck = new Deck.Builder("Standard").deckCount(1).build();
        Hand hand = new Hand("Player");

        deck.deal(hand, 52);

        assertEquals(0, deck.size());
        assertEquals(52, hand.size());
    }

    @Test
    void dealTooManyCardsThrowsException() {
        Deck deck = new Deck.Builder("Standard").deckCount(1).build();
        Hand hand = new Hand("Player");

        assertThrows(IllegalArgumentException.class, () -> {
            deck.deal(hand, 53);
        });
    }

    @Test
    void dealNullHandThrowsException() {
        Deck deck = new Deck.Builder("Standard").deckCount(1).build();

        assertThrows(IllegalArgumentException.class, () -> {
            deck.deal(null, 5);
        });
    }

    @Test
    void dealZeroCardsThrowsException() {
        Deck deck = new Deck.Builder("Standard").deckCount(1).build();
        Hand hand = new Hand("Player");

        assertThrows(IllegalArgumentException.class, () -> {
            deck.deal(hand, 0);
        });
    }

    @Test
    void dealNegativeCardsThrowsException() {
        Deck deck = new Deck.Builder("Standard").deckCount(1).build();
        Hand hand = new Hand("Player");

        assertThrows(IllegalArgumentException.class, () -> {
            deck.deal(hand, -1);
        });
    }
}
