package games;

import org.slf4j.Logger;

import static games.CardUtils.*;

public class Drunkard {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Drunkard.class);

    private static StringBuffer message = new StringBuffer();

    //массив карт 2-х игроков
    private static int[][] playersCards = new int[2][CARDS_TOTAL_COUNT + 1];

    //массивы указателей на карты игроков
    private static int[] playersCardsBeginCursors = {0, 0};
    private static int[] playersCardsEndCursors = {18, 18};

    //массив карт для сравнения
    private static int[] cardsForComparison = new int[2];

    public static void main(String... __) {


        
        initialGame();

        while (findCardsNumber(0) != 0 && findCardsNumber(1) != 0) {

            cardsForComparison[0] = playersCards[0][playersCardsBeginCursors[0]];
            playersCardsBeginCursors[0] = incrementIndex(playersCardsBeginCursors[0]);

            message.append("Игрок №1 карта: " + CardUtils.toString(cardsForComparison[0]) + ";");

            cardsForComparison[1] = playersCards[1][playersCardsBeginCursors[1]];
            playersCardsBeginCursors[1] = incrementIndex(playersCardsBeginCursors[1]);

            message.append(" Игрок №2 карта: " + CardUtils.toString(cardsForComparison[1]) + ".");

            //выигрыш первого игрока
            if (findWinCard(getPar(cardsForComparison[0]), getPar(cardsForComparison[1])) == 1) {

                receiveCard(cardsForComparison[0], 0);
                receiveCard(cardsForComparison[1], 0);

                message.append(" Выиграл игрок 1!");
                playersCardsPrint();

                log.info(message.toString());
                message.setLength(0);

                continue;
            }

            //ничья
            if (findWinCard(getPar(cardsForComparison[0]), getPar(cardsForComparison[1])) == 0) {

                receiveCard(cardsForComparison[0], 0);
                receiveCard(cardsForComparison[1], 1);

                message.append(" Спор - каждый остаётся при своих!");
                playersCardsPrint();

                log.info(message.toString());
                message.setLength(0);

                continue;
            }

            //выигрыш второго игрока
            receiveCard(cardsForComparison[0], 1);
            receiveCard(cardsForComparison[1], 1);

            message.append(" Выиграл игрок 2!");
            playersCardsPrint();

            log.info(message.toString());
            message.setLength(0);
        }

        log.info(((findCardsNumber(0) == 36) ? "Победил первый игрок!" : "Победил второй игрок!"));
    }

    //изменение индекса начала/конца колоды
    private static int incrementIndex(final int i) {
        return (i + 1) % (CARDS_TOTAL_COUNT + 1);
    }

    //получение количества карт у игрока
    private static int findCardsNumber(final int player) {

        return playersCardsEndCursors[player] - playersCardsBeginCursors[player] +
                ((playersCardsEndCursors[player] >= playersCardsBeginCursors[player]) ? 0: CARDS_TOTAL_COUNT + 1);
    }

    //возврат карты игроку
    private static void receiveCard(final int card, final int player) {

        playersCards[player][playersCardsEndCursors[player]] = card;
        playersCardsEndCursors[player] = incrementIndex(playersCardsEndCursors[player]);
    }

    //Раздача карт игрокам
    private static void initialGame() {

        int[] cardsFull = getShaffledCards();

        for (int i = 0; i <= 17; i++) {
            playersCards[0][i] = cardsFull[i];
            playersCards[1][i] = cardsFull[i + 18];
        }
    }

    //проверка на выигрыш первой карты по сравнению со второй по правилам пьяницы
    // 1 - выигрыш 1 карты
    // 0 - ничья
    // -1 - выигрыш 2 карты
    private static int findWinCard(final Par card1, final Par card2) {

        return (card1.compareTo(card2) == 0) ? 0 :
                (card1.equals(Par.SIX) && card2.equals(Par.ACE)) ? 1:
                        (card1.equals(Par.ACE) && card2.equals(Par.SIX)) ? -1 :
                                (card1.compareTo(card2) > 0) ? 1 : -1;
    }

    //Печать количества карт у каждого игрока
    private static void playersCardsPrint() {
        message.append(" У игрока №1 " +
                findCardsNumber(0) +
                " карт,");
        message.append(" у игрока №2 " +
                findCardsNumber(1) +
                " карт");
    }
}
