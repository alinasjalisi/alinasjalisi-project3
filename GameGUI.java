import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

// QUIT GAME leaves the server
// NEW GAME creates new game with the same person

public class GameGUI extends JFrame
{
    //private methods to everythings we are creating

    //get the player symbol and display it on the left side of the GUI
    private JTextField PlaySymbol;
    //store the ipAddress
    private JTextField IP_Address;
    //store the port number
    private JTextField PortNum;
    //explain how to play the game
    private JTextArea HowTo;
    //botton to connect and disconnect
    private JButton Connect_Disconnect;
    //text for has the last move
    private JTextArea LastMOVE;
    //used to connect GameClient
    private GameClient gameGUI;
    //label for option player can do on the right side of the GUI
    private JLabel optionsLabel;
    //player wants to play a new game
    private JButton newGameButton;
    //player want to quit the game
    private JButton quitGameButton;
    //label to tell player what there symbol is
    private JLabel yourSymbolLabel;
    //set the actual symbol either x or y
    private JTextArea userSymbolTextArea;
    //store the 3x3 button
    private JButton[][] boardButtons;
    //used for cient networking
    private GameClient newPlayer;
    //store to keep track of player turn
    private PlayerTurn playerTurn;
    //stor whos the current player
    private String currentPlayer;
    //store instance of last move
    private LastMove lastMove;

    private GameServer server;

    private JLabel opponent;

    private int clickedRow = -1;
    private int clickedCol = -1;


    //used to initialize an new instance of GameGui class
    public GameGUI()
    {
        //set the title of the windo
        super("TicTacToe");
        //used to connect with the server methods
        startGameGUI();
    }

    //ALLDONE
    //used for initializing and orgarning the physical stuff
    private void startGameGUI()
    {
        //server = new GameServer(0);
        //initilize in game gui
        playerTurn = new PlayerTurn(boardButtons);

        //store title, size ad feature
        setTitle("TicTacToe(connected)");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Store properties for the top panel
        JPanel TFrame = new JPanel(new GridBagLayout());
        GridBagConstraints top_settings = new GridBagConstraints();

        //create, configuring and add the top part of the game gui in main panle
        //control the placemnet and layout behavior
        JPanel Top_part = createTop();
        top_settings.gridx = 0;
        top_settings.gridy = 0;
        top_settings.weightx = 1.0;
        top_settings.fill = GridBagConstraints.HORIZONTAL;
        TFrame.add(Top_part, top_settings);
        
        
        JPanel LFrame = new JPanel(new GridBagLayout());
        GridBagConstraints left_settings = new GridBagConstraints();

        //create, configuring and add the left part of the game gui in main panle

        JPanel Left_part = createLeftPanel();
        left_settings.gridx = 0;
        left_settings.gridy = 0;
        left_settings.weightx = 0.2;
        left_settings.fill = GridBagConstraints.BOTH;
        LFrame.add(Left_part, left_settings);

        //store properties for the middle part if the gui
        JPanel MFrame = new JPanel(new GridBagLayout());
        GridBagConstraints middle_settings = new GridBagConstraints();

        JPanel middle_part = createMiddlePanel();
        middle_settings.gridx = 0;
        middle_settings.gridy = 1;
        middle_settings.weightx = 1.0;
        middle_settings.weighty = 1.0;
        middle_settings.fill = GridBagConstraints.BOTH;
        MFrame.add(middle_part, middle_settings);

        // Store properties for the right part of the gui
        JPanel RFrame = new JPanel(new GridBagLayout());
        GridBagConstraints right_settings = new GridBagConstraints();

        JPanel right_part = createRightPanel();
        right_settings.gridx = 1;
        right_settings.gridy = 1;
        right_settings.weightx = 0.2; // Adjust this value as needed
        right_settings.weighty = 1.0;
        right_settings.fill = GridBagConstraints.BOTH;
        RFrame.add(right_part, right_settings);

        // Store properties for the bottom part of the GUI
        JPanel BFrame = new JPanel(new GridBagLayout());
        GridBagConstraints bottom_settings = new GridBagConstraints();

        JPanel bottom_part = createBottomPanel();
        bottom_settings.gridx = 0;
        bottom_settings.gridy = 2;
        bottom_settings.gridwidth = 2;
        bottom_settings.weightx = 1.0;
        bottom_settings.fill = GridBagConstraints.HORIZONTAL;
        BFrame.add(bottom_part, bottom_settings);

       //add all components to the main frame
       add(TFrame, BorderLayout.NORTH);
       add(LFrame, BorderLayout.WEST);
       add(MFrame, BorderLayout.CENTER);
       add(RFrame, BorderLayout.EAST);
       add(BFrame, BorderLayout.SOUTH);
   }


//ALL DONE
//store properties for the right part of the panel
private JPanel createRightPanel() {
    

    JPanel rightPanel = new JPanel(new GridLayout(4, 1));
    

    // Create a JLabel for the "Options" section
    optionsLabel = new JLabel("Options:");
    optionsLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
    rightPanel.add(optionsLabel, BorderLayout.NORTH);

      

    // Create a JPanel for buttons and labels
    JPanel optionsButtonsPanel = new JPanel(new GridLayout(3, 1));

    // Create a JButton to start a new game
    newGameButton = new JButton("New Game");
    //store the size of the button
    newGameButton.setPreferredSize(new Dimension(100, 20));
    //call the disconnect action button after click so the other can be turned on
    newGameButton.addActionListener(new NewGameButtonClickListener());
    //add the button to frame
    optionsButtonsPanel.add(newGameButton);

    // Create a JButton to quit the current agme and leave server
    quitGameButton = new JButton("Quit Game");
    //store the size of the buttob
    quitGameButton.setPreferredSize(new Dimension(100, 20));
    
    quitGameButton.addActionListener(new QuitGameButtonClickListener());

    optionsButtonsPanel.add(quitGameButton);
    //class so when clicked it reset the board and you leave the server

    // Create a JLabel for the "Your symbol is" section
    // TODO: gets the randomly assigned symbol and displays it, has it blank until assigned a symbol
    yourSymbolLabel = new JLabel("Your symbol is:");
    yourSymbolLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
    optionsButtonsPanel.add(yourSymbolLabel);

    // Add the options buttons panel to the right panel
    rightPanel.add(optionsButtonsPanel, BorderLayout.CENTER);

    // Create a JTextArea to display the user's symbol
    userSymbolTextArea = new JTextArea();
    userSymbolTextArea.setEditable(false);
    // Need to call a method from a different class to get the user's symbol
    userSymbolTextArea.setFont(new Font(Font.SERIF, Font.PLAIN, 100));
    rightPanel.add(userSymbolTextArea, BorderLayout.SOUTH);

    if(gameGUI != null){
    userSymbolTextArea.setText(String.valueOf( gameGUI.getSymbol() ) /*gameGUI.getSymbol())*/);
    userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 300)));

    if(gameGUI.assignSymbol() == true){
        userSymbolTextArea = new JTextArea(gameGUI.getSymbol());
    }
    }

    rightPanel.add(userSymbolTextArea, BorderLayout.SOUTH);

    return rightPanel;
}
//Should be all done
// Create a panel to display the last move
private JPanel createLeftPanel() {
    JPanel leftPanel = new JPanel(new BorderLayout());

    // Add a label for the "Last Move" section
    JLabel lastMoveLabel = new JLabel("Last Move: ");
    leftPanel.add(lastMoveLabel, BorderLayout.NORTH);

    // Create a JTextArea to display the last move message
    LastMOVE = new JTextArea();
    LastMOVE.setEditable(false);
    LastMOVE.setPreferredSize(new Dimension(150, 100));
    leftPanel.add(LastMOVE, BorderLayout.CENTER);

    return leftPanel;
}

