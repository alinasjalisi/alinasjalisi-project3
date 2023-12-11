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
    //VARIABLE USED FOR TOP
    //store the ipAddress
    private JTextField IP_Address;
    //store the port number
    private JTextField PortNum;
    //botton to connect and disconnect
    private JButton Connect_Disconnect;
 
    //VARIABLE USED IN LEFT SIDE OF GUI
    //text for has the last move
    private JTextArea LastMOVE;
 
    //VARIABLE USED IN RIGHT SIDE OF GUI
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

    private JTextArea statusTextArea; 
 
    //store the symbol being sent in my server
    public String ClientSymbol ;
    //store the apponent
    public String opponent;
 
    public int opponentClickedRow;
 
    public int opponentClickCol;

    //for the clicked button to simultaneously show on both gui who clck which cell (ex X will show in cell 1 for both gui bc player x move there)
    public String buttonClicked;
    public int buttonClickedRow;
    public int buttonClickedCol;
 
    //VARIABLE USED IN MIDDLE
    //store the 3x3 button
    private JButton[][] boardButtons;
    //keep track of row
    private int clickedRow = -1;
    //keep track of col
    private int clickedCol = -1;
    //store the clicked button
    private JButton clickedButton;
 
    //VARIABLE USED IN BOOTTOM OF GUI
    //explain how to play the game
    private JTextArea HowTo;
 
    //VARIABLE FOR CLASSES
    //store the server properties
    private GameServer server;
    //used to connect GameClient
    private GameClient gameGUI;

    private tictactoeboard board; 

    private int ClickCount;



    //used to initialize an new instance of GameGui class
    public GameGUI()
    {
        //set the title of the windo
        super("TicTacToe");
        //used to connect with the server methods
        //board = new tictactoeboard();
        startGameGUI();
    }
 
    //ALL DONE
    //used for initializing and orgarning the physical stuff
    private void startGameGUI()
    {
        board = new tictactoeboard();
        
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
 
//COMEBACK
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
    //COMEBACK
    //call the disconnect action button after click so the other can be turned on
    newGameButton.addActionListener(new NewGameButtonClickListener());
    //add the button to frame
    optionsButtonsPanel.add(newGameButton);
 
    // Create a JButton to quit the current agme and leave server
    quitGameButton = new JButton("Quit Game");
    //store the size of the buttob
    quitGameButton.setPreferredSize(new Dimension(100, 20));
    //COMEBACK
    quitGameButton.addActionListener(new QuitGameButtonClickListener());
 
    optionsButtonsPanel.add(quitGameButton);
    //class so when clicked it reset the board and you leave the server
 
    // COMEBACK
    yourSymbolLabel = new JLabel("Your symbol is:");
    yourSymbolLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
    optionsButtonsPanel.add(yourSymbolLabel);
 
    // Add the options buttons panel to the right panel
    rightPanel.add(optionsButtonsPanel, BorderLayout.CENTER);
 
    //COMEBACK
    // Create a JTextArea to display the user's symbol
    userSymbolTextArea = new JTextArea();
    userSymbolTextArea.setEditable(false);
    // Need to call a method from a different class to get the user's symbol
    userSymbolTextArea.setFont(new Font(Font.SERIF, Font.PLAIN, 100));
    rightPanel.add(userSymbolTextArea, BorderLayout.SOUTH);
 
    rightPanel.add(userSymbolTextArea, BorderLayout.SOUTH);
 
    return rightPanel;
}
 
