package cardsapi;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DiscardPileTest {

    // --- Builder Pattern Tests ---

    @Test
    void builderCreatesValidDiscardPile() {
        DiscardPile pile = new DiscardPile.Builder("My Discard Pile").build();

        assertEquals("My Discard Pile", pile.getName());
        assertTrue(pile.isEmpty());
    }

    @Test
    void builderDefaultsNameWhenNull() {
        DiscardPile pile = new DiscardPile.Builder(null).build();

        assertEquals("Discard Pile", pile.getName());
    }

    // --- Card Manipulation Tests ---

    @Test
    void peekTopCardOnEmptyPileReturnsNull() {
        DiscardPile pile = new DiscardPile.Builder("Test").build();

        assertNull(pile.peekTopCard());
    }

    @Test
    void peekTopCardReturnsTopCard() {
        DiscardPile pile = new DiscardPile.Builder("Test").build();
        
        // Add a card manually for the test
        pile.addCard(new Card(Rank.ACE, Suit.SPADES));

        Card topCard = pile.peekTopCard();
        assertNotNull(topCard);
        assertEquals(Rank.ACE, topCard.getRank());
    }

    @Test
    void returnTopCardMovesCardToDeck() {
        DiscardPile pile = new DiscardPile.Builder("Test").build();
        
        // Add a card
        pile.addCard(new Card(Rank.KING, Suit.HEARTS));
        
        Deck targetDeck = new Deck.Builder("Target").deckCount(1).build();
        int deckSizeBefore = targetDeck.size();

        Card returnedCard = pile.returnTopCard(targetDeck);

        assertEquals(deckSizeBefore + 1, targetDeck.size());
        assertEquals(0, pile.size());
        assertNotNull(returnedCard);
        assertEquals(Rank.KING, returnedCard.getRank());
    }

    @Test
    void returnTopCardThrowsOnEmptyPile() {
        DiscardPile pile = new DiscardPile.Builder("Test").build();
        Deck targetDeck = new Deck.Builder("Target").deckCount(1).build();

        assertThrows(IllegalArgumentException.class, () -> {
            pile.returnTopCard(targetDeck);
        });
    }

    // --- Restriction Tests ---

    @Test
    void removeCardThrowsException() {
        DiscardPile pile = new DiscardPile.Builder("Test").build();

        assertThrows(UnsupportedOperationException.class, () -> {
            pile.removeCard(new Card(Rank.ACE, Suit.SPADES));
        });
    }
}
