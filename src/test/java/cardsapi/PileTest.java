package cardsapi;

//imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PileTest {

    private static class TestPile extends Pile {

        TestPile(String name) {
            super(name);
        }

        TestPile(ArrayList<Card> cards, String name) {
            super(cards, name);
        }
    }


    @Test
    void validConstructor() {
        TestPile pile = new TestPile("Test");

        assertEquals("Test", pile.getName());
    }


    @Test
    void emptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TestPile("");
        });
    }


    @Test
    void nullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TestPile(null);
        });
    }


    @Test
    void nullCollectionThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TestPile(null, "Test");
        });
    }


    @Test
    void addCardWorks() {
        TestPile pile = new TestPile("Test");

        Card card = new Card(Rank.ACE, Suit.SPADES);

        pile.addCard(card);

        assertTrue(pile.getCards().contains(card));
    }


    @Test
    void addNullCardThrowsException() {
        TestPile pile = new TestPile("Test");

        assertThrows(IllegalArgumentException.class, () -> {
            pile.addCard(null);
        });
    }


    @Test
    void removeCardWorks() {
        TestPile pile = new TestPile("Test");

        Card card = new Card(Rank.ACE, Suit.SPADES);

        pile.addCard(card);
        pile.removeCard(card);

        assertFalse(pile.getCards().contains(card));
    }


    @Test
    void transferCardWorks() {
        TestPile source = new TestPile("Source");
        TestPile destination = new TestPile("Destination");

        Card card = new Card(Rank.ACE, Suit.SPADES);

        source.addCard(card);

        source.transferCard(destination, card);

        assertFalse(source.getCards().contains(card));
        assertTrue(destination.getCards().contains(card));
    }


    @Test
    void getCardsIsUnmodifiable() {
        TestPile pile = new TestPile("Test");

        assertThrows(UnsupportedOperationException.class, () -> {
            pile.getCards().add(new Card(Rank.ACE, Suit.SPADES));
        });
    }


    @Test
    void sizeTracksCards() {
        TestPile pile = new TestPile("Test");

        pile.addCard(new Card(Rank.ACE, Suit.SPADES));

        assertEquals(1, pile.size());
    }
}