//DONE FOR NOW
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
        JPanel middlePanel = new JPanel(new BorderLayout());
 
        statusTextArea = new JTextArea();
        statusTextArea.setEditable(false);
        statusTextArea.setFont(new Font(Font.SERIF, Font.PLAIN, 18)); // Adjusted font size
        middlePanel.add(statusTextArea, BorderLayout.NORTH);
 
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        boardButtons = new JButton[3][3];
        //create action listener that when u click it it disappears and turns into that symbol
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardButtons[i][j] = new JButton();
                boardButtons[i][j].setFont(new Font(Font.SERIF, Font.PLAIN, 24)); // Adjusted font size
                boardButtons[i][j].setEnabled(false);
                boardButtons[i][j].addActionListener(new BoardButtonClickListener());
                boardPanel.add(boardButtons[i][j]);
                clickedRow = i;
                clickedCol = j;
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
        try
        {
            //call checkConnect in ClientNetworking
            if (gameGUI.connectToServer())
            {
                System.out.println("SERVER MAKING");
                // server = new GameServer(port);
                System.out.println("SERVER MADE");
                //change properties for button
                GameGUI.this.Connect_Disconnect.removeActionListener(this);
                GameGUI.this.Connect_Disconnect.addActionListener(new disconnectAction());
                GameGUI.this.Connect_Disconnect.setText("Disconnect");
                //JOptionPane.showMessageDialog(GameGUI.this, "YOU HAVE CONNECTED TO THE SERVER");

 
                //then have the player assigned a random symbol
                //gameGUI.assignSymbol();
 
                String assignedSymbol = ClientSymbol;
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
    //COMEBACK
    // new class for board button click listener
    private class BoardButtonClickListener implements ActionListener{

        //System.out.println("Getting in the button action");
        //getter for row and colo
 
        public int getClickedRow() {
            return clickedRow;
        }
   
        public int getClickedCol() {
            return clickedCol;
        }
 
        public void actionPerformed(ActionEvent e) {
                ClickCount++;

                clickedButton = (JButton) e.getSource();
 
                //Find the clicked button position
                clickedRow = -1;
                clickedCol = -1;
                System.out.println("");
 
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
                        boardButtons[clickedRow][clickedCol].setText(String.valueOf(ClientSymbol));
                        boardButtons[clickedRow][clickedCol].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
                        boardButtons[clickedRow][clickedCol].setEnabled(false);

                        /*if (board.makeMove(getClickedRow(), getClickedCol())) {
                            boardButtons[getClickedRow()][getClickedCol()].setText(board.currentPlayerSymbol());
                            boardButtons[getClickedRow()][getClickedCol()].setEnabled(false);
                    
                            // Check for win or draw
                            if (board.isWinner()) {
                                JOptionPane.showMessageDialog(GameGUI.this, "Player " + board.currentPlayerSymbol() + " wins!");
                            } else if (board.isDraw()) {
                                JOptionPane.showMessageDialog(GameGUI.this, "The game is a draw!");
                            } else {
                                board.switchPlayer();
                            }
                        }*/
                        if (board.isWinner(ClientSymbol)) {
                        System.out.println("SXDCFVGBHNJKLKJHFDXSDFGHJK");
                            // Display winner and take any necessary actions...
                            JOptionPane.showMessageDialog(GameGUI.this, "Player " + ClientSymbol + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                            // Add logic for post-win actions...
                        } else {
                            // Check if it's a draw
                            board.draw();
                        }

                        
                        int moveNumber = clickedRow * 3 + clickedCol + 1;
                        System.out.println(ClientSymbol);

                        String StringmoveNumber = String.valueOf(moveNumber);
                        System.out.println("check string num: " + StringmoveNumber);
                        statusTextArea.setText("                                                   Player " + opponent + " turn");
                        sendMove(StringmoveNumber+","+ClientSymbol);
                    }
                }
        }
    }



       
 
//COMEBACK
//update the player symbol
public void getPlayerSymbol(String symbol)
{
    userSymbolTextArea.setText(symbol);
}
 
//COMEBACK
public synchronized void receiveMsg(String move) {
    try {
        String move1 = move;
       
        if (move.startsWith("SYMBOL X")) {
            buttonClicked = "X";
            ClientSymbol = "X";
            opponent = "O";
 
            JOptionPane.showMessageDialog(GameGUI.this, "Your symbol is: X");
            userSymbolTextArea.setText(ClientSymbol);
            statusTextArea.setText("                                                   Player " + opponent + " turn");
            userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
 
        }
        //if the server tell you your move is y
        if (move.startsWith("SYMBOL O")) {
            buttonClicked = "O";

            ClientSymbol = "O";

            opponent = "X";
            JOptionPane.showMessageDialog(GameGUI.this, "Your symbol is: O");
            userSymbolTextArea.setText(ClientSymbol);
            statusTextArea.setText("                                                   Player " + ClientSymbol + " turn");
            userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
        }

        if(move.startsWith("TWO_PLAYER_CONNECTED")){
            enableAllButtons();

        }

        /*if(move.startsWith("NOT_TWO_PLAYER_CONNECTED")){
            disableAllButtons();
        }*/

        if (move.startsWith("1_O")) {
            
            if (boardButtons[0][0].getText().isEmpty()) {
                    boardButtons[0][0].setText("O");
                    boardButtons[0][0].setEnabled(false);
                    boardButtons[0][0].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                    ClickCount++;
                    LastMOVE.setText("Player O made\n"+ "a move to \n" + "row 1 col 1");
                    LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                    System.out.println("fghjk");
                    statusTextArea.setText("                                                   Player X's turn");
                    userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
        }
    }
        if (move.startsWith("2_O")) {
                if (boardButtons[0][1].getText().isEmpty()) {
                    boardButtons[0][1].setText("O");
                    boardButtons[0][1].setEnabled(false);
                    boardButtons[0][1].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                    LastMOVE.setText("Player O made \n"+ "a move to \n"+ "row 1 col 2");
                    LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                    statusTextArea.setText("                                                   Player X's turn");
                    userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                    ClickCount++;

        }
        }
        if (move.startsWith("3_O")) {
                if (boardButtons[0][2].getText().isEmpty()) {
                    boardButtons[0][2].setText("O");
                    boardButtons[0][2].setEnabled(false);
                    boardButtons[0][2].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                    LastMOVE.setText("Player O made\n" + "a move to\n"+ "row 1 col 3");
                    LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                    statusTextArea.setText("                                                   Player X's turn");
                    userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
                    ClickCount++;

        }
    }
        if (move.startsWith("4_O")) {
                if (boardButtons[1][0].getText().isEmpty()) {
                    boardButtons[1][0].setText("O");
                    boardButtons[1][0].setEnabled(false);
                    boardButtons[1][0].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                    LastMOVE.setText("Player O made\n"+ " a move to\n"+ " row 2 col 1");
                    LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                    statusTextArea.setText("                                                   Player X's turn");
                    userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
                    ClickCount++;

        }
    }
        if (move.startsWith("5_O")) {
                if (boardButtons[1][1].getText().isEmpty()) {
                    boardButtons[1][1].setText("O");
                    boardButtons[1][1].setEnabled(false);
                    boardButtons[1][1].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                    LastMOVE.setText("Player O made\n"+ " a move to\n"+ " row 2 col 2");
                    LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                    statusTextArea.setText("                                                   Player X's turn");
                    userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
                    ClickCount++;

        }
    }
        if (move.startsWith("6_O")) {
                if (boardButtons[1][2].getText().isEmpty()) {
                    boardButtons[1][2].setText("O");
                    boardButtons[1][2].setEnabled(false);
                    boardButtons[1][2].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                    LastMOVE.setText("Player O made\n"+ " a move to\n"+ " row 2 col 3");
                    LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                    statusTextArea.setText("                                                   Player X's turn");
                    userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
                    ClickCount++;

        }    
    }
        if (move.startsWith("7_O")) {
            if (boardButtons[2][0].getText().isEmpty()) {
                boardButtons[2][0].setText("O");
                boardButtons[2][0].setEnabled(false);
                boardButtons[2][0].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                LastMOVE.setText("Player O made\n"+ " a move to\n"+ " row 3 col 1");
                LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                statusTextArea.setText("                                                   Player X's turn");
                userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
                ClickCount++;

        }
    } 
        if (move.startsWith("8_O")) {
            if (boardButtons[2][1].getText().isEmpty()) {
                boardButtons[2][1].setText("O");
                boardButtons[2][1].setEnabled(false);
                boardButtons[2][1].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                LastMOVE.setText("Player O made\n"+ " a move to\n"+ " row 3 col 2");
                LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                statusTextArea.setText("                                                   Player X's turn");
                userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
                ClickCount++;

        }
        }    
        if (move.startsWith("9_O")) {
            if (boardButtons[2][2].getText().isEmpty()) {
                boardButtons[2][2].setText("O");
                boardButtons[2][2].setEnabled(false);
                boardButtons[2][2].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                LastMOVE.setText("Player O made\n"+ " a move to\n"+ " row 3 col 3");
                LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                statusTextArea.setText("                                                   Player X's turn");
                userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
                ClickCount++;

        }
    }    

        //FOR X 
        if (move.startsWith("1_X")) {
            if (boardButtons[0][0].getText().isEmpty()) {
                boardButtons[0][0].setText("X");
                boardButtons[0][0].setEnabled(false);
                boardButtons[0][0].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                LastMOVE.setText("Player X made\n"+ " a move to\n"+ " row 1 col 1");
                System.out.println("sdfghjk");
                LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                statusTextArea.setText("                                                   Player O's turn");
                userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
        }
    }
        if (move.startsWith("2_X")) {
            if (boardButtons[0][1].getText().isEmpty()) {
                boardButtons[0][1].setText("X");
                boardButtons[0][1].setEnabled(false);
                boardButtons[0][1].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                LastMOVE.setText("Player X made\n"+ " a move to\n"+ " row 1 col 2");
                LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                statusTextArea.setText("                                                   Player O's turn");
                userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
        }
    }
        if (move.startsWith("3_X")) {
                if (boardButtons[0][2].getText().isEmpty()) {
                    boardButtons[0][2].setText("X");
                    boardButtons[0][2].setEnabled(false);
                    boardButtons[0][2].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                    LastMOVE.setText("Player X made\n"+ " a move to\n"+ " row 1 col 3");
                    LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                    statusTextArea.setText("                                                   Player O's turn");
                    userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
        }
    }
        if (move.startsWith("4_X")) {
                if (boardButtons[1][0].getText().isEmpty()) {
                boardButtons[1][0].setText("X");
                boardButtons[1][0].setEnabled(false);
                boardButtons[1][0].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                LastMOVE.setText("Player X made\n"+ " a move to\n"+ " row 2 col 1");
                LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                statusTextArea.setText("                                                   Player O's turn");
                userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
        }
    }
        if (move.startsWith("5_X")) {
                if (boardButtons[1][1].getText().isEmpty()) {
                    boardButtons[1][1].setText("X");
                    boardButtons[1][1].setEnabled(false);
                    boardButtons[1][1].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                    LastMOVE.setText("Player X made\n"+ " a move to\n"+ " row 2 col 2");
                    LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                statusTextArea.setText("                                                   Player O's turn");
                    userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
        }
    }
        if (move.startsWith("6_X")) {
                if (boardButtons[1][2].getText().isEmpty()) {
                    boardButtons[1][2].setText("X");
                    boardButtons[1][2].setEnabled(false);
                    boardButtons[1][2].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                    LastMOVE.setText("Player X made\n"+ " a move to\n"+ " row 2 col 3");
                    LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                statusTextArea.setText("                                                   Player O's turn");
                    userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
        }
    }    
        if (move.startsWith("7_X")) {
            if (boardButtons[2][0].getText().isEmpty()) {
                boardButtons[2][0].setText("X");
                boardButtons[2][0].setEnabled(false);
                boardButtons[2][0].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                LastMOVE.setText("Player X made\n"+ " a move to\n"+ " row 3 col 1");
                LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                statusTextArea.setText("                                                   Player O's turn");
                userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
        }
    }    
        if (move.startsWith("8_X")) {
            if (boardButtons[2][1].getText().isEmpty()) {
                boardButtons[2][1].setText("X");
                boardButtons[2][1].setEnabled(false);
                boardButtons[2][1].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                LastMOVE.setText("Player X made\n"+ " a move to\n"+ " row 3 col 2");
                LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                statusTextArea.setText("                                                   Player O's turn");
                userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
        }
    }    
        if (move.startsWith("9_X")) {
            if (boardButtons[2][2].getText().isEmpty()) {
                boardButtons[2][2].setText("O");
                boardButtons[2][2].setEnabled(false);
                boardButtons[2][2].setFont((new Font(Font.SERIF, Font.PLAIN, 100)));

                LastMOVE.setText("Player X made\n"+ " a move to\n"+ " row 3 col 3");
                LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
                statusTextArea.setText("                                                   Player O's turn");
                userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 100)));
        }
    }


        //if the server tell you that the opponent quit the game
        if (move.startsWith("Quit_Game")) {
            resetBoardPanel();
            enableAllButtons();
            JOptionPane.showMessageDialog(GameGUI.this, "Player Has Quit Game");
            userSymbolTextArea.setText (" ");
            LastMOVE.setText("you quit the game,\n"+ "please disconnect\n"+" from server \n"+"and reconnect to\n"+ "play another game!");
            LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
            gameGUI.disconnect();
            

        }
        // if the server tell you that the opponent start a new game
        if(move.startsWith("New_Game")){
            resetBoardPanel();
            enableAllButtons();            
            JOptionPane.showMessageDialog(GameGUI.this, "Player Has Started A New Game");
            LastMOVE.setText("a new game is\n"+" beginning player X\n"+" gets the first move!");
            LastMOVE.setFont(new Font(Font.SERIF, Font.PLAIN, 18));

 
        }
    }  
    catch (NumberFormatException e) {
        // Handle the case where the move string is not in the expected format
        e.printStackTrace(); // Log the exception or handle it appropriately
    }
}
    
