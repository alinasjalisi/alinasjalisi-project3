import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameClient{
    //used to read incoming move from person you are playing against
    private BufferedReader read;
    //used to write in order to send moe to other player
    private PrintWriter write;
    //allow client to have connect to server
    private Socket socket;
    //check that the user is correctly connect to the server
    private boolean check = false;
    //store the ipaddress
    private final String serverAddress;
    //store the port number
    private final int serverport;
    //store the gui of the client
    private GameGUI gameGUI;
    //store server properties
    public GameServer server;

    //allow t initialize variable from the given variable store in the Gui
    public GameClient(String serverAddress, int serverport, GameGUI gameGUI){
        this.serverAddress = serverAddress;
        this.serverport = serverport;
        this.gameGUI = gameGUI;  
    }
 
    //inner class repesenting the thread for reading move
    private class ReadingThread extends Thread{
        //store the play symbol
        //protected String msg;
        //fuction to run thread
        public void run() {
            try {
                // Initialize resources (send, receive, etc.)
                while (isConnected()) {
                    //create variable that store incoming message being passed in the server
                    String msg = read.readLine();
                    //use read for assigning synbil

                    if (msg.equals("D_R_A_W")) {
                        gameGUI.receiveMsg(msg);
                    }

                    if (msg.equals("SYMBOL O")) {
                        gameGUI.receiveMsg(msg);
                    }
                    if (msg.equals("1_O")) {
                        gameGUI.receiveMsg(msg);
                    }
                    if (msg.equals("2_O")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 1 row 3
                    if (msg.equals("3_O")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 2 row 1
                    if (msg.equals("4_O")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 2 row 2
                    if (msg.equals("5_O")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 2 row 3
                    if (msg.equals("6_O")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 3 row 1
                    if (msg.equals("7_O")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 3 row 2
                    if (msg.equals("8_O")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 3 row 3
                    if (msg.equals("9_O")) {
                        gameGUI.receiveMsg(msg);
                    }


                    if (msg.equals("SYMBOL X")) {
                        gameGUI.receiveMsg(msg);
                    }
                    if (msg.equals("1_X")) {
                        gameGUI.receiveMsg(msg);
                    }
                    if (msg.equals("2_X")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 1 row 3
                    if (msg.equals("3_X")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 2 row 1
                    if (msg.equals("4_X")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 2 row 2
                    if (msg.equals("5_X")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 2 row 3
                    if (msg.equals("6_X")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 3 row 1
                    if (msg.equals("7_X")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 3 row 2
                    if (msg.equals("8_X")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 3 row 3
                    if (msg.equals("9_X")) {
                        gameGUI.receiveMsg(msg);
                    }
                    if(msg.equals("TWO_PLAYER_CONNECTED")){
                        gameGUI.receiveMsg(msg);
                    }
                    if(msg.equals("O_WON")){
                        gameGUI.receiveMsg(msg);
                    }
                    if(msg.equals("X_WON")){
                        gameGUI.receiveMsg(msg);
                    }
                    //if msg == a click button that want to start a quit button, communite that you are starting new game
                    if (msg.equals("Quit_Game")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //detect is there is a last move sen out last move
                    if (msg.equals("New_Game")) {
                        gameGUI.receiveMsg(msg);
                    }
                    if(msg.equals("D_R_A_W")){
                        gameGUI.receiveMsg(msg);
                    }
                    if (msg == null) {
                        //not correctly connected to the server
                        check = false;
                        disconnect();
                    }
                    else{
                        processMessage(msg);
                    }
                }
            } catch (IOException e) {
                    //handle disconnect error
                    if (isConnected()) {
                        //call the disconnect to disconnect
                        disconnect();
                        //print error
                        gameGUI.StateError("Server Has Disconnected");
                    }
                }
            }
        }
 
    // Assigns a random symbol to the player once connected, either X or O
 
    public void processMessage(String msg) {
        gameGUI.receiveMsg(msg);

    }

 
    public void writeMessage(String message) {
        if (isConnected()) {
            write.println(message);
            write.flush();
        }
    }
   
    public boolean connectToServer() {
        try {
            System.out.println("Connecting to server at " + serverAddress + ":" + serverport);
 
            // Close existing resources if any
            disconnect();
            //open socket and get ip and port
            socket = new Socket(serverAddress, serverport);
            //call printer to be able to outprint
            write = new PrintWriter(socket.getOutputStream(), true);
            //call read to input
            read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       
            // Perform handshake
            write.println("SECRET\n3c3c4ac618656ae32b7f3431e75f7b26b1a14a87\nNAME\n" );
            //delete stuff
            write.flush();
            // call read function
            new ReadingThread().start();
            //check if connected to server
            check = true;
            //return since it work to help later boolean
            return true;
        } catch (UnknownHostException e) {
            //error if invalid ip
            e.printStackTrace();
            gameGUI.StateError("Invalid Host. Please enter a valid host address.");
            return false;
        } catch (NumberFormatException e) {
            //error in invalid port
            e.printStackTrace();
            gameGUI.StateError("Invalid Port. Please enter a valid port number.");
            return false;
        } catch (ConnectException e) {
            //error if connection doesn't work
            e.printStackTrace();
            gameGUI.StateError("Connection refused. Please check the server availability.");
            return false;
        } catch (IOException e) {
            //anything could be wrong
            e.printStackTrace();
            gameGUI.StateError("Connection error");
            return false;
        }
    }  
    //store if player is connected to server
    public boolean isConnected(){
        return check;
    }
    //fucntion to perform diconnection
    public void disconnect() {
        try {
            if (isConnected()) {
                write.close();
                read.close();
                socket.close();
                check = false;
                //server = null; //added
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
