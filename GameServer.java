import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//code for the server
//has instance if tictactoe board to manage state and enofr game logic
//help manage GameClient to connect two different user
//recieve move and allow server to actaully run
public class GameServer{
    //initialize the tictacToeBoard
    private TicTacToeBoard tictacToeBoard;
    //allow to server to listen to incoming connected to certain player
    private ServerSocket serverSock;
    //manage the access to the Server
    private final Object secret = new Object();
    //keep track of the people connected to the server
    private List<GameClient> upcomingPlayer;
    //keep track of the playermove
    private List<String> upcomingMove;

    //allow to take in the specific port
    public GameServer(int serverport){
        //craete new server
        try{
            serverSock = new ServerSocket(serverport);
            upcomingPlayer = new ArrayList<>();
            upcomingMove = new ArrayList<>();
            ticTacToeBoard = new TicTacToeBoard(); 

        } catch (IOException e) {
            //if it does not work
            System.err.println("Cannot establish server socket");
            System.exit(1);
        }
    
    }

    //communicate server to client
    public class GameClient extends Thread{
        //represent the socket which the serve communites w/client
        private Socket playerSocket;
        //used to send message
        private PrintWriter send;
        //used to recieve message
        private BufferedReader receieve;

        //allow for incomingplayer to connect and accept the serevr
        public GameClient(Socket playerSocket){
        //assign the player to the socket
        this.playerSocket = playerSocket;
        //add the newplayer
        addPlayer(this);

    }

    //used to override the thread and craete the behavior for the thread
    public void run(){
        try{
        //initialize the send message to client
        send = new PrintWriter(playerSocket.getOutputStream());
        //initize the read message to client
        receieve = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
        //allow for inifite loop
        while (true) {
            //reaad line from client inpute
            String move = receieve.readLine();
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
    } catch (Exception e) {
        //something wrong
        e.printStackTrace();
    } finally {
        //insure to be executed
        try {
            //close everything
            send.close();
            receieve.close();
            playerSocket.close();
            upcomingPlayer.remove(this);
            System.out.println("Connection lost:" + playerSocket.getRemoteSocketAddress());
        } catch (IOException e) {
            //error occured
            e.printStackTrace();
            }
        }
    }
    

    //send the move
    public void sendMove(String move){
        //send and shows the more
        send.println(move);
        send.flush();
    }

    //check if the player is valid
    public boolean isValid(){
        return !playerSocket.isClosed();
    }
}
public synchronized void enqueueMove(String move) {
    synchronized (secret) {
        upcomingMove.add(move);
    }
}

//remove all the moves
private void dequeueAll(){
        ///get the list of message
        List<String> moves = new ArrayList<>(upcomingMove);
        //clear og list
        upcomingMove.clear();
        //iterate through copy and send out
        for(GameClient player : upcomingPlayer){
            for (String move : moves) {
                player.sendMove(move);
        }
    }
}

//add the new player to server
public void addPlayer(GameClient player) {
    synchronized (secret) {
        upcomingPlayer.add(player);
    }
}

//remove the player form server
public void removePlayer(GameClient player){
    synchronized(secret){
        upcomingPlayer.add(player);

    }
}
// Correct placement of serve() method
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

//start the new game
public void startGame(){
    //reset the board

    //update the player

}

public void processMove( GameClient player, String move){
    //store the row and colomn

    //allow for move to be processw when valid move
    
    //statment when in a win, drae and lose



}

public void updateClient(){
    //send the move to update voth side
}

public String getCurrentState(){
    //reset the gae board, update client ad display update to the clients
}



        

public static void main(String args[]) {
    int port = Integer.parseInt(args[0]);
    GameServer server = new GameServer(port);
    server.serve();
}
}
//
//add to connect to tictactoeboard:
//startGame method to initiate new game
//processMove: to recieve and process from clients, updating the game statues
//update clent to send updates to all connected client about current agme state
//checkWin to ckeck for win conditions using the tictac toeboard methos
//checkDraw to ckeck for possible draw

