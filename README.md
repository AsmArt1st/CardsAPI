# CardsAPI

Version: 1.0.0

A Java card game API for creating and manipulating standard playing cards, decks of cards, and card hands.
Originally a learning project but we'll see how big this grows


## Features

Card objects with enum ranks and suits
Standard deck creation for 52-card decks (larger deck support planned)
Deck shuffling and dealing x cards to a hand
Hand management through the Hand class
JUnit test files
Maven support


## Setup

Clone the repository:

```bash
git clone https://github.com/AsmArt1st/Card-Game-API.git
```

Navigate into the project:

```bash
cd Card-Game-API
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

Card
Represents a single playing card with an enum rank and suit

Deck
Creates and manages standard 52 card decks

Hand
Stores and manages "hands" of card objects

## Example Usage

```java
import cardsapi.*;

Deck deck = new Deck("Game Deck");
Hand hand = new Hand("Player's Hand");

deck.shuffle();
deck.deal(hand, 5);

System.out.println(hand);
```

## Future Plans

-add support for bigger decks
-more utilities

-additional dec
k customization

and more to come!
