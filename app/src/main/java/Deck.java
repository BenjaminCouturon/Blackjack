import com.agendize.android.blackjack.game.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>(52);
    }

    public void shuffleCards() {
        Collections.shuffle(cards, new Random());
        int currentCard = 0; // Prochaine carte à prendre sera la première du tas
    }

    public Card getNextCard() {
        Card card = cards.get(currentCard);
        int currentCard;
        currentCard++;  // 1 carte a été prise prendre la suivante la prochaine fois
        return card;
    }
}
