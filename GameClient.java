import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
 
//client side cod
//allow gameserver to call class
//magage the local Gui
//sed and recieve udate from the server
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
 
    /*public void setServer(GameServer server) {
        this.server = server;
    }*/
 
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
 
                    if (msg.equals("SYMBOL X")) {
                        gameGUI.receiveMsg(msg);
                    }
 
                    if (msg.equals("SYMBOL O")) {
                        gameGUI.receiveMsg(msg);
                    }
                    if (msg.equals("1")) {
                        gameGUI.receiveMsg(msg);
                    }
                    if (msg.equals("2")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 1 row 3
                    if (msg.equals("3")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 2 row 1
                    if (msg.equals("4")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 2 row 2
                    if (msg.equals("5")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 2 row 3
                    if (msg.equals("6")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 3 row 1
                    if (msg.equals("7")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 3 row 2
                    if (msg.equals("8")) {
                        gameGUI.receiveMsg(msg);
                    }
                    //they click col 3 row 3
                    if (msg.equals("9")) {
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
                    if (msg == null) {
                        //not correctly connected to the server
                        check = false;
                        disconnect();
                    }else{
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
 
    //HARD CODE FIRST PERSON TO CONNECT
    //having a hardtime getting the playcount from the server
   
    public void processMessage(String msg) {
        // Process the received message as needed
        // Example: Update the GUI, handle different message types, etc.
        gameGUI.receiveMsg(msg);
        //System.out.println("Recieve from geu " + msg);
        //send the message to server
        //server.enqueueMove(msg); //NOTWORKINGIDKY
 
       
    }

 
    public void writeMessage(String message) {
        if (isConnected()) {
            //add the message
            write.println(message);
            // System.out.println("recieved from server " + message);
            //call a method in game server to be able to recieve the message
            //server.enqueueMove(message);
            //remove message
            write.flush();
        }
    }
   
    //vommunicate with the server
    //public void ClientNetworking
    //add reading thread and fix boolean too
    //check if player is connected to server and used in GameGui
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
        //System.out.println(" check is connected in gameClient");
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
