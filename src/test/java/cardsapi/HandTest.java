package cardsapi;

//imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class HandTest {

    @Test
    void validConstructor() {
        Hand hand = new Hand("Player");

        assertEquals("Player", hand.getName());
    }

    @Test
    void emptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hand("");
        });
    }

    @Test
    void nullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hand(null);
        });
    }

    @Test
    void addCardWorks() {
        Hand hand = new Hand("Player");
        Card card = new Card(Rank.ACE, Suit.SPADES);

        hand.addCard(card);

        assertTrue(hand.getCards().contains(card));
    }

    @Test
    void addMultipleCardsWorks() {
        Hand hand = new Hand("Player");

        hand.addCard(new Card(Rank.ACE, Suit.SPADES));
        hand.addCard(new Card(Rank.KING, Suit.HEARTS));

        assertEquals(2, hand.size());
    }

    @Test
    void removeCardWorks() {
        Hand hand = new Hand("Player");
        Card card = new Card(Rank.ACE, Suit.SPADES);

        hand.addCard(card);
        hand.removeCard(card);

        assertFalse(hand.getCards().contains(card));
    }

    @Test
    void removeMissingCardDoesNothing() {
        Hand hand = new Hand("Player");
        Card card = new Card(Rank.ACE, Suit.SPADES);

        assertDoesNotThrow(() -> {
            hand.removeCard(card);
        });
    }

    @Test
    void getCardsIsUnmodifiable() {
        Hand hand = new Hand("Player");

        hand.addCard(new Card(Rank.ACE, Suit.SPADES));

        assertThrows(UnsupportedOperationException.class, () -> {
            hand.getCards().add(new Card(Rank.KING, Suit.HEARTS));
        });
    }

    @Test
    void sizeStartsAtZero() {
        Hand hand = new Hand("Player");

        assertEquals(0, hand.size());
    }

    @Test
    void sizeChangesAfterAddAndRemove() {
        Hand hand = new Hand("Player");
        Card card = new Card(Rank.ACE, Suit.SPADES);

        hand.addCard(card);

        assertEquals(1, hand.size());

        hand.removeCard(card);

        assertEquals(0, hand.size());
    }

    @Test
    void transferCardMovesCard() {
        Hand source = new Hand("Source");
        Hand destination = new Hand("Destination");

        Card card = new Card(Rank.ACE, Suit.SPADES);

        source.addCard(card);

        source.transferCard(destination, card);

        assertFalse(source.getCards().contains(card));
        assertTrue(destination.getCards().contains(card));
    }

    @Test
    void transferNullPileThrowsException() {
        Hand hand = new Hand("Player");
        Card card = new Card(Rank.ACE, Suit.SPADES);

        hand.addCard(card);

        assertThrows(IllegalArgumentException.class, () -> {
            hand.transferCard(null, card);
        });
    }

    @Test
    void transferNullCardThrowsException() {
        Hand hand = new Hand("Player");
        Hand destination = new Hand("Destination");

        assertThrows(IllegalArgumentException.class, () -> {
            hand.transferCard(destination, null);
        });
    }

    @Test
    void transferMissingCardThrowsException() {
        Hand hand = new Hand("Player");
        Hand destination = new Hand("Destination");

        Card card = new Card(Rank.ACE, Suit.SPADES);

        assertThrows(IllegalArgumentException.class, () -> {
            hand.transferCard(destination, card);
        });
    }
}
