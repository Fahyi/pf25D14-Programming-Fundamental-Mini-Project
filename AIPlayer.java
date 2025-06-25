public class AIPlayer {
    private final Seed aiSeed;
    private final Seed opponentSeed;
    private final Difficulty difficulty;

    public AIPlayer(Seed aiSeed, Difficulty difficulty) {
        this.aiSeed = aiSeed;
        this.opponentSeed = (aiSeed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
        this.difficulty = difficulty;
    }

    public int[] getNextMove(Seed[][] cells) {
        return switch (difficulty) {
            case EASY -> getRandomMove(cells);
            case MEDIUM -> getBlockingMove(cells);
            case HARD -> getBestMove(cells);
        };
    }

    private int[] getRandomMove(Seed[][] cells) {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                if (cells[row][col] == Seed.NO_SEED) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    private int[] getBlockingMove(Seed[][] cells) {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                if (cells[row][col] == Seed.NO_SEED) {
                    cells[row][col] = opponentSeed;
                    if (isWinning(cells, opponentSeed)) {
                        cells[row][col] = Seed.NO_SEED;
                        return new int[]{row, col};
                    }
                    cells[row][col] = Seed.NO_SEED;
                }
            }
        }
        return getRandomMove(cells);
    }

    private int[] getBestMove(Seed[][] board) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == Seed.NO_SEED) {
                    board[row][col] = aiSeed;
                    int score = minimax(board, 0, false);
                    board[row][col] = Seed.NO_SEED;
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{row, col};
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(Seed[][] board, int depth, boolean isMaximizing) {
        State result = evaluate(board);
        if (result != State.PLAYING) {
            return switch (result) {
                case CROSS_WON -> aiSeed == Seed.CROSS ? 10 - depth : depth - 10;
                case NOUGHT_WON -> aiSeed == Seed.NOUGHT ? 10 - depth : depth - 10;
                case DRAW -> 0;
                default -> 0;
            };
        }

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == Seed.NO_SEED) {
                        board[row][col] = aiSeed;
                        best = Math.max(best, minimax(board, depth + 1, false));
                        board[row][col] = Seed.NO_SEED;
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == Seed.NO_SEED) {
                        board[row][col] = opponentSeed;
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board[row][col] = Seed.NO_SEED;
                    }
                }
            }
            return best;
        }
    }

    private State evaluate(Seed[][] board) {
        Board tempBoard = new Board();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                tempBoard.cells[r][c].content = board[r][c];
            }
        }
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] != Seed.NO_SEED) {
                    State result = tempBoard.checkGameState(board[r][c], r, c);
                    if (result != State.PLAYING) return result;
                }
            }
        }
        return State.PLAYING;
    }

    private boolean isWinning(Seed[][] board, Seed player) {
        Board tempBoard = new Board();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                tempBoard.cells[r][c].content = board[r][c];
            }
        }
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == player) {
                    State result = tempBoard.checkGameState(player, r, c);
                    if (result == State.CROSS_WON || result == State.NOUGHT_WON) return true;
                }
            }
        }
        return false;
    }
}
