import model.Board;
import view.BoardConsole;

public class Application {
    public static void main(String[] args) {

        Board board = new Board(6, 6, 8);
        new BoardConsole(board);

    }
}
