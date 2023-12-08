import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameServer implements Runnable {

    private final Object secret = new Object();
    private ServerSocket serverSock;
    private List<GameClient> connectedClients;
    private List<String> upcomingMove;
    public int tracker = 1;

<<<<<<< HEAD
    //private static GameServer server;

=======
>>>>>>> f4b2359fb1f51b2aa1ba23ccb082482e93aacded
    //store num of ppl in the server
    public int playerCount = 0;

    public GameServer(int serverPort) {
        try {
            serverSock = new ServerSocket(serverPort);
            connectedClients = new ArrayList<>();
            upcomingMove = new ArrayList<>();
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

<<<<<<< HEAD
        
=======
>>>>>>> f4b2359fb1f51b2aa1ba23ccb082482e93aacded
        //add the play to server and read the size joinning server
        //keep track of the client added
        public GameClient(Socket playerSocket) {
            this.playerSocket = playerSocket;
            addPlayer(this);
<<<<<<< HEAD
            //System.out.println(connectedClients.size());
=======
            System.out.println(connectedClients.size());
>>>>>>> f4b2359fb1f51b2aa1ba23ccb082482e93aacded
            try{
             // Move the call to sendSymbol after initializing the send PrintWriter
             send = new PrintWriter(playerSocket.getOutputStream(), true);
             receive = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
             sendSymbol();
<<<<<<< HEAD
             //receive();
=======
>>>>>>> f4b2359fb1f51b2aa1ba23ccb082482e93aacded
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
                    if (move.equals(null)) {
                        //removePlayer(this);
                        enqueueMove(move);
                        dequeueAll();
                        break;
                    }

                    // Broadcast the move to all connected clients
                    upcomingMove.add(move);
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
<<<<<<< HEAD

            System.out.println("Recieved move from client" + move);
            enqueueMove(move);
            dequeueAll();
=======
            send.println(move);
>>>>>>> f4b2359fb1f51b2aa1ba23ccb082482e93aacded

        }

        public synchronized void sendSymbol() {
<<<<<<< HEAD
            if(tracker == 1 || tracker == 2){
            tracker++;
            playerCount++;
            //System.out.println("Count" + playerCount);
            if(playerCount == 1){
            this.symbol = "X";
            //System.out.println(this.symbol);
           }
           else{
            this.symbol = "O";
            //System.out.println(this.symbol);
           }
           //System.out.println("SYMBOL " + this.symbol);
=======
            playerCount++;
            System.out.println("Count" + playerCount);
            if(playerCount == 1){
            this.symbol = "X";
            System.out.println(this.symbol);
           }
           else{
            this.symbol = "O";
            System.out.println(this.symbol);
           }
           System.out.println("SYMBOL " + this.symbol);
>>>>>>> f4b2359fb1f51b2aa1ba23ccb082482e93aacded
            send.println("SYMBOL " + this.symbol);
        }
    }
}

    //take in all teh message
    public synchronized void enqueueMove(String move) {
<<<<<<< HEAD

        System.out.println("Recieved move from gui" + move);
            //enqueueMove(move);
            //();
=======
>>>>>>> f4b2359fb1f51b2aa1ba23ccb082482e93aacded
            upcomingMove.add(move);
    }

    //display all the message
    private synchronized void dequeueAll() {
<<<<<<< HEAD
        List<String> moves = new ArrayList<>(upcomingMove);
        upcomingMove.clear();
        for (GameClient player : connectedClients) {
            for (String move : moves) {
                player.sendMove(move);
=======
            List<String> moves = new ArrayList<>(upcomingMove);
            upcomingMove.clear();
            for (GameClient player : connectedClients) {
                for (String move : moves) {
                    player.sendMove(move);
                }
>>>>>>> f4b2359fb1f51b2aa1ba23ccb082482e93aacded
            }
    }
<<<<<<< HEAD
=======

    /*public synchronized void addPlayer(GameClient player) {
            connectedClients.add(player);
            System.out.println("inc. playercount : " + playerCount);
    }*/

    /*public synchronized void removePlayer(GameClient player) {
            connectedClients.remove(player);
        
    }*/


>>>>>>> f4b2359fb1f51b2aa1ba23ccb082482e93aacded
    public void serve() {
        while (true) {
            try {
                Socket player = serverSock.accept();
                //System.out.println("New Connection: " + player.getRemoteSocketAddress());
                GameClient client = new GameClient(player);
                client.setServer(this); // Set the server using the method
                client.start();
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
        Thread serverThread = new Thread(server);
        serverThread.start();
    }
}