//ALL DONE
//store properties for top of the panel
private JPanel createTop(){
    //edit top half where the player symbol that they type X or O, The ip Address and the port get sent to the GameClient
    //Also add connect action to the connect and disconnect button

        //setting the top panel with name, ip, port, and connect
        JPanel topPanel = new JPanel(new FlowLayout());
        
        // displays IP Address
        IP_Address = new JTextField(7);
        topPanel.add(new JLabel("IP Address: "));
        topPanel.add(IP_Address);
        
        //displays the port used
        PortNum = new JTextField(5);
        topPanel.add(new JLabel("Port: "));
        topPanel.add(PortNum);

        Connect_Disconnect = new JButton("Connect");
        Connect_Disconnect.addActionListener(new connectAction()); //sere's part
        topPanel.add(Connect_Disconnect);

        return topPanel;

    }

    //NEEDS FIXED
    private JPanel createMiddlePanel(){

        //add that when you click a button it get remove and reveal the empty behind
        //need to add hash in the backgroup as wll as when you remove the button
        //your specific symbol appear in the backgroud

        
        JPanel middlePanel = new JPanel(new BorderLayout());

        JTextArea statusTextArea = new JTextArea();
        statusTextArea.setEditable(false);
        statusTextArea.setFont(new Font(Font.SERIF, Font.PLAIN, 18)); // Adjusted font size

        if(gameGUI == null){
            statusTextArea.setText("                                                   Player's  " + " " + " turn");

        }else{
        statusTextArea.setText("                                                   Player's  " + String.valueOf(gameGUI.getSymbol())+ " turn");
        }
        middlePanel.add(statusTextArea, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        boardButtons = new JButton[3][3];
        //  TODO: create action listener that when u click it it disappears and turns into that symbol
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardButtons[i][j] = new JButton();
                boardButtons[i][j].setFont(new Font(Font.SERIF, Font.PLAIN, 24)); // Adjusted font size
                boardButtons[i][j].addActionListener(new BoardButtonClickListener());
                boardPanel.add(boardButtons[i][j]);
            }
        }
        middlePanel.add(boardPanel, BorderLayout.CENTER);
        
        return middlePanel;
    }

