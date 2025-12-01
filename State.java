public class State {
    // VARIABLES
    private int height;
    private int x;
    private int y;

    // CONSTRUCTOR
    public State(int h, int x, int y) {
        height = h;
        this.x = x;
        this.y = y;
    }

    // METHODS
    public int height() {
        return height;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }
}
