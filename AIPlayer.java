public class AIPlayer {
    private final Seed aiSeed;
    private final Seed opponentSeed;

    public AIPlayer(Seed aiSeed) {
        this.aiSeed = aiSeed;
        this.opponentSeed = (aiSeed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
    }

    public int[] getNextMove(Seed[][] cells) {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                if (cells[row][col] == Seed.NO_SEED) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }
}
