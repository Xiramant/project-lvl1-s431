package games;

import java.util.Random;

//основной класс игры Однорукий бандит
public class Slot {

    //размер ставки
    private static final int BET = 10;

    //размер выигрыша
    private static final int WIN = 1000;

    //количество барабанов в автомате
    private static final int DRUM_COUNT = 3;

    //количество позиций у барабанов
    private static final int DRUM_POSITION_COUNT = 7;

    public static void main(String... __) {

        //капитал игрока
        int stack = 100;

        //массив состояний барабанов
        int[] drums = new int[DRUM_COUNT];

        while (stack > 0) {

            System.out.println(System.lineSeparator()
                    + "У Вас " + stack + "$"
                    + ", ставка - " + BET + "$");

            stack -= BET;

            System.out.println("Крутим барабаны!");

            for (int i = 0; i < drums.length; i++) {

                drums[i] = (drums[i] + new Random().nextInt(100)) % DRUM_POSITION_COUNT;
            }

            System.out.println("Розыгрыш принёс следующие результаты:");

            for (int i = 0; i < drums.length; i++) {
                System.out.print((i + 1) + "-й барабан - " + drums[i]);
                System.out.print((i == drums.length - 1) ? "." + System.lineSeparator() : ", ");
            }

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
                System.out.print("Выигрыш " + WIN + "$");
            } else {
                System.out.print("Проигрыш " + BET + "$");
            }

            System.out.print(", ваш капитал теперь составляет: "
                    + stack + "$" + System.lineSeparator());
        }
    }
}
