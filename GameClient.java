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
public class GameClient implements Runnable{
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
    @Override
    public void run() {
        try {
            // Initialize resources (send, receive, etc.)

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
        public boolean connectToServer() {
            try {
                // Close existing resources if any
                disconnect();
        
                socket = new Socket(serverAddress, serverport);
                write = new PrintWriter(socket.getOutputStream(), true);
                read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
                // Perform handshake
                write.println("SECRET\n3c3c4ac618656ae32b7f3431e75f7b26b1a14a87\nNAME\n" + symbol);
                write.flush();
        
                // Instantiate the ReadingThread
                ReadingThread readingThread = new ReadingThread();
                
                // Start a new thread to handle reading from the server
                new Thread(readingThread).start();
        
                check = true;
                System.out.println("Connected");
                return true;
            } catch (UnknownHostException e) {
                e.printStackTrace();
                gameGUI.StateError("Invalid Host. Please enter a valid host address.");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                gameGUI.StateError("Invalid Port. Please enter a valid port number.");
            } catch (ConnectException e) {
                e.printStackTrace();
                gameGUI.StateError("Connection refused. Please check the server availability.");
            } catch (IOException e) {
                e.printStackTrace();
                gameGUI.StateError("Connection error");
            }
        
            // Close resources if there's an exception
            disconnect();
            return false;
        }
        
        
        //store if player is connected to server
        public boolean isConnected(){
            return check;
        }

        //fucntion to perform diconnection
        private void disconnect() {
            try {
                if (socket != null) {
                    socket.close();
                }
                if (write != null) {
                    write.close();
                }
                if (read != null) {
                    read.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


}

//connect toServer to connect the server and initialize the gae state
//sendMove(move:move) to send use moves to the server for processing
//updateGui to update the local graphic user interface based on server update 

