package games;

import java.io.IOException;
import org.slf4j.Logger;

public class Choice {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Choice.class);

    static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void main(String... __) throws IOException{

        log.info(System.lineSeparator() +
                "Выберите игру:" + System.lineSeparator() +
                "1 - \"однорукий бандит\", " +
                "2 - \"пьяница\", " +
                "3 - \"очко\"");

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
                log.info("Игры с таким номером нет!");
        }
    }

    static char getCharacterFromUser() throws IOException {
        byte[] input = new byte[1 + LINE_SEPARATOR.length()];
        if (System.in.read(input) != input.length)
            throw new RuntimeException("Пользователь ввёл недостаточное кол-во символов");
        return (char) input[0];
    }

}
