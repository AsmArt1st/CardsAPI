package cardsapi;

//imports yay
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.ArrayList;

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

    // --- Seeded Shuffle Tests ---

    @Test
    void seededShuffleProducesDeterministicOrder() {
        Deck deck1 = new Deck.Builder("Test").deckCount(1).build();
        Deck deck2 = new Deck.Builder("Test").deckCount(1).build();

        long seed = 42L;
        deck1.shuffle(seed);
        deck2.shuffle(seed);

        assertEquals(new ArrayList<>(deck1.getCards()), new ArrayList<>(deck2.getCards()));
    }

    @Test
    void differentSeedsProduceDifferentOrders() {
        Deck deck1 = new Deck.Builder("Test").deckCount(1).build();
        Deck deck2 = new Deck.Builder("Test").deckCount(1).build();

        deck1.shuffle(100L);
        deck2.shuffle(200L);

        assertNotEquals(new ArrayList<>(deck1.getCards()), new ArrayList<>(deck2.getCards()));
    }

    @Test
    void shufflePreservesCardSet() {
        Deck deck = new Deck.Builder("Test").deckCount(1).build();
        HashSet<Card> originalCards = new HashSet<>(deck.getCards());

        deck.shuffle(12345L);

        assertEquals(originalCards, new HashSet<>(deck.getCards()));
        assertEquals(52, deck.size());
    }

    @Test
    void reapplyingSameSeedRestoresOrder() {
        Deck deck1 = new Deck.Builder("Test").deckCount(1).build();
        Deck deck2 = new Deck.Builder("Test").deckCount(1).build();

   
        var originalOrder = new ArrayList<>(deck1.getCards());

        deck1.shuffle(999L);
        deck2.shuffle(999L);

        assertEquals(new ArrayList<>(deck1.getCards()), new ArrayList<>(deck2.getCards()));
        assertNotEquals(originalOrder, new ArrayList<>(deck1.getCards()));
    }

    @Test
    void seededShuffleDeterminesDealOrder() {
        Deck deck1 = new Deck.Builder("Test").deckCount(1).build();
        Hand hand1 = new Hand("P1");

        Deck deck2 = new Deck.Builder("Test").deckCount(1).build();
        Hand hand2 = new Hand("P2");

        deck1.shuffle(777L);
        deck1.deal(hand1, 5);

        deck2.shuffle(777L);
        deck2.deal(hand2, 5);

        assertEquals(new ArrayList<>(deck1.getCards()), new ArrayList<>(deck2.getCards()));
    }

    @Test
    void edgeCaseSeedZeroWorks() {
        Deck deck = new Deck.Builder("Test").deckCount(1).build();
        assertDoesNotThrow(() -> deck.shuffle(0L));
        assertEquals(52, deck.size());
    }

    @Test
    void edgeCaseNegativeSeedWorks() {
        Deck deck = new Deck.Builder("Test").deckCount(1).build();
        assertDoesNotThrow(() -> deck.shuffle(-1L));
        assertEquals(52, deck.size());
    }

    // --- Deal & State Tests ---

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
