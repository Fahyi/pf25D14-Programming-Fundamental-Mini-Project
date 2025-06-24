public class Move {
    private char player;
    private int row;
    private int col;
    private int moveNumber;

    public Move(char player, int row, int col, int moveNumber) {
        this.player = player;
        this.row = row;
        this.col = col;
        this.moveNumber = moveNumber;
    }

    public char getPlayer() {
        return player;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    @Override
    public String toString() {
        return "Move{" +
                "player=" + player +
                ", row=" + row +
                ", col=" + col +
                ", moveNumber=" + moveNumber +
                '}';
    }
}
