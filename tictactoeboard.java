// //logic for constituting a win/draw/loss
// import javax.swing.JOptionPane;

// public class tictactoeboard {
    

//     private GameGUI player;

//     // Creates a 2D Stringacter array to represent the 3x3 board
//     private static String[][] board = new String[3][3];

//     // Constructor that calls initializeboard() method to create a fresh board
//     public tictactoeboard() 
//     {
//         //board = new String[3][3]; 
//         initializeBoard();
//     }

//     // Initialize the board with "-" symbols in every cell
//     private void initializeBoard() {
//         for (int i = 0; i < 3; i++) {
//             for (int j = 0; j < 3; j++) {
//                 board[i][j] = "-";
//             }
//         }
//     }

//     // Method to update the board with the specified symbol at the given row and column
//     public void updateBoard(int row, int col, String symbol) {
//         // Check if the cell is already occupied
//         if (board[row][col] == "-") {
//             // Update the cell with the specified symbol
//             board[row][col] = symbol;
//         } else {
//             // Throw an exception if the 2d array's cell is already occupied
//             throw new IllegalArgumentException("Cell already occupied! Click an available cell.");
//         }
//     }

//     // Method to check if the specified symbol has won the game
//     public static boolean isWinner(String symbol) {
//         //evaluates if any of the checkRows, checkColumns, or checkDiagonals methods are true. 
//             //if even one of them is true, then isWinner returns true (as perscribed in the 3 methods below) if all of them are false, then isWinner returns false (as perscribed in the 3 methods below)

//             if (checkRows(symbol) || checkColumns(symbol) || checkDiagonals(symbol)) {
//                 // Display a pop-up message declaring the winner
//                 JOptionPane.showMessageDialog(null, "Player " + symbol + " has won!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
//                 return true;
//             }
//             return false;
        
//     }

//     // Check if the symbol has won in any row
//     private static boolean checkRows(String symbol) {
//         for (int i = 0; i < 3; i++) {
//             if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) {
//                 return true;
//             }
//         }
//         // If the symbol has not won in any row return false for isWinner() method
//         return false;
//     }

//     // Check if the symbol has won in any column
//     private static boolean checkColumns(String symbol) {
//         for (int i = 0; i < 3; i++) {
//             if (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol) {
//                 return true;
//             }
//         }
//         // If the symbol has not won in any column return false for isWinner() method
//         return false;
//     }

//     // Check if the symbol has won in either forward or backward diagonal / or \
//     private static boolean checkDiagonals(String symbol) {
//         for (int i = 0; i < 3; i++) {
//         //checking for forward diagonal \
//         if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol){
//                     return true;
//                 }
//         //checking for backward diagonal /
//         if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol){
//             return true;
//         }
//         }
//         // If the symbol has not won in either diagonal return false for isWinner() method
//         return false;
//     }

//     //NEED TO WORK ON THIS METHOD BC...
//         //doesn't evaluate if draw happens even when not all cells are full 
//         //doesn't have any connection to 3 above methods, do we only need to call isDraw when all 3 have failed? 
//         public static boolean isDraw() 
//         {
//             for (int i = 0; i < 3; i++) {
//                 for (int j = 0; j < 3; j++) {
//                     if (board[i][j] == "-") {
//                         return false;
//                     }
//                 }
//             }
//             JOptionPane.showMessageDialog(null, "The game is a draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
//             return true;
//         }
        

//     //relays the current board state when called
//     public static String[][] getBoard() {
//         return board;
//     }
// }


import javax.swing.JOptionPane;

public class tictactoeboard {

    private String[][] board;
    private String currentPlayerSymbol;

    public tictactoeboard() {
        board = new String[3][3];
        currentPlayerSymbol = "X";
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = "-";
            }
        }
    }

    public boolean makeMove(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3 || !board[row][col].equals("-")) {
            return false;
        }
        board[row][col] = currentPlayerSymbol;
        currentPlayerSymbol = (currentPlayerSymbol.equals("X")) ? "O" : "X";
        return true;
    }

    public boolean isWinner() {
        // Check rows
        for (int row = 0; row < 3; row++) {
            if (board[row][0].equals(currentPlayerSymbol) && board[row][1].equals(currentPlayerSymbol) && board[row][2].equals(currentPlayerSymbol)) {
                return true;
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (board[0][col].equals(currentPlayerSymbol) && board[1][col].equals(currentPlayerSymbol) && board[2][col].equals(currentPlayerSymbol)) {
                return true;
            }
        }

        // Check diagonals
        if ((board[0][0].equals(currentPlayerSymbol) && board[1][1].equals(currentPlayerSymbol) && board[2][2].equals(currentPlayerSymbol)) ||
                (board[0][2].equals(currentPlayerSymbol) && board[1][1].equals(currentPlayerSymbol) && board[2][0].equals(currentPlayerSymbol))) {
            return true;
        }

        return false;
    }

    public boolean isDraw() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col].equals("-")) {
                    return false;
                }
            }
        }
        return true;
    }

    public String[][] getBoard() {
        return board;
    }

}
