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
    private List<String> upcomingMove;
   

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
 
        public GameClient(Socket playerSocket) {
            this.playerSocket = playerSocket;
            addPlayer(this);
            try{
             // Move the call to sendSymbol after initializing the send PrintWriter
             send = new PrintWriter(playerSocket.getOutputStream(), true);
             receive = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
            }catch (IOException e) {
                e.printStackTrace(); 
            }
        }
 
        //occur when server is running
        @Override
        public void run() {
            try {
                send = new PrintWriter(playerSocket.getOutputStream(), true);
                receive = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));

                while (true) {
                    String move = receive.readLine();

                    if(move.equals("HWIN1 O")){
                        move = "O_WON";
                    }
                    if(move.equals("HWIN2 O")){
                        move = "O_WON";
                    }
                    if(move.equals("HWIN3 O")){
                        move = "O_WON";
                    }
                    if(move.equals("VWIN1 O")){
                        move = "O_WON";
                    }
                    if(move.equals("VWIN2 O")){
                        move = "O_WON";
                    }
                    if(move.equals("VWIN3 O")){
                        move = "O_WON";
                    }
                    if(move.equals("DWIN1 O")){
                        move = "O_WON";
                    }
                    if(move.equals("DWIN2 O")){
                        move = "O_WON";
                    }
                    if(move.equals("HWIN1 X")){
                        move = "X_WON";
                    }
                    if(move.equals("HWIN2 X")){
                        move = "X_WON";
                    }
                    if(move.equals("HWIN3 X")){
                        move = "X_WON";
                    }
                    if(move.equals("VWIN1 X")){
                        move = "X_WON";
                    }
                    if(move.equals("VWIN2 X")){
                        move = "X_WON";
                    }
                    if(move.equals("VWIN3 X")){
                        move = "X_WON";
                    }
                    if(move.equals("DWIN1 X")){
                        move = "X_WON";
                    }
                    if(move.equals("DWIN2 X")){
                        move = "X_WON";
                    }

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

                    if(move.equals("Draw")){
                        move = "D_R_A_W";
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
            send.println(move);
        }
 
        public synchronized void recieveMove(String move){
            enqueueMove(move);
            dequeueAll();
 
        }
 
        public synchronized void sendSymbol() {
            if(playerCount == 0){
            playerCount++;
            this.symbol = "X";
           }
           else{
            playerCount = 5;
            this.symbol = "O";
           }
           send.println("SYMBOL " + this.symbol);
        }

        public synchronized void allPlay(){

            if(playerCount == 1){
                send.println("TWO_PLAYER_CONNECTED");
                broadcastMessage("TWO_PLAYER_CONNECTED");
            }
            
        }

        private synchronized void broadcastMessage(String message) {
            for (GameClient player : connectedClients) {
                player.sendMove(message);
            }
        }
    }
 
    //take in all teh message
    public synchronized void enqueueMove(String move) {
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
            try {
                Socket player = serverSock.accept();
                if(playerCount == 0 || playerCount == 1){
                GameClient client = new GameClient(player);
                client.allPlay();
                client.sendSymbol();
                client.setServer(this); 
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
    }
}
