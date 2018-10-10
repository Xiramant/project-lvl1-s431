package games;

import java.util.Random;

//основной класс игры Однорукий бандит
public class Slot {

    //капитал игрока
    private static int stack = 100;

    //размер ставки
    private static final int BET = 10;

    //размер выигрыша
    private static final int WIN = 1000;

    //количество барабанов в автомате
    private static final int DRUM_COUNT = 3;

    //количество позиций у барабанов
    private static final int DRUM_POSITION_COUNT = 7;

    public static int getStack() {
        return stack;
    }

    public static void setStack(final int stackChange) {
        stack += stackChange;
    }

    public static void main(String... __) {

        //массив состояний барабанов
        int[] drums = new int[DRUM_COUNT];

        while (getStack() > 0) {

            System.out.println(System.lineSeparator()
                    + "У Вас " + getStack() + "$"
                    + ", ставка - " + BET + "$");

            setStack(-BET);

            System.out.println("Крутим барабаны!");

            for (int i = 0; i < drums.length; i++) {

                drums[i] = getDrumPosition(drums[i]);
            }

            System.out.println("Розыгрыш принёс следующие результаты:");

            for (int i = 0; i < drums.length; i++) {
                System.out.print((i + 1) + "-й барабан - " + drums[i]);
                System.out.print((i == drums.length - 1) ? "." + System.lineSeparator() : ", ");
            }

            if (isWin(drums)) {
                setStack(WIN);
                System.out.print("Выигрыш " + WIN + "$");
            } else {
                System.out.print("Проигрыш " + BET + "$");
            }

            System.out.print(", ваш капитал теперь составляет: "
                    + getStack() + "$" + System.lineSeparator());
        }
    }

    //получение новой позиции барабана
    private static int getDrumPosition(final int drumPositionInitial) {

        return (drumPositionInitial + new Random().nextInt(100)) % DRUM_POSITION_COUNT;
    }

    //проверка выигрыша (совпадения позиций барабанов)
    private static boolean isWin(final int[] drums) {

        int drumPositionFirst = drums[0];

        for (int drum :drums) {

            if (drum != drumPositionFirst) {
                return false;
            }
        }

        return true;
    }

}
