package cardsapi;

//imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class HandTest {

    @Test
    void validConstructor() {
        Hand hand = new Hand.Builder("Player").build();

        assertEquals("Player", hand.getName());
    }

    @Test
    void emptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hand.Builder("").build();
        });
    }

    @Test
    void nullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hand.Builder(null).build();
        });
    }

    @Test
    void addCardWorks() {
        Hand hand = new Hand.Builder("Player").build();
        Card card = new Card(Rank.ACE, Suit.SPADES);

        hand.addCard(card);

        assertTrue(hand.getCards().contains(card));
    }

    @Test
    void addMultipleCardsWorks() {
        Hand hand = new Hand.Builder("Player").build();

        hand.addCard(new Card(Rank.ACE, Suit.SPADES));
        hand.addCard(new Card(Rank.KING, Suit.HEARTS));

        assertEquals(2, hand.size());
    }

    @Test
    void removeCardWorks() {
        Hand hand = new Hand.Builder("Player").build();
        Card card = new Card(Rank.ACE, Suit.SPADES);

        hand.addCard(card);
        hand.removeCard(card);

        assertFalse(hand.getCards().contains(card));
    }

    @Test
    void removeMissingCardDoesNothing() {
        Hand hand = new Hand.Builder("Player").build();
        Card card = new Card(Rank.ACE, Suit.SPADES);

        assertDoesNotThrow(() -> {
            hand.removeCard(card);
        });
    }

    @Test
    void getCardsIsUnmodifiable() {
        Hand hand = new Hand.Builder("Player").build();

        hand.addCard(new Card(Rank.ACE, Suit.SPADES));

        assertThrows(UnsupportedOperationException.class, () -> {
            hand.getCards().add(new Card(Rank.KING, Suit.HEARTS));
        });
    }

    @Test
    void sizeStartsAtZero() {
        Hand hand = new Hand.Builder("Player").build();

        assertEquals(0, hand.size());
    }

    @Test
    void sizeChangesAfterAddAndRemove() {
        Hand hand = new Hand.Builder("Player").build();
        Card card = new Card(Rank.ACE, Suit.SPADES);

        hand.addCard(card);

        assertEquals(1, hand.size());

        hand.removeCard(card);

        assertEquals(0, hand.size());
    }

    @Test
    void transferCardMovesCard() {
        Hand hand = new Hand.Builder("Player").build();
        Hand destination = new Hand.Builder("Destination").build();

        Card card = new Card(Rank.ACE, Suit.SPADES);

        hand.addCard(card);

        hand.transferCard(destination, card);

        assertFalse(hand.getCards().contains(card));
        assertTrue(destination.getCards().contains(card));
    }

    @Test
    void transferNullPileThrowsException() {
        Hand hand = new Hand.Builder("Player").build();
        Card card = new Card(Rank.ACE, Suit.SPADES);

        hand.addCard(card);

        assertThrows(IllegalArgumentException.class, () -> {
            hand.transferCard(null, card);
        });
    }

    @Test
    void transferNullCardThrowsException() {
        Hand hand = new Hand.Builder("Player").build();
        Hand destination = new Hand.Builder("Destination").build();

        assertThrows(IllegalArgumentException.class, () -> {
            hand.transferCard(destination, null);
        });
    }

    @Test
    void transferMissingCardThrowsException() {
        Hand hand = new Hand.Builder("Player").build();
        Hand destination = new Hand.Builder("Destination").build();

        Card card = new Card(Rank.ACE, Suit.SPADES);

        assertThrows(IllegalArgumentException.class, () -> {
            hand.transferCard(destination, card);
        });
    }

    // --- Phase 5: Hand Utilities Tests ---

    @Test
    void rankSortOrdersCardsCorrectly() {
        Hand hand = new Hand.Builder("Player").build();
        hand.addCard(new Card(Rank.TWO, Suit.SPADES));
        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));
        hand.addCard(new Card(Rank.KING, Suit.CLUBS));

        hand.rankSort();

        List<Card> sortedCards = new ArrayList<>(hand.getBackingCollection());
        assertEquals(Rank.ACE, sortedCards.get(0).getRank());
        assertEquals(Rank.KING, sortedCards.get(1).getRank());
        assertEquals(Rank.TWO, sortedCards.get(2).getRank());
    }

    @Test
    void suitSortOrdersCardsCorrectly() {
        Hand hand = new Hand.Builder("Player").build();
        hand.addCard(new Card(Rank.ACE, Suit.SPADES));
        hand.addCard(new Card(Rank.ACE, Suit.CLUBS));
        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));

        hand.suitSort();

        List<Card> sortedCards = new ArrayList<>(hand.getBackingCollection());
        assertEquals(Suit.SPADES, sortedCards.get(0).getSuit());
        assertEquals(Suit.HEARTS, sortedCards.get(1).getSuit());
        assertEquals(Suit.CLUBS, sortedCards.get(2).getSuit());
    }

    @Test
    void playCardMovesCardToCommunityPile() {
        Hand hand = new Hand.Builder("Player").build();
        CommunityPile communityPile = new CommunityPile.Builder("Community").maxSize(10).build();
        Card card = new Card(Rank.ACE, Suit.SPADES);
        hand.addCard(card);

        hand.playCard(communityPile, card);

        assertFalse(hand.getCards().contains(card));
        assertTrue(communityPile.getCards().contains(card));
    }

    @Test
    void takeCardMovesCardFromCommunityPile() {
        Hand hand = new Hand.Builder("Player").build();
        CommunityPile communityPile = new CommunityPile.Builder("Community").maxSize(10).build();
        Card card = new Card(Rank.ACE, Suit.SPADES);
        communityPile.addCard(card);

        hand.takeCard(communityPile, card);

        assertTrue(hand.getCards().contains(card));
        assertFalse(communityPile.getCards().contains(card));
    }

    @Test
    void drawCardMovesCardFromDrawPile() {
        Hand hand = new Hand.Builder("Player").build();
        Deck sourceDeck = new Deck.Builder("Source").deckCount(1).build();
        DrawPile drawPile = new DrawPile.Builder("Draw").homeDeck(sourceDeck).build();
        Card card = drawPile.peekTopCard(); // Get the top card to use for the test

        hand.drawCard(drawPile);

        assertFalse(drawPile.getCards().contains(card));
        assertTrue(hand.getCards().contains(card));
    }

    @Test
    void discardCardMovesCardToDiscardPile() {
        Hand hand = new Hand.Builder("Player").build();
        DiscardPile discardPile = new DiscardPile.Builder("Discard").build();
        Card card = new Card(Rank.ACE, Suit.SPADES);
        hand.addCard(card);

        hand.discardCard(discardPile, card);

        assertFalse(hand.getCards().contains(card));
        assertTrue(discardPile.getCards().contains(card));
    }
}