import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;

public class GameGUI extends JFrame
{
    //private methods to everythings we are creating
    //top buttons used to connect to the server
    private JTextField IP_Address;
    private JTextField PortNum;
    private JTextField HowTo; //instructions for game
    private JButton Connect_Disconnect;

    private JButton New_Game;

    private JButton Click1A;
    private JButton Click2A;
    private JButton Click3A;
    private JButton Click1B;
    private JButton Click2B;
    private JButton Click3B;
    private JButton Click1C;
    private JButton Click2C;
    private JButton Click3C;
   
    //used to connect GameClient
    private GameClient newPlayer;

    public GameGUI()
    {


        super("TicTacToe");

        

        //used to connect with the server methods
        startGameGUI();
    }


    private void startGameGUI()
    {
        setTitle("TicTacToe(connected)");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
                
        //setting the top panel with name, ip, port, and connect
        JPanel topPanel = new JPanel(new FlowLayout());
        
        // displays IP Address
        IP_Address = new JTextField(7);
        topPanel.add(new JLabel("IP Address "));
        //IP_Address.setText(""); don't think we need
        topPanel.add(IP_Address);
        
        //displays the port used
        PortNum = new JTextField(5);
        topPanel.add(new JLabel("Port "));
        //PortNum.setText(""); don't need this
        topPanel.add(PortNum);
        //add(topPanel);  don't think we need

        // creates the connect button
        Connect_Disconnect = new JButton("Connect");
        Connect_Disconnect.addActionListener(new connectAction());
        topPanel.add(Connect_Disconnect);
        add(topPanel, BorderLayout.NORTH);

        //adding instructions how to play the game at the bottom
        JPanel bottomPanel = new JPanel();
        HowTo = new JTextField(20);
        //needs to be at the bottom of JFrame
        bottomPanel.add(HowTo);

        // alina - not sure if this works but i tried lol: 
    // Create a panel for the status and TicTacToe board
    private JPanel createMiddlePanel() {
    JPanel middlePanel = new JPanel(new GridLayout(2, 1));

    // Create a panel for the TicTacToe board
    JPanel boardPanel = new JPanel(new GridLayout(3, 3));

    // Create nine JButtons for the TicTacToe board cells
    JButton[][] boardButtons = new JButton[3][3];
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            boardButtons[i][j] = new JButton();
            boardButtons[i][j].addActionListener(new BoardButtonActionListener(i, j));
            boardPanel.add(boardButtons[i][j]);
        }
    }

    // Create a JTextArea to display the status
    JTextArea statusTextArea = new JTextArea();
    statusTextArea.setEditable(false);
    statusTextArea.setFont(new Font(Font.SERIF, Font.PLAIN, 24));
    statusTextArea.setText("Player X's turn");

    // Add the board panel and status text area to the middle panel
    middlePanel.add(boardPanel);
    middlePanel.add(statusTextArea);

    return middlePanel;
}

    private JPanel createRightPanel() {
    JPanel rightPanel = new JPanel(new GridLayout(4, 1));

    // Create a JLabel for the "Options" section
    JLabel optionsLabel = new JLabel("Options");
    rightPanel.add(optionsLabel);

    // Create a JButton to start a new game
    JButton newGameButton = new JButton("New Game");
    newGameButton.addActionListener(new NewGameButtonActionListener());
    rightPanel.add(newGameButton);

    // Create a JButton to quit the current game
    JButton quitGameButton = new JButton("Quit Game");
    quitGameButton.addActionListener(new QuitGameButtonActionListener());
    rightPanel.add(quitGameButton);

    // Create a JLabel for the "Your symbol is" section
    JLabel yourSymbolLabel = new JLabel("Your symbol is:");
    rightPanel.add(yourSymbolLabel);

    // Create a JTextArea to display the user's symbol
    JTextArea userSymbolTextArea = new JTextArea();
    userSymbolTextArea.setEditable(false);
    // Need to call a method from a different class to get the user's symbol
    userSymbolTextArea.setText("X");
    //maybe an if statement changing it to userSymbolTextArea.setText("O"); if the method that sets user symbols already assigned player 1 X



    // Add the user symbol text area to the right panel
    rightPanel.add(userSymbolTextArea);

    return rightPanel;
}

// Create a panel to display the last move
private JPanel createLeftPanel() {
    JPanel leftPanel = new JPanel(new FlowLayout());

    // Create a JTextArea to display the last move message
    JTextArea lastMoveTextArea = new JTextArea();
    lastMoveTextArea.setEditable(false);
    // Need to call a method from the LastMove class to get the last move message
    lastMoveTextArea.setText("Player X made the last move at A1");
    //^^need to fix cuz it should be calling on lastmove.java class to update the text here ^^

    // Add the last move text area to the left panel
    leftPanel.add(lastMoveTextArea);

    return leftPanel;
}

    }

    public static void main(String[] args) 
    {
        // create an instance of EnigmaFrame and calls it to run
        GameGUI gameGUI = new GameGUI();
        gameGUI.setVisible(true);
    }
}
//GUI for the game


//pseudo code for GUI - done by gabby 
/*
 *  JFrame labeled "TicTacToe"
 * 
 *  mainPanel - contains board
 * make all 9 parts of the board clickable
 *  when O clicks on a blank, it turns into an O
 *  when X clicks on a blank, it turns into an X
 *  after the user clicks that area becomes NON EDITABLE
 * 
 * option button QUIT to end game
 * 
 * bottomPanel - JTextfield with instructions
 * top panel for ip port
 * 3 more pannels
 * 1. middle for status and tictactoe board - main pannel
 * 2. right - options and your symbol
 * 3. left - last position 
 * 
 * display Player's symbol in bottom right corner
 * 
 * status changes of player's turn
 * 
 */


/*add for fucntion and stuff - Sere
button for x and o
//create an instance of Gamelient
//check ifConnected to server
//send message such as x and o
//recieve essage like x and o
//error handling
//logic to quit the current game and start aew one
//shoes the symbol
//the game status like whos is who turn and such

//craete an updateBoardMethd: to update the gameboard once action are done and reflecte cahnge made in the tictactoesboard
//displayStatus(): display the current came state
//playerTurn: method to display when it a certain person turn



*/ 
