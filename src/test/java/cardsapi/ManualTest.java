package cardsapi;

//imports yay
import java.util.ArrayList;

/**
 * Just a general testing file with main() to test at your own discretion
 */

public class ManualTest {

    public static void main(String[] args) {
        
        //initialization
        Deck gameDeck = new Deck.Builder("Game Deck")
                                .deckCount(1)
                                .withJokers(false)
                                .isShuffled(true)
                                .build();

        Deck drawDeck = new Deck.Builder("Source Deck")
                                .deckCount(1)
                                .withJokers(false)
                                .isShuffled(true)
                                .build();

        DrawPile gameDrawPile = new DrawPile.Builder("Draw Pile")
                                        .homeDeck(drawDeck)
                                        .build();

        DiscardPile gameDiscardPile = new DiscardPile.Builder("Discard Pile").build();

        CommunityPile gameCommunityPile = new CommunityPile.Builder("Community Pile")
                                                         .maxSize(5)
                                                         .build();

        Hand p1Hand = new Hand.Builder("Player One").build();

        Hand p2Hand = new Hand.Builder("Player Two").build();

        System.out.println("\nInitialization Test");
        System.out.println("\n" + gameDeck);
        System.out.println("\n" + gameDrawPile);
        System.out.println("\n" + gameDiscardPile);
        System.out.println("\n" + gameCommunityPile);
        System.out.println("\n" + p1Hand);
        System.out.println("\n" + p2Hand);

        try {

            Thread.sleep(1000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        
        //gameplay methods

        System.out.println("\nGameplay Methods Test");

        gameDeck.deal(p1Hand, 5);
        System.out.println("\n" + p1Hand);

        gameDeck.deal(p2Hand, 5);
        System.out.println("\n" + p2Hand);

        p1Hand.playCard(gameCommunityPile, ((ArrayList<Card>) p1Hand.getBackingCollection()).get(0));
        System.out.println("\n" + p1Hand);
        System.out.println("\n" + gameCommunityPile);

        p2Hand.takeCard(gameCommunityPile, ((ArrayList<Card>) gameCommunityPile.getBackingCollection()).get(0));
        System.out.println("\n" + p2Hand);
        System.out.println("\n" + gameCommunityPile);

        p1Hand.drawCard(gameDrawPile);
        System.out.println("\n" + p1Hand);
        System.out.println("\n" + gameDrawPile);

        p1Hand.discardCard(gameDiscardPile, ((ArrayList<Card>) p1Hand.getBackingCollection()).get(0));
        System.out.println("\n" + p1Hand);
        System.out.println("\n" + gameCommunityPile);

        gameDiscardPile.returnTopCard(drawDeck);
        System.out.println("\n" + gameDiscardPile);
        System.out.println("\n" + gameDeck);

        try {

            Thread.sleep(1000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        //sorting methods

        System.out.println("\nSorting Methods Test");

        System.out.println("\n" + p1Hand);

        p1Hand.rankSort();
        System.out.println("\n" + p1Hand);

        p1Hand.suitSort();
        System.out.println("\n" + p1Hand);
    }
}

