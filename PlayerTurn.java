import javax.swing.*;

public class PlayerTurn {

    private boolean isPlayer1Turn;
    private JButton[][] boardButtons;

    public PlayerTurn(JButton[][] boardButtons) {
        this.isPlayer1Turn = true;
    }

    public void switchTurn() {
        isPlayer1Turn = !isPlayer1Turn;
        updateButtonAvailability();
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    public void resetTurn() {
        isPlayer1Turn = true;
    }

    public void updateButtonAvailability() {
        boolean isCurrentPlayer1 = isPlayer1Turn();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardButtons[i][j].setEnabled(isCurrentPlayer1);
            }
        }
    }
}