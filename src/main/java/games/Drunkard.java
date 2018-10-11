package games;

import static org.apache.commons.math3.util.MathArrays.shuffle;

public class Drunkard {

    //количество карт одной масти в колоде
    private static final int PARS_TOTAL_COUNT = Par.values().length;

    //количество карт в колоде
    private static final int CARDS_TOTAL_COUNT = PARS_TOTAL_COUNT * Suit.values().length;

    //массив карт 2-х игроков
    private static int[][] playersCards = new int[2][CARDS_TOTAL_COUNT];

    //массивы указателей на карты игроков
    private static int[] playersCardsBeginCursors = new int[2];
    private static int[] playersCardsEndCursors = new int[2];

    //массив карт для сравнения
    private static int[] cardsForComparison = new int[2];

    public static void main(String... __) {

        //полная колода карт
        int[] cardsFull = cardsFull();

        shuffle(cardsFull);

        for (int i = 0; i < 2; i++) {
            playersCards[i] = cardsFull.clone();
        }

        playersCardsBeginCursors[0] = 0;
        playersCardsEndCursors[0] = 17;

        playersCardsBeginCursors[1] = 18;
        playersCardsEndCursors[1] = 35;

        int cardCountPlayer1 = 18;

        while (cardCountPlayer1 != 0 && cardCountPlayer1 != 36) {

            cardsForComparison[0] = playersCards[0][playersCardsBeginCursors[0]];
            playersCardsBeginCursors[0] = incrementIndex(playersCardsBeginCursors[0]);

            System.out.print("Игрок №1 карта: " + toString(cardsForComparison[0]) + ";");

            cardsForComparison[1] = playersCards[1][playersCardsBeginCursors[1]];
            playersCardsBeginCursors[1] = incrementIndex(playersCardsBeginCursors[1]);

            System.out.print(" Игрок №2 карта: " + toString(cardsForComparison[1]) + ".");

            //выигрыш первого игрока
            if (findWinCard(getPar(cardsForComparison[0]), getPar(cardsForComparison[1])) == 1) {

                receiveCard(cardsForComparison[0], 0);
                receiveCard(cardsForComparison[1], 0);

                cardCountPlayer1++;

                System.out.print(" Выиграл игрок 1!");
                System.out.print(" У игрока №1 " +
                        findCardsNumber(playersCardsBeginCursors[0], playersCardsEndCursors[0]) +
                        " карт,");
                System.out.println(" у игрока №2 " +
                        findCardsNumber(playersCardsBeginCursors[1], playersCardsEndCursors[1]) +
                        " карт");

                continue;
            }

            //ничья
            if (findWinCard(getPar(cardsForComparison[0]), getPar(cardsForComparison[1])) == 0) {

                receiveCard(cardsForComparison[0], 0);
                receiveCard(cardsForComparison[1], 1);

                System.out.print(" Спор - каждый остаётся при своих!");
                System.out.print(" У игрока №1 " +
                        findCardsNumber(playersCardsBeginCursors[0], playersCardsEndCursors[0]) +
                        " карт,");
                System.out.println(" у игрока №2 " +
                        findCardsNumber(playersCardsBeginCursors[1], playersCardsEndCursors[1]) +
                        " карт");

                continue;
            }

            //выигрыш второго игрока
            receiveCard(cardsForComparison[0], 1);
            receiveCard(cardsForComparison[1], 1);

            cardCountPlayer1--;

            System.out.print(" Выиграл игрок 2!");
            System.out.print(" У игрока №1 " +
                    findCardsNumber(playersCardsBeginCursors[0], playersCardsEndCursors[0]) +
                    " карт,");
            System.out.println(" у игрока №2 " +
                    findCardsNumber(playersCardsBeginCursors[1], playersCardsEndCursors[1]) +
                    " карт");
        }

        if (cardCountPlayer1 == 36) {
            System.out.println("Победил первый игрок!");
        } else {
            System.out.println("Победил второй игрок!");
        }

    }

    //получение масти по порядковому номеру карты
    private static Suit getSuit(final int cardNumber) {
        return Suit.values()[cardNumber / PARS_TOTAL_COUNT];
    }

    //получение номинала карты по порядковому номеру карты
    private static Par getPar(final int cardNumber) {
        return Par.values()[cardNumber % PARS_TOTAL_COUNT];
    }

    private static String toString(final int cardNumber) {
        return getPar(cardNumber) + " " + getSuit(cardNumber);
    }

    //изменение индекса начала/конца колоды
    private static int incrementIndex(final int i) {
        return (i + 1) % CARDS_TOTAL_COUNT;
    }

    //получение количества карт у игрока
    private static int findCardsNumber(final int indexBegin, final int indexEnd) {

        return indexEnd - indexBegin + 1 +
                ((indexEnd >= indexBegin) ? 0: CARDS_TOTAL_COUNT);
    }

    //получение массива полной колоды карт
    private static int[] cardsFull() {

        int[] cardsFull = new int[CARDS_TOTAL_COUNT];

        for (int i = 0; i < CARDS_TOTAL_COUNT; i++) {
            cardsFull[i] = i;
        }

        return cardsFull;
    }

    //возврат карты игроку
    private static void receiveCard(final int card, final int player) {

        playersCards[player][playersCardsEndCursors[player]] = card;
        playersCardsEndCursors[player] = incrementIndex(playersCardsEndCursors[player]);
    }

    //проверка на выигрыш первой карты по сравнению со второй по правилам пьяницы
    // 1 - выигрыш 1 карты
    // 0 - ничья
    // -1 - выигрыш 2 карты
    private static int findWinCard(final Par card1, final Par card2) {

        if (card1.compareTo(card2) > 0 ||
                (card1.equals(Par.SIX) && card2.equals(Par.ACE))) {

            if (! (card1.equals(Par.ACE) && card2.equals(Par.SIX))) {
                return 1;
            }
        }

        if (card1.compareTo(card2) == 0) return 0;

        return -1;
    }

}
