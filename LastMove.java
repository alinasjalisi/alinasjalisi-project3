import java.lang.reflect.Method;
import javax.swing.*;

public class LastMove{
    //store the symbol what is the last move
    private String playerSymbol;
    //store the row
    private int lastMoveRow;
    //store the colomun
    private int lastMoveCol;

    //store the constructors
    public LastMove(){
        //initilize values being passed in
        playerSymbol = "";
        lastMoveCol = -1;
        lastMoveRow = -1;
    }

    //method that set the last move
    public void setLastMove(String symbol, int row, int col ){
        this.playerSymbol = symbol;
        this.lastMoveCol = row;
        this.lastMoveCol = col;
    }

    //get the player symbol of the last move
    public String getPlayerSymbol() {
        return playerSymbol;
    }

    //get the row of the last move
    public int getLastMoveRow() {
        return lastMoveRow;
    }

    //get the column of the last move
    public int getLastMoveCol() {
        return lastMoveCol;
    }
    
    // formatting string describing the last move
    public String getLastMoveDescription() {
        return playerSymbol + " has the last move at Row: " + lastMoveRow + ", Col: " + lastMoveCol;
    }
}


//GUI depends on this class, should be able to display the last move on gameboard by last player
//This method will be used and displayed in gui
// class is suppose to find and return the play that has the last move as well as the coordinate on the board that is that last place a player can click and play move
//in gui it should display this "X or Y has the last move and row (somethings), and col(something) is te last possible move"
