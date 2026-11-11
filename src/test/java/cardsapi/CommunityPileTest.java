package cardsapi;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CommunityPileTest {

    // --- Builder Pattern Tests ---

    @Test
    void builderCreatesValidCommunityPile() {
        CommunityPile pile = new CommunityPile.Builder("My Community Pile")
                .maxSize(5)
                .build();

        assertEquals("My Community Pile", pile.getName());
        assertTrue(pile.isEmpty());
    }

    @Test
    void builderDefaultsNameWhenNull() {
        CommunityPile pile = new CommunityPile.Builder(null)
                .maxSize(5)
                .build();

        assertEquals("Community Pile", pile.getName());
    }

    @Test
    void builderThrowsOnZeroMaxSize() {
        assertThrows(IllegalStateException.class, () -> {
            new CommunityPile.Builder("Test").maxSize(0).build();
        });
    }

    // --- Card Manipulation Tests ---

    @Test
    void addCardWorksWhenUnderLimit() {
        CommunityPile pile = new CommunityPile.Builder("Test")
                .maxSize(3)
                .build();

        pile.addCard(new Card(Rank.ACE, Suit.SPADES));
        pile.addCard(new Card(Rank.TWO, Suit.HEARTS));

        assertEquals(2, pile.size());
    }

    @Test
    void addCardThrowsExceptionWhenAtLimit() {
        CommunityPile pile = new CommunityPile.Builder("Test")
                .maxSize(1)
                .build();

        pile.addCard(new Card(Rank.ACE, Suit.SPADES));

        assertThrows(UnsupportedOperationException.class, () -> {
            pile.addCard(new Card(Rank.KING, Suit.CLUBS));
        });
    }
}
