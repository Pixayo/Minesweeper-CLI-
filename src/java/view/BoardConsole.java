package view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import model.Board;
import exceptions.ExplosionException;
import exceptions.ExitException;

public class BoardConsole {

    private final Board board;
    private final Scanner in = new Scanner(System.in);

    public BoardConsole(Board board) {
        this.board = board;
        executeGame();
    }

    private void executeGame() {
        try {
            boolean running = true;

            while (running) {
                String opt = getInput("Next round? (Y/n) ");

                if ("n".equalsIgnoreCase(opt)) {
                    running = false;
                } else {
                    board.resetBoard();
                    gameLoop();
                }
            }
        } catch (ExitException e) {
            System.out.println("Game finished!");
        } finally {
            in.close();
        }
    }

    private void gameLoop() {
        try {
            while(!board.isObjectiveComplete()) {
                System.out.println(board);
                String input = getInput("Type (x, y): ");

                Iterator<Integer> xy = Arrays.stream(input.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();

                input = getInput("1 - Explore or 2 - (un)Flag : ");
                if ("1".equals(input.trim())) {
                    board.exploreFieldIn(xy.next(), xy.next());
                } else if ("2".equals(input.trim())) {
                    board.flagFieldIn(xy.next(), xy.next());
                }
            }

            System.out.println(board);
            System.out.println("You Won!!!");

        } catch (ExplosionException e) {
            System.out.println(board);
            System.out.println("Don't dig mines!!!");
        }
    }

    private String getInput(String msg) {
        System.out.print(msg);
        String input = in.nextLine();

        if ("exit".equalsIgnoreCase(input)) {
            throw new ExitException();
        }

        return input;
    }
}
