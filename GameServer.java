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

        public GameClient(Socket playerSocket) {
            this.playerSocket = playerSocket;
            this.symbol = generateRandomSymbol();
            addPlayer(this);
        }

        @Override
        public void run() {
            try {
                send = new PrintWriter(playerSocket.getOutputStream(), true);
                receive = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));

                // Send the assigned symbol to the client
                send.println("SYMBOL " + symbol);

                while (true) {
                    String move = receive.readLine();
                    if (move == null) {
                        removePlayer(this);
                        dequeueAll();
                        //if nothing break
                        break;
                    }
                    //write message back
                    send.println(move);
                    send.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //insure to be executed
                try {
                    //close everything
                    send.close();
                    receive.close();
                    playerSocket.close();
                    removePlayer(this);
                    System.out.println("Connection lost:" + playerSocket.getRemoteSocketAddress());
                } catch (IOException e) {
                    //error occured
                    e.printStackTrace();
                }
            }
        }

        public void sendMove(String move) {
            send.println(move);
        }
    }

    private String generateRandomSymbol() {
        Random random = new Random();
        return random.nextBoolean() ? "X" : "O";
    }

    public synchronized void enqueueMove(String move) {
        synchronized (secret) {
            upcomingMove.add(move);
        }
    }

    private void dequeueAll() {
        synchronized (secret) {
            List<String> moves = new ArrayList<>(upcomingMove);
            upcomingMove.clear();
            for (GameClient player : connectedClients) {
                for (String move : moves) {
                    player.sendMove(move);
                }
            }
        }
    }

    public void addPlayer(GameClient player) {
        synchronized (secret) {
            connectedClients.add(player);
        }
    }

    public void removePlayer(GameClient player) {
        synchronized (secret) {
            connectedClients.remove(player);
        }
    }

    public void serve() {
        while (true) {
            try {
                Socket player = serverSock.accept();
                System.out.println("New Connection: " + player.getRemoteSocketAddress());
                new GameClient(player).start();
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