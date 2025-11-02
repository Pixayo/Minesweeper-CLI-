import model.Field;

public class Application {
    public static void main(String[] args) {

        Field test = new Field(1,1);
        Field neighbor = new Field(2,2);
        Field farNeighbor = new Field(3,3);

        System.out.println(test);

        test.explore();
        System.out.println(test);

        test.reset();
        test.toggleFlagged();
        test.explore();
        System.out.println(test);

        test.addNeighbor(neighbor);
        neighbor.addNeighbor(farNeighbor);
        farNeighbor.placeMine();

        test.reset();
        test.explore();
        System.out.println(test + " " + neighbor + " " + farNeighbor);

    }
}
