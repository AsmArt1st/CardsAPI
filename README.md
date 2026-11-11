# CardsAPI

Version: 2.0.0

A Java card game API for creating and manipulating standard playing cards, decks of cards, and card hands.
Originally a learning project but we'll see how big this grows


## Features

Card objects with enum ranks and suits
Standard deck creation for 52-card decks, with larger decks in multiples of 52 now supported as well
Support for Jokers (Red and Black) in decks now
Deck shuffling and dealing x cards to a hand
Specialized Piles like Draw Piles, Discard Piles, and Community Piles for easier game design
Hand management through the Hand class, now including rankSort, suitSort, and a bunch of specialized pile specific methods
Comparators to sort cards by rank and suit
New and improved builder patterns for all piles and sub-piles
JUnit test files
Maven support


## Setup

Clone the repository:

```bash
git clone https://github.com/AsmArt1st/CardsAPI
```

Navigate into the project:

```bash
cd CardsAPI
```

Run the tests:

```bash
mvn test
```

(Optional) Generate API documentation:

```bash
mvn javadoc:javadoc
```


## Classes

Card - 
Represents a single playing card with an enum rank and suit

Rank - 
Enum definitions of card ranks with sorting support

Suit - 
Enum definitions of card suits with sorting support

Joker - 
Enum definitions of jokers with sorting support

Pile - 
Abstract base class for collections of cards

Deck - 
Creates and manages standard 52 card decks

Hand - 
Stores and manages "hands" of card objects

DrawPile - 
Deck-like pile that supports drawing and prevents adding cards

DiscardPile - 
A pile that supports adding cards back to a deck and prevents removal of cards

CommunityPile - 
A pile that allows for the adding and removal of cards through hands

## Example Usage

```java
import cardsapi.*;

// Create a Deck with Jokers using the Builder
Deck deck = new Deck.Builder("Game Deck")
                    .deckCount(1)
                    .withJokers(true)
                    .isShuffled(true)
                    .build();

// Create a Hand
Hand hand = new Hand.Builder("Player's Hand").build();

// Create a CommunityPile with a max size of 5
CommunityPile community = new CommunityPile.Builder("Board")
                                           .maxSize(5)
                                           .build();

// Deal Cards
deck.deal(hand, 5);

// Sort the Hand by Rank
hand.rankSort();

// Play a Card to the CommunityPile
Card topCard = hand.getCards().iterator().next(); // In practice, you'd find the specific card
hand.playCard(community, topCard);

System.out.println(hand);
```

## Future Plans

-Hand Evaluation Methods

-Common card game support (ie Blackjack or Poker)

-Even more utilites

and more to come!
