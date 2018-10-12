package games;

import java.util.Random;
import org.slf4j.Logger;

//основной класс игры Однорукий бандит
public class Slot {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Slot.class);

    //размер ставки
    private static final int BET = 10;

    //размер выигрыша
    private static final int WIN = 1000;

    //количество барабанов в автомате
    private static final int DRUM_COUNT = 3;

    //количество позиций у барабанов
    private static final int DRUM_POSITION_COUNT = 7;

    public static void main(String... __) {

        StringBuffer message = new StringBuffer();

        //капитал игрока
        int stack = 100;

        //массив состояний барабанов
        int[] drums = new int[DRUM_COUNT];

        while (stack > 0) {

            log.info("У Вас " + stack + "$"
                    + ", ставка - " + BET + "$");

            stack -= BET;

            message.append("Крутим барабаны!");

            for (int i = 0; i < drums.length; i++) {

                drums[i] = (drums[i] + new Random().nextInt(100)) % DRUM_POSITION_COUNT;
            }

            message.append(" Розыгрыш принёс следующие результаты:");

            log.info(message.toString());
            message.setLength(0);

            for (int i = 0; i < drums.length; i++) {
                message.append((i + 1) + "-й барабан - " + drums[i]);
                message.append((i == drums.length - 1) ? "." : ", ");
            }

            log.info(message.toString());
            message.setLength(0);

            //флаг выигрыша
            boolean isWin = true;

            //позиция первого барабана
            // для определения наличия выигрыша
            int drumPositionFirst = drums[0];

            for (int drum :drums) {

                if (drum != drumPositionFirst) {
                    isWin = false;
                    break;
                }
            }

            if (isWin) {
                stack += WIN;
                message.append("Выигрыш " + WIN + "$");
            } else {
                message.append("Проигрыш " + BET + "$");
            }

            message.append(", ваш капитал теперь составляет: "
                    + stack + "$" + System.lineSeparator());

            log.info(message.toString());
            message.setLength(0);
        }
    }
}
