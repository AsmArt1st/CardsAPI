package cardsapi;
//imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Deque;

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

        assertEquals(new ArrayList<>(deck1.getBackingCollection()), new ArrayList<>(deck2.getBackingCollection()));
    }

    @Test
    void differentSeedsProduceDifferentOrders() {
        Deck deck1 = new Deck.Builder("Test").deckCount(1).build();
        Deck deck2 = new Deck.Builder("Test").deckCount(1).build();

        deck1.shuffle(100L);
        deck2.shuffle(200L);

        assertNotEquals(deck1.getBackingCollection(), deck2.getBackingCollection());
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

        long seed = 999L;
        deck1.shuffle(seed);
        deck2.shuffle(seed);

        assertEquals(new ArrayList<>(deck1.getBackingCollection()), new ArrayList<>(deck2.getBackingCollection()));
    }

    @Test
    void seededShuffleDeterminesDealOrder() {
        Deck deck1 = new Deck.Builder("Test").deckCount(1).build();
        Hand hand1 = new Hand.Builder("P1").build();

        Deck deck2 = new Deck.Builder("Test").deckCount(1).build();
        Hand hand2 = new Hand.Builder("P2").build();

        deck1.shuffle(777L);
        deck1.deal(hand1, 5);

        deck2.shuffle(777L);
        deck2.deal(hand2, 5);

        assertEquals(hand1.getBackingCollection(), hand2.getBackingCollection());
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

    // --- Draw/Peek & State Tests (Phase 4) ---

    @Test
    void peekTopCardReturnsTopCard() {
        Deck deck = new Deck.Builder("Test").deckCount(1).build();
        deck.shuffle(12345L); // Shuffle to ensure order is consistent

        Card topCard = deck.peekTopCard();
        assertNotNull(topCard, "Top card should not be null in a full deck");
        assertEquals(((Deque<Card>) deck.getBackingCollection()).peekLast(), topCard);
    }

    @Test
    void peekTopCardOnEmptyDeckReturnsNull() {

        Deck fullDeck = new Deck.Builder("Test").deckCount(1).build();
        while (fullDeck.size() > 0) {
            fullDeck.drawTopCard(new Hand.Builder("Temp").build());
        }

        assertNull(fullDeck.peekTopCard());
    }

    @Test
    void drawTopCardRemovesFromDeck() {
        Deck deck = new Deck.Builder("Test").deckCount(1).build();
        Hand hand = new Hand.Builder("Player").build();

        int sizeBefore = deck.size();
        deck.drawTopCard(hand);

        assertEquals(sizeBefore - 1, deck.size());
        assertEquals(1, hand.size());
    }

    @Test
    void resetRestoresOriginalDeck() {
        Deck deck = new Deck.Builder("Test").deckCount(1).build();
        List<Card> originalCards = new ArrayList<>(deck.getBackingCollection());

        deck.shuffle(999L);
        deck.drawTopCard(new Hand.Builder("Temp").build()); // Remove one to make it different

        deck.reset();

        assertEquals(originalCards, new ArrayList<>(deck.getBackingCollection()));
    }

    // --- Deck Manipulation Tests ---

    @Test
    void cutSplitsDeckCorrectly() {
        Deck deck = new Deck.Builder("Test").deckCount(1).build();
        int originalSize = deck.size();

        deck.cut();

        assertEquals(originalSize, deck.size());
        assertNotEquals(new ArrayList<>(deck.getBackingCollection()), 
                        new ArrayList<>(new Deck.Builder("Test").deckCount(1).build().getBackingCollection()));
    }

    @Test
    void mergeCombinesTwoDecks() {
        Deck deck1 = new Deck.Builder("Deck1").deckCount(1).build();
        Deck deck2 = new Deck.Builder("Deck2").deckCount(1).build();
        
        int size1 = deck1.size();
        int size2 = deck2.size();

        deck1.merge(deck2);

        assertEquals(size1 + size2, deck1.size());
    }

    @Test
    void copyCreatesIndependentDeck() {
        Deck original = new Deck.Builder("Original").deckCount(1).build();
        Deck copy = original.copy("Copy");

        assertEquals(new ArrayList<>(original.getBackingCollection()), new ArrayList<>(copy.getBackingCollection()));
        assertNotSame(original.getBackingCollection(), copy.getBackingCollection());
    }

    @Test
    void splitSplitsDeckIntoTwo() {
        Deck deck = new Deck.Builder("Test").deckCount(1).build();
        int originalSize = deck.size();

        List<Deck> splitDecks = deck.split(originalSize / 2, "First", "Second");

        assertEquals(2, splitDecks.size());
        assertEquals(originalSize, splitDecks.get(0).size() + splitDecks.get(1).size());
        assertEquals(0, deck.size());
    }

    // --- Deal & State Tests ---

    @Test
    void dealRemovesCardsFromDeck() {
        Deck deck = new Deck.Builder("Standard").deckCount(1).build();
        Hand hand = new Hand.Builder("Player").build();

        deck.deal(hand, 5);

        assertEquals(47, deck.size());
        assertEquals(5, hand.size());
    }

    @Test
    void dealMovesCardsToHand() {
        Deck deck = new Deck.Builder("Standard").deckCount(1).build();
        Hand hand = new Hand.Builder("Player").build();

        deck.deal(hand, 10);

        assertEquals(42, deck.size());
        assertEquals(10, hand.size());
    }

    @Test
    void dealAllCardsWorks() {
        Deck deck = new Deck.Builder("Standard").deckCount(1).build();
        Hand hand = new Hand.Builder("Player").build();

        deck.deal(hand, 52);

        assertEquals(0, deck.size());
        assertEquals(52, hand.size());
    }

    @Test
    void dealTooManyCardsThrowsException() {
        Deck deck = new Deck.Builder("Standard").deckCount(1).build();
        Hand hand = new Hand.Builder("Player").build();

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
        Hand hand = new Hand.Builder("Player").build();

        assertThrows(IllegalArgumentException.class, () -> {
            deck.deal(hand, 0);
        });
    }

    @Test
    void dealNegativeCardsThrowsException() {
        Deck deck = new Deck.Builder("Standard").deckCount(1).build();
        Hand hand = new Hand.Builder("Player").build();

        assertThrows(IllegalArgumentException.class, () -> {
            deck.deal(hand, -1);
        });
    }
}