package xo.view;

import xo.controllers.CurrentMoveController;
import xo.controllers.MoveController;
import xo.controllers.WinnerController;
import xo.model.Field;
import xo.model.Figure;
import xo.model.Game;
import xo.model.exceptions.AlreadyOccupiedException;
import xo.model.exceptions.InvalidPointException;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleView {

    private final CurrentMoveController currentMoveController = new CurrentMoveController();
    private final WinnerController winnerController = new WinnerController();
    private final MoveController moveController = new MoveController();

    public void show(final Game game) {
        final Field field = game.getField();
        for (int x = 0; x < field.getSize(); x++) {
            if (x != 0)
                printSeparator();
            printLine(field, x);
        }
    }

    public void showGameInfo(final Game game) {
        printSeparator2();
        showGameName(game);
        System.out.format("Player 1: %s, figure %s\n", game.getPlayers()[0].getName(), game.getPlayers()[0].getFigure());
        System.out.format("Player 2: %s, figure %s\n", game.getPlayers()[1].getName(), game.getPlayers()[1].getFigure());
        System.out.println("Good luck!");
        printSeparator2();
    }

    public void showGameName(final Game game) {
        System.out.format("Game name: \"%s\"\n", game.getName());
    }

    public boolean move(final Game game) {
        final Field field = game.getField();
        final Figure winner = winnerController.getWinner(field);
        if (winner != null) {
            printSeparator2();
            System.out.printf("Game over! The winner is: %s \u263A\n", winner);
            printSeparator2();
            return false;
        }

        final Figure currentFigure = currentMoveController.currentMove(field);
        if (currentFigure == null) {
            printSeparator3();
            System.out.println("Game over! No winner, try again \u263A");
            printSeparator3();
            return false;
        }

        System.out.printf("Make a move for %s\n", currentFigure);
        final Point point = askPoint();
        try {
            moveController.applyFigure(field, point, currentFigure);
        } catch (InvalidPointException | AlreadyOccupiedException e) {
            System.out.println("Invalid coordinates! Select different ones.");
        }
        return true;
    }

    private Point askPoint() {
        return new Point(askCoordinate("X") - 1, askCoordinate("Y") - 1);
    }

    private int askCoordinate(final String coordinateName) {
        System.out.printf("Please input %s number:", coordinateName.equals("X") ? "row" : "column");
        final Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextInt();
        } catch (final InputMismatchException e) {
            System.out.println("Invalid number!");
            return askCoordinate(coordinateName);
        }
    }

    private void printLine(final Field field,
                           final int x) {
        for (int y = 0; y < field.getSize(); y++) {
            if (y != 0)
                System.out.print("|");
            System.out.print(" ");
            final Figure figure;
            try {
                figure = field.getFigure(new Point(x, y));
            } catch (InvalidPointException e) {
                throw new RuntimeException(e);
            }
            System.out.print(figure != null ? figure : " ");
            System.out.print(" ");
        }
        System.out.println();
    }

    private void printSeparator() {
        System.out.println("-----------");
    }

    private void printSeparator2() {
        System.out.println("******************************");
    }

    private void printSeparator3() {
        System.out.println("**********************************");
    }
}
