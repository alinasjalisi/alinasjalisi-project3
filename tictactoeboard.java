//logic for constituting a win/draw/loss

public class tictactoeboard {

    // Creates a 2D character array to represent the 3x3 board
    private final char[][] board = new char[3][3];

    // Constructor that calls initializeboard() method to create a fresh board
    public void TicTacToeBoard() {
        //board = new char[3][3]; 
        initializeBoard();
    }

    // Initialize the board with '-' symbols in every cell
    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    // Method to update the board with the specified symbol at the given row and column
    public void updateBoard(int row, int col, char symbol) {
        // Check if the cell is already occupied
        if (board[row][col] == '-') {
            // Update the cell with the specified symbol
            board[row][col] = symbol;
        } else {
            // Throw an exception if the 2d array's cell is already occupied
            throw new IllegalArgumentException("Cell already occupied! Click an available cell.");
        }
    }

    // Method to check if the specified symbol has won the game
    public boolean isWinner(char symbol) {
        //evaluates if any of the checkRows, checkColumns, or checkDiagonals methods are true. 
            //if even one of them is true, then isWinner returns true (as perscribed in the 3 methods below)
            //if all of them are false, then isWinner returns false (as perscribed in the 3 methods below)
        return (checkRows(symbol) || checkColumns(symbol) || checkDiagonals(symbol));
        //****** should we have a statement here declaring witch symbol is a winner? or will that be in GUI/client
        // call stateWin method from gameGUI that prompts game to call a pop up message that displays which user x or o won
        
    }

    // Check if the symbol has won in any row
    private boolean checkRows(char symbol) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) {
                return true;
            }
        }
        // If the symbol has not won in any row return false for isWinner() method
        return false;
    }

    // Check if the symbol has won in any column
    private boolean checkColumns(char symbol) {
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol) {
                return true;
            }
        }
        // If the symbol has not won in any column return false for isWinner() method
        return false;
    }

    // Check if the symbol has won in either forward or backward diagonal / or \
    private boolean checkDiagonals(char symbol) {
        for (int i = 0; i < 3; i++) {
        //checking for forward diagonal \
        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol){
                    return true;
                }
        //checking for backward diagonal /
        if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol){
            return true;
        }
        }
        // If the symbol has not won in either diagonal return false for isWinner() method
        return false;
    }

    //NEED TO WORK ON THIS METHOD BC...
        //doesn't evaluate if draw happens even when not all cells are full 
        //doesn't have any connection to 3 above methods, do we only need to call isDraw when all 3 have failed? 
    public boolean isDraw() {
        //Check for empty cells: checks if the current cell is still occupied by the default '-' symbol. 
        //If it finds an empty cell, it returns false, indicating that game is not a draw since there is still an available cell.
        //maybe: if (!isWinner(symbol)) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    //relays the current board state when called
    public char[][] getBoard() {
        return board;
    }
}
