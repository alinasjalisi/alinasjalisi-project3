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
    private JTextArea HowTo; //instructions for game
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
        //Connect_Disconnect.addActionListener(new connectAction()); //sere's part
        topPanel.add(Connect_Disconnect);
        add(topPanel, BorderLayout.NORTH);

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