private void updateButtonOnGUI(int row, int col) {
    if (boardButtons[row][col].getText().isEmpty()) {
        boardButtons[row][col].setText(String.valueOf(buttonClicked));

        boardButtons[row][col].setEnabled(false);

        // Switch to the new player
        buttonClicked = (buttonClicked.equals("X")) ? "O" : "X";
    }
}

private String getCurrentPlayerSymbol() {
    // Implement logic to determine if it's Player X or Player O's turn
    // For example, you can use a boolean flag or keep track of the current turn in the TicTacToeBoard class
    return "X"; // Placeholder, replace with your logic
}

    public synchronized void sendMove(String msg){
        if(this.gameGUI != null && msg != null){
            System.out.println("send move in server from gui move mdae by player: " + msg);
            gameGUI.writeMessage(msg);
        }
    }

    public synchronized void receiveMoveFromServer() {
        String move = server.dequeueMove();
        if (move != null) {
            // Update the GUI with the received move
            System.out.println("Received player 1 move from server and now updating other players GUI: " + move);
            // ... update GUI code ...
            // delete this receiveMoveFromServer.length-1;
        }
    }
 
    //error
    public void StateError(String msg)
    {
        JOptionPane.showMessageDialog(null, msg, "Error!", JOptionPane.ERROR_MESSAGE);
    }
 
private class NewGameButtonClickListener implements ActionListener{
       
    public void actionPerformed(ActionEvent e){
       

        sendMove("Receive_New_Game");
       
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

private void disableAllButtons() {
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            boardButtons[i][j].setEnabled(false);
        }
    }
}


//ALL DONE
//store button action after you click the button
private class QuitGameButtonClickListener implements ActionListener {
 
    public void actionPerformed(ActionEvent e){
        //change the properties when you click quitgamebutton
       
        sendMove("Recieve_Quit_Game");
       
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
                boardButtons[i][j].setText(ClientSymbol);
            }
        }
 
        // Enable buttons that were previously disabled due to user clicks
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            boardButtons[i][j].setEnabled(true);
        }
    }
       
}
 
//ALLDONE
    public static void main(String[] args)
    {
        //main methd for running the JFram
        GameGUI gameGUI = new GameGUI();
        gameGUI.setVisible(true);
    }
}