//ALLDONE
//store properties in the botton section of gui
private JPanel createBottomPanel()
{
    //adding instructions how to play the game at the bottom
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    JLabel howToLabel = new JLabel("How to Play: ");
    bottomPanel.add(howToLabel);
    HowTo = new JTextArea();
    HowTo.setPreferredSize(new Dimension(700, 50));
    bottomPanel.add(HowTo);
    HowTo.setText("You will be randomly assigned a symbol: 'X' or 'O'.\n"
    + "Click on one of the buttons on the board to make your move.\n"
    + "To win, create a column, row, or diagonal pattern with your symbol before the other player can.\n"
    + "A draw will only occur when the entire board is used up and neither player was able to create one of the patterns.");
    HowTo.setEditable(false);
    add(bottomPanel, BorderLayout.SOUTH);

    return bottomPanel;
}

//ALLDONE
//does the action when you click the connect button
private class connectAction implements ActionListener {
    
    public void actionPerformed(ActionEvent e) {
        //get host
        String host = IP_Address.getText();
        //get port
        int port = Integer.parseInt(PortNum.getText());
        //sent it to clientnetwork to be able to run
        gameGUI = new GameClient(host, port, GameGUI.this);
        //try for error
        try
        {
            //call checkConnect in ClientNetworking
            if (gameGUI.connectToServer()) 
            {
                //change properties for button
                GameGUI.this.Connect_Disconnect.removeActionListener(this);
                GameGUI.this.Connect_Disconnect.addActionListener(new disconnectAction());
                GameGUI.this.Connect_Disconnect.setText("Disconnect");

                //then have the player assigned a random symbol
                gameGUI.assignSymbol();

                String assignedSymbol = gameGUI.getSymbol();

                // Now you can access the player's symbol using gameGUI.getSymbol() or a similar method
                //NEEDS FIXED
                JOptionPane.showMessageDialog(GameGUI.this, "Your symbol is: " + gameGUI.getSymbol(), "Symbol Assigned", JOptionPane.INFORMATION_MESSAGE);

                updatePlayerSymbol(gameGUI.getSymbol());
            }
            }
            //when error but a try catch saying ipaddress doesn't exist
            catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(GameGUI.this, "IP address number does not exist", " connection failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    //ALL DONE
    //disconnect properties
    private class disconnectAction implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //change the propeties to button to disconnect
            GameGUI.this.Connect_Disconnect.removeActionListener(this);
            GameGUI.this.Connect_Disconnect.addActionListener(new connectAction());
            GameGUI.this.Connect_Disconnect.setText("Connect");
            //reset everything to empty
            resetBoardPanel();
            //call to disconnect from server
            gameGUI.disconnect();
        }
    }

    //ALLDONE FOR NOW
    // new method to reset boardPanel
    private void resetBoardPanel() 
    {
        //somehow calls the private NewMove button so when you clik that too the game resets
        for (int i = 0; i < 3; i++) 
        {
            for (int j = 0; j < 3; j++) 
            {
                boardButtons[i][j].setText("");
            }
        }
    }

    // new class for board button click listener
    private class BoardButtonClickListener implements ActionListener{


        //getter for row and colo

        public int getClickedRow() {
            return clickedRow;
        }
    
        public int getClickedCol() {
            return clickedCol;
        }

        public void actionPerformed(ActionEvent e) {
            if (playerTurn.isPlayer1Turn()) {
                //determin the positoon of the clicked button on the game board and update its state
                JButton clickedButton = (JButton) e.getSource();

                //Find the clicked button position
                clickedRow = -1;
                clickedCol = -1;

                //iterate through board to find the clicked button
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (boardButtons[i][j] == clickedButton) {
                            clickedRow = i;
                            clickedCol = j;
                            break;
                        }
                    }
                }
                if (clickedRow != -1 && clickedCol != -1) {
                    // Check if the clicked button is empty
                    if (boardButtons[clickedRow][clickedCol].getText().isEmpty()) {
                        // Update the button with the current player's symbol
                        boardButtons[clickedRow][clickedCol].setText(String.valueOf(gameGUI.getSymbol()));
        
                        // Disable the clicked button to prevent further clicks
                        boardButtons[clickedRow][clickedCol].setEnabled(false);

                        //switch to new player
                        playerTurn.switchTurn();

                        if (playerTurn.getTurnCount() == 8) {
                            // Display the "Last Move" since there is exactly one move left
                            String lastMoveDescription = "Last Move: Player " + gameGUI.getSymbol() + " at (" + clickedRow + ", " + clickedCol + ")";
                            LastMOVE.setText(lastMoveDescription);
                        }

                        sendMove();
                    }
                }
            }
        }
    }
        

