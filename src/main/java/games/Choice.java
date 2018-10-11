package games;

import java.io.IOException;

public class Choice {

    static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void main(String... __) {

        System.out.println("Выберите игру:\n" +
                "1 - \"однорукий бандит\", " +
                "2 - \"пьяница\", " +
                "3 - \"очко\"");

        try {
            switch(getCharacterFromUser()) {
                case '1':
                    Slot.main();
                    break;
                case '2':
                    Drunkard.main();
                    break;
                case '3':
                    BlackJack.main();
                    break;
                default:
                    System.out.println("Игры с таким номером нет!");
            }
        } catch (IOException e) {}

    }

    static char getCharacterFromUser() throws IOException {
        byte[] input = new byte[1 + LINE_SEPARATOR.length()];
        if (System.in.read(input) != input.length)
            throw new RuntimeException("Пользователь ввёл недостаточное кол-во символов");
        return (char) input[0];
    }

}
