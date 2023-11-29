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
    private static final Set<String> assignedSymbols = new HashSet<>();
    //store the ipaddress
    private final String serverAddress;
    //store the port number
    private final int serverport;
    //store the gui of the client
    private GameGUI gameGUI;
    //take in the client move
    private String playerMove;

    //allow t initialize variable from the given variable store in the Gui
    public GameClient(String symbol, String serverAddress, int serverport, GameGUI gameGUI){
        this.symbol = symbol;
        this.serverAddress = serverAddress;
        this.serverport = serverport;
        this.gameGUI = gameGUI;
    }

    //allow for reading the incoming move
    private class ReadingThread extends Thread
    {
        //function to be able to run the actaul thread
        public void run() {
            try {
                while (true) {
                    String receivedMove = read.readLine();
        
                    if (receivedMove == null) {
                        check = false;
                        disconnect();
                    } else if (check) {
                        gameGUI.updatePlayer(receivedMove);
                    } else {
                        gameGUI.receiveMove(receivedMove);
                    }
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
        public void sendMove(String move){
            if(isConnected()){
                write.println(move);
            }
        }

        // Assigns a random symbol to the player once connected, either X or O
        public void assignSymbol() 
        {
            // Keep generating a random symbol until an unassigned one is found
            do 
            {
                Random random = new Random();
                this.symbol = (random.nextBoolean()) ? "X" : "O";
            } 
            while (!assignedSymbols.add(this.symbol)); // Add the symbol to the set, continue if it's already assigned
        }

        //getter function
        public String getSymbol() 
        {
            return symbol;
        }

        //check if player is connected to server and used in GameGui
        public boolean connectToServer(){
            try{
                //call the disconnect function
                //disconnect();

                socket = new Socket(serverAddress, serverport);
                //call to be able to show other player move
                write = new PrintWriter(socket.getOutputStream(), true);
                //call read to input
                read = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //perform handskate to insure to convey trust, respect, balance, and equalit
                write.println("SECRET\n3c3c4ac618656ae32b7f3431e75f7b26b1a14a87\nNAME\n" + symbol);
                //delete stuff
                write.flush();
                //call read function
                new ReadingThread().start();
                //check if the function is tru
                check = true;
                //return since it work to help with later boolean
                System.out.println("Connected");
                return true;
            }catch (UnknownHostException e) {
                //error if invalid ip
                e.printStackTrace();
                gameGUI.StateError("Invalid Port. Please enter a valid port number.");
                return false;
            } catch (NumberFormatException e) {
                //error is invalid port
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
                gameGUI.StateError("Invalid Port and/or IP Address. Please try again.");
                return false;
            }
        }
        
        //store if player is connected to server
        public boolean isConnected(){
            return check;
        }

        //fucntion to perform diconnection
        public void disconnect(){
            //allow for everything to close and state not connected
            try{
                if(isConnected()){
                    write.close();
                    read.close();
                    socket.close();
                    check = false;
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }


}
//connect toServer to connect the server and initialize the gae state
//sendMove(move:move) to send use moves to the server for processing
//updateGui to update the local graphic user interface based on server update 