//comeback
//update the player symbol
public void updatePlayer(String player)
{
    yourSymbolLabel.setText(player);
}

//update the player symbol on the GUI
public void updatePlayerSymbol(String symbol){
    //Updatethe gui to display the assigned symbol
    // Assuming you have two players (player 1 and player 2)
    if (symbol.equals("X")) {
        userSymbolTextArea.setText("X");
    } else if (symbol.equals("O")) {
        userSymbolTextArea.setText("O");
    }
    // You can customize this based on the actual names or labels you want to display
}


public synchronized void receiveMove(String move) {
    try {
        // Check if the move is in the "SYMBOL:" format
        if (move.startsWith("SYMBOL:")) {
            // Handle the SYMBOL message, for example, update the player symbol
            String symbol = move.substring("SYMBOL:".length()).trim();
            updatePlayer(symbol);
        } else {
            // Split the move string into coordinates (row, column)
            String[] coordinates = move.split(",");
            int row = Integer.parseInt(coordinates[0]);
            int col = Integer.parseInt(coordinates[1]);

            // Update the board with the opponent's move
            String opponentSymbol = gameGUI.getSymbol(); // Use getSymbol to get the opponent's symbol
            boardButtons[row][col].setText(String.valueOf(opponentSymbol));
            boardButtons[row][col].setEnabled(false); // Disable the button

            // Check if the game has ended
            if (tictactoeboard.isWinner(opponentSymbol)) {
                // Handle the case where the opponent wins
                System.out.println("Opponent Wins!");
            } else if (tictactoeboard.isDraw()) {
                // Handle the case where the game is a draw
                System.out.println("It's a Draw!");
            } else {
                // Switch players
                currentPlayer = gameGUI.getSymbol(); // Assuming you want to switch to the current player
                updatePlayer(currentPlayer);
            }
        }
    } catch (NumberFormatException e) {
        // Handle the case where the move string is not in the expected format
        e.printStackTrace(); // Log the exception or handle it appropriately
    }
}

    //send the player a move
    public synchronized void sendMove(){
        //
        //store the move that the onther player had, which is a symbol
        String move = clickedRow + "," + clickedCol;
        if (move != null) {
            gameGUI.sendMove(move);
        }
    }

    //error
    public void StateError(String msg)
    {
        JOptionPane.showMessageDialog(null, msg, "Error!", JOptionPane.ERROR_MESSAGE);
    }


private class NewGameButtonClickListener implements ActionListener{
        
    public void actionPerformed(ActionEvent e){
        //restart the board
        resetBoardPanel();
        //reassgn symbol
        gameGUI.assignSymbol();
        //updat the gui to siplay the new symbol
        updatePlayerSymbol(gameGUI.getSymbol());
        JOptionPane.showMessageDialog(GameGUI.this, "Your symbol is: " + gameGUI.getSymbol(), "Symbol Reassigned", JOptionPane.INFORMATION_MESSAGE);
        //enable all button on the board
        enableAllButtons();
        // Reset turns when starting a new game
        playerTurn.resetTurn(); 




    // Add event handler for "New Game" button
        
    }
}

//allow for al the button that were previously disable for the to be able use
private void enableAllButtons(){
    // Enable buttons that were previously disabled due to user clicks
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            boardButtons[i][j].setEnabled(true);
        }
    }
}


//ALL DONE
//store button action after you click the button
private class QuitGameButtonClickListener implements ActionListener {

    public void actionPerformed(ActionEvent e){
        //change the properties when you click quitgamebutton
        GameGUI.this.Connect_Disconnect.removeActionListener(this);
        //reset everything to empty
        resetBoardPanel();
        enableAllButtons();
        //call to disconnect from server
        gameGUI.disconnect();

        userSymbolTextArea.setText (" ");
        
    }
}
     // Method to handle receiving updated board state from the server
    public void updateBoardState(String[][] board) 
    {
        // Update the game board buttons based on the received board state
        for (int i = 0; i < 3; i++) 
        {
            for (int j = 0; j < 3; j++) 
            {
                boardButtons[i][j].setText(currentPlayer);
            }
        }

        // Enable buttons that were previously disabled due to user clicks
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            boardButtons[i][j].setEnabled(true);
        }
    }

    
    tictactoeboard.isWinner(gameGUI.getSymbol());
    tictactoeboard.isDraw();
        
}

//ALLDONE
    public static void main(String[] args) 
    {
        //main methd for running the JFram
        GameGUI gameGUI = new GameGUI();
        gameGUI.setVisible(true);
    }
}


