package xo;

import xo.model.Field;
import xo.model.Figure;
import xo.model.Game;
import xo.model.Player;
import xo.view.ConsoleView;

import java.util.Scanner;

public class XOCLI {

    public final static String GAME_NAME = "XO";
    public final static String WELCOME_TEXT = "Welcome to " + GAME_NAME + " game \u263A";

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final String name1;
        final String name2;

        System.out.println(WELCOME_TEXT);

        System.out.print("Player X, please, enter your name:");
        name1 = scanner.nextLine();

        System.out.print("Player O, please, enter your name:");
        name2 = scanner.nextLine();

        final Player[] players = new Player[2];
        players[0] = new Player(name1, Figure.X);
        players[1] = new Player(name2, Figure.O);

        final Game gameXO = new Game(players, new Field(3), GAME_NAME);
        final ConsoleView consoleView = new ConsoleView();
        consoleView.showGameInfo(gameXO);
        consoleView.show(gameXO);

        while (consoleView.move(gameXO)) {
            consoleView.show(gameXO);
        }
    }
}
