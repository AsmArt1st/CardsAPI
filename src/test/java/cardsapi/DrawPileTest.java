package cardsapi;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class DrawPileTest {

    // --- Builder Pattern Tests ---

    @Test
    void builderCreatesValidDrawPile() {
        Deck sourceDeck = new Deck.Builder("Source").deckCount(1).build();
        DrawPile pile = new DrawPile.Builder("My Draw Pile")
                .homeDeck(sourceDeck)
                .build();

        assertEquals("My Draw Pile", pile.getName());
        assertEquals(52, pile.size());
    }

    @Test
    void builderDefaultsNameWhenNull() {
        Deck sourceDeck = new Deck.Builder("Source").deckCount(1).build();
        DrawPile pile = new DrawPile.Builder(null)
                .homeDeck(sourceDeck)
                .build();

        assertEquals("Draw Pile", pile.getName());
    }

    @Test
    void builderThrowsOnNullDeck() {
        assertThrows(IllegalStateException.class, () -> {
            new DrawPile.Builder("Test").homeDeck(null).build();
        });
    }

    @Test
    void builderShufflesWhenRequested() {
        Deck sourceDeck = new Deck.Builder("Source").deckCount(1).build();
        DrawPile pile = new DrawPile.Builder("Shuffled")
                .homeDeck(sourceDeck)
                .isShuffled(true)
                .build();

        // If it was shuffled, the order should be different from the source
        assertNotEquals(new ArrayList<>(sourceDeck.getBackingCollection()), 
                        new ArrayList<>(pile.getBackingCollection()));
    }

    // --- Card Manipulation Tests ---

    @Test
    void peekTopCardReturnsTopCard() {
        Deck sourceDeck = new Deck.Builder("Source").deckCount(1).build();
        sourceDeck.shuffle(12345L); // Ensure deterministic order for test
        
        DrawPile pile = new DrawPile.Builder("Test")
                .homeDeck(sourceDeck)
                .build();

        Card topCard = pile.peekTopCard();
        assertNotNull(topCard);
        assertEquals(sourceDeck.peekTopCard(), topCard);
    }

    // --- Restriction Tests ---

    @Test
    void addCardThrowsException() {
        Deck sourceDeck = new Deck.Builder("Source").deckCount(1).build();
        DrawPile pile = new DrawPile.Builder("Test")
                .homeDeck(sourceDeck)
                .build();

        assertThrows(UnsupportedOperationException.class, () -> {
            pile.addCard(new Card(Rank.ACE, Suit.SPADES));
        });
    }

    @Test
    void removeCardThrowsException() {
        Deck sourceDeck = new Deck.Builder("Source").deckCount(1).build();
        DrawPile pile = new DrawPile.Builder("Test")
                .homeDeck(sourceDeck)
                .build();

        assertThrows(UnsupportedOperationException.class, () -> {
            pile.removeCard(new Card(Rank.ACE, Suit.SPADES));
        });
    }
}
