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
    //store the player symbol
    private String symbol;
    //keep track of symbol that have been assigned to payers to ensure that eah symbol is unique
    private static final Set<String> assignedSymbols = new HashSet<>();
    //store the ipaddress
    private final String serverAddress;
    //store the port number
    private final int serverport;
    //store the gui of the client
    private GameGUI gameGUI;
    //store server properties
    public GameServer server;

    public int playerCount = 0 ;


    //allow t initialize variable from the given variable store in the Gui
    public GameClient(String serverAddress, int serverport, GameGUI gameGUI){
        this.serverAddress = serverAddress;
        this.serverport = serverport;
        this.gameGUI = gameGUI;
        //this.server = server;
        //playerCount++;
    }

    //inner class repesenting the thread for reading move
    private class ReadingThread extends Thread{
        //store the play symbol
        //protected String move; 
        //fuction to run thread
        public void run() {
            try {
                // Initialize resources (send, receive, etc.)
                while (isConnected()) {
                    //assign symbol in thread, say you symbol x or 0
                    if(line)

                    //create variable that store incoming move
                    String receivedMoveString = read.readLine();
                    //use read for assigning synbil
                    

                    if (receivedMoveString == null) {
                        //not correctly connected to the server
                        check = false;
                        disconnect();
                    }
                            gameGUI.receiveMove(receivedMoveString);
                            //update the player player on both side
                            gameGUI.updatePlayer(receivedMoveString);
                    }
                } catch (IOException e) {
                    if (isConnected()) {
                        disconnect();
                        gameGUI.StateError("Server Has Disconnected");
                    }
                }
            }
        }

        
    //used for write a move and connect to the server
    public synchronized void sendMove(String move){
        if(isConnected()){
            write.println(move);
            write.flush();
        }

    }

    // Assigns a random symbol to the player once connected, either X or O

    //HARD CODE FIRST PERSON TO CONNECT
    //having a hardtime getting the playcount from the server
    public boolean assignSymbol() 
    {
        /*int count = server.playCount();
        System.out.println("Count: " + count);
        if(count == 0){
            this.symbol = "X";
            //count++;
            System.out.println("Game client" + this.symbol);
        }
        else{
            this.symbol = "O";
            System.out.println("Game client" + this.symbol);

        }
        */
        this.symbol = symbol;
        return true;
    }

    

    //getter function
    public String getSymbol() 
    {
        return this.symbol;
    }

    public void writeMessage(String message) {
        if (isConnected()) {
            //add the message
            write.println(message);
        }
    }
    
    //vommunicate with the server 
    //public void ClientNetworking
    //add reading thread and fix boolean too
    //check if player is connected to server and used in GameGui
    public boolean connectToServer() {
        try {
            // Close existing resources if any
            disconnect();
            //open socket and get ip and port
            socket = new Socket(serverAddress, serverport);
            //call printer to be able to outprint
            write = new PrintWriter(socket.getOutputStream(), true);
            //call read to input 
            read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
            // Perform handshake
            write.println("SECRET\n3c3c4ac618656ae32b7f3431e75f7b26b1a14a87\nNAME\n" + symbol);
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}