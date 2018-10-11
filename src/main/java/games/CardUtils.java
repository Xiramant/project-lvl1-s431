package games;

import static org.apache.commons.math3.util.MathArrays.shuffle;

public class CardUtils {

    //количество карт одной масти в колоде
    static final int PARS_TOTAL_COUNT = Par.values().length;

    //количество карт в колоде
    static final int CARDS_TOTAL_COUNT = PARS_TOTAL_COUNT * Suit.values().length;

    //получение масти по порядковому номеру карты
    static Suit getSuit(final int cardNumber) {
        return Suit.values()[cardNumber / PARS_TOTAL_COUNT];
    }

    //получение номинала карты по порядковому номеру карты
    static Par getPar(final int cardNumber) {
        return Par.values()[cardNumber % PARS_TOTAL_COUNT];
    }

    static String toString(final int cardNumber) {
        return getPar(cardNumber) + " " + getSuit(cardNumber);
    }

    static int[] getShaffledCards() {

        // колода карт
        int[] cards = new int[CARDS_TOTAL_COUNT];

        for (int i = 0; i < CARDS_TOTAL_COUNT; i++) {
            cards[i] = i;
        }

        shuffle(cards);
        return cards;
    }
}
