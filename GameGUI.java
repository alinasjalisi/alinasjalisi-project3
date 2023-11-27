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

        //setting the top panel with name, ip, port, and connect
        JPanel topPanel = new JPanel(new FlowLayout());
        
        // displays IP Address
        IP_Address = new JTextField(7);
        topPanel.add(new JLabel("IP Address "));
        IP_Address.setText("");
        topPanel.add(IP_Address);
        
        //displays the port used
        PortNum = new JTextField(5);
        topPanel.add(new JLabel("Port "));
        PortNum.setText("");
        topPanel.add(PortNum);
        add(topPanel); 

        // creates the connect button
        Connect_Disconnect = new JButton("Connect");
        topPanel.add(Connect_Disconnect);

        //adding instructions how to play the game at the bottom
        JPanel bottomPanel = new JPanel();
        HowTo = new JTextField(20);
        //needs to be at the bottom of JFrame
        bottomPanel.add(HowTo);

        //used to connect with the server methods
        startGameGUI();
    }

    private void startGameGUI()
    {

    }

    public static void main(String[] args) 
    {
        // create an instance of EnigmaFrame and calls it to run
        GameGUI gameGUI = new GameGUI();
        gameGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameGUI.setSize(900, 600);
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