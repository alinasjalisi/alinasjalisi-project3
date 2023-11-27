import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;

public class GameGUI extends JFrame
{
    //private methods to everythings we are creating
    //top buttons used to connect to the server
    private JTextField PlaySymbol;
    private JTextField IP_Address;
    private JTextField PortNum;
    private JTextArea HowTo; //instructions for game
    private JButton Connect_Disconnect;
    //text for has the last move
    private JTextArea LastMOVE;
    private JButton New_Game;
    //used to connect GameClient
    //private GameClient newPlayer;

    private JLabel optionsLabel;
    private JButton newGameButton;
    private JButton quitGameButton;
    private JLabel yourSymbolLabel;
    private JTextArea userSymbolTextArea;

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

        // Store properties for the top panel
        JPanel TFrame = new JPanel(new GridBagLayout());
        GridBagConstraints top_settings = new GridBagConstraints();

        JPanel Top_part = createTop();
        top_settings.gridx = 0;
        top_settings.gridy = 0;
        top_settings.weightx = 1.0;
        top_settings.fill = GridBagConstraints.HORIZONTAL;
        TFrame.add(Top_part, top_settings);
        
        
        JPanel LFrame = new JPanel(new GridBagLayout());
        GridBagConstraints left_settings = new GridBagConstraints();

        JPanel Left_part = createLeftPanel();
        left_settings.gridx = 0;
        left_settings.gridy = 0;
        left_settings.weightx = 0.2;
        left_settings.fill = GridBagConstraints.BOTH;
        LFrame.add(Left_part, left_settings);

        //store properties for the Middle section
        JPanel MFrame = new JPanel(new GridBagLayout());
        GridBagConstraints middle_settings = new GridBagConstraints();

        JPanel middle_part = createMiddlePanel();
        middle_settings.gridx = 0;
        middle_settings.gridy = 1;
        middle_settings.weightx = 1.0;
        middle_settings.weighty = 1.0;
        middle_settings.fill = GridBagConstraints.BOTH;
        MFrame.add(middle_part, middle_settings);

        // Store properties for the right section
        JPanel RFrame = new JPanel(new GridBagLayout());
        GridBagConstraints right_settings = new GridBagConstraints();

        JPanel right_part = createRightPanel();
        right_settings.gridx = 1;
        right_settings.gridy = 1;
        right_settings.weightx = 0.2; // Adjust this value as needed
        right_settings.weighty = 1.0;
        right_settings.fill = GridBagConstraints.BOTH;
        RFrame.add(right_part, right_settings);

        // Store properties for the bottom section
        JPanel BFrame = new JPanel(new GridBagLayout());
        GridBagConstraints bottom_settings = new GridBagConstraints();

        JPanel bottom_part = createBottomPanel();
        bottom_settings.gridx = 0;
        bottom_settings.gridy = 2;
        bottom_settings.gridwidth = 2;
        bottom_settings.weightx = 1.0;
        bottom_settings.fill = GridBagConstraints.HORIZONTAL;
        BFrame.add(bottom_part, bottom_settings);

       // Add components to the main frame
       add(TFrame, BorderLayout.NORTH);
       add(LFrame, BorderLayout.WEST);
       add(MFrame, BorderLayout.CENTER);
       add(RFrame, BorderLayout.EAST);
       add(BFrame, BorderLayout.SOUTH);
   }

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
    newGameButton.setPreferredSize(new Dimension(100, 20));
    //newGameButton.addActionListener(new NewGameButtonActionListener());
    optionsButtonsPanel.add(newGameButton);

    // Create a JButton to quit the current game
    quitGameButton = new JButton("Quit Game");
    quitGameButton.setPreferredSize(new Dimension(100, 20));
    //quitGameButton.addActionListener(new QuitGameButtonActionListener());
    optionsButtonsPanel.add(quitGameButton);

    // Create a JLabel for the "Your symbol is" section
    yourSymbolLabel = new JLabel("Your symbol is:");
    yourSymbolLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
    optionsButtonsPanel.add(yourSymbolLabel);

    // Add the options buttons panel to the right panel
    rightPanel.add(optionsButtonsPanel, BorderLayout.CENTER);

    // Create a JTextArea to display the user's symbol
    userSymbolTextArea = new JTextArea();
    userSymbolTextArea.setEditable(false);
    // Need to call a method from a different class to get the user's symbol
    userSymbolTextArea.setText("X");
    userSymbolTextArea.setFont((new Font(Font.SERIF, Font.PLAIN, 80)));
    // Maybe an if statement changing it to userSymbolTextArea.setText("O");
    // if the method that sets user symbols already assigned player 1 X

    // Add the user symbol text area to the right panel
    rightPanel.add(userSymbolTextArea, BorderLayout.SOUTH);

    return rightPanel;
}

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

//store properties for top of the panel
private JPanel createTop(){
        //setting the top panel with name, ip, port, and connect
        JPanel topPanel = new JPanel(new FlowLayout());


        PlaySymbol = new JTextField(7);
        topPanel.add(new JLabel("Player: "));
        topPanel.add(PlaySymbol);

        
        // displays IP Address
        IP_Address = new JTextField(7);
        topPanel.add(new JLabel("IP Address: "));
        topPanel.add(IP_Address);
        
        //displays the port used
        PortNum = new JTextField(5);
        topPanel.add(new JLabel("Port: "));
        topPanel.add(PortNum);

        Connect_Disconnect = new JButton("Connect");
        topPanel.add(Connect_Disconnect);

        return topPanel;

    }

    private JPanel createMiddlePanel(){

        JPanel middlePanel = new JPanel(new BorderLayout());

        JTextArea statusTextArea = new JTextArea();
        statusTextArea.setEditable(false);
        statusTextArea.setFont(new Font(Font.SERIF, Font.PLAIN, 18)); // Adjusted font size
        statusTextArea.setText("Player X's turn");
        middlePanel.add(statusTextArea, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        JButton[][] boardButtons = new JButton[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardButtons[i][j] = new JButton();
                boardButtons[i][j].setFont(new Font(Font.SERIF, Font.PLAIN, 24)); // Adjusted font size
                boardPanel.add(boardButtons[i][j]);
            }
        }
        middlePanel.add(boardPanel, BorderLayout.CENTER);

        return middlePanel;
    }

    private JPanel createBottomPanel(){
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



    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> {
            GameGUI gameGUI = new GameGUI();
            gameGUI.setVisible(true);
        });
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
