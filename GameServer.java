import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
 
public class GameServer implements Runnable {
 
    private final Object secret = new Object();
    private ServerSocket serverSock;
    private List<GameClient> connectedClients;
    //upcoming = move in local gui sent from player 1 to server
    private List<String> upcomingMove;
    //outgoing = server sends out player 1's move to other player's local GUI
    //private List<String> outgoingMove;
   // public int tracker = 1;
 
    //private static GameServer server;
 
    //store num of ppl in the server
    //public int playerCount = 0;

    private GameClient gameClient;

    private int playerCount = 0;

 
    public GameServer(int serverPort) {
        try {
            serverSock = new ServerSocket(serverPort);
            connectedClients = new ArrayList<>();
            upcomingMove = new ArrayList<>();
            //outgoingMove = new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Cannot establish server socket");
            System.exit(1);
        }
    }
 
    public class GameClient extends Thread {
        private Socket playerSocket;
        private PrintWriter send;
        private BufferedReader receive;
        private String symbol;
        private GameServer server;
        //store num of ppl in the server
 
       
        //add the play to server and read the size joinning server
        //keep track of the client added
        public GameClient(Socket playerSocket) {
            this.playerSocket = playerSocket;
            addPlayer(this);
            //System.out.println(connectedClients.size());
            try{
             // Move the call to sendSymbol after initializing the send PrintWriter
             send = new PrintWriter(playerSocket.getOutputStream(), true);
             receive = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
            }catch (IOException e) {
                e.printStackTrace();  // Handle the exception appropriately, e.g., log it or exit
            }
        }
 
        //occur when server is running
        @Override
        public void run() {
            try {
                send = new PrintWriter(playerSocket.getOutputStream(), true);
                receive = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
                //Send the assigned symbol to the GameClient taht then get display in us
                //send.println("SYMBOL:" + this.symbol);
                //when client connect you just send whatever
 
                //listen for a move
                while (true) {
                    String move = receive.readLine();
                    System.out.println("Server Message: " + move);

                    //FOR O CELLS
                    if(move.equals("1,O")){
                        move ="1_O";
                    }
                    if(move.equals("2,O")){
                        move ="2_O";
                    }
                    if(move.equals("3,O")){
                        move ="3_O";
                    }
                    if(move.equals("4,O")){
                        move ="4_O";
                    }
                    if(move.equals("5,O")){
                        move ="5_O";
                    }
                    if(move.equals("6,O")){
                        move ="6_O";
                    }
                    if(move.equals("7,O")){
                        move ="7_O";
                    }
                    if(move.equals("8,O")){
                        move ="8_O";
                    }
                    if(move.equals("9,O")){
                        move ="9_O";
                    }

                    //NOW FOR X CELLS
                    if(move.equals("1,X")){
                        move ="1_X";
                    }
                    if(move.equals("2,X")){
                        move ="2_X";
                    }
                    if(move.equals("3,X")){
                        move ="3_X";
                    }
                    if(move.equals("4,X")){
                        move ="4_X";
                    }
                    if(move.equals("5,X")){
                        move ="5_X";
                    }
                    if(move.equals("6,X")){
                        move ="6_X";
                    }
                    if(move.equals("7,X")){
                        move ="7_X";
                    }
                    if(move.equals("8,X")){
                        move ="8_X";
                    }
                    if(move.equals("9,X")){
                        move ="9_X";
                    }


                    if(move.equals("Receive_New_Game")){
                        move = "New_Game";

                    }

                    if(move.equals("Recieve_Quit_Game")){
                        move = "Quit_Game";
                    }
                    
                    enqueueMove(move);
                    dequeueAll();
                    
                }
           
            }catch (IOException e) {
                e.printStackTrace();
                }
             finally {
                try {
                    send.close();
                    receive.close();
                    playerSocket.close();
                    removePlayer(this);
                    //System.out.println("Connection lost:" + playerSocket.getRemoteSocketAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        public void setServer(GameServer server) {
            this.server = server;
        }
 
        public void removePlayer(GameClient player) {
            synchronized (secret) {
                connectedClients.remove(player);
                playerCount--;
            }
        }
 
        public void addPlayer(GameClient player) {
            synchronized (secret) {
                connectedClients.add(player);
            }
        }
 
        public synchronized void sendMove(String move) {
            // Send a move to the client
            System.out.println("in send move in server: " + move);
            send.println(move);
        }
 
        public synchronized void recieveMove(String move){
 
            System.out.println("Recieved move from gui update by user move" + move);
            enqueueMove(move);
            dequeueAll();
 
        }
 
        public synchronized void sendSymbol() {
            // if(tracker == 1 || tracker == 2){
            // tracker++;
            System.out.println("Count: " + playerCount);
            //System.out.println("Count" + playerCount);
            if(playerCount == 0){
            playerCount++;
            this.symbol = "X";
            //System.out.println(this.symbol);
           }
           else{
            playerCount++;
            this.symbol = "O";
            //System.out.println(this.symbol);
           }
           //System.out.println("SYMBOL " + this.symbol);
            send.println("SYMBOL " + this.symbol);
        }
    }
//}
 
    //take in all teh message
    public synchronized void enqueueMove(String move) {
        System.out.println("Recieved move from gui...we sent player 1 move from their gui to server" + move);
            //enqueueMove(move);
            //();
            upcomingMove.add(move);
            dequeueAll();
    }

    public synchronized void dequeueMove(String move) {
        List<String> outgoingMove = new LinkedList<String>();
        for(String m : outgoingMove) {
            if(move.isEmpty() != false) upcomingMove.add(move);
            }
            for(String d : upcomingMove) {
                upcomingMove.remove(move);
            }
            //enqueueMove(d);
            //dequeueAll();
//dequeu ones you just added to upcoming move list from outgoing moves. thats what updated on other gui

        System.out.println("we got player 1 move from their gui in our gui thru server" + move);
            //enqueueMove(move);
            //();
            upcomingMove.get(0);
            
    }

    public synchronized String dequeueMove() {
        if (upcomingMove.isEmpty()) {
            return null;
        }
        return upcomingMove.remove(0);
    }
 
    //display all the message
    private synchronized void dequeueAll() {
        List<String> moves = new ArrayList<>(upcomingMove);
        upcomingMove.clear();
        for (GameClient player : connectedClients) {
            for (String move : moves) {
                player.sendMove(move);
            }
        }
    }
    public void serve() {
        while (true) {
            // System.out.println("ACCEPTED1");
            try {
                Socket player = serverSock.accept();

                System.out.println("count " + playerCount);
                if(playerCount == 0 || playerCount == 1){
                GameClient client = new GameClient(player);
                client.sendSymbol();
                client.setServer(this); // Set the server using the method
                client.start();
               }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    @Override
    public void run() {
        serve();
    }
 
    public static void main(String[] args) {
        int serverPort = 1234;
        GameServer server = new GameServer(serverPort);
        server.serve();
        // Thread serverThread = new Thread(server);
        // serverThread.start();
    }
}
