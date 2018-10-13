package games;

import java.io.IOException;
import org.slf4j.Logger;

public class BlackJack {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BlackJack.class);

    // Основная колода
    private static int[] cards;

    // Счётчик карт основной колоды
    private static int cursor;

    // карты игроков. Первый индекс - номер игрока
    private static int[][] playersCards;

    // курсоры карт игроков. Индекс - номер игрока
    private static int[] playersCursors;

    // Деньги игроков
    private static int[] playersMoney = {100, 100};

    //Максимальное значение для выигрыша
    private static final int MAX_VALUE = 21;

    //Максимальное количество карт у игрока/компьютера
    private static final int MAX_CARDS_COUNT = 8;

    //количество очков после которого не стоит брать карту
    private static final int BREAK_POINT = 17;

    public static void main(String... __) throws IOException {

        while (playersMoney[0] != 0 &&
                playersMoney[1] != 0) {

            initRound();

            while (playersCursors[0] < 2 ||
                    (sum(0) < 20 && confirm("Берём ещё?"))) {
                addCard2Player(0);
            }

            while (playersCursors[1] < 2 ||
                    (getFinalSum(0) != 0 && sum(1) < BREAK_POINT)) {
                if (!(playersCursors[1] < 2)) log.info("Компьютер решил взять ещё карту.");
                addCard2Player(1);
            }

            log.info("Сумма ваших очков - " + getFinalSum(0) +
                    ", компьютера - " + getFinalSum(1));

            if (getFinalSum(0) > getFinalSum(1)) {

                playersMoney[0] += 10;
                playersMoney[1] -= 10;

                log.info("Вы выиграли раунд! Получаете 10$" + System.lineSeparator());

                continue;
            }

            if (getFinalSum(0) < getFinalSum(1)) {

                playersMoney[0] -= 10;
                playersMoney[1] += 10;

                log.info("Вы проиграли раунд! Теряете 10$" + System.lineSeparator());

                continue;
            }

            log.info("Ничья! Вы остаетесь при своих деньгах" + System.lineSeparator());
        }

        if (playersMoney[0] > 0)
            log.info("Вы выиграли! Поздравляем!");
        else
            log.info("Вы проиграли! Соболезнуем!");

    }

    //Перевод номинала карты в количество очков в игре "Очко"
    private static int value(final int card) {

        switch (CardUtils.getPar(card)) {
            case JACK: return 2;
            case QUEEN: return 3;
            case KING: return 4;
            case SIX: return 6;
            case SEVEN: return 7;
            case EIGHT: return 8;
            case NINE: return 9;
            case TEN: return 10;
            case ACE:
            default: return 11;
        }
    }

    // Инициализация переменных в начале раунда
    private static void initRound() {

        log.info("У Вас " + playersMoney[0] +
                "$, у компьютера - " + playersMoney[1] +
                "$. Начинаем новый раунд!");

        cards = CardUtils.getShaffledCards();
        playersCards = new int[2][MAX_CARDS_COUNT];
        cursor = 0;
        playersCursors = new int[]{0, 0};
    }

    //Результат действия - взять карту
    private static void addCard2Player(final int player) {

        playersCards[player][playersCursors[player]] = cards[cursor];

        if (player == 0) {
            log.info("Вам выпала карта " + CardUtils.toString(cards[cursor]));
        } else {
            log.info("Компьютеру выпала карта " + CardUtils.toString(cards[cursor]));
        }

        cursor++;
        playersCursors[player]++;
    }

    //Подсчет количества очков у игрока
    private static int sum(int player) {

        int sum = 0;

        for (int i = 0; i < playersCursors[player]; i++) {
            sum += value(playersCards[player][i]);
        }

        return sum;
    }

    //Подсчет количества очков игрока
    // с учетом обнуления очков, если сумма больше 21
    private static int getFinalSum(int player) {

        return (sum(player) > MAX_VALUE) ? 0: sum(player);
    }

    //Подтверждение на вопрос - берете ли вы карту?
    private static boolean confirm(String message) throws IOException {

        log.info(message + " \"Y\" - Да, " + "{любой другой символ} - нет (Что бы выйти из игры, нажмите Ctrl + C)");

        switch (Choice.getCharacterFromUser()) {
            case 'Y':
            case 'y': return true;
            default: return false;
        }
    }

